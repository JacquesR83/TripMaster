package jroullet83.ms_user.consumer;


import jroullet83.ms_user.model.User;
import jroullet83.ms_user.model.VisitedLocation;

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
