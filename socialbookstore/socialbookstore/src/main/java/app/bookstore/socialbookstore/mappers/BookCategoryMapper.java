package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.BookCategory;

@Repository
public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	
	Optional<BookCategory> findByCategoryName(String name);
	
	
	@Query(value="SELECT b.book_id FROM books b INNER JOIN user_profile_book_category u ON b.category_id = u.category_id "
			 + "WHERE u.user_profile_id = :userId",
			 nativeQuery=true)
	List<Integer> findByFavouriteCategories(@Param("userId") int userId);
	
}