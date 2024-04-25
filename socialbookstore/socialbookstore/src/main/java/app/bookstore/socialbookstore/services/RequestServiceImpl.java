package app.bookstore.socialbookstore.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.bookstore.socialbookstore.domain.Request;
import app.bookstore.socialbookstore.mappers.RequestMapper;

@Service
public class RequestServiceImpl implements RequestService{
	
	@Autowired
	RequestMapper requestMapper;

	@Override
	public void saveRequest(Request request) {
		requestMapper.save(request);
	}

	@Override
	public List<Request> showRequests(int borrowerId) {
		List<Request> requests = requestMapper.findByBorrowerId(borrowerId);
		
		return requests;
	}

}
