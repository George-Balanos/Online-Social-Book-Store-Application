package socialbookstore.domain;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "books")
public class Book {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="book_id")
	private int bookId;
	
	@Column(name="title")
	private String title;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="author_id")
	private List<BookAuthor> bookAuthors;
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="category_id")
	private BookCategory bookCategory;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "userprofile_book",
			joinColumns = @JoinColumn(
				name = "book_id", referencedColumnName = "id"	
			),
			inverseJoinColumns = @JoinColumn(
				name = "userProfile_id", referencedColumnName = "id"
			)
	)
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



	public void setBookId(int bookId) {
		this.bookId = bookId;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public List<BookAuthor> getBookAuthors() {
		return bookAuthors;
	}



	public void setBookAuthors(List<BookAuthor> bookAuthors) {
		this.bookAuthors = bookAuthors;
	}



	public BookCategory getBookCategory() {
		return bookCategory;
	}



	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}



	public List<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}



	public void setRequestingUsers(List<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}



	public void addRequestingUser(UserProfile requestingUser) {
		requestingUsers.add(requestingUser);
	}
	
}
