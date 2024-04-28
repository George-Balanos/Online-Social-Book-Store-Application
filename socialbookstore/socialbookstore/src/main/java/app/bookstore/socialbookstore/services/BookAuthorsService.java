package app.bookstore.socialbookstore.services;

import java.util.Optional;
import app.bookstore.socialbookstore.domain.BookAuthor;

public interface BookAuthorsService {
	Optional<BookAuthor> findByAuthorName(String name);
	void saveBookAuthor(BookAuthor bookAuthor);
	boolean isPresent(String name);
	Optional<BookAuthor> getByAuthorName(String name);
	
}
