package org.craftedsw.tripservicekata.trip;

import static org.craftedsw.tripservicekata.trip.UserBuilder.aUser;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;
import static org.mockito.BDDMockito.*;

import java.util.List;

import org.craftedsw.tripservicekata.exception.UserNotLoggedInException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {

	private static final User GUEST = null;
	private static final User UNUSED_USER = null;
	private static final User REGISTERED_USER = new User();
	private static final User ANOTHER_USER = new User();
	private static final Trip TO_BRAZIL = new Trip();
	private static final Trip TO_LONDON = new Trip();
	
	@Mock private TripDAO tripDAO;
	
	@InjectMocks @Spy private TripService tripService = new TripService();

	@Test(expected = UserNotLoggedInException.class) public void 
	should_throw_UserNotLoggedInException_when_loggedInUser_is_null() throws Exception {
		tripService.getFriendTrips(UNUSED_USER, GUEST);
	}
	
	@Test public void 
	showld_not_return_any_trips_when_they_are_not_friedns() throws Exception {
		User friend = aUser()
							.friendWith(ANOTHER_USER)
							.withTrips(TO_BRAZIL)
							.build();
		
		List<Trip> tripsByUser = tripService.getFriendTrips(friend, REGISTERED_USER);
		assertThat(tripsByUser.size(), is(0));
	}
	
	@Test public void
	sholud_return_frineds_trips_when_they_are_frineds() throws Exception {
		User friend = aUser()
							.friendWith(ANOTHER_USER, REGISTERED_USER)
							.withTrips(TO_BRAZIL, TO_LONDON)
							.build();
		
		given(tripDAO.tripsByUser(friend)).willReturn(friend.trips());
		
		List<Trip> tripsByUser = tripService.getFriendTrips(friend, REGISTERED_USER);
		assertThat(tripsByUser.size(), is(2));
	}
}
