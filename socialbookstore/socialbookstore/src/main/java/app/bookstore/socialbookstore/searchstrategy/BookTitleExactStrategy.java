package app.bookstore.socialbookstore.searchstrategy;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.services.BookService;

public class BookTitleExactStrategy implements SearchEngineStrategy{

	@Override
	public List<Book> executeSearch(String query, BookService bookService) {
		return bookService.getByTitle(query);
	}

}
