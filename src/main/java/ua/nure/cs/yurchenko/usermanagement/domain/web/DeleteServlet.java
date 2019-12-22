package ua.nure.cs.yurchenko.usermanagement.domain.web;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.ValidationException;

import ua.nure.cs.yurchenko.usermanagement.domain.User;
import ua.nure.cs.yurchenko.usermanagement.domain.db.DatabaseException;

public class DeleteServlet extends EditServlet {

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/delete.jsp").forward(req, resp);
	}

	@Override
	protected void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doCancel(req, resp);
	}

	@Override
	protected void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		// TODO Auto-generated method stub
		try {
            DaoFactory.getInstance().getUserDao().delete((User) req.getSession().getAttribute("user"));
        } catch (DatabaseException e) {
            req.setAttribute("error", "Error in the database: " + e.getMessage());
            req.getRequestDispatcher("/delete.jsp").forward(req, resp);
        }
        req.getRequestDispatcher("/browse").forward(req, resp);
	}
}
