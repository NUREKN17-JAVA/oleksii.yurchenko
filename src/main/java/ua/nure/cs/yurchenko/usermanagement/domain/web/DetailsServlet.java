package ua.nure.cs.yurchenko.usermanagement.domain.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DetailsServlet extends EditServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if (req.getParameter("backButton") != null) {
            req.getRequestDispatcher("/browse").forward(req, resp);
        } else {
            showPage(req, resp);
        }
	}

	@Override
	protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.getRequestDispatcher("/details.jsp").forward(req, resp);
	}

}
