package app.bookstore.socialbookstore.domain;


import java.util.List;
import java.util.Set;

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
	
	@Column(name="summary")
	private String summary;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "author_book",
		joinColumns = @JoinColumn(
				name = "book_id",referencedColumnName = "book_id"
		),
		inverseJoinColumns = @JoinColumn(
				name = "author_name",referencedColumnName = "author_name")
	)
	private List<BookAuthor> bookAuthors;
	
	
	@ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="category_id")
	private BookCategory bookCategory;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@JoinTable(
			name = "userprofile_book",
			joinColumns = @JoinColumn(
				name = "book_id", referencedColumnName = "book_id"	
			),
			inverseJoinColumns = @JoinColumn(
				name = "userProfile_id", referencedColumnName = "user_profile_id"
			)
	)
	private Set<UserProfile> requestingUsers;
	
	@Column(name="book_owner_id")
    private int bookOwnerId;
	

	public Book(String title, List<BookAuthor> authors, BookCategory bookCategory) {
		
		this.title = title;
		this.bookAuthors = authors;
		this.bookCategory = bookCategory;
	}
	
	public Book() {
		
	}
	
	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	public int getBookOwnerId() {
		return bookOwnerId;
	}

	public void setBookOwnerId(int bookOwnerId) {
		this.bookOwnerId = bookOwnerId;
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

	public Set<UserProfile> getRequestingUsers() {
		return requestingUsers;
	}

	public void setRequestingUsers(Set<UserProfile> requestingUsers) {
		this.requestingUsers = requestingUsers;
	}

	public void addRequestingUser(UserProfile requestingUser) {
		requestingUsers.add(requestingUser);
	}
}
