package app.bookstore.socialbookstore.services;

import java.util.List;

import app.bookstore.socialbookstore.domain.Request;

public interface RequestService {
	
	public void saveRequest(Request request);
	public List<Request> showRequests(int borrowerId);
	
}
