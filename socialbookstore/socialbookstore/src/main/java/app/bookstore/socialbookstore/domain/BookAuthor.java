package app.bookstore.socialbookstore.domain;

import java.util.List;

import jakarta.persistence.*;

@Entity 
@Table(name = "book_authors")
public class BookAuthor {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="author_id")
	private int authorId;
	
	@Column(name = "author_name")
	private String authorName; //name
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
		name = "author_book",
		joinColumns = @JoinColumn(
				name = "author_id",referencedColumnName = "author_id"
		),
		inverseJoinColumns = @JoinColumn(
				name = "book_id",referencedColumnName = "book_id")
	)
	private List<Book> books;
	
	public BookAuthor(String name, List<Book> books) {

		this.authorName = name;
		this.books = books;
	}
	
	public BookAuthor() {
		
	}
	
	public int getAuthorId() {
		return authorId;
	}



	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}



	public String getAuthorName() {
		return authorName;
	}



	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}



	public List<Book> getBooks() {
		return books;
	}



	public void setBooks(List<Book> books) {
		this.books = books;
	}



	public void addBook(Book authorBook) {
		books.add(authorBook);
	}
	
}
