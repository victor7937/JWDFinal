package by.victor.jwd.controller.command.impl;

import java.io.IOException;


import by.victor.jwd.bean.User;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.UserService;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Authentication implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		ServiceProvider provider = ServiceProvider.getInstance();
		UserService userService = provider.getUserService();

		User user = null;
		RequestDispatcher requestDispatcher = null;
		try {
			user = userService.authorization(email, password);
			
			if (user == null) {
				response.sendRedirect("Controller?command=gotosigninpage&message=no_such_user");
				return;
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("user", user);
			response.sendRedirect("/lei-shoes");

		} catch (ServiceException e) {
			response.sendRedirect("Controller?command=gotosigninpage&message=wrong_e_or_p");
		}

	}

}
