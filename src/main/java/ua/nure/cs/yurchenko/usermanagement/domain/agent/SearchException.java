package ua.nure.cs.yurchenko.usermanagement.domain.agent;

import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class SearchException extends Exception {

	public SearchException(DatabaseException e) {
		// TODO Auto-generated constructor stub
		e.printStackTrace();
	}
	
}
