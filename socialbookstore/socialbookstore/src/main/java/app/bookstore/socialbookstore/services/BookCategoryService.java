package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.BookCategory;

public interface BookCategoryService {
	List<BookCategory> getAllCategories();
    void saveCategory(BookCategory category);
    Optional<BookCategory> findCategoryById(int id);
    Optional<BookCategory> getCategoryByName(String name);
    void deleteCategoryById(int id);
    boolean existsCategoryById(int id);
}
