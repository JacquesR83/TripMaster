package TourGuide.consumer;


import TourGuide.model.Attraction;
import TourGuide.model.VisitedLocation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.UUID;


@Component
public class GpsGateway {

    private final RestTemplate restTemplate;

    //construct
    public GpsGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Returns Attractions from gpsController's address, the Attraction model must be declared in current service
    public ResponseEntity<Attraction[]> getAttractions(){
        return restTemplate.getForEntity("http://localhost:8082/gps/attractions", Attraction[].class);
    }

    // Visited location must be declared in the model, as Attraction, as Location
    public ResponseEntity<VisitedLocation> getUserLocation(UUID id) {
        return restTemplate.getForEntity("http://localhost:8082/gps/location/{uuid}", VisitedLocation.class, id.toString());
    }



}