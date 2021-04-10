package by.victor.jwd.controller.command.impl;

import java.io.IOException;


import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.exception.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.CustomerService;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.victor.jwd.controller.constant.ParamValues.*;

public class Authentication implements Command {

	private final static Logger logger = Logger.getLogger(Authentication.class);

	private static final String SESSION_ATTRIBUTE = "email";
	private static final String AUTH_SUCCESS_REDIRECT = "/lei-shoes";
	private static final String WRONG_E_OR_P_REDIRECT = "Controller?command=gotosigninpage&message=wrong_e_or_p";
	private static final String ROLE_ATTRIBUTE = "role";


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
			session.setAttribute(SESSION_ATTRIBUTE, customer.getEmail());
			if (customer.getRole() == UserRole.ADMIN) {
				session.setAttribute(ROLE_ATTRIBUTE, UserRole.ADMIN.toString().toLowerCase());
			} else {
				session.setAttribute(ROLE_ATTRIBUTE, UserRole.USER.toString().toLowerCase());
			}
			response.sendRedirect(AUTH_SUCCESS_REDIRECT);

		} catch (ServiceException e) {
			logger.error("Authentication error ", e );
			throw new ControllerException("Authentication error");
		}

	}

}
