package TourGuide.controller;

import TourGuide.model.User;
import TourGuide.model.VisitedLocation;


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
