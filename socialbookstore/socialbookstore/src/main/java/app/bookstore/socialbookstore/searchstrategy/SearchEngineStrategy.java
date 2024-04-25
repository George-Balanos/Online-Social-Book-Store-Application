package app.bookstore.socialbookstore.searchstrategy;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;

public interface SearchEngineStrategy {
	
	public List<Book> executeSearch(String query);
	
}
