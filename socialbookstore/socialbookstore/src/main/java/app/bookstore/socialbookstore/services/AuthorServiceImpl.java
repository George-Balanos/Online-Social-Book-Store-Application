package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private BookAuthorMapper authorMapper;

    @Override
    public List<BookAuthor> getAllAuthors() {
        return authorMapper.findAll();
    }

    @Override
    public Optional<BookAuthor> findAuthorById(int id) {
        return authorMapper.findById(id);
    }

    @Override
    public void saveAuthor(BookAuthor author) {
        authorMapper.save(author);
    }

    @Override
    public void deleteAuthorById(int id) {
        authorMapper.deleteById(id);
    }

    @Override
    public boolean existsAuthorById(int id) {
        return authorMapper.existsById(id);
    }
}