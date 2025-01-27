package TourGuide.consumerTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TestToShowIfRewardGateWayWorksRewardController {

    TestHelloGateWay testHelloGateWay = new TestHelloGateWay(new RestTemplate());

    // Affichage de si le rewardGateWay fonctionne (port 8083)
    // Inutile car on récupère l'information transmise depuis le TestHelloController, qu'on aurait eu avec un getMapping
    @GetMapping("/reward-hello-from-gps")
    public String getHello() {
        return testHelloGateWay.getHello();
    }
}
