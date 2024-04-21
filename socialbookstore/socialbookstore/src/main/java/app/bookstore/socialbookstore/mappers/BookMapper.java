package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.Book;

@Repository
public interface BookMapper extends JpaRepository<Book, Integer>{
	
	List<Book> findByTitle(String title);	//Exact query.
	List<Book> findByTitleContaining(String title);	//Inexact query.
	List<Book> findByBookOwnerId(int id);
	Optional<Book> findByBookId(int id);
	
}
