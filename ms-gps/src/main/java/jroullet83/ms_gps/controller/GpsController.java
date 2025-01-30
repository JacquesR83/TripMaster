package jroullet83.ms_gps.controller;


import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import jroullet83.ms_gps.service.GpsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("gps")
public class GpsController {

    @Autowired
    GpsService gpsService;

    @GetMapping("/attractions")
    public List<Attraction> getAttractions() {
        return gpsService.getAttractions();
    }

    @GetMapping("/attractions/{id}")
    public Attraction getAttractionById(@PathVariable String id) {
        return gpsService.getAttractionById(UUID.fromString(id));
    }

    @RequestMapping("/location/{id}")
    public VisitedLocation getUserLocation(@PathVariable String id){
        VisitedLocation visitedLocation = gpsService.getUserLocation(UUID.fromString(id));
        // pas besoin du model Visited Location
        // on appelle directement le service qui fournit une Visited Location
        return visitedLocation;

    }
//
//    @GetMapping("/attractions/{attractionName}")
//    public ResponseEntity<Attraction> getAttractions(@PathVariable String attractionName) {
//        List<Attraction> attractions = gpsService.getAttractions();
//
//        Attraction attraction = attractions
//                .stream()
//                .filter(a -> attractionName.equals(a.attractionName))
//                .findFirst()
//                .orElse(null);
//
//        if(attraction == null){
//            return ResponseEntity.notFound().build();
//        }
//        else {
//            return ResponseEntity.ok(attraction);
//        }
//    }
//


}
