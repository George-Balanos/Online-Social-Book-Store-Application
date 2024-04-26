package app.bookstore.socialbookstore.searchstrategy;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.services.BookService;

public interface SearchEngineStrategy {
	
	public List<Book> executeSearch(String query, BookService bookService);
	
}
