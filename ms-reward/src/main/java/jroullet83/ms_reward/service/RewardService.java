package jroullet83.ms_reward.service;



import jroullet83.ms_reward.consumer.GpsGateway;
import jroullet83.ms_reward.consumer.UserGateway;
import jroullet83.ms_reward.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class RewardService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private final UserGateway userGateway;
	private final GpsGateway gpsGateway;
	private final RewardCentral rewardCentral;
	private final List<Attraction> attractions;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private ExecutorService executorService = Executors.newFixedThreadPool(1000);



	public RewardService(GpsGateway gpsGateway, RewardCentral rewardCentral, UserGateway userGateway, List<Attraction> attractions) {
		this.gpsGateway = gpsGateway;
		this.rewardCentral = rewardCentral;
		this.userGateway = userGateway;
        this.attractions = attractions;
    }
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
//		List<VisitedLocation> userLocations = userGateway.getVisitedLocations();
		List<Attraction> attractions = (List<Attraction>) gpsGateway.getAttractions();

		// Awards for each attraction
		Set<String> rewardedAttractions = user.getUserRewards().stream()
				.map(r -> r.attraction.attractionName)
				.collect(Collectors.toSet());

		// Iterates on visited locations
		for (VisitedLocation visitedLocation : userLocations) {
			// Iterates on visited attractions
			for (Attraction attraction : attractions) {
				if (!rewardedAttractions.contains(attraction.attractionName)) {
					if (nearAttraction(visitedLocation, attraction)) {
						// Add reward to user and update Set of rewarded attractions
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						rewardedAttractions.add(attraction.attractionName);
					}
				}
			}
		}
	}

	public User calculateRewards(User user, VisitedLocation visitedLocation) {

		List<Attraction> attractions = Arrays.stream(gpsGateway.getAttractions().getBody())
				// gets already rewarded attractions appart
				.filter(attraction -> userHasNotReward(user.getUserRewards(),attraction))
				.collect(Collectors.toList());
				// checks remaining attractions and give rewards
		attractions.parallelStream()
				.filter(a-> nearAttraction(visitedLocation, a))
				.findFirst()
				.ifPresent(a-> user.addUserReward(new UserReward(visitedLocation, a, getRewardPoints(a, user))));

		return user;
		// Previous Code
		//		List<VisitedLocation> userLocations = user.getVisitedLocations();
//		List<Attraction> attractions = (List<Attraction>) gpsGateway.getAttractions();
//		// Awards for each attraction
//		Set<String> rewardedAttractions = user.getUserRewards().stream()
//				.map(r -> r.attraction.attractionName)
//				.collect(Collectors.toSet());
//
//		// Iterates on visited locations
//		for (VisitedLocation visitedLocation2 : userLocations) {
//			// Iterates on visited attractions
//			for (Attraction attraction : attractions) {
//				if (!rewardedAttractions.contains(attraction.attractionName)) {
//					if (nearAttraction(visitedLocation2, attraction)) {
//						// Add reward to user and update Set of rewarded attractions
//						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
//						rewardedAttractions.add(attraction.attractionName);
//					}
//				}
//			}
//		}
//		return user;
	}


	public User calculateRewards(User user, Attraction[] attractions) {

		// Get Locations by user
		List<VisitedLocation> userLocations = new ArrayList<>(user.getVisitedLocations());

		// Get Attractions without rewards by user
		List<Attraction> attractions2 = Arrays.stream(attractions)
				// gets already rewarded attractions appart
				.filter(attraction -> userHasNotReward(user.getUserRewards(),attraction))
				.collect(Collectors.toList());

		// Checking user went near attraction
		List<Pair<VisitedLocation, Attraction>> collection = userLocations.stream().flatMap(l->attractions2.parallelStream().map(a->Pair.of(l,a))
						.filter(p-> nearAttraction(p.getLeft(),p.getRight()))).collect(Collectors.toList());

		// Rewarding users
		// Completable Future -> permits running asynchronous and keep calculating rewards while updating user
		CompletableFuture.runAsync(() ->
		{
			updateUser(user, collection);
			// Executor Service
		}, executorService);

		return user;
	}

	private void updateUser (User user, List<Pair<VisitedLocation, Attraction>> collection) {
		collection.parallelStream()
				.forEach(p->user.addUserReward(new UserReward(p.getLeft(),p.getRight(), getRewardPoints(p.getRight(),user))));
		userGateway.updateUser(user);
	}


	private boolean userHasNotReward(List<UserReward> userRewards, Attraction attraction) {
		return userRewards.stream().noneMatch(r -> r.attraction.attractionName.equals(attraction.attractionName));
	}

	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}

	private int getRewardPoints(Attraction attraction, User user) {
		return rewardCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

}
