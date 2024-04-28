package app.bookstore.socialbookstore.services;

import java.util.Optional;
import app.bookstore.socialbookstore.domain.User;


public interface UserService {
	public void saveUser(User user);
	public boolean isUserPresent(User user);
	public Optional<User> getUser(String username);
}
