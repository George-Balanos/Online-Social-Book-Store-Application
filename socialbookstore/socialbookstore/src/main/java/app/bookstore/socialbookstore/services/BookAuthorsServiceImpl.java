package app.bookstore.socialbookstore.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;

@Service
public class BookAuthorsServiceImpl implements BookAuthorsService{
	@Autowired
	BookAuthorMapper bookAuthorMapper;

	@Override
	public Optional<BookAuthor> findByAuthorName(String name) {
		Optional<BookAuthor> bookAuthors = bookAuthorMapper.findByAuthorName(name);
		
		return bookAuthors;
	}

	@Override
	public void saveBookAuthor(BookAuthor bookAuthor) {
		bookAuthorMapper.save(bookAuthor);
	}

	@Override
	public boolean isPresent(String name) {
		return bookAuthorMapper.findByAuthorName(name).isPresent();
	}

	@Override
	public Optional<BookAuthor> getByAuthorName(String name) {
		Optional<BookAuthor> bookAuthor = bookAuthorMapper.findByAuthorName(name);
		
		return bookAuthor;
	}
	
}
