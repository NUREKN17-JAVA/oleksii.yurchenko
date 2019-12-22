package ua.nure.cs.yurchenko.usermanagement.domain.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ua.nure.cs.yurchenko.usermanagement.domain.User;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class AddServlet extends EditServlet {

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/add.jsp").forward(req, resp);
	}

	@Override
	protected void processUser(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		DaoFactory.getInstance().getUserDao().create(user);
	}
	
}
