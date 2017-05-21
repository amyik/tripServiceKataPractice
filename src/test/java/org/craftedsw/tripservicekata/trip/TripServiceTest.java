package org.craftedsw.tripservicekata.trip;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.isNotNull;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;

public class TripServiceTest {

	private User LOGGED_IN_USER;
	private User FRIEND;
	private User ANOTHER_USER;
	private static final Trip TO_SEOUL = new Trip();
	private static final Trip TO_HONGKONG = new Trip();
	
	private TripService tripService = new TestableTripService();
	
	@Before
	public void init(){
		LOGGED_IN_USER = new User();
		FRIEND = new User();
		ANOTHER_USER = new User();
		
		FRIEND.addTrip(TO_SEOUL);
		FRIEND.addTrip(TO_HONGKONG);
	}

	@Test(expected = UserNotLoggedInException.class) public void 
	should_throw_UserNotLoggedInException_when_loggedInUser_is_null() throws Exception {
		LOGGED_IN_USER = null;
		tripService.getTripsByUser(FRIEND);
	}
	
	@Test public void 
	sholud_not_return_exception_when_loggedInUser_is_not_null() throws Exception {
		
		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND);
		assertThat(tripsByUser, is(notNullValue()));
	}
	
	@Test public void 
	sholud_return_empty_tripList_when_they_are_not_frineds() throws Exception {
		
		FRIEND.addFriend(ANOTHER_USER);
		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND);
		
		assertThat(tripsByUser.isEmpty(), is(Boolean.valueOf(true)));
	}
	
	@Test public void 
	should_return_tripList_when_they_are_frineds() throws Exception {
		
		FRIEND.addFriend(LOGGED_IN_USER);
		FRIEND.addFriend(ANOTHER_USER);
		
		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND);
		assertThat("trip size 비교",tripsByUser.size(), is(2));
	}
	
	public class TestableTripService extends TripService {

		@Override
		protected User getLoggedInUser() {
			return LOGGED_IN_USER;
		}
		
		@Override
		protected List<Trip> findTripsByUser(User user) {
			return user.trips();
		}
	}
}
