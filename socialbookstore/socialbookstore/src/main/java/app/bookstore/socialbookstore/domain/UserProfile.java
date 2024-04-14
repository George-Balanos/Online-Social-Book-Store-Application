package app.bookstore.socialbookstore.domain;

import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name="user_profiles")
public class UserProfile {
	
	@Id
	@Column(name="user_profile_id")
	private int userProfileId;
	
	@Column(name="username_profile")
	private String usernameProfile;
	
	@Column(name="full_name")
	private String fullName;
	
	@Column(name="user_age")
	private int userAge;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "user_profile_author",
			joinColumns = @JoinColumn(name="user_profile_id",referencedColumnName = "user_profile_id"),
			inverseJoinColumns = @JoinColumn(name="author_id",referencedColumnName = "author_id")
	)
	private List<BookAuthor> favouriteBookAuthors;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(
			name = "user_profile_book_category",
			joinColumns = @JoinColumn(name="user_profile_id"),
			inverseJoinColumns = @JoinColumn(name="category_id")
	)
	private List<BookCategory> favouriteBookCategories;
	
	@ManyToMany(mappedBy = "requestingUsers")
	private List<Book> bookOffers;
	
	public UserProfile(String username, String fullName, int age) {
		super();
		this.usernameProfile = username;
		this.fullName = fullName;
		this.userAge = age;
	}
	
	public UserProfile() {
		super();
	}
	
	public String getUsernameProfile() {
		return usernameProfile;
	}



	public void setUsernameProfile(String usernameProfile) {
		this.usernameProfile = usernameProfile;
	}



	public String getFullName() {
		return fullName;
	}



	public void setFullName(String fullName) {
		this.fullName = fullName;
	}



	public int getUserAge() {
		return userAge;
	}



	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}



	public List<BookAuthor> getFavouriteBookAuthors() {
		return favouriteBookAuthors;
	}



	public void setFavouriteBookAuthors(List<BookAuthor> favouriteBookAuthors) {
		this.favouriteBookAuthors = favouriteBookAuthors;
	}



	public List<BookCategory> getFavouriteBookCategories() {
		return favouriteBookCategories;
	}



	public void setFavouriteBookCategories(List<BookCategory> favouriteBookCategories) {
		this.favouriteBookCategories = favouriteBookCategories;
	}



	public List<Book> getBookOffers() {
		return bookOffers;
	}



	public void setBookOffers(List<Book> bookOffers) {
		this.bookOffers = bookOffers;
	}



	public void setUserProfileId(int userProfileId) {
		this.userProfileId = userProfileId;
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
