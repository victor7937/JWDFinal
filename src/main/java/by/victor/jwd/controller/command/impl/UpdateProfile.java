package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
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

    private static final String PROFILE_PATH = CommandPath.createCommand(CommandName.GOTOPROFILE).createPath();
    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    private static final String ERROR_MSG_TEXT_EMAIL = "Sorry, this email has already taken";
    public static final String MESSAGE_PARAM = "message";
    public static final String UPD_SUCCESS_VALUE = "upd_success";
    public static final String UPD_FAIL_VALUE = "not_upd";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestValidator requestValidator = ValidationProvider.getInstance().getProfileValidator();
        if (!requestValidator.validate(request)){
            logger.info("Profile updating data is incorrect");
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PATH);
            requestDispatcher.forward(request, response);
            return;
        }

        String currentEmail = (String) request.getSession().getAttribute(EMAIL_PARAM);
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
                request.getSession().setAttribute(EMAIL_PARAM, customer.getEmail());
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(MESSAGE_PARAM, UPD_SUCCESS_VALUE)
                        .createPath());
            }
            else {
                logger.error("Profile wasn't updated");
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(MESSAGE_PARAM, UPD_FAIL_VALUE)
                        .createPath());
            }
        } catch (EmailExistsException e) {
            logger.info("Email exists");
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMAIL);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(PROFILE_PATH);
            requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            logger.error("Can't update customer",e);
            throw new ControllerException("Error while updating customer");
        }
    }
}
