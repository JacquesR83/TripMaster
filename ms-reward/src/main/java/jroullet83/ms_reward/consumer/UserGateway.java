package jroullet83.ms_reward.consumer;


import jroullet83.ms_reward.model.Location;
import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<User[]> getAllUsers() {
        return restTemplate.getForEntity("http://localhost:8084/user/users", User[].class);
    }

    public ResponseEntity<Location> getUserLocation(String userName){
        return restTemplate.getForEntity("http://localhost:8084/user/location?userName={userName}", Location.class, userName);
    }


    public ResponseEntity<Void> updateUser(User user) {
        HttpEntity<User> request = new HttpEntity<>(user);
        return restTemplate.exchange("http://localhost:8084/user/update" + user.getUserName(), HttpMethod.PUT, request, Void.class);
    }
}
