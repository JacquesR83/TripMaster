package jroullet83.ms_reward;

import jroullet83.ms_reward.consumer.GpsGateway;
import jroullet83.ms_reward.consumer.UserGateway;
import jroullet83.ms_reward.model.Attraction;
import jroullet83.ms_reward.model.Location;
import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;
import jroullet83.ms_reward.service.RewardService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MsRewardApplicationTests {

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private GpsGateway gpsGateway;

	@Test
	void contextLoads() {
	}

	@Test
	void userGatewayLocationTest() {
	Location location;
	location = userGateway.getUserLocation("internalUser77").getBody();
	assert location != null;
	}

	@Test
	void userGatewayGetAllUsersTest() {
		User[] users = userGateway.getAllUsers().getBody();
		assert users != null;
	}

	@Test
	void gpsGatewayAttractionsTest() {
		Attraction[] attractions = gpsGateway.getAttractions().getBody();
		assert attractions != null;
	}



}
