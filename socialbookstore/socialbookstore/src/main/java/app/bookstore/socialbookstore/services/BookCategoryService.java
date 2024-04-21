package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.BookCategory;

public interface BookCategoryService {
	List<BookCategory> getAllCategories();
    Optional<BookCategory> findCategoryById(int id);
    void saveCategory(BookCategory category);
    void deleteCategoryById(int id);
    boolean existsCategoryById(int id);
}
