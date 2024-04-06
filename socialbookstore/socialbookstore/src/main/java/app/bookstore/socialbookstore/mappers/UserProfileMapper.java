package app.bookstore.socialbookstore.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.UserProfile;

@Repository
public interface UserProfileMapper extends JpaRepository<UserProfile, Integer>{
	
	Optional<UserProfile> findByUsernameProfile(String username);
	Optional<UserProfile> findByFullName(String fullName);
	Optional<UserProfile> findByUsernameProfileAndUserProfileId(String username, int userProfileId);
	
}
