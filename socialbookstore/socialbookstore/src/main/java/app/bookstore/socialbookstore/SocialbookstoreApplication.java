package app.bookstore.socialbookstore;

import java.util.ArrayList;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import app.bookstore.socialbookstore.domain.Book;
import app.bookstore.socialbookstore.domain.BookAuthor;
import app.bookstore.socialbookstore.domain.UserProfile;
import app.bookstore.socialbookstore.mappers.BookAuthorMapper;
import app.bookstore.socialbookstore.mappers.BookCategoryMapper;
import app.bookstore.socialbookstore.mappers.BookMapper;
import app.bookstore.socialbookstore.mappers.UserMapper;
import app.bookstore.socialbookstore.mappers.UserProfileMapper;

@SpringBootApplication
public class SocialbookstoreApplication{

	public static void main(String[] args) {
		SpringApplication.run(SocialbookstoreApplication.class, args);
	}

}
