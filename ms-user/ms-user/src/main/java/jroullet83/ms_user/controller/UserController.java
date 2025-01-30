package jroullet83.ms_user.controller;


import jroullet83.ms_user.consumer.GpsGateway;
import jroullet83.ms_user.consumer.RewardGateway;
import jroullet83.ms_user.model.Location;
import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;
import jroullet83.ms_user.service.TourGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    TourGuideService tourGuideService;
    @Autowired
    private GpsGateway gpsGateway;
    @Autowired
    private RewardGateway rewardGateway;

    // Get User Rewards



    //Get Visited Locations
    @GetMapping("/location")
    public Location getLocation(@RequestParam String userName) {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(tourGuideService.getUser(userName));
        // To get back the "location" field from visitedLocation
        return visitedLocation.location;
    }


    //Get All Users
    @GetMapping("/users")
    public List<User> getUsers(){
        TourGuideService tourGuideService = new TourGuideService(gpsGateway, rewardGateway);
        List<User> users = tourGuideService.getAllUsers();
        return users;
    }


//    @GetMapping("/user-information")
//    public ResponseEntity<UserDto> getUserInformationToDto(User user) {
//        // Getting User and Setting Dto
//        if (user == null) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Map user to dto
//        UserDto dto = new UserDto();
//        dto.setUserId(user.getUserId());
//        dto.setUserRewards(user.getUserRewards());
//        dto.setVisitedLocations(user.getVisitedLocations());
//        return ResponseEntity.ok(dto);
//    }
//
//    @GetMapping("/username")
//    public ResponseEntity<String> getUserName(@RequestParam User user) {
//        return ResponseEntity.ok(user.getUserName());
//    }



}
