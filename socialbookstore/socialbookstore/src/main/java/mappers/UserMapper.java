package mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import socialbookstore.domain.User;

public interface UserMapper extends JpaRepository<User, Integer>{
	
	Optional<User> findByUsername(String username);
	Optional<User> findById(String username);
	
}
