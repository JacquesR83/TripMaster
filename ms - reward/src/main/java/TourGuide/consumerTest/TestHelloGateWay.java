package TourGuide.consumerTest;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TestHelloGateWay {

    private final RestTemplate restTemplate;

    public TestHelloGateWay(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getHello() {
        // Va appeler le Controlleur de l'autre microservice par une requête HTTP.
        // Le controlleur n'est pas à disposition dans ce microservice.
        String url = "http://localhost:8082/hello";
        return restTemplate.getForObject(url, String.class);
    }

}
