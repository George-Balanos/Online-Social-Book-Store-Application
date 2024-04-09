package app.bookstore.socialbookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.services.UserService;

@Controller
public class AuthenticationController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/login")
	public String login() {
		return "authentication/signin";
	}
	
	@RequestMapping("/register")
	public String register(Model model) {
		model.addAttribute("user", new User());
		return "authentication/signup";
	}
	
	@RequestMapping("/save")
	public String registerUser(@ModelAttribute("user") User user, Model model) {
		
		if(userService.isUserPresent(user)) {
			model.addAttribute("successMessage","User already registered!");
			return "authentication/signin";
		}
		
		userService.saveUser(user);
		model.addAttribute("successMessage","User registered successfully!");
		
		return "authentication/signin";	
	}
}
