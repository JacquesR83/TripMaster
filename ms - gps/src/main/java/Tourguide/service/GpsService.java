package Tourguide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GpsService {

    private final GpsUtil gpsUtil;

    public GpsService(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }

    public VisitedLocation getUserLocation(UUID id) {

        return gpsUtil.getUserLocation(id);
    }

    public List<Attraction> getAttractions(){

        return gpsUtil.getAttractions();
    }

    public Attraction getAttractionById(UUID id){
        return (Attraction) gpsUtil.getAttractions().stream().filter(attraction -> id.equals(attraction.attractionId)).collect(Collectors.toList());
    }



}
