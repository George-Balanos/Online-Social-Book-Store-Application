package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;

@Repository
public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer>{
	
	Optional<BookAuthor> findByAuthorName(String name);
	List<BookAuthor> findByAuthorNameContaining(String name);
	
	@Query(value="SELECT b.book_id FROM books b INNER JOIN author_book ab ON b.book_id = ab.book_id "
		 + "INNER JOIN user_profile_author u ON ab.author_name = u.author_name "
		 + "WHERE u.user_profile_id = :userId",
		 nativeQuery=true)
	List<Integer> findByFavouriteAuthors(@Param("userId") int userId);
}
