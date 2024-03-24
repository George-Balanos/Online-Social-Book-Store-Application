package mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialbookstore.domain.BookAuthor;

public interface BookAuthorMapper extends JpaRepository<BookAuthor, Integer>{
	
	List<BookAuthor> findByName(String name);
	List<BookAuthor> findByNameContaining(String name);
	
}
