package jroullet83.ms_user.model;

import java.util.List;
import java.util.UUID;

public class UserDto {

    private UUID userId;
    private List<UserReward> userRewards;
    private List<VisitedLocation> visitedLocations;


    public UserDto() {

    }

    public List<UserReward> getUserRewards() {
        return userRewards;
    }

    public void setUserRewards(List<UserReward> userRewards) {
        this.userRewards = userRewards;
    }

    public List<VisitedLocation> getVisitedLocations() {
        return visitedLocations;
    }

    public void setVisitedLocations(List<VisitedLocation> visitedLocations) {
        this.visitedLocations = visitedLocations;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UserDto(List<UserReward> userRewards, List<VisitedLocation> visitedLocations, UUID userId) {
        this.userRewards = userRewards;
        this.visitedLocations = visitedLocations;
        this.userId = userId;
    }
}
