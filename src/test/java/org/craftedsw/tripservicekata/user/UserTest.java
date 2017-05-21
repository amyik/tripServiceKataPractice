package org.craftedsw.tripservicekata.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.craftedsw.tripservicekata.trip.UserBuilder;
import org.junit.Test;

public class UserTest {
	
	private static final User BOB = new User();
	private static final User TOM = new User();

	@Test public void
	should_return_false_when_they_are_not_friends() throws Exception {
		User aUser = UserBuilder.aUser()
							.friendWith(BOB)
							.build();
		
		assertThat(aUser.isFriendWith(TOM), is(false));
	}
	
	@Test
	public void 
	should_return_true_when_they_are_friends() throws Exception {
		User aUser = UserBuilder.aUser()
						.friendWith(BOB, TOM)
						.build();
		
		assertThat(aUser.isFriendWith(BOB), is(true));
		
	}

}
