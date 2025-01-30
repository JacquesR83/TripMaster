package jroullet83.ms_user.consumer;

import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RewardGateway {

    private final RestTemplate restTemplate;

    // Constructor
    public RewardGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Methods to pin from Reward Controller
    public ResponseEntity<User> calculateRewards(User user, VisitedLocation visitedLocation) {
        CalculateRewardsDTO dto = new CalculateRewardsDTO(); // "dto" declaration
        dto.setUser(user);
        dto.setVisitedLocation(visitedLocation);
        // HttpEntity allows to bring "dto" object with the http request
        HttpEntity<CalculateRewardsDTO> httpEntity = new HttpEntity<>(dto);
        return restTemplate.postForEntity("http://localhost:8083/reward/",httpEntity, User.class);
    }

}
