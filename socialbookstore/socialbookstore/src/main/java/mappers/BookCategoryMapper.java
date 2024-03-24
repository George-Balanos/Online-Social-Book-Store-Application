package mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import socialbookstore.domain.BookCategory;

public interface BookCategoryMapper extends JpaRepository<BookCategory, Integer>{
	
	List<BookCategory> findByName(String name);
	
}
