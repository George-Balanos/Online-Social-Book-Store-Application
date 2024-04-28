package app.bookstore.socialbookstore.domain;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name="book_categories")
public class BookCategory {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="category_name")
	private String categoryName;//name
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "bookId")
	private List<Book> booksInCategory;
	
	public BookCategory(String name, List<Book> books) {
		
		this.categoryName = name;
		this.booksInCategory = books;
		
	}
	
	public BookCategory(int id, String name) {
		this.categoryId = id;
		this.categoryName = name;
	}
	
	public BookCategory() {
		super();
	}
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public List<Book> getBooksInCategory() {
		return booksInCategory;
	}

	public void setBooksInCategory(List<Book> booksInCategory) {
		this.booksInCategory = booksInCategory;
	}

	public void addBookInCategory(Book bookInCategory) {
		booksInCategory.add(bookInCategory);
	}
	
}
