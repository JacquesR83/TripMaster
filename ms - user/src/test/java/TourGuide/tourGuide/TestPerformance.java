//package TourGuide.tourGuide;
//
//import TourGuide.consumer.GpsGateway;
//import TourGuide.consumer.RewardGateway;
//import TourGuide.helper.InternalTestHelper;
//import TourGuide.model.Attraction;
//import TourGuide.model.User;
//import TourGuide.model.VisitedLocation;
//import TourGuide.service.TourGuideService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StopWatch;
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TestPerformance {
//
//    GpsGateway gpsGateway;
//
//	/*
//	 * A note on performance improvements:
//	 *
//	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
//	 *
//	 *     		InternalTestHelper.setInternalUserNumber(100000);
//	 *
//	 *
//	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
//	 *     at the end of the tests remains consistent.
//	 *
//	 *     These are performance metrics that we are trying to hit:
//	 *
//	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
//	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//     *
//     *     highVolumeGetRewards: 100,000 users within 20 minutes:
//	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	 */
//
//
//	@Test
//	public void highVolumeGetRewards() {
////		RewardGateway rewardGateway = new RewardGateway(gpsGateway, new RewardCentral());
//
//		// Users should be incremented up to 100,000, and test finishes within 20 minutes
////		InternalTestHelper.setInternalUserNumber(100);
//
//		// Counting time for the whole process
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		//Calls the constructor of TourGuideService
////		TourGuideService tourGuideService = new TourGuideService(gpsGateway, rewardsService);
//
//
//		// Gets First Attraction in a list of attractions
//	    Attraction attraction = gpsGateway.getAttractionById("0").getBody();
//        System.out.println(attraction);
//
//		//Generate a list of Users
////		List<User> allUsers = new ArrayList<>();
////		allUsers = tourGuideService.getAllUsers();
//
//
//		// Add visited locations to the field of users
////		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
////		allUsers.parallelStream().forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
//
//		//Calculate rewards for each user
////		StopWatch RewardCalculationTime = new StopWatch();
////		RewardCalculationTime.start();
////	    allUsers.forEach(u -> rewardsService.calculateRewards(u));
//
//
//		// Replace with a parallelStream for optimization
////	    allUsers.parallelStream().forEach(rewardsService::calculateRewards);
////		RewardCalculationTime.stop();
//
//		// ASSERTION if rewards were calculated and exist
////		for(User user : allUsers) {
////			assertTrue(user.getUserRewards().size() > 0);
////		}
//
//
//		// Stops tracker
////		tourGuideService.tracker.stopTracking();
//		stopWatch.stop();
//
////		System.out.println("calculateRewards_CLASS_FOR LOOP: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(RewardCalculationTime.getTotalTimeMillis()) + "seconds");
////		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()) + " seconds."); // Test results : For 500 users : 271 seconds => For 10^5 users : 55600 seconds = 15h27min
////		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()));
//	}
//
//}
