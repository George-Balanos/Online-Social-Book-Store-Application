package socialbookstore.domain;

import java.util.List;

public class BookCategory {
	
	private int categoryId;
	private String categoryName;
	private List<Book> booksInCategory;
	
	public BookCategory(int id, String name, List<Book> books) {
		
		this.categoryId = id;
		this.categoryName = name;
		this.booksInCategory = books;
		
	}
	
	public int getCategoryId() {
		return categoryId;
	}
	
	public String getCategoryName() {
		return categoryName;
	}
	
	public List<Book> getBooksInCategory(){
		return booksInCategory;
	}
	
	public void addBookInCategory(Book bookInCategory) {
		booksInCategory.add(bookInCategory);
	}
	
}
