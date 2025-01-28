package Tourguide.consumer;

import Tourguide.model.UserDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class UserGateway {

    private final RestTemplate restTemplate;

    public UserGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


//    public ResponseEntity<UserDto> getUserInformation() {
//        String url = "http://localhost:8084/user-information";
//        UserDto userDto = restTemplate.getForObject(url, UserDto.class);
//        return new ResponseEntity<>(userDto, HttpStatus.OK);
//    }


}
