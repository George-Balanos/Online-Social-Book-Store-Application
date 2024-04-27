package app.bookstore.socialbookstore.recommendationstrategy;

import java.util.List;

import app.bookstore.socialbookstore.services.BookService;

public class FavouriteBookCategoryStrategy implements RecommendationEngineStrategy{

	@Override
	public List<Integer> recommendBooks(int userId, BookService bookService) {
		return bookService.getByFavouriteCategory(userId);
	}

}
