package TourGuide.service;


import TourGuide.consumer.GpsGateway;
import TourGuide.controller.CalculateRewardsDTO;
import TourGuide.model.*;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RewardService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsGateway gpsGateway;
	private final RewardCentral rewardCentral;
	
	public RewardService(GpsGateway gpsGateway, RewardCentral rewardCentral) {
		this.gpsGateway = gpsGateway;
		this.rewardCentral = rewardCentral;
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}

	public void calculateRewards(User user) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = (List<Attraction>) gpsGateway.getAttractions();

		// Awards for each attraction
		Set<String> rewardedAttractions = user.getUserRewards().stream()
				.map(r -> r.attraction.attractionName)
				.collect(Collectors.toSet());

		// Iterates on visited locations
		for (VisitedLocation visitedLocation : userLocations) {
			// Iterates on visited attractions
			for (Attraction attraction : attractions) {
				if (!rewardedAttractions.contains(attraction.attractionName)) {
					if (nearAttraction(visitedLocation, attraction)) {
						// Add reward to user and update Set of rewarded attractions
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						rewardedAttractions.add(attraction.attractionName);
					}
				}
			}
		}
	}

	public User calculateRewards(User user, VisitedLocation visitedLocation) {
		List<VisitedLocation> userLocations = user.getVisitedLocations();
		List<Attraction> attractions = (List<Attraction>) gpsGateway.getAttractions();

		// Awards for each attraction
		Set<String> rewardedAttractions = user.getUserRewards().stream()
				.map(r -> r.attraction.attractionName)
				.collect(Collectors.toSet());

		// Iterates on visited locations
		for (VisitedLocation visitedLocation2 : userLocations) {
			// Iterates on visited attractions
			for (Attraction attraction : attractions) {
				if (!rewardedAttractions.contains(attraction.attractionName)) {
					if (nearAttraction(visitedLocation2, attraction)) {
						// Add reward to user and update Set of rewarded attractions
						user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						rewardedAttractions.add(attraction.attractionName);
					}
				}
			}
		}
		return user;
	}


	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}


	private int getRewardPoints(Attraction attraction, User user) {
		return rewardCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}

	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

}
