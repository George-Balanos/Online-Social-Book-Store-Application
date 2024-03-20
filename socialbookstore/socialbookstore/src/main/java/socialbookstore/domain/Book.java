package socialbookstore.domain;

import java.util.List;

public class Book {
	private int bookId;
	private String title;
	private List<BookAuthor> bookAuthors;
	private BookCategory bookCategory;
	private List<UserProfile> requestingUsers;
	
	public Book(int id, String title, List<BookAuthor> authors, BookCategory bookCategory) {
		
		this.bookId = id;
		this.title = title;
		this.bookAuthors = authors;
		this.bookCategory = bookCategory;
		
	}
	
	public int getBookId() {
		return bookId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public List<BookAuthor> getBookAuthors(){
		return bookAuthors;
	}
	
	public BookCategory getBookCategory() {
		return bookCategory;
	}
	
	public List<UserProfile> getRequestingUsers(){
		return requestingUsers;
	}
	
	public void addRequestingUser(UserProfile requestingUser) {
		requestingUsers.add(requestingUser);
	}
	
}
