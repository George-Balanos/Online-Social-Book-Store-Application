package app.bookstore.socialbookstore.services;

import java.util.List;

import app.bookstore.socialbookstore.domain.Book;

public interface BookService {
	public void saveBook(Book book);
	public List<Book> getByTitle(String title);
	public List<Book> getByBookAuthor(String author);
}
