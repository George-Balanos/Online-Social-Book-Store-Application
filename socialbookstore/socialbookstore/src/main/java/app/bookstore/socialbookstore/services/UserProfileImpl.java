package app.bookstore.socialbookstore.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

@Service
public class UserProfileImpl implements UserProfileService{
	
	@Autowired
	private UserProfileMapper userProfileMapper;

	@Autowired
	private BookMapper bookMapper;
	
	@Override
	public void saveUserProfile(UserProfile userProfile) {
		userProfileMapper.save(userProfile);
	}
	
	@Override
    public Optional<UserProfile> findUserProfile(String username) {
        Optional<UserProfile> storedUser = userProfileMapper.findByUsernameProfile(username);
        return storedUser; 
    }
	
	@Override
    public Optional<UserProfile> findUserProfileById(int id) {
        Optional<UserProfile> storedUser = userProfileMapper.findByUserProfileId(id);
        return storedUser; 
    }

	@Override
	public List<Book> getMyBookOffers(int id) {
		return bookMapper.findByBookOwnerId(id);
	}

	@Override
	public List<Book> getAllBookOffers(int id) {
		List<Book> otherBooks = new ArrayList<>();
        List<Book> allBooks = bookMapper.findAll();

        for (Book book : allBooks) {
            if (book.getBookOwnerId() != id) {
                otherBooks.add(book);
            }
        }

        System.out.println(otherBooks.size());
        
        return otherBooks;
	}

	@Override
    public List<String> getUsersRequests(int id) {

        return userProfileMapper.getUsersRequests(id);
    }

	@Override
	public List<String> getDeclinedUsers(int id, int borrowerId) {
		return userProfileMapper.findDeclinedUsers(id, borrowerId);
	}

	@Override
	public void deleteBookRequest(int bookId) {
		userProfileMapper.removeBookRequest(bookId);
	}

	@Override
    public List<String> getClosedRequests(int id) {
        return userProfileMapper.findClosedRequests(id);
    }

	@Override
    public void deleteSimpleBookRequest(int id, int bookId) {
        userProfileMapper.removeSimpleBookRequest(id, bookId);
    }

	@Override
	public void deleteBookRequestAfterBookOfferWithdraw(int id) {
		userProfileMapper.removeBookRequestAfterWithdrawal(id);
	}
}
