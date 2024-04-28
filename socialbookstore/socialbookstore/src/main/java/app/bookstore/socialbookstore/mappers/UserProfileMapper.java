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
	
	@Transactional
    @Modifying
    @Query(
        value = "DELETE FROM userprofile_book WHERE book_id = :bookId",
        nativeQuery = true)
    void removeBookRequest(@Param("bookId") int bookId);
	
	@Query(
			value = "SELECT category_name FROM book_categories WHERE category_id "
					+ "NOT IN (SELECT category_id FROM user_profile_book_category WHERE user_profile_id = :myId)",
			nativeQuery = true)
	List<String> findOtherBookCategories(@Param("myId") int myId);
	
	@Query(
		    value = "SELECT category_name FROM book_categories WHERE category_id IN"
		    		+ "(SELECT category_id FROM user_profile_book_category WHERE user_profile_id = :myId)",
		    nativeQuery = true)
	List<String> findMyBookCategories(@Param("myId") int myId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE upbc " +
	               "FROM user_profile_book_category upbc " +
	               "INNER JOIN book_categories bc ON upbc.category_id = bc.category_id " +
	               "WHERE upbc.user_profile_id = :myId AND bc.category_name = :categoryName",
	       nativeQuery = true)
	void removeMyBookCategories(@Param("myId") int myId, @Param("categoryName") String categoryName);
	
	@Query(
            value= "SELECT user_profile_id,books.book_id "
            	 + "FROM userprofile_book INNER JOIN books on books.book_id = userprofile_book.book_id "
            	 + "WHERE books.book_owner_id = :book_owner_id",
            nativeQuery = true)
    List<String> findUsersRequests(@Param("book_owner_id") int book_owner_id);
	
	
	@Query(
            value= "SELECT book_id, user_profile_id from userprofile_book where :bookId = book_id and :borrower_id <> user_profile_id",
            nativeQuery = true)
    List<String> findDeclinedUsers(@Param("bookId") int bookId, @Param("borrower_id") int borrowerId);
	
	@Query(
            value = "SELECT book_id, status FROM requests WHERE borrower_id = :borrowerId",
            nativeQuery = true)
    List<String> findReviewedRequests(@Param("borrowerId") int borrowerId);
	
	@Transactional
    @Modifying
    @Query(value = "DELETE FROM userprofile_book WHERE user_profile_id = :myId and book_id = :bookId",
           nativeQuery = true)
    void removeSingleBookRequest(@Param("myId") int myId, @Param("bookId") int bookId);
	
	@Transactional
    @Modifying
    @Query(value = "DELETE FROM requests WHERE book_id = :bookId AND book_id not in (SELECT book_id FROM books)",
           nativeQuery = true)
    void removeBookRequestAfterWithdrawal(@Param("bookId") int bookId);
	
	Optional<UserProfile> findByUsernameProfile(String username);
	Optional<UserProfile> findByFullName(String fullName);
	Optional<UserProfile> findByUsernameProfileAndUserProfileId(String username, int userProfileId);
	Optional<UserProfile> findByUserProfileId(int id);
}
