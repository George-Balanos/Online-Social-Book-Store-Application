package app.bookstore.socialbookstore;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

@SpringBootApplication
public class SocialbookstoreApplication implements CommandLineRunner {
	
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
	
	@Override
	public void run(String... args) throws Exception {
		/*UserProfile user = new UserProfile("VC1", "Vaggelis1", 21);
	    userProfileMapper.save(user);
		
		System.out.println("Creating Author");
		BookAuthor author1 = new BookAuthor("VC1",null);
		
		List<BookAuthor> authors = new ArrayList<>();
		authors.add(author1);
		
		System.out.println("Creating Books");
		Book book1 = new Book("Algorithms1",authors,null);
		
		List<Book> books = new ArrayList<>();
		books.add(book1);
		
		UserProfile profile1 = new UserProfile("VC1","Vag1",21);
		
		author1.setBooks(books);
		book1.setBookAuthors(authors);
		profile1.setBookOffers(books);
		
		
		bookAuthorMapper.save(author1);
		bookMapper.save(book1);
		userProfileMapper.save(profile1);*/
	}

}
