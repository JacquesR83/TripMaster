package jroullet83.ms_reward.controller;

import jroullet83.ms_reward.consumer.GpsGateway;
import jroullet83.ms_reward.consumer.UserGateway;
import jroullet83.ms_reward.model.Attraction;
import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;
import jroullet83.ms_reward.service.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RewardController {

    private final GpsGateway gpsGateway;
    private final RewardService rewardService;
    private final UserGateway userGateway;

    @Autowired
    public RewardController(RewardService rewardService, GpsGateway gpsGateway, UserGateway userGateway) {
        this.rewardService = rewardService;
        this.gpsGateway = gpsGateway;
        this.userGateway = userGateway;
    }

    // Has to be sent to MS-GPS -> as it takes a User and a Visited Location
    // we will have to carry both of them back here
    // instead of doing calculation where one property belongs to


    //TODO: do it here first, then migrate it to ms-gps (will need Json mixIn, to provide decompiled models (like "Attraction", "Location"...) to ms-gps
    @PostMapping("/reward")
    // Sends a CalculateRewardsDTO to the calculateRewards Method
    // DTO must be in the same MS to fulfill it
    // Change the method to return a user
    public User getRewards (@RequestBody CalculateRewardsDTO calculateRewardsDTO) {
        return rewardService.calculateRewards(calculateRewardsDTO.getUser(),calculateRewardsDTO.getVisitedLocation());
    }

    // Testing gateway
    @GetMapping("/attractions")
    public Attraction[] getAttractions() {
        return gpsGateway.getAttractions().getBody();
    }

    //Testing gateway
    @GetMapping("/users")
    public User[] getUsers() {
        return userGateway.getAllUsers().getBody();
    }

    //Testing  gateway
    @RequestMapping("/location/{userName}")
    public VisitedLocation getLocation(@PathVariable String userName) {
        System.out.println(userName);
        return null; //TODO
    }


//
//    @RequestMapping("/location/{id}")
//    public VisitedLocation getUserLocation(@PathVariable String id){
//        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(id));
//        // pas besoin du model Visited Location
//        // on appelle directement le service qui fournit une Visited Location
//        return visitedLocation;
//
//    }



}
