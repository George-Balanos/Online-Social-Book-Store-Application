package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import jakarta.transaction.Transactional;

@Service
public class BookServiceImpl implements BookService{

	@Autowired
	BookMapper bookMapper;
	
	@Autowired
	BookAuthorMapper bookAuthorMapper;

	@Autowired
	BookCategoryMapper bookCategoryMapper;
	
	@Override
	public void saveBook(Book book) {
		bookMapper.save(book);
	}

	@Override
	public List<Book> getByTitle(String title) {
		List<Book> matchedBooks = bookMapper.findByTitle(title);
		
		return matchedBooks;
	}

	@Override
	public List<Book> getByBookAuthor(String author) {
		Optional<BookAuthor> bookAuthor = bookAuthorMapper.findByAuthorName(author);
		
		List<Book> matchedBooks = bookAuthor.get().getBooks();
		
		return matchedBooks;
	}
	
	@SuppressWarnings("deprecation")
	@Override
    @Transactional
    public void deleteBookOfferByTitle(String title) {
        List<Book> matchedBooks = bookMapper.findByTitle(title);
        System.out.println("Size: " + matchedBooks.size());

        bookMapper.deleteInBatch(matchedBooks);
        bookMapper.flush(); 
	}

	@Override
	public Optional<Book> getById(int id) {
		return bookMapper.findByBookId(id);
	}


	@Override
	public void removeBook(int id) {
		bookMapper.deleteById(id);
	}

	@Override
	public List<Book> getByExactAuthorName(String name) {
		return bookMapper.findByBookAuthorsAuthorName(name);
	}

	@Override
	public List<Book> getByInexactAuthorName(String name) {
		return bookMapper.findByBookAuthorsAuthorNameContaining(name);
	}

	@Override
	public List<Book> getByInexactTitle(String title) {
		return bookMapper.findByTitleContaining(title);
	}

	@Override
	public List<Integer> getByFavouriteAuthors(int userId) {
		return bookAuthorMapper.findByFavouriteAuthors(userId);
	}

	@Override
	public List<Integer> getByFavouriteCategory(int userId) {
		return bookCategoryMapper.findByFavouriteCategories(userId);
	}
}
