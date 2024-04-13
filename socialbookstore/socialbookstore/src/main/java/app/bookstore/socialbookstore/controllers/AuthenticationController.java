package app.bookstore.socialbookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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
import app.bookstore.socialbookstore.services.UserService;

@Controller
public class AuthenticationController {
	@Autowired
    UserService userService;
	
	@GetMapping("/register")
	public String register(@ModelAttribute User user) {
		User newUser = new User();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(user.getPassword());
		
		
		return "authentication/signup";
	}
	
	@PostMapping("/save")
	public String signup(@ModelAttribute User user, Model model) {
		if (userService.isUserPresent(user)) {
            model.addAttribute("successMessage", "User already registered!");
            return "authentication/login";
        }
        
        userService.saveUser(user);
        model.addAttribute("successMessage", "User registered successfully");
        
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		
		return "authentication/login";
	}
}