package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.BookAuthor;

public interface AuthorService {
    List<BookAuthor> getAllAuthors();
    Optional<BookAuthor> findAuthorById(int id);
    void saveAuthor(BookAuthor author);
    void deleteAuthorById(int id);
    boolean existsAuthorById(int id);
    
    
}