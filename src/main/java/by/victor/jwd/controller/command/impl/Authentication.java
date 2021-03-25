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

	private static final String EMAIL_PARAM = "email";
	private static final String PASSWORD_PARAM = "password";
	private static final String SESSION_ATTRIBUTE = "CUSTOMER";
	private static final String REGISTR_SUCCESS_REDIRECT = "/lei-shoes";
	private static final String WRONG_E_OR_P_REDIRECT = "Controller?command=gotosigninpage&message=wrong_e_or_p";


	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String email = request.getParameter(EMAIL_PARAM);
		String password = request.getParameter(PASSWORD_PARAM);

		CustomerService customerService = ServiceProvider.getInstance().getCustomerService();

		try {
			Customer customer = customerService.authorization(email, password);
			
			if (customer == null) {
				response.sendRedirect(WRONG_E_OR_P_REDIRECT);
				return;
			}

			HttpSession session = request.getSession(true);
			session.setAttribute(SESSION_ATTRIBUTE, customer);
			response.sendRedirect(REGISTR_SUCCESS_REDIRECT);

		} catch (ServiceException e) {
			response.sendRedirect("Controller?command=gotosigninpage&message=error");
		}

	}

}
