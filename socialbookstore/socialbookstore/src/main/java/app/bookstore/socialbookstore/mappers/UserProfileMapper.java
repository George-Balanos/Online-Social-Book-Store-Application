package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.domain.UserProfile;

@Repository
public interface UserProfileMapper extends JpaRepository<UserProfile, Integer>{
	
	@Query(
		value = "SELECT author_name FROM book_authors WHERE author_name "
				+ "NOT IN (SELECT author_name FROM user_profile_author WHERE user_profile_id = :myId)",
		nativeQuery = true)
	List<String> findOtherBookAuthors(@Param("myId") int myId);
	Optional<UserProfile> findByUsernameProfile(String username);
	Optional<UserProfile> findByFullName(String fullName);
	Optional<UserProfile> findByUsernameProfileAndUserProfileId(String username, int userProfileId);
	Optional<UserProfile> findByUserProfileId(int id);
}
