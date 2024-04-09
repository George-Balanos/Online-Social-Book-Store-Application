package app.bookstore.socialbookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.mappers.UserMapper;

public class UserServiceImpl implements UserService, UserDetailsService{

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userMapper.findByUsername(username).orElseThrow(
			()-> new UsernameNotFoundException(
					String.format("USER_NOT_FOUND %s", username)
			));
	}

	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userMapper.save(user);
	}

	@Override
	public boolean isUserPresent(User user) {
		// TODO Auto-generated method stub
		Optional<User> storedUser = userMapper.findByUsername(user.getUsername());
		return storedUser.isPresent();
	}
	
}
