package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.Book;

public interface BookService {
	public void saveBook(Book book);
	public void clearDuplicates(Book book);
	public List<Book> getByTitle(String title);
	public List<Book> getByBookAuthor(String author);
	public void deleteBookOfferByTitle(String title);
	public Optional<Book> getById(int id);
}
