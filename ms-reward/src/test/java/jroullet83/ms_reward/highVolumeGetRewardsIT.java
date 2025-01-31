package jroullet83.ms_reward;

import jroullet83.ms_reward.consumer.GpsGateway;
import jroullet83.ms_reward.consumer.UserGateway;
import jroullet83.ms_reward.model.Attraction;
import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;
import jroullet83.ms_reward.service.RewardService;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rewardCentral.RewardCentral;


import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class highVolumeGetRewardsIT {


    @Autowired
    GpsGateway gpsGateway;
    @Autowired
    UserGateway userGateway;

    @Test
    void contextLoads() {
    }

    @Test
    public void highVolumeGetRewards() throws InterruptedException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Attraction[] attractions = gpsGateway.getAttractions().getBody();
        RewardService rewardService = new RewardService(gpsGateway, new RewardCentral(), userGateway, Arrays.stream(attractions).toList());

        // Users should be incremented up to 100,000, and test finishes within 20 minutes

        Attraction attraction = Arrays.stream(gpsGateway.getAttractions().getBody()).toList().get(0);
        List<User> allUsers = Arrays.stream(userGateway.getAllUsers().getBody()).toList();

        System.out.println(allUsers.size());
        // Multiple streams to potimize performance
        allUsers.parallelStream().forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
        // Multiple streams to potimize performance
        allUsers.parallelStream().forEach(u -> rewardService.calculateRewards(u, attractions));
        for (User user : allUsers) {
            //While calculating rewards, stops 200 ms
            while (user.getUserRewards().size() == 0) {
                TimeUnit.MILLISECONDS.sleep(200);
            }
        }
        stopWatch.stop();

        for (User user : allUsers) {
            Assertions.assertTrue(user.getUserRewards().size() > 0);
        }
        System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
        Assertions.assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
    }


}
