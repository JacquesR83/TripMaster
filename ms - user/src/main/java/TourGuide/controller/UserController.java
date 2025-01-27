package TourGuide.controller;

import TourGuide.model.User;
import TourGuide.model.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    // Get User Rewards


    //Get Visited Locations


    int a = 0;

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
