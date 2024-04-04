package app.bookstore.socialbookstore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

@SpringBootApplication
public class SocialbookstoreApplication {
	
	@Autowired
	private BookAuthorMapper bookAuthorMapper;
	
	@Autowired
	private BookCategoryMapper bookCategoryMapper;
	
	@Autowired
	private BookMapper bookMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private UserProfileMapper userProfileMapper;
	

	public static void main(String[] args) {
		SpringApplication.run(SocialbookstoreApplication.class, args);
	}

}
