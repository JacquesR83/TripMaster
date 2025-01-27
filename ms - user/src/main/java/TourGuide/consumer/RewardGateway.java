package TourGuide.consumer;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RewardGateway {

    private final RestTemplate restTemplate;

    // Constructor
    public RewardGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Methods to pin from gateway


}
