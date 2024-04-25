package app.bookstore.socialbookstore.domain;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.*;

@Entity 
@Table(name = "book_authors")
public class BookAuthor {
	
	@Id
	@Column(name = "author_name")
	private String authorName; 
	
	@ManyToMany(mappedBy = "bookAuthors")
	private List<Book> books;
	
	public BookAuthor(String name, List<Book> books) {

		this.authorName = name;
		this.books = books;
	}
	
	public BookAuthor() {
		
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
