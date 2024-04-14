package app.bookstore.socialbookstore.services;

import java.util.Optional;

import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.domain.UserProfile;

public interface UserProfileService {
	public Optional<UserProfile> findUserProfile(String username);
	public Optional<UserProfile> findUserProfileById(int id);
	public void saveUserProfile(UserProfile userProfile);
}
