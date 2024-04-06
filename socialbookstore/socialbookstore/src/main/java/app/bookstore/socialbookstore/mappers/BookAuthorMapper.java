package app.bookstore.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.BookAuthor;

@Repository
public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer>{
	
	List<BookAuthor> findByAuthorName(String name);
	List<BookAuthor> findByAuthorNameContaining(String name);
	
}
