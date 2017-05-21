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
		tripService = new TestableTripService();
	}

	@Test(expected = UserNotLoggedInException.class) public void 
	should_throw_UserNotLoggedInException_when_loggedInUser_is_null() throws Exception {
		
		loggedInUser = GUEST;
		tripService.getTripsByUser(UNUSED_USER);
	}
	
	@Test public void 
	showld_not_return_any_trips_when_they_are_not_friedns() throws Exception {
		
		loggedInUser = REGISTERED_USER;
		
		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addTrip(TO_BRAZIL);
		
		List<Trip> tripsByUser = tripService.getTripsByUser(friend);
		assertThat(tripsByUser.size(), is(0));
	}
	
	@Test public void
	sholud_return_frineds_trips_when_they_are_frineds() throws Exception {
		
		loggedInUser = REGISTERED_USER;
		
		User friend = new User();
		friend.addFriend(ANOTHER_USER);
		friend.addFriend(loggedInUser);
		friend.addTrip(TO_BRAZIL);
		friend.addTrip(TO_LONDON);
		
		List<Trip> tripsByUser = tripService.getTripsByUser(friend);
		assertThat(tripsByUser.size(), is(2));
	}
	
//	
//	@Test public void 
//	sholud_not_return_exception_when_loggedInUser_is_not_null() throws Exception {
//		
//		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND);
//		assertThat(tripsByUser, is(notNullValue()));
//	}
//	
//	@Test public void 
//	sholud_return_empty_tripList_when_they_are_not_frineds() throws Exception {
//		
//		assertThat(ANOTHER_USER.getFriends().contains(LOGGED_IN_USER), is(false));
//		List<Trip> tripsByUser = tripService.getTripsByUser(ANOTHER_USER);
//		assertThat(tripsByUser.isEmpty(), is(Boolean.valueOf(true)));
//	}
//	
//	@Test public void 
//	sholud_return_empty_tripList_when_frined_has_not_trips() throws Exception {
//		
//		assertThat(FRIEND_NOTRIPS.getFriends().contains(LOGGED_IN_USER), is(true));
//		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND_NOTRIPS);
//		assertThat(tripsByUser.isEmpty(), is(Boolean.valueOf(true)));
//	}
//	
//	@Test public void 
//	should_return_tripList_when_they_are_frineds() throws Exception {
//		
//		assertThat(FRIEND_NOTRIPS.getFriends().contains(LOGGED_IN_USER), is(true));
//		List<Trip> tripsByUser = tripService.getTripsByUser(FRIEND);
//		assertThat(tripsByUser.size(), is(2));
//	}
//	
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
