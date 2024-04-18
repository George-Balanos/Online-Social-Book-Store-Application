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
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.services.BookAuthorsService;
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
	BookMapper bookMapper;
	
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
		
		BookCategory category = new BookCategory(bookCategory,null);
		Book book = new Book(title,null,category);

		// Split bookAuthors by comma
	    String[] authorNames = bookAuthors.split(",");
	    
	    // Create author objects based on splits
	    List<BookAuthor> authors = new ArrayList<>();
	    for (String authorName : authorNames) {
	        if(bookAuthorService.isPresent(authorName)) {
	        	System.out.println("IFFFFFFFFF222");
	        	System.out.println(bookAuthorService.isPresent(authorName));
	        	bookAuthorService.getByAuthorName(authorName).get().addBook(book);
	        }else {
	        	System.out.println("elseeeee");
	        	BookAuthor author = new BookAuthor(authorName.trim(),null); // Trim whitespace around author name
	        	bookAuthorService.saveBookAuthor(author); 
	        	System.out.println("Author:" + authorName);
	        	authors.add(author);
	        }   
	    }
	    
	    book.setBookAuthors(authors);
	    
	    bookMapper.save(book);
		
		return "redirect:/success";
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
	
}
