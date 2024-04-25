package app.bookstore.socialbookstore.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.Book;

@Repository
public interface BookMapper extends JpaRepository<Book, Integer>{
	
	List<Book> findByTitle(String title);	//Exact query.
	List<Book> findByTitleContaining(String title);	//Inexact query.
	List<Book> findByBookOwnerId(int id);
	Optional<Book> findByBookId(int id);
	void deleteByBookId(int id);
	
	
	
	List<Book> findByBookAuthorsAuthorName(String authorName); //Exact query
	
	@Query("SELECT b FROM Book b JOIN b.bookAuthors ba WHERE ba.authorName LIKE %:authorName%")
	List<Book> findByBookAuthorsAuthorNameContaining(String authorName); //Inexact query
} 
