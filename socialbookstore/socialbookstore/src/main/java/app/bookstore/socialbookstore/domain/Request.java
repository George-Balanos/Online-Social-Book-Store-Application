package app.bookstore.socialbookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="requests")
public class Request {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="book_id")
	private int bookId;

	@Column(name="borrower_id")
	private int borrowerId;
	
	@Column(name="status")
	private int status;
	
	private String bookTitle;
	
	public Request(int borrowerId,int bookId) {
		this.borrowerId = borrowerId;
		this.bookId = bookId;
	}
	
	public Request(int borrowerId,int bookId, int status) {
		this.borrowerId = borrowerId;
		this.bookId = bookId;
		this.status = status;
	}
	
	public Request(int bookId, int status, String title) {
		this.bookId = bookId;
		this.bookTitle = title;
		this.status = status;
	}
	
	public Request() {
		
	}

	public int getBorrowerId() {
		return borrowerId;
	}

	public void setBorrowerId(int borrowerId) {
		this.borrowerId = borrowerId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	public String getBookTitle() {
		return this.bookTitle;
	}
}
