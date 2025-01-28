//package TourGuide;
//
//
//import TourGuide.consumer.RewardGateway;
//import TourGuide.helper.InternalTestHelper;
//import org.junit.jupiter.api.Assertions;
//import org.springframework.util.StopWatch;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//
//import TourGuide.consumer.GpsGateway;
//import TourGuide.service.TourGuideService;
//import TourGuide.model.User;
//
//
//import java.util.*;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//class MSUserApplicationIT {
//
//	@Autowired
//	GpsGateway gpsGateway;
//	@Autowired
//	RewardGateway rewardGateway;
//	@Autowired
//	TourGuideService tourGuideService;
//
//	@Test
//	void contextLoads() {
//	}
//
//	@Test
//	public void highVolumeTrackLocation() {
//		System.out.println(Locale.getDefault());
//		Locale.setDefault(new Locale("en","US"));
//
//		//RewardGateway rewardGateway = new RewardGateway(gpsGateway, new RewardCentral());
//		// Users should be incremented up to 100,000, and test finishes within 15 minutes
//		InternalTestHelper.setInternalUserNumber(100);
//		TourGuideService tourGuideService = new TourGuideService(gpsGateway, rewardGateway);
//
//		List<User> allUsers = new ArrayList<>();
//		allUsers = tourGuideService.getAllUsers();
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
////		for(User user : allUsers) {
////			tourGuideService.trackUserLocation(user); // Field format error -> Locale.setDefault -> US
////			// Explanation : (Double parse change from , (in French numbers) to . (US numbers) (at latitude/longitude Position) / Otherwise, it's considered as a String)
////		}
//
//		// Replace with a parallelStream
//		allUsers.parallelStream().forEach(tourGuideService::trackUserLocation);
//
//		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();
//
////		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		Assertions.assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTotalTimeMillis()));
//
//	}
//
//}
