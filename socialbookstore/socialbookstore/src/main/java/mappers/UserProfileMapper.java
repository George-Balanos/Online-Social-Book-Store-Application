package mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import socialbookstore.domain.UserProfile;

public interface UserProfileMapper extends JpaRepository<UserProfile, Integer>{
	
	Optional<UserProfile> findByUsername(String username);
	Optional<UserProfile> findByFullName(String fullName);
	Optional<UserProfile> findByUsernameProfileAndUserProfileId(String username, int userProfileId);
	
}
