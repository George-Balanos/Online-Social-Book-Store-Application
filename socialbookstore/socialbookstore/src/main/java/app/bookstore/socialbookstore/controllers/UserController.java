package app.bookstore.socialbookstore.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.domain.BookCategory;
import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;
import app.bookstore.socialbookstore.services.AuthorService;
import app.bookstore.socialbookstore.services.BookAuthorsService;
import app.bookstore.socialbookstore.services.BookCategoryService;
import app.bookstore.socialbookstore.services.BookService;
import app.bookstore.socialbookstore.services.UserProfileService;
import app.bookstore.socialbookstore.services.UserService;

@Controller
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	BookAuthorsService bookAuthorService;
	
	@Autowired
	BookService bookService;
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
	BookCategoryService bookCategoryService;
	
	@Autowired
	UserProfileMapper userProfileMapper;
	
	@RequestMapping("/success")
	public String getUserHome(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.err.println(currentPrincipalName);
		
		Optional<User> user = userService.getUser(currentPrincipalName);
		
		Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
		model.addAttribute("currentUser",currentUser);
		
		return "/dashboard";
	}
	
	@RequestMapping("/success/showInfo")
	public String showInfo(Model model) {
		return "dashboard";
	}
	
	@RequestMapping("/edit_profile")
	public String showSettings(Model model) {
		return "edit_profile";
	}
	
	@RequestMapping("/recommendations")
    public String showRecommendations(Model model) {
        return "Recommendations";
    }  
	
	@RequestMapping("/my_book_offers")
    public String showMyBookOffers(Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.err.println(currentPrincipalName);
		Optional<User> user = userService.getUser(currentPrincipalName);
		Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
		
		int id = currentUser.get().getUserProfileId();
		
		List<Book> myBookOffers = userProfileService.getMyBookOffers(id);
		model.addAttribute("myBookOffers",myBookOffers);
		
        return "my_book_offers";
    }
	
	@RequestMapping("/book_offer_form")
    public String showBookOfferForm(Model model) {		
        return "book_offer_form";
    }
	
	@PostMapping("/make_book_offer")
	public String makeBookOffer(Model model, @RequestParam("title") String title, 
    		@RequestParam("bookAuthors") String bookAuthors, 
    		@RequestParam("bookCategory") String bookCategory,
    		@RequestParam("summary") String summary) {
		
		System.out.println(title);
		System.out.println(bookAuthors);
		System.out.println(bookCategory);
		System.out.println(summary);
		
		int saveBook = 0;
		
		BookCategory category = new BookCategory(bookCategory,null);
		Book book = new Book(title,null,category);
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> user = userService.getUser(currentPrincipalName);
		Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
		
		book.setBookOwnerId(currentUser.get().getUserProfileId());

		// Split bookAuthors by comma
	    String[] authorNames = bookAuthors.split(",");
	    
	    // Create author objects based on splits
	    List<BookAuthor> authors = new ArrayList<>();
	    for (String authorName : authorNames) {
	        if(bookAuthorService.isPresent(authorName)) {
	        	System.out.println(bookAuthorService.isPresent(authorName));
	        	bookAuthorService.getByAuthorName(authorName).get().addBook(book);
	        }else {
	        	BookAuthor author = new BookAuthor(authorName.trim(),null); // Trim whitespace around author name
	        	
	        	List<Book> tempBooks = new ArrayList<>();
	        	tempBooks.add(book);
	        	author.setBooks(tempBooks);
	        	
	        	bookAuthorService.saveBookAuthor(author); 
	        	System.out.println("Author:" + authorName);
	        	authors.add(author);
	        	saveBook = 1;
	        }   
	    }
	    
	    book.setBookAuthors(authors);
	    
	    
	    if(saveBook == 0)
	    	bookService.saveBook(book);
		
	    return "redirect:/my_book_offers";
    }
	
	@PostMapping("/delete_book_offer_by_title")
    public String deleteBookOffer(@RequestParam("title") String title, Model model) {
        System.out.println("Request received to delete book offer with title: " + title);
        try {
            bookService.deleteBookOfferByTitle(title);
            System.out.println("Book offer deleted successfully");
            model.addAttribute("successMessage", "Book offer deleted successfully");
        } catch (Exception e) {
            System.err.println("Failed to delete book offer: " + e.getMessage());
            model.addAttribute("errorMessage", "Failed to delete book offer: " + e.getMessage());
        }
        return "redirect:/my_book_offers";
    }
	
	
	@PostMapping("/request_book_offer")
	public String requestBookOffer(@RequestParam("bookId") int bookId,Model model) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		Optional<User> user = userService.getUser(currentPrincipalName);
		Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
		
		Optional<Book> selectedBook = bookService.getById(bookId);
		
		currentUser.get().addBookOffer(selectedBook.get());
		selectedBook.get().addRequestingUser(currentUser.get());
		
		bookService.saveBook(selectedBook.get());
		
		return "redirect:/book_offers";
	}
	
	
	@PostMapping("/updateProfile")
	public String updateProfile(Model model, @RequestParam(value = "username_profile", required = false) String username, 
			@RequestParam(value = "full_name", required = false) String fullName, 
			@RequestParam(value = "user_age", required = false) String userAge) {
		System.out.println("Hello");
		
		try {
            int age = Integer.parseInt(userAge);
            if (username.isEmpty() || fullName.isEmpty() || userAge.isEmpty()) {

                return "redirect:/edit_profile";
            }

            System.out.println("username_profile " + username);
            System.out.println("fullname " + fullName);
            System.out.println("Age " + age);
            
            Optional<User> user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
            Optional<UserProfile> currentProfile = userProfileService.findUserProfileById(user.get().getUserId());
            
            currentProfile.get().setUsernameProfile(username);
            currentProfile.get().setFullName(fullName);
            currentProfile.get().setUserAge(Integer.parseInt(userAge));
            
            userProfileService.saveUserProfile(currentProfile.get());
            
            model.addAttribute("currentUser",currentProfile.get());
            
            return "redirect:/success";
        } catch (NumberFormatException e) {

            return "redirect:/edit_profile";
        }
	}
	
	@RequestMapping("/book_offers")
    public String showBookOffers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.err.println(currentPrincipalName);
        Optional<User> user = userService.getUser(currentPrincipalName);
        Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
        
        int myId = currentUser.get().getUserProfileId();
     
        
        List<Book> allBookOffers = userProfileService.getAllBookOffers(myId); 
        
        model.addAttribute("allBookOffers",allBookOffers);
        
        return "book_offers";
    }
	
	@RequestMapping("/favourites")
    public String showFavourites(Model model) {
        
        return "favourites";
    }
    @RequestMapping("/favourite_authors")
    public String showFavouriteAuthors(Model model) {
        
        return "favourite_authors";
    }
    @RequestMapping("/favourite_categories")
    public String showFavouriteCategories(Model model) {
        
        return "favourite_categories";
    }

    @RequestMapping("/show_authors")
    public String showAuthors(Model model) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.err.println(currentPrincipalName);
        Optional<User> user = userService.getUser(currentPrincipalName);
        Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
        
        int myId = currentUser.get().getUserProfileId();
    	
    	List<String> tempAuthors = userProfileMapper.findOtherBookAuthors(myId);
    	System.out.println(myId);
    	
    	List<BookAuthor> bookAuthors = new ArrayList<>();
    	
    	for(String author: tempAuthors) {
    		bookAuthors.add(bookAuthorService.findByAuthorName(author).get());
    	}
    	
        model.addAttribute("authors", bookAuthors);
        System.out.println("hereeee");
        return "select_authors"; 
    }
    
    @RequestMapping("/show_categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", bookCategoryService.getAllCategories());
        System.out.println("hereeee");
        return "select_categories"; 
    }
	
    
    @PostMapping("/favourite_author_by_name")
    public String favouriteBookAuthor(@RequestParam("authorName") String authorName) {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        System.err.println(currentPrincipalName);
        Optional<User> user = userService.getUser(currentPrincipalName);
        Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
        
        Optional<BookAuthor> selectedBookAuthor = bookAuthorService.findByAuthorName(authorName);
        
        int myId = currentUser.get().getUserProfileId();
        
        currentUser.get().addFavouriteBookAuthor(selectedBookAuthor.get());
        
        userProfileService.saveUserProfile(currentUser.get());
        
        System.out.println(userProfileMapper.findOtherBookAuthors(myId));
        
    	return "redirect:/show_authors";
    }
    
}
