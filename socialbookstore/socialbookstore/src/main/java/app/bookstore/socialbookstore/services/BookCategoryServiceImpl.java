package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.bookstore.socialbookstore.domain.BookCategory;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;

@Service
public class BookCategoryServiceImpl implements BookCategoryService {

    @Autowired
    private BookCategoryMapper categoryMapper;

    @Override
    public List<BookCategory> getAllCategories() {
        return categoryMapper.findAll();
    }

    @Override
    public Optional<BookCategory> findCategoryById(int id) {
        return categoryMapper.findById(id);
    }


    @Override
    public void saveCategory(BookCategory category) {
        categoryMapper.save(category);
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryMapper.deleteById(id);
    }

    @Override
    public boolean existsCategoryById(int id) {
        return categoryMapper.existsById(id);
    }
}
