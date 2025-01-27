package Tourguide;

import TourGuide.consumer.GpsGateway;
import TourGuide.model.Attraction;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class highVolumeGetRewardsIT {

    @Autowired
    GpsGateway gpsGateway;

    @Test
    void contextLoads() {
    }

    @Test
    public void highVolumeGetRewards() {
        GpsGateway gpsGateway;

        RewardsService rewardsService = new RewardsService(gpsGateway, new RewardCentral());

        // Users should be incremented up to 100,000, and test finishes within 20 minutes
        InternalTestHelper.setInternalUserNumber(100);

        // Counting time for the whole process
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //Calls the constructor of TourGuideService
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);


        // Gets First Attraction in a list of attractions
        Attraction attraction = gpsGateway.getAttractions().get(0);


        //Generate a list of Users
        List<User> allUsers = new ArrayList<>();
        allUsers = tourGuideService.getAllUsers();


        // Add visited locations to the field of users
//		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
        allUsers.parallelStream().forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));

        //Calculate rewards for each user
        StopWatch RewardCalculationTime = new StopWatch();
        RewardCalculationTime.start();
//	    allUsers.forEach(u -> rewardsService.calculateRewards(u));


        // Replace with a parallelStream for optimization
        allUsers.parallelStream().forEach(rewardsService::calculateRewards);
        RewardCalculationTime.stop();

        // ASSERTION if rewards were calculated and exist
        for(User user : allUsers) {
            assertTrue(user.getUserRewards().size() > 0);
        }


        // Stops tracker
        tourGuideService.tracker.stopTracking();
        stopWatch.stop();

        System.out.println("calculateRewards_CLASS_FOR LOOP: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(RewardCalculationTime.getTime()) + "seconds");
        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds."); // Test results : For 500 users : 271 seconds => For 10^5 users : 55600 seconds = 15h27min
        assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }
}
