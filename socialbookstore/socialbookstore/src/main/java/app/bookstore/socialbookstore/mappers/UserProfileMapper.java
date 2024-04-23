package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.UserProfile;
import jakarta.transaction.Transactional;

@Repository
public interface UserProfileMapper extends JpaRepository<UserProfile, Integer>{
	
	@Query(
		value = "SELECT author_name FROM book_authors WHERE author_name "
				+ "NOT IN (SELECT author_name FROM user_profile_author WHERE user_profile_id = :myId)",
		nativeQuery = true)
	List<String> findOtherBookAuthors(@Param("myId") int myId);
	
	@Query(
	    value = "SELECT author_name FROM user_profile_author WHERE user_profile_id = :myId",
	    nativeQuery = true)
	List<String> findMyBookAuthors(@Param("myId") int myId);

	@Transactional
    @Modifying
    @Query(
        value = "DELETE FROM user_profile_author WHERE user_profile_id = :myId AND author_name = :authorName",
        nativeQuery = true)
    void removeMyBookAuthors(@Param("myId") int myId, @Param("authorName") String authorName);
	
	Optional<UserProfile> findByUsernameProfile(String username);
	Optional<UserProfile> findByFullName(String fullName);
	Optional<UserProfile> findByUsernameProfileAndUserProfileId(String username, int userProfileId);
	Optional<UserProfile> findByUserProfileId(int id);
}
