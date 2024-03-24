package socialbookstore.domain;

import java.util.List;

public class UserProfile {
	
	private int userProfileId;
	private String usernameProfile;
	private String fullName;
	private int userAge;
	private List<BookAuthor> favouriteBookAuthors;
	private List<BookCategory> favouriteBookCategories;
	private List<Book> bookOffers;
	
	public UserProfile(String username, String fullName, int age) {
		this.usernameProfile = username;
		this.fullName = fullName;
		this.userAge = age;
	}
	
	public String getUsernameProfile() {
		return usernameProfile;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public int getUserAge() {
		return userAge;
	}
	
	public List<BookAuthor> getFavouriteBookAuthors(){
		return favouriteBookAuthors;
	}
	
	public List<BookCategory> getFavouriteBookCategories(){
		return favouriteBookCategories;
	}
	
	public List<Book> getBookOffers(){
		return bookOffers;
	}
	
	public void addFavouriteBookAuthor(BookAuthor bookAuthor) {
		this.favouriteBookAuthors.add(bookAuthor);
	}
	
	public void addFavouriteBookCategory(BookCategory bookCategory) {
		this.favouriteBookCategories.add(bookCategory);
	}
	
	public void addBookOffer(Book bookOffer) {
		this.bookOffers.add(bookOffer);
	}
	
	public void changeUsername(String newUsername) {
		this.usernameProfile = newUsername;
	}

	public int getUserProfileId() {
		return userProfileId;
	}
}
