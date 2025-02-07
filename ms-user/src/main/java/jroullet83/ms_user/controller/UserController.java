package jroullet83.ms_user.controller;


import jroullet83.ms_user.consumer.GpsGateway;
import jroullet83.ms_user.consumer.RewardGateway;
import jroullet83.ms_user.model.Location;
import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;
import jroullet83.ms_user.service.TourGuideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/visitedlocations")
    public List<VisitedLocation> getVisitedLocations(@RequestParam String userName) {
        User user = tourGuideService.getUser(userName);
        return user.getVisitedLocations();
    }

    @PostMapping("/visitedlocations/add")
    public VisitedLocation createVisitedLocation(@RequestParam String userName, @RequestBody VisitedLocation visitedLocation) {
        User user = tourGuideService.getUser(userName);
        user.addToVisitedLocations(visitedLocation);
        return visitedLocation;
    }



    //Get All Users
    @GetMapping("/users")
    public List<User> getUsers(){
        //TourGuideService tourGuideService = new TourGuideService(gpsGateway, rewardGateway);
        List<User> users = tourGuideService.getAllUsers();
        return users;
    }

    //Add User
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return new ResponseEntity<>(tourGuideService.addUser(user), HttpStatus.CREATED);
    }

    //Todo test : Add visited location to user and return this visited location when getLocation is called, to get lastvisitedLocation
    //Todo test : Add visited location to user and return this visited location when getLocation is called, to get lastvisitedLocation

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
