package app.bookstore.socialbookstore.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import app.bookstore.socialbookstore.domain.BookCategory;
import app.bookstore.socialbookstore.domain.Role;
import app.bookstore.socialbookstore.domain.User;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;

@Controller
public class HomeController {
	
	@Autowired
	BookCategoryMapper bookCategoryMapper;

    @GetMapping("")
    public String home() {
    	
    	BookCategory bookCategory1 = new BookCategory(1,"Drama");
    	BookCategory bookCategory2 = new BookCategory(2,"Sci-fi");
    	BookCategory bookCategory3 = new BookCategory(3,"Fantasy");
    	BookCategory bookCategory4 = new BookCategory(4,"Romance");
    	BookCategory bookCategory5 = new BookCategory(5,"Comedy");
    	
    	bookCategoryMapper.save(bookCategory1);
    	bookCategoryMapper.save(bookCategory2);
    	bookCategoryMapper.save(bookCategory3);
    	bookCategoryMapper.save(bookCategory4);
    	bookCategoryMapper.save(bookCategory5);
    	
        return "homepage";
    }
   
    @GetMapping("/login")
    public String showLoginForm(Model model) {
    	User user = new User();
    	model.addAttribute("user",user);
    	return "authentication/login";
    }
}