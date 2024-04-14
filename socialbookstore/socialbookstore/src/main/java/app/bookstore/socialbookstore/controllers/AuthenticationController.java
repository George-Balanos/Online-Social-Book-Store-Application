package app.bookstore.socialbookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import app.bookstore.socialbookstore.domain.Role;
import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.services.UserProfileService;
import app.bookstore.socialbookstore.services.UserService;

@Controller
public class AuthenticationController {
	@Autowired
    UserService userService;
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	public BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/register")
	public String register(@ModelAttribute User user) {
		return "authentication/signup";
	}
	
	@PostMapping("/save")
    public String signup(@ModelAttribute User user, Model model, @RequestParam("password") String password, @RequestParam("confirmPassword") String confirmPassword) {
        System.out.println((confirmPassword.equals(password)));
        if (!(confirmPassword.equals(password))) {
            // Passwords don't match, return signup form with error message
            model.addAttribute("errorMessage", "Passwords do not match");
            return "authentication/signup";
        }

        if (userService.isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "authentication/login";
        }

        userService.saveUser(user);
        
        UserProfile newUserProfile = new UserProfile("","",0);
        newUserProfile.setUserProfileId(user.getUserId());
        newUserProfile.setUsernameProfile(user.getUsername());
        
        userProfileService.saveUserProfile(newUserProfile);
        
        model.addAttribute("successMessage", "User registered successfully");
        System.out.println(user.getUsername());
        System.out.println(user.getPassword());
        System.out.println(user.getUserId());

        return "authentication/login";
    }
}