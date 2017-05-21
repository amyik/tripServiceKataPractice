package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

public class TripServiceTest {

	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	private User loggedInUser;
	
	private TripService tripService;

	@Before
	public void init() {
		loggedInUser = REGISTERED_USER;
		tripService = new TestableTripService();
	}

	@Test(expected = UserNotLoggedInException.class) public void 
	should_throw_UserNotLoggedInException_when_loggedInUser_is_null() throws Exception {
		
		loggedInUser = GUEST;
		tripService.getTripsByUser(UNUSED_USER);
	}
	
	@Test public void 
	showld_not_return_any_trips_when_they_are_not_friedns() throws Exception {
		
		
		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addTrip(TO_BRAZIL);
		
		List<Trip> tripsByUser = tripService.getTripsByUser(friend);
		assertThat(tripsByUser.size(), is(0));
	}
	
	@Test public void
	sholud_return_frineds_trips_when_they_are_frineds() throws Exception {
		
		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addFriend(loggedInUser);
		friend.addTrip(TO_BRAZIL);
		friend.addTrip(TO_LONDON);
		
		List<Trip> tripsByUser = tripService.getTripsByUser(friend);
		assertThat(tripsByUser.size(), is(2));
	}
	
	public class TestableTripService extends TripService {

		@Override
		protected User getLoggedInUser() {
			return loggedInUser;
		}
		
		@Override
		protected List<Trip> tripsBy(User user) {
			return user.trips();
		}
	}
}
