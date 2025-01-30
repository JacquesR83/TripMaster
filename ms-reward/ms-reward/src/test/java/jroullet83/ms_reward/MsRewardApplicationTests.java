package jroullet83.ms_reward;

import jroullet83.ms_reward.consumer.UserGateway;
import jroullet83.ms_reward.model.Location;
import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsRewardApplicationTests {

    @Autowired
    private UserGateway userGateway;

	@Test
	void contextLoads() {
	}

	@Test
	void UsergateWayTest() {
	Location location;
	location = userGateway.getUserLocation("internalUser77").getBody();
	assert location != null;

	}



}
