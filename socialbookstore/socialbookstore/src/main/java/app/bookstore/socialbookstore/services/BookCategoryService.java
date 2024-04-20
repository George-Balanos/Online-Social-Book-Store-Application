package app.bookstore.socialbookstore.services;

import java.util.Optional;

import app.bookstore.socialbookstore.domain.BookCategory;

public interface BookCategoryService {
	public boolean isPresent(String name);
	public Optional<BookCategory> getByCategoryName(String name);
	public void saveCategory(BookCategory bookCategory);
}
