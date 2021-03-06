package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.controller.validator.ValidationProvider;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.EmailExistsException;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.CustomerParams.*;
import static by.victor.jwd.controller.constant.GlobalParams.MESSAGE_PARAM;

public class SaveNewCustomer implements Command {
	public static final String SUCCESS_VALUE = "register_success";
	public static final String REGISTRATION_FAIL_MESSAGE = "Registration fail";
	private static final String INCORRECT_CUSTOMER_ATTRIBUTE = "incorrect_customer";
	private static final String ERROR_MSG_ATTRIBUTE = "err_message";
	private static final String ERROR_MSG_TEXT_EMAIL = "Sorry, this email has already taken";
	public static final String FORWARD_PATH = "/WEB-INF/jsp/register.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestValidator requestValidator = ValidationProvider.getInstance().getRegistrationValidator();

		Customer customer = new Customer(request.getParameter(NAME_PARAM), request.getParameter(EMAIL_PARAM),
				request.getParameterValues(PASSWORD_PARAM)[0], request.getParameterValues(PHONE_PARAM)[0] + request.getParameterValues(PHONE_PARAM)[1],  request.getParameter(COUNTRY_PARAM),
				request.getParameter(CITY_PARAM),request.getParameter(ADDRESS_PARAM));

		if (!requestValidator.validate(request)){
			request.setAttribute(INCORRECT_CUSTOMER_ATTRIBUTE, customer);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
			requestDispatcher.forward(request, response);
			return;
		}

		CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
		try {
			if (customerService.registration(customer)) {
				response.sendRedirect(CommandPath.createCommand(CommandName.GOTOSIGNINPAGE)
						.addParam(MESSAGE_PARAM, SUCCESS_VALUE)
						.createPath());
			}
			else {
				throw new ControllerException(REGISTRATION_FAIL_MESSAGE);
			}
		} catch (EmailExistsException e) {
			request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMAIL);
			request.setAttribute(INCORRECT_CUSTOMER_ATTRIBUTE, customer);
			RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
			requestDispatcher.forward(request, response);
		} catch (ServiceException e) {
			throw new ControllerException(REGISTRATION_FAIL_MESSAGE, e);
		}
	}
}
