package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.Book;

public interface BookService {
	public void saveBook(Book book);
	
	public List<Book> getByTitle(String title);
	public List<Book> getByInexactTitle(String title);
	
	public List<Book> getByBookAuthor(String author);
	public void deleteBookOfferByTitle(String title);
	public Optional<Book> getById(int id);
	public void removeBook(int id);
	
	public List<Book> getByExactAuthorName(String name);
	public List<Book> getByInexactAuthorName(String name);
	
	public List<Integer> getByFavouriteAuthors(int userId);
	public List<Book> getByFavouriteCategory(int userId);
}
