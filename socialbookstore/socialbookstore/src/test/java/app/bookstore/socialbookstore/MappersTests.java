package app.bookstore.socialbookstore;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.domain.BookCategory;
import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;



@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MappersTests {

    @Autowired
    private UserProfileMapper userProfileMapper;

    private UserProfile userProfile;
    
    @Autowired
    private BookMapper bookMapper;
    
    private Book book;
    
    @Autowired
    private BookAuthorMapper bookAuthorMapper;
    
    private BookAuthor bookAuthor;
    
    @Autowired
    private BookCategoryMapper bookCategoryMapper;
    
    private BookCategory bookCategory;
    
    @Autowired
    private UserMapper userMapper;
    
    private User user;

    @Before
    public void setUp() {
        userProfile = new UserProfile("Mpal", "George Mpal", 21);
        userProfile.setUserProfileId(1);
        
        bookAuthor = new BookAuthor("VC",null);
        bookCategory = new BookCategory("Sci-fi",null);
        
        
        List<BookAuthor> bookAuthors = new ArrayList<>();
        List<Book> books = new ArrayList<>();
       
        
        book = new Book("Matrix",bookAuthors,bookCategory);
        books.add(book);
        
        bookAuthor.setBooks(books);
        bookCategory.setBooksInCategory(books);
        
        bookAuthors.add(bookAuthor);
        
        user = new User();
        user.setUsername("ZAS");
     
        userMapper.save(user);
        bookMapper.save(book);
        bookAuthorMapper.save(bookAuthor);
        bookCategoryMapper.save(bookCategory);
        userProfileMapper.save(userProfile);
    }

    @Test
    public void testSaveUserProfile() {
        Optional<UserProfile> userProfileOptional = userProfileMapper.findByFullName("George Mpal");
        assertTrue(userProfileOptional.isPresent()); // Check if UserProfile is present
        
        UserProfile userProfile = userProfileOptional.get();
        assertEquals("George Mpal", userProfile.getFullName()); // Compare the full name
        assertEquals(21, userProfile.getUserAge());
        
    }
    
    @Test
    public void testDeleteUserProfile() {
        Optional<UserProfile> userProfileOptional = userProfileMapper.findByFullName("George Mpal");
        assertTrue(userProfileOptional.isPresent());
        
        UserProfile userProfileToDelete = userProfileOptional.get();
        userProfileMapper.delete(userProfileToDelete);

        Optional<UserProfile> userProfileOptionalAfterDeletion = userProfileMapper.findByFullName("George Mpal");
        assertTrue(!userProfileOptionalAfterDeletion.isPresent()); 
    }

    
    @Test
    public void testUpdateUserProfile() {
    	Optional<UserProfile> userProfileOptional = userProfileMapper.findByFullName("George Mpal");
    	assertTrue(userProfileOptional.isPresent());
    	
    	userProfile.setUserAge(22);
    	userProfileMapper.save(userProfile);
    	
    	Optional<UserProfile> userProfileOptionalAfterUpdate = userProfileMapper.findByFullName("George Mpal");
    	UserProfile userProfile = userProfileOptionalAfterUpdate.get();
    	assertEquals(22, userProfile.getUserAge());
    }
    
    
    @Test
    public void testSaveBook() {
        // Retrieve the saved book from the database
        Optional<Book> savedBookOptional = bookMapper.findById(book.getBookId());

        // Assert that the book is present in the database
        assertTrue(savedBookOptional.isPresent());
        Book savedBook = savedBookOptional.get();

        // Assert book details
        assertEquals("Matrix", savedBook.getTitle());
        assertEquals(1, savedBook.getBookAuthors().size()); // Assuming only one author is added
        assertEquals("VC", savedBook.getBookAuthors().get(0).getAuthorName());
        assertEquals("Sci-fi", savedBook.getBookCategory().getCategoryName());
    }
    
    
    @Test
    public void testDeleteBook() {
    	Optional<Book> savedBookOptional = bookMapper.findById(book.getBookId());
    	assertTrue(savedBookOptional.isPresent());
    	
    	bookMapper.delete(book);
    	Optional<Book> savedBookOptionalAfterDeletion = bookMapper.findById(book.getBookId());
    	assertTrue(!savedBookOptionalAfterDeletion.isPresent()); 	
    }
    
    
    @Test
    public void testUpdateBook() {
    	Optional<Book> savedBookOptional = bookMapper.findById(book.getBookId());
    	assertTrue(savedBookOptional.isPresent());
    	
    	BookCategory bookCategory2 = new BookCategory("Drama",bookCategory.getBooksInCategory());
    	book.setBookCategory(bookCategory2);
    	
    	bookMapper.save(book);
    	
    	Optional<Book> savedBookOptionalAfterUpdate = bookMapper.findById(book.getBookId());
    	
    	Book book = savedBookOptionalAfterUpdate.get();
    	
    	assertEquals("Drama", book.getBookCategory().getCategoryName());
    }
    
    
    @Test
    public void testSaveBookAuthor() {
    	Optional<BookAuthor> savedBookAuthor = bookAuthorMapper.findByAuthorName(bookAuthor.getAuthorName());
    	assertEquals(savedBookAuthor.get().getAuthorName(),bookAuthor.getAuthorName());
    	
    	List<Book> books2 = new ArrayList<>();
    	
    	for(Book book: bookAuthor.getBooks()) {
    		books2.add(book);
    	}
    	
    	BookAuthor bookAuthor2 = new BookAuthor("VC",books2);
    	
    	bookAuthorMapper.save(bookAuthor2);
    	
    	Optional<BookAuthor> savedBookAuthorAfter = bookAuthorMapper.findByAuthorName(bookAuthor2.getAuthorName());
    	
    	assertEquals(savedBookAuthorAfter.get().getAuthorName(),bookAuthor.getAuthorName());
    }
    
    
    @Test
    public void testDeleteBookAuthor() {
    	Optional<BookAuthor> savedBookAuthor = bookAuthorMapper.findByAuthorName(bookAuthor.getAuthorName());
    	assertEquals(savedBookAuthor.get().getAuthorName(),bookAuthor.getAuthorName());
    	
    	bookAuthorMapper.delete(bookAuthor);
    	
    	Optional<BookAuthor> savedBookAuthorAfterDeletion = bookAuthorMapper.findByAuthorName(bookAuthor.getAuthorName());
    	
    	assertTrue(savedBookAuthorAfterDeletion.isEmpty());
    }
    
    
    @Test
    public void testUpdateBookAuthor() {
    	Optional<BookAuthor> savedBookAuthor = bookAuthorMapper.findByAuthorName(bookAuthor.getAuthorName());
    	assertEquals(savedBookAuthor.get().getAuthorName(),bookAuthor.getAuthorName());
    	
    	bookAuthor.setAuthorName("Mpal");
    	bookAuthorMapper.save(bookAuthor);
    	
    	Optional<BookAuthor> savedBookAuthorAfterUpdate = bookAuthorMapper.findByAuthorName("Mpal");
    	
    	assertEquals(savedBookAuthorAfterUpdate.get().getAuthorName(),"Mpal");	
    }
    
    @Test
    public void testSaveBookCategory() {
         Optional<BookCategory> bookCategoryOptional = bookCategoryMapper.findById(bookCategory.getCategoryId());
         assertTrue(bookCategoryOptional.isPresent());
         BookCategory savedCategory = bookCategoryOptional.get();
         assertEquals("Sci-fi", savedCategory.getCategoryName());

    }
    
    @Test
    public void testDeleteCategory() {
        Optional<BookCategory> bookCategoryOptional = bookCategoryMapper.findById(bookCategory.getCategoryId());
        assertTrue(bookCategoryOptional.isPresent());
        bookCategoryMapper.delete(bookCategory);
        Optional<BookCategory> savedCategoryOptionalAfterDeletion = bookCategoryMapper.findById(bookCategory.getCategoryId());
        assertTrue(!savedCategoryOptionalAfterDeletion.isPresent());
    }
    
    
    @Test 
    public void testUpdateBookCategory() {
        Optional<BookCategory> bookCategoryOptional = bookCategoryMapper.findById(bookCategory.getCategoryId());
        assertTrue(bookCategoryOptional.isPresent());
        
        Book book2 = new Book();
        
        bookCategory.getBooksInCategory().add(book2);
        
        bookCategoryMapper.save(bookCategory);
        
        Optional<BookCategory> bookCategoryOptionalAfterUpdate = bookCategoryMapper.findById(bookCategory.getCategoryId());
        
        BookCategory bookCategory = bookCategoryOptionalAfterUpdate.get();
        
        assertEquals(2, bookCategory.getBooksInCategory().size());
    }
    
    
    @Test
    public void testSaveUser() {
    	Optional<User> userOptional = userMapper.findByUserId(user.getUserId());
    	assertTrue(userOptional.isPresent());
    	
    	user = userOptional.get();
    	assertEquals(user.getUsername(),"ZAS");
    }
    
    
    @Test
    public void testDeleteUser() {
    	Optional<User> userOptional = userMapper.findByUserId(user.getUserId());
    	assertTrue(userOptional.isPresent());
    	
    	userMapper.delete(user);
    	
    	Optional<User> userOptionalAfterDeletion = userMapper.findByUserId(user.getUserId());
    	assertTrue(!userOptionalAfterDeletion.isPresent());
    }
    
    
    @Test
    public void testUpdateUser() {
    	Optional<User> userOptional = userMapper.findByUserId(user.getUserId());
    	assertTrue(userOptional.isPresent());
    	
    	user.setUsername("Apostolos Zarras");
    	userMapper.save(user);
    	
    	Optional<User> userOptionalAfterUpdate = userMapper.findByUserId(user.getUserId());
    	assertEquals(userOptionalAfterUpdate.get().getUsername(),"Apostolos Zarras");
    }
}