package app.bookstore.socialbookstore.recommendationstrategy;

import java.util.List;

import app.bookstore.socialbookstore.services.BookService;

public interface RecommendationEngineStrategy {

	public List<Integer> recommendBooks(int userId, BookService bookService);
	
}
