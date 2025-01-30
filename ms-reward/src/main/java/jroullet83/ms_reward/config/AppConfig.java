package jroullet83.ms_reward.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import rewardCentral.RewardCentral;

@Configuration
public class AppConfig {

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    RewardCentral getRewardCentral() {
        return new RewardCentral();
    }
}
