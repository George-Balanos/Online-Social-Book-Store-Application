package app.bookstore.socialbookstore.mappers;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.Request;

@Repository
public interface RequestMapper extends JpaRepository<Request, Integer>{
	
	List<Request> findByBorrowerId(int borrowerId);
	
}
