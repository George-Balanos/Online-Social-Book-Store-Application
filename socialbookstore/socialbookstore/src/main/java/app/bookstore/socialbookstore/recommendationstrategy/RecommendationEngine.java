package app.bookstore.socialbookstore.recommendationstrategy;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.services.BookService;

public class RecommendationEngine {
	private RecommendationEngineStrategy strategy;
	
	public void configureRecommendationEngine(RecommendationEngineStrategy strategy) {
		this.strategy = strategy;
	}
	
	public List<Integer> recommend(int userId, BookService bookService){
		return strategy.recommendBooks(userId, bookService);
	}
}
