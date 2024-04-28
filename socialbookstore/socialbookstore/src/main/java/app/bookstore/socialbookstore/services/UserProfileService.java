package app.bookstore.socialbookstore.services;

import java.util.List;
import java.util.Optional;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.UserProfile;

public interface UserProfileService {
	public Optional<UserProfile> findUserProfile(String username);
	public Optional<UserProfile> findUserProfileById(int id);
	public void saveUserProfile(UserProfile userProfile);
	public List<Book> getMyBookOffers(int id);
	public List<Book> getAllBookOffers(int id);
	public List<String> getUsersRequests(int id);
	public List<String> getDeclinedUsers(int id, int borrowerId);
	public void deleteBookRequest(int bookId);
	public List<String> getClosedRequests(int id);
	public void deleteSimpleBookRequest(int id, int bookId);
	public void deleteBookRequestAfterBookOfferWithdraw(int id);
	public List<String> getMyBookAuthors(int id);
	public List<String> getMyBookCategories(int id);
	public List<String> getOtherBookAuthors(int id);
	public List<String> getOtherBookCategories(int id);
	public void deleteMyBookCategories(int id, String categoryName);
	public void deleteMyBookAuthors(int id, String authorName);
}
