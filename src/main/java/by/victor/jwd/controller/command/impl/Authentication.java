package by.victor.jwd.controller.command.impl;

import java.io.IOException;


import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.CustomerService;
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

		CustomerService customerService = ServiceProvider.getInstance().getCustomerService();

		Customer customer = null;
		RequestDispatcher requestDispatcher = null;
		try {
			customer = customerService.authorization(email, password);
			
			if (customer == null) {
				response.sendRedirect("Controller?command=gotosigninpage&message=no_such_user");
				return;
			}

			HttpSession session = request.getSession(true);
			session.setAttribute("customer", customer);
			response.sendRedirect("/lei-shoes");

		} catch (ServiceException e) {
			response.sendRedirect("Controller?command=gotosigninpage&message=wrong_e_or_p");
		}

	}

}
