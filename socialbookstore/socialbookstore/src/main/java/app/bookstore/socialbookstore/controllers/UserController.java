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

import app.bookstore.socialbookstore.domain.*;
import app.bookstore.socialbookstore.recommendationstrategy.*;
import app.bookstore.socialbookstore.searchstrategy.*;
import app.bookstore.socialbookstore.services.*;
import jakarta.servlet.http.HttpServletRequest;

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
	BookCategoryService bookCategoryService;
	
	@Autowired
	RequestService requestService;
	
	private SearchEngine searchEngine = new SearchEngine();
	private SearchEngineStrategy searchEngineStrategy;
	
	private RecommendationEngine recommendationEngine = new RecommendationEngine();
	private RecommendationEngineStrategy recommendationEngineStrategy;
	
	public Optional<UserProfile> getCurrentUserProfile(){
		Optional<User> user = getCurrentUser();
		Optional<UserProfile> currentUser = userProfileService.findUserProfileById(user.get().getUserId());
		
		return currentUser;
	}
	
	public Optional<User> getCurrentUser(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentPrincipalName = authentication.getName();
		System.err.println(currentPrincipalName);
		Optional<User> user = userService.getUser(currentPrincipalName);
		
		return user;
	}
	
	@RequestMapping("/success")
	public String getUserHome(Model model) {		
		model.addAttribute("currentUser",getCurrentUserProfile());
		
		List<String> usersRequests = userProfileService.getUsersRequests(getCurrentUser().get().getUserId());
		
		List<Request> requestList = new ArrayList<>();
		List<UserProfile> userProfileRequesters = new ArrayList<>();
		
        //System.out.println("Size of users requests: " + usersRequests.size());
        
        for (String id : usersRequests) {
        	String[] temp = id.split(",");
        	try {
                Integer borrowerId = Integer.parseInt(temp[0].trim()); 
                Integer bookId = Integer.parseInt(temp[1].trim()); 
                
                //System.out.println("Requester id: " + borrowerId);
                Optional<UserProfile> tmpBorrowerProfile = userProfileService.findUserProfileById(borrowerId);
                
                if (tmpBorrowerProfile.isPresent()) {
                    Request newRequest = new Request(borrowerId,bookId);
                    newRequest.setBookTitle(bookService.getById(bookId).get().getTitle());
                    requestList.add(newRequest);
                    userProfileRequesters.add(tmpBorrowerProfile.get());
                } else {
                    System.out.println("User with id " + borrowerId + " not found.");
                }
                
            } catch (NumberFormatException e) {
                System.out.println("Invalid user id: " + id);
            }
        }
        
        //System.out.println(requestList.size());
        
        model.addAttribute("requestingUsers", requestList);
        model.addAttribute("userProfileRequesters", userProfileRequesters);
        
        
        List<String> reviewedRequests = userProfileService.getClosedRequests(getCurrentUser().get().getUserId());
        
        model.addAttribute("myResponses",getMyResponses(reviewedRequests));
        
		return "/dashboard";
	}
	
	public List<Request> getMyResponses(List<String> reviewedRequests){
		List<Request> myResponses = new ArrayList<>();
        
        //System.out.println(myResponses.size());
        
        for(String request: reviewedRequests) {
        	String[] tempRequest = request.split(",");
        	
        	Integer bookId = Integer.parseInt(tempRequest[0].trim());
        	String bookTitle = bookService.getById(bookId).get().getTitle();
        	
        	Integer status = Integer.parseInt(tempRequest[1].trim());
        	
        	Request newRequest = new Request(bookId,status,bookTitle);
        	myResponses.add(newRequest);
        }
        
        return myResponses;
	}
	
	@PostMapping("/search_exact_author")
	public String searchExactAuthor(Model model, @RequestParam("search") String query) {
		searchEngineStrategy = new BookAuthorExactStrategy();
		
		makeSearchResults(model, query);
		
		return "search_results";
	}
	
	@PostMapping("/search_inexact_author")
	public String searchInexactAuthor(Model model, @RequestParam("search") String query) {
		searchEngineStrategy = new BookAuthorInexactStrategy();
		
		makeSearchResults(model, query);
		
		return "search_results";
	}
	
	@PostMapping("/search_exact_title")
	public String searchExactTitle(Model model, @RequestParam("search") String query) {
		searchEngineStrategy = new BookTitleExactStrategy();
		
		makeSearchResults(model, query);
		
		return "search_results";
	}
	
	@PostMapping("/search_inexact_title")
	public String searchInexactTitle(Model model, @RequestParam("search") String query) {
		searchEngineStrategy = new BookTitleInexactStrategy();
		
		makeSearchResults(model, query);
		
		return "search_results";
	}
	
	public void makeSearchResults(Model model, @RequestParam("search") String query) {
		searchEngine.configureSearchEngine(searchEngineStrategy);
		
		List<Book> matchedBooks = searchEngine.doSearch(query,bookService);
		
		model.addAttribute("matchedBooks",matchedBooks);
		model.addAttribute("currentUserId",getCurrentUserProfile().get().getUserProfileId());
	}
	
	
	
	@PostMapping("accept_request")
	public String acceptRequest(@RequestParam("bookId") Integer bookId, @RequestParam("borrowerId") Integer borrowerId) {
		
		//System.out.println("Accept button clicked for Book ID: " + bookId + ", Borrower ID: " + borrowerId);
		
		//Accepted
		Request newRequest = new Request(borrowerId,bookId,1);
		requestService.saveRequest(newRequest);
		
		saveDeclinedRequests(bookId, borrowerId);
		
		bookService.getById(bookId).get().setBookOwnerId(borrowerId);
        bookService.saveBook(bookService.getById(bookId).get());
		
		userProfileService.deleteBookRequest(bookId);
		
        //System.out.println("Declined users size: " + declinedUsers.size());
		
		return "redirect:/success";
	}
	
	public void saveDeclinedRequests(int bookId, int borrowerId) {
		List<String> declinedUsers = userProfileService.getDeclinedUsers(bookId, borrowerId);
		
		for(String user: declinedUsers) {
			System.out.println(user);
			
			String[] tempUser = user.split(",");
			
			Integer tempBookId = Integer.parseInt(tempUser[0].trim()); 
			Integer tempUserId = Integer.parseInt(tempUser[1].trim()); 
			
			Request newRequest = new Request(tempUserId,tempBookId,0);
			requestService.saveRequest(newRequest);
		}
	}
	
	
	@PostMapping("/decline_request")
    public String declineRequest(@RequestParam("borrowerId") int borrowerId, @RequestParam("bookId") int bookId) {
        userProfileService.deleteSimpleBookRequest(borrowerId, bookId);
        Request newRequest = new Request(borrowerId, bookId,0);
        requestService.saveRequest(newRequest);
        return "redirect:/success";
    }
	
	@RequestMapping("/success/showInfo")
	public String showInfo(Model model) {
		return "dashboard";
	}
	
	@RequestMapping("/edit_profile")
	public String showSettings(Model model) {
		return "edit_profile";
	}
	
	@RequestMapping("/recommendations_authors")
    public String showRecommendationsByAuthor(Model model) {
		recommendationEngineStrategy = new FavouriteBookAuthorsStrategy();
		
		makeRecommendationResults(model);
		
        return "Recommendations";
    } 
	
	@RequestMapping("/recommendations_categories")
    public String showRecommendationsByCategory(Model model) {
		recommendationEngineStrategy = new FavouriteBookCategoryStrategy();
		
		makeRecommendationResults(model);
		
        return "Recommendations";
    } 
	
	public void makeRecommendationResults(Model model) {
		recommendationEngine.configureRecommendationEngine(recommendationEngineStrategy);
		
		List<Integer> recommended = recommendationEngine.recommend(getCurrentUserProfile().get().getUserProfileId(), bookService);
		List<Book> recommendedBooks = new ArrayList<>();
		
		for(Integer elem: recommended) {
			recommendedBooks.add(bookService.getById(elem).get());
		}
		
		model.addAttribute("recommendedBooks",recommendedBooks);
		model.addAttribute("currentUserId",getCurrentUserProfile().get().getUserProfileId());
	}
	
	@RequestMapping("/my_book_offers")
    public String showMyBookOffers(Model model) {
		int id = getCurrentUserProfile().get().getUserProfileId();
		
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
		
		//System.out.println(title);
		//System.out.println(bookAuthors);
		//System.out.println(bookCategory);
		//System.out.println(summary);
		
		Optional<BookCategory> selectedCategory = bookCategoryService.getCategoryByName(bookCategory);
		
		Book book = new Book(title,null,selectedCategory.get());
		
		book.setBookOwnerId(getCurrentUserProfile().get().getUserProfileId());
		book.setSummary(summary);
		
		bookService.saveBook(book);

		// Split bookAuthors by comma
	    String[] authorNames = bookAuthors.split(",");
	    
	    book.setBookAuthors(processBookAuthorsFromForm(authorNames, book));
	    
	    bookService.saveBook(book);
		
	    return "redirect:/my_book_offers";
    }
	
	public List<BookAuthor> processBookAuthorsFromForm(String[] authorNames, Book book){
		// Create author objects based on splits
	    List<BookAuthor> authors = new ArrayList<>();
	    
	    for (String authorName : authorNames) {
	        if(bookAuthorService.isPresent(authorName)) {
	        	//System.out.println(bookAuthorService.isPresent(authorName));
	        	bookAuthorService.getByAuthorName(authorName).get().addBook(book);
	        }else {
	        	BookAuthor author = new BookAuthor(authorName.trim(),null); // Trim whitespace around author name
	        	List<Book> tempBooks = new ArrayList<>();
	        	tempBooks.add(book);
	        	author.setBooks(tempBooks);
	        	bookAuthorService.saveBookAuthor(author); 
	        	//System.out.println("Author:" + authorName);
	        	authors.add(author);
	        }   
	    }
	    
	    return authors;
	} 
	
	@PostMapping("/delete_book_offer_by_title")
    public String deleteBookOffer(@RequestParam("title") String title,@RequestParam("bookId") int bookId, Model model) {
        //System.out.println("Request received to delete book offer with title: " + title);
        
        try {
            bookService.removeBook(bookId);
            //System.out.println("Book offer deleted successfully");
            model.addAttribute("successMessage", "Book offer deleted successfully.");
        } catch (Exception e) {
            //System.err.println("Failed to delete book offer: " + e.getMessage());
            model.addAttribute("errorMessage", "Failed to delete book offer: " + e.getMessage());
        }
        
        userProfileService.deleteBookRequestAfterBookOfferWithdraw(bookId);
        
        return "redirect:/my_book_offers";
    }
	
	
	@PostMapping("/request_book_offer")
	public String requestBookOffer(HttpServletRequest request, @RequestParam("bookId") int bookId,Model model) {
		Optional<Book> selectedBook = bookService.getById(bookId);
		
		getCurrentUserProfile().get().addBookOffer(selectedBook.get());
		selectedBook.get().addRequestingUser(getCurrentUserProfile().get());
		
		bookService.saveBook(selectedBook.get());
		
		String referer = request.getHeader("Referer");
		//System.out.println(referer);
		
        return "redirect:" + referer;
		//return "redirect:/book_offers";
	}
	
	
	@PostMapping("/updateProfile")
	public String updateProfile(Model model, @RequestParam(value = "username_profile", required = false) String username, 
			@RequestParam(value = "full_name", required = false) String fullName, 
			@RequestParam(value = "user_age", required = false) String userAge,
			@RequestParam(value = "user_address", required = false) String address,
            @RequestParam(value = "userEmail", required = false) String email,
            @RequestParam(value = "phoneNumber", required = false) String phone_number) {
		
		System.out.println("Hello");
		
		try {
            int age = Integer.parseInt(userAge);
            if (username.isEmpty() || fullName.isEmpty() || userAge.isEmpty()) {

                return "redirect:/edit_profile";
            }
            System.out.println("username_profile " + username);
            System.out.println("fullname " + fullName);
            System.out.println("Age " + age);
            
            setProfileInformation(username, fullName, userAge, address, email, phone_number);
            
            userProfileService.saveUserProfile(getCurrentUserProfile().get());
            
            model.addAttribute("currentUser",getCurrentUserProfile().get());
            
            return "redirect:/success";
        } catch (NumberFormatException e) {

            return "redirect:/edit_profile";
        }
	}
	
	public void setProfileInformation(String username, 
			String fullName, 
			String userAge,
            String address, 
            String email, 
            String phone_number) {
		
		 Optional<UserProfile> currentProfile = this.getCurrentUserProfile();
	     currentProfile.get().setUsernameProfile(username);
	     currentProfile.get().setFullName(fullName);
	     currentProfile.get().setUserAge(Integer.parseInt(userAge));
	     currentProfile.get().setUserAddress(address);
	     currentProfile.get().setUserEmail(email);
	     currentProfile.get().setPhoneNumber(phone_number);
	}
	
	
	@RequestMapping("/book_offers")
    public String showBookOffers(Model model) { 
        int myId = getCurrentUserProfile().get().getUserProfileId();
        
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
		int myId = getCurrentUserProfile().get().getUserProfileId();
        
        List<String> tempAuthors = userProfileService.getMyBookAuthors(myId);
        //System.out.println(myId);
        
        List<BookAuthor> bookAuthors = new ArrayList<>();
        
        for(String author: tempAuthors) {
            bookAuthors.add(bookAuthorService.findByAuthorName(author).get());
        }
        
        model.addAttribute("authors", bookAuthors);
        //System.out.println("hereeee yoooooooooooo");
        
        return "favourite_authors";
    }
	
	@RequestMapping("/favourite_categories")
    public String showFavouriteCategories(Model model) {
        int myId = getCurrentUserProfile().get().getUserProfileId();
        
        List<String> tempCategories = userProfileService.getMyBookCategories(myId);
        List<BookCategory> bookCategories= new ArrayList<>();
        
        for(String category: tempCategories) {
            bookCategories.add(bookCategoryService.getCategoryByName(category).get());
        }
        //System.out.println(myId);
        model.addAttribute("categories", bookCategories);
        //System.out.println("hereeee yoooooooooooo");
        
        return "favourite_categories";
    }

    @RequestMapping("/show_authors")
    public String showAuthors(Model model) {
    	int myId = getCurrentUserProfile().get().getUserProfileId();
    	
    	List<String> tempAuthors = userProfileService.getOtherBookAuthors(myId);
    	//System.out.println(myId);
    	
    	List<BookAuthor> bookAuthors = new ArrayList<>();
    	
    	for(String author: tempAuthors) {
    		bookAuthors.add(bookAuthorService.findByAuthorName(author).get());
    	}
    	
        model.addAttribute("authors", bookAuthors);
        //System.out.println("hereeee");
        return "select_authors"; 
    }
    
    @RequestMapping("/show_categories")
    public String showCategories(Model model) {
        model.addAttribute("categories", bookCategoryService.getAllCategories());
        //System.out.println("hereeee");
        
        int myId = getCurrentUserProfile().get().getUserProfileId();
        
        List<String> tempCategories = userProfileService.getOtherBookCategories(myId);
        //System.out.println(myId);
        List<BookCategory> bookCategories = new ArrayList<>();
        
        for(String category: tempCategories) {
            bookCategories.add(bookCategoryService.getCategoryByName(category).get());
        }
        
        model.addAttribute("categories", bookCategories);
        //System.out.println("hereeeeyoooo");
        
        return "select_categories"; 
    }  

    
    @PostMapping("/favourite_author_by_name")
    public String favouriteBookAuthor(@RequestParam("authorName") String authorName) {
    	Optional<BookAuthor> selectedBookAuthor = bookAuthorService.findByAuthorName(authorName);
        
        //int myId = getCurrentUserProfile().get().getUserProfileId();
        
        getCurrentUserProfile().get().addFavouriteBookAuthor(selectedBookAuthor.get());
        
        userProfileService.saveUserProfile(getCurrentUserProfile().get());
        
        //System.out.println(userProfileMapper.findOtherBookAuthors(myId));
        
    	return "redirect:/show_authors";
    }
    
    @PostMapping("/favourite_category_by_name")
    public String favouriteBookCategory(@RequestParam("categoryName") String categoryName) {
        Optional<BookCategory> selectedBookCategory = bookCategoryService.getCategoryByName(categoryName);
        
        //int myId = getCurrentUserProfile().get().getUserProfileId();
        
        getCurrentUserProfile().get().addFavouriteBookCategory(selectedBookCategory.get());
        
        userProfileService.saveUserProfile(getCurrentUserProfile().get());
        
        //System.out.println(userProfileMapper.findOtherBookCategories(myId));
        
        
        return "redirect:/show_categories";
    }
    
    @PostMapping("/remove_favourite_category")
    public String removeFavouriteCategory(@RequestParam("categoryName") String categoryName) {
        int myId = getCurrentUserProfile().get().getUserProfileId();
        
        userProfileService.deleteMyBookCategories(myId, categoryName);
        
        return "favourite_categories";
    }
    
    @PostMapping("/remove_favourite_author")
    public String removeFavouriteAuhtor(@RequestParam("authorName") String authorName) {
    	int myId = getCurrentUserProfile().get().getUserProfileId();
        
        userProfileService.deleteMyBookAuthors(myId, authorName);
    	
    	return "favourite_authors";
    } 
}
