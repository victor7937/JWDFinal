package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.controller.validator.ValidationProvider;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.exception.EmailExistsException;
import by.victor.jwd.service.exception.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.ParamValues.*;

public class UpdateProfile implements Command {
    private final static Logger logger = Logger.getLogger(UpdateProfile.class);
    private final static String SUCCESS_PATH = "Controller?command=gotoprofile&message=upd_success";
    private final static String WRONG_UPD_PATH = "Controller?command=gotoprofile&message=not_upd";
    private final static String PROFILE_PATH = "Controller?command=gotoprofile";
    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    private static final String ERROR_MSG_TEXT_EMAIL = "Sorry, this email has already taken";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestValidator requestValidator = ValidationProvider.getInstance().getProfileValidator();
        if (!requestValidator.validate(request)){
            logger.info("Profile updating data is incorrect");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PATH);
            requestDispatcher.forward(request, response);
            return;
        }

        String currentEmail = (String) request.getSession().getAttribute("email");
        Customer customer = new Customer(request.getParameter(NAME_PARAM), request.getParameter(EMAIL_PARAM),
                "", request.getParameter(PHONE_PARAM),  request.getParameter(COUNTRY_PARAM),
                request.getParameter(CITY_PARAM), request.getParameter(ADDRESS_PARAM));

        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        String password = request.getParameterValues(NEW_PASSWORD_PARAM)[0];

        if (!(password == null  || "".equals(password))) {
            customer.setPassword(password);
        } else {
            try {
                customer.setPassword(customerService.getByEmail(currentEmail).getPassword());
            } catch (ServiceException e) {
                logger.error("Can't update customers password",e);
                throw new ControllerException("Error while updating customers password");
            }
        }

        try {
            if (customerService.update(currentEmail, customer)){
                request.getSession().setAttribute("email", customer.getEmail());
                response.sendRedirect(SUCCESS_PATH);
            }
            else {
                logger.error("Profile wasn't updated");
                response.sendRedirect(WRONG_UPD_PATH);
            }
        } catch (EmailExistsException e) {
            logger.info("Email exists");
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMAIL);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PATH);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            //goToExceptionsPage
            logger.error("Can't update customer",e);
            throw new ControllerException("Error while updating customer");
        }
    }
}
