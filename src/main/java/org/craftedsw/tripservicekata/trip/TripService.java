package org.craftedsw.tripservicekata.trip;

import java.util.ArrayList;
import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.craftedsw.tripservicekata.user.UserSession;

public class TripService {

	public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
		
		if(getLoggedInUser() == null){
			throw new UserNotLoggedInException();
		}

		return user.isFriendWith(getLoggedInUser()) 
				? tripsBy(user)
				: noTrips();
	}

	public ArrayList<Trip> noTrips() {
		return new ArrayList<Trip>();
	}

	protected List<Trip> tripsBy(User user) {
		List<Trip> tripList;
		tripList = TripDAO.findTripsByUser(user);
		return tripList;
	}

	protected User getLoggedInUser() { // seam(연결점)을 만든다. seam : 클래스들이 이어지는/분리되는 지점
		User loggedUser = UserSession.getInstance().getLoggedUser();
		return loggedUser;
	}
	
}
