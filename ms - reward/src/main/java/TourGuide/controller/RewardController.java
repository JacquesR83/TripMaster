package TourGuide.controller;

import TourGuide.consumer.GpsGateway;
import TourGuide.model.Attraction;
import TourGuide.model.User;
import TourGuide.model.UserReward;
import TourGuide.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RewardController {

    private final GpsGateway gpsGateway;
    private final RewardService rewardService;

    @Autowired
    public RewardController(RewardService rewardService, GpsGateway gpsGateway) {
        this.rewardService = rewardService;
        this.gpsGateway = gpsGateway;
    }

    // Has to be sent to MS-GPS -> as it takes a User and a Visited Location
    // we will have to carry both of them back here
    // instead of doing calculation where one property belongs to


    //TODO: do it here first, then migrate it
    @PostMapping("/reward")
    // Sends a CalculateRewardsDTO to the calculateRewards Method
    // DTO must be in the same MS to fulfill it
    // Change the method to return a user
    public User getRewards (@RequestBody CalculateRewardsDTO calculateRewardsDTO) {
        return rewardService.calculateRewards(calculateRewardsDTO.getUser(),calculateRewardsDTO.getVisitedLocation());
    }

    @GetMapping("/attractions")
    public Attraction[] getAttractions() {
        return gpsGateway.getAttractions().getBody();
    }


}
