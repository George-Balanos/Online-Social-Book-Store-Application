package app.bookstore.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.BookCategory;

@Repository
public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	
	List<BookCategory> findByCategoryName(String name);
	
}