package ua.nure.cs.yurchenko.usermanagement.domain.web;

import java.io.IOException;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

import ua.nure.cs.yurchenko.usermanagement.domain.User;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DaoFactory;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class EditServlet extends HttpServlet {

	protected void service(HttpServletRequest req, HttpServletResponse resp)
		throws ServletException, IOException {
		if (req.getParameter("addButton") != null) {
			doOk(req, resp);
		} else if (req.getParameter("cancelButton") != null) {
			doCancel(req, resp);
		} else {
			showPage(req, resp);
		}
	}

	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/edit.jsp").forward(req, resp);
	}

	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/browse.jsp").forward(req, resp);
	}

	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// TODO Auto-generated method stub
		User user = null;
		try {
			user = getUser(req);
		} catch (ValidationException e1) {
			// TODO Auto-generated catch block
			req.setAttribute("error", e1.getMessage());
			showPage(req, resp);
			return;
		}
		try {
			processUser(user);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			new ServletException(e);
		}
		req.getRequestDispatcher("/browse").forward(req, resp);
	}

	protected User getUser(HttpServletRequest req) throws ValidationException, java.text.ParseException {
		// TODO Auto-generated method stub
		User user = new User();
		String idStr = req.getParameter("id");
		String firstName = req.getParameter("firstName");
		String lastName = req.getParameter("lastName");
		String dateStr = req.getParameter("date");
		
		if (firstName == null) {
			throw new ValidationException("First name is empty");
		}
		
		if (lastName == null) {
			throw new ValidationException("Last name is empty");
		}
		
		if (dateStr == null) {
			throw new ValidationException("Date is empty");
		}
		
		if (idStr != null) {
			user.setId(new Long(idStr));
		}
		user.setFirstName(firstName);
		user.setLastName(lastName);
		try {
			user.setDateOfBirth(DateFormat.getDateInstance().parse(dateStr));
		} catch (ParseException e) {
			throw new ValidationException("Date format is incorrect");
		}
		return user;
	}

	protected void processUser(User user) throws DatabaseException {
		// TODO Auto-generated method stub
		DaoFactory.getInstance().getUserDao().update(user);
	}
}
