package app.bookstore.socialbookstore.services;

import java.util.Optional;

import app.bookstore.socialbookstore.domain.BookAuthor;

public interface BookAuthorsService {
	public Optional<BookAuthor> findByAuthorName(String name);
	public void saveBookAuthor(BookAuthor bookAuthor);
	public boolean isPresent(String name);
	public Optional<BookAuthor> getByAuthorName(String name);
}
