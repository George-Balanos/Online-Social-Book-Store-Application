package mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialbookstore.domain.Book;

public interface BookMapper extends JpaRepository<Book, Integer>{
	
	List<Book> findByTitle(String title);	//Exact query.
	List<Book> findByTitleContaining(String title);	//Inexact query.
	
}
