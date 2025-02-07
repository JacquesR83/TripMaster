package jroullet83.ms_user.service;



import jroullet83.ms_user.consumer.GpsGateway;
import jroullet83.ms_user.consumer.RewardGateway;
import jroullet83.ms_user.helper.InternalTestHelper;
import jroullet83.ms_user.model.Location;
import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;
import jroullet83.ms_user.tracker.Tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TourGuideService {
	private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
	private final GpsGateway gpsGateway;
	private final RewardGateway rewardGateway;
	private final TripPricer tripPricer = new TripPricer();
	public final Tracker tracker;
	boolean testMode = true;
	ExecutorService executorService = Executors.newFixedThreadPool(200);

	public TourGuideService(GpsGateway gpsGateway, RewardGateway rewardGateway) {
		this.gpsGateway = gpsGateway;
		this.rewardGateway = rewardGateway;
		
		if(testMode) {
			logger.info("TestMode enabled");
			logger.debug("Initializing users");
			initializeInternalUsers();
			logger.debug("Finished initializing users");
		}
		tracker = new Tracker(this);
		addShutDownHook();
	}

	
	public User getUser(String userName) {
		return internalUserMap.get(userName);
	}
	
	public List<User> getAllUsers() {
		return internalUserMap.values().stream().collect(Collectors.toList());
	}
	
	public User addUser(User user) {
		if(!internalUserMap.containsKey(user.getUserName())) {
			internalUserMap.put(user.getUserName(), user);
		}
		return user;
	}
	
	public List<Provider> getTripDeals(User user) {
		int cumulatativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
		List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(), 
				user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulatativeRewardPoints);
		user.setTripDeals(providers);
		return providers;
	}

	public VisitedLocation getUserLocation(User user) {
		VisitedLocation visitedLocation = (user.getVisitedLocations().size()>0) ?
				user.getLastVisitedLocation() :
				trackUserLocation(user).join();
		return visitedLocation;
	}



	// Completable Future -> opens a new thread and executes all tasks
	// Each instruction is run  1 by 1
	//Completable future manage complex asynchronous tasks or cross-depending calculations series: chains tasks and manage dependencies between them
	public CompletableFuture<VisitedLocation> trackUserLocation(User user) {
		CompletableFuture.supplyAsync(() -> {
			VisitedLocation visitedLocation = gpsGateway.getUserLocation(user.getUserId()).getBody();
			user.addToVisitedLocations(visitedLocation);
			rewardGateway.calculateRewards(user, visitedLocation);
			return visitedLocation;
		}, executorService); // ExecutorService is a pool of threads -> main app keeps going
		return null;
	}



	// Permits to stop the test if calculation takes too long
	public void shutdown() throws InterruptedException {

		executorService.shutdown();
		try {
			if(!executorService.awaitTermination(15, TimeUnit.MINUTES)){

				executorService.shutdownNow();
			}
		}catch (InterruptedException e){
			e.printStackTrace();
			executorService.shutdownNow();
		}

	}



	private void addShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() { 
		      public void run() {
		        tracker.stopTracking();
		      } 
		    }); 
	}
	
	/**********************************************************************************
	 * 
	 * Methods Below: For Internal Testing
	 * 
	 **********************************************************************************/
	private static final String tripPricerApiKey = "test-server-api-key";
	// Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
	private final Map<String, User> internalUserMap = new HashMap<>();
	private void initializeInternalUsers() {
		IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
			String userName = "internalUser" + i;
			String phone = "000";
			String email = userName + "@tourGuide.com";
			User user = new User(UUID.randomUUID(), userName, phone, email);
//			generateUserLocationHistory(user);
			internalUserMap.put(userName, user);
		});
		logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
	}
	
	private void generateUserLocationHistory(User user) {
		IntStream.range(0, 3).forEach(i-> {
			user.addToVisitedLocations(new VisitedLocation(user.getUserId(), new Location(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
		});
	}
	
	private double generateRandomLongitude() {
		double leftLimit = -180;
	    double rightLimit = 180;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private double generateRandomLatitude() {
		double leftLimit = -85.05112878;
	    double rightLimit = 85.05112878;
	    return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
	}
	
	private Date getRandomTime() {
		LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
	    return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
	}
	
}
