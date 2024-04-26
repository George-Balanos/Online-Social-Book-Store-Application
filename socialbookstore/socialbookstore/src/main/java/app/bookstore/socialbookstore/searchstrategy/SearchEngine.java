package app.bookstore.socialbookstore.searchstrategy;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.services.BookService;

public class SearchEngine {
	private SearchEngineStrategy strategy;
	
	public void configureSearchEngine(SearchEngineStrategy strategy) {
		this.strategy = strategy;
	}
	
	public List<Book> doSearch(String query,BookService bookService) {
		return strategy.executeSearch(query,bookService);
	}
	
}
