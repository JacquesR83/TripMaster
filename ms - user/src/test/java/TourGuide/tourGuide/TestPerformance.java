//package TourGuide.tourGuide;
//
//import gpsUtil.GpsUtil;
//import gpsUtil.location.Attraction;
//import gpsUtil.location.VisitedLocation;
//import org.apache.commons.lang3.time.StopWatch;
//import org.junit.Test;
//import rewardCentral.RewardCentral;
//import tourGuide.helper.InternalTestHelper;
//import tourGuide.service.RewardsService;
//import tourGuide.service.TourGuideService;
//import tourGuide.user.User;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertTrue;
//
//public class TestPerformance {
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
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//
//		// Users should be incremented up to 100,000, and test finishes within 20 minutes
//		InternalTestHelper.setInternalUserNumber(100);
//
//		// Counting time for the whole process
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//
//		//Calls the constructor of TourGuideService
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//
//
//		// Gets First Attraction in a list of attractions
//	    Attraction attraction = gpsUtil.getAttractions().get(0);
//
//
//		//Generate a list of Users
//		List<User> allUsers = new ArrayList<>();
//		allUsers = tourGuideService.getAllUsers();
//
//
//		// Add visited locations to the field of users
////		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
//		allUsers.parallelStream().forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
//
//		//Calculate rewards for each user
//		StopWatch RewardCalculationTime = new StopWatch();
//		RewardCalculationTime.start();
////	    allUsers.forEach(u -> rewardsService.calculateRewards(u));
//
//
//		// Replace with a parallelStream for optimization
//	    allUsers.parallelStream().forEach(rewardsService::calculateRewards);
//		RewardCalculationTime.stop();
//
//		// ASSERTION if rewards were calculated and exist
//		for(User user : allUsers) {
//			assertTrue(user.getUserRewards().size() > 0);
//		}
//
//
//		// Stops tracker
//		tourGuideService.tracker.stopTracking();
//		stopWatch.stop();
//
//		System.out.println("calculateRewards_CLASS_FOR LOOP: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(RewardCalculationTime.getTime()) + "seconds");
//		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); // Test results : For 500 users : 271 seconds => For 10^5 users : 55600 seconds = 15h27min
//		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
//
//}
