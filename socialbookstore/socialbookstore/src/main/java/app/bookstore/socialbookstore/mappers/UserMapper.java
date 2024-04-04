package app.bookstore.socialbookstore.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import app.bookstore.socialbookstore.domain.User;

@Repository
public interface UserMapper extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	Optional<User> findById(String username);
	
}
