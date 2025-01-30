package jroullet83.ms_reward.controller;


import jroullet83.ms_reward.model.User;
import jroullet83.ms_reward.model.VisitedLocation;

public class CalculateRewardsDTO {

    private User user;
    private VisitedLocation visitedLocation;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public VisitedLocation getVisitedLocation() {
        return visitedLocation;
    }

    public void setVisitedLocation(VisitedLocation visitedLocation) {
        this.visitedLocation = visitedLocation;
    }
}
