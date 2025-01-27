package Tourguide.consumerTest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestHelloController {

    // Creation d'un controlleur qui va se faire consommer par le gateway d'un autre service
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

}
