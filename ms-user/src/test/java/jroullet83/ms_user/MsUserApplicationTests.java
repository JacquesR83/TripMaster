package jroullet83.ms_user;

import jroullet83.ms_user.consumer.GpsGateway;
import jroullet83.ms_user.consumer.RewardGateway;
import jroullet83.ms_user.helper.InternalTestHelper;
import jroullet83.ms_user.model.Attraction;
import jroullet83.ms_user.model.Location;
import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;
import jroullet83.ms_user.service.TourGuideService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;

import java.util.*;

import java.util.concurrent.TimeUnit;

import static java.util.UUID.randomUUID;

@SpringBootTest
class MsUserApplicationTests {

	@Autowired
	GpsGateway gpsGateway;
	@Autowired
	RewardGateway rewardGateway;
	@Autowired
	TourGuideService tourGuideService;

	@Test
	void contextLoads() {
	}

	@Test
	public void getUserLocationExistsTest() {
		UUID userId = randomUUID();
		ResponseEntity<VisitedLocation> visitedLocationTest = gpsGateway.getUserLocation(userId);
		Assertions.assertNotNull(visitedLocationTest.getBody());
	}

	@Test
	public void getUserLocationEqualityTest() throws InterruptedException {
		User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
		Attraction attraction2 = gpsGateway.getAttractions().getBody()[0];
		user.addToVisitedLocations(new VisitedLocation(user.getUserId(), attraction2, new Date()));
		List<VisitedLocation> visitedLocations = user.getVisitedLocations();
		Assertions.assertTrue(visitedLocations.get(0).userId.equals(user.getUserId()));

	}


	@Test
	public void highVolumeTrackLocation2() {
		System.out.println(Locale.getDefault());
		Locale.setDefault(new Locale("en","US"));

		//RewardGateway rewardGateway = new RewardGateway(gpsGateway, new RewardCentral());
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(100000);
		System.out.println(InternalTestHelper.getInternalUserNumber());
		tourGuideService = new TourGuideService(gpsGateway, rewardGateway);

		List<User> allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
//		for(User user : allUsers) {
//			tourGuideService.trackUserLocation(user); // Field format error -> Locale.setDefault -> US
//			// Explanation : (Double parse change from , (in French numbers) to . (US numbers) (at latitude/longitude Position) / Otherwise, it's considered as a String)
//		}

		// Replace with a parallelStream
		allUsers.parallelStream().forEach(tourGuideService::trackUserLocation);

		stopWatch.stop();
		tourGuideService.tracker.stopTracking();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()) + " seconds.");
		Assertions.assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()));
	}

	@Test
	public void highVolumeTrackLocation() throws InterruptedException {
//		Locale.setDefault(new Locale("en", "US"));
		// Users should be incremented up to 100,000, and test finishes within 15 minutes
		InternalTestHelper.setInternalUserNumber(10000);
		System.out.println(InternalTestHelper.getInternalUserNumber());
		TourGuideService tourGuideService = new TourGuideService(gpsGateway, rewardGateway);

		List<User> allUsers = tourGuideService.getAllUsers();

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		// allUser elements calculation is done in parallel (Java uses a pool of threads (ForkJoinPool) to process each element of User's collection)
		// parallelise le traitement d'une collection
		allUsers.parallelStream().forEach(u -> tourGuideService.trackUserLocation(u));
		tourGuideService.shutdown();
		stopWatch.stop();

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()) + " seconds.");
		Assertions.assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()));
	}

}
