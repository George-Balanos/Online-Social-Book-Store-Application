package app.bookstore.socialbookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

@Service
public class UserProfileImpl implements UserProfileService{
	
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Override
	public void saveUserProfile(UserProfile userProfile) {
		userProfileMapper.save(userProfile);
	}
	
	@Override
    public Optional<UserProfile> findUserProfile(String username) {
        Optional<UserProfile> storedUser = userProfileMapper.findByUsernameProfile(username);
        return storedUser; 
    }
	
	@Override
    public Optional<UserProfile> findUserProfileById(int id) {
        Optional<UserProfile> storedUser = userProfileMapper.findByUserProfileId(id);
        return storedUser; 
    }

}