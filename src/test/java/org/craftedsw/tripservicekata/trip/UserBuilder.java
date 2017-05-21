package org.craftedsw.tripservicekata.trip;

import org.craftedsw.tripservicekata.user.User;

public class UserBuilder {

	public static UserBuilder aUser() {

		return new UserBuilder();
	}

	private User[] friends = new User[]{};
	private Trip[] trips = new Trip[]{};

	public UserBuilder friendWith(User... friends) {

		this.friends = friends;
		return this;
	}

	public UserBuilder withTrips(Trip...trips) {
		
		this.trips = trips;
		return this;
	}

	public User build() {

		User user = new User();
		addTripsTo(user);
		addFriendsTo(user);
		return user;
	}

	private void addFriendsTo(User user) {

		for(User friend : this.friends){
			user.addFriend(friend);
		}
	}

	private void addTripsTo(User user) {

		for(Trip trip : this.trips){
			user.addTrip(trip);
		}
	}


}