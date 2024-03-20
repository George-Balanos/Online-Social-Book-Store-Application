package socialbookstore.domain;

import java.util.List;

public class BookAuthor {
	
	private int authorId;
	private String authorName;
	private List<Book> books;
	
	public BookAuthor(int id, String name, List<Book> books) {
		this.authorId = id;
		this.authorName = name;
		this.books = books;
	}
	
	public int getAuthorId() {
		return authorId;
	}
	
	public String getAuthorName() {
		return authorName;
	}
	
	public List<Book> getBooks(){
		return books;
	}
	
	public void addBook(Book authorBook) {
		books.add(authorBook);
	}
	
}
