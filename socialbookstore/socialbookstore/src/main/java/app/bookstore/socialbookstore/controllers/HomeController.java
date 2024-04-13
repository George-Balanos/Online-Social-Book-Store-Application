package app.bookstore.socialbookstore.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.bookstore.socialbookstore.domain.Role;
import app.bookstore.socialbookstore.domain.User;

@Controller
public class HomeController {

    @GetMapping("")
    public String home() {
        return "homepage";
    }
   
    @GetMapping("/login")
    public String showLoginForm(Model model) {
    	User user = new User();
    	model.addAttribute("user",user);
    	return "authentication/login";
    }
}