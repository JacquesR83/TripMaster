package TourGuide.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserGateway {
    private final RestTemplate restTemplate;
    //Construct
    public UserGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Methods to pin from User MS-Controller


}
