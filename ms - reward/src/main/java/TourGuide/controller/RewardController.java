package TourGuide.controller;

import TourGuide.model.UserReward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.RewardCentral;

import java.util.List;

@RestController
@RequestMapping("reward")
public class RewardController {

    @Autowired
    RewardCentral rewardCentral;

    public RewardController(RewardCentral rewardCentral) {
        this.rewardCentral = rewardCentral;
    }

    // Has to be sent to MS-GPS -> as it takes a User and a Visited Location
    // we will have to carry both of them back here
    // instead of doing calculation where one property belongs to


    //TODO: do it here first, then migrate it
    @GetMapping("/calculate-reward")
    public List<UserReward> calculateReward() {
        return rewardService.calculateRewards(user);
    }
}
