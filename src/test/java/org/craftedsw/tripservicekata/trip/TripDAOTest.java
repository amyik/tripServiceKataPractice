package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.exception.CollaboratorCallException;
import org.craftedsw.tripservicekata.user.User;
import org.junit.Test;

public class TripDAOTest {
	
	@Test(expected = CollaboratorCallException.class) public void 
	should_throw_CollaboratorCallException_when_retriveing_findTripsByUser_method() throws Exception {
		
		new TripDAO().tripsByUser(new User());
	}

}
