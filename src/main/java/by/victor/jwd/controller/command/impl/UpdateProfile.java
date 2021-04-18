package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.controller.validator.ValidationProvider;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.CustomerParams.*;
import static by.victor.jwd.controller.constant.GlobalParams.MESSAGE_PARAM;

public class UpdateProfile implements Command {

    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    public static final String UPD_SUCCESS_VALUE = "upd_success";
    public static final String UPD_FAIL_VALUE = "not_upd";
    private static final String AUTHORIZATION_ERROR = "Authorization error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestValidator requestValidator = ValidationProvider.getInstance().getProfileValidator();
        if (!requestValidator.validate(request)){
            String message = (String)request.getAttribute(ERROR_MSG_ATTRIBUTE);
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                    .addParam(MESSAGE_PARAM, message)
                    .createPath());
            return;
        }

        String email = request.getParameter(EMAIL_PARAM);
        String currentEmail = (String) request.getSession().getAttribute(EMAIL_PARAM);
        if (currentEmail == null || currentEmail.isBlank() || !currentEmail.equals(email)){
            throw new ControllerException(AUTHORIZATION_ERROR);
        }

        Customer customer = new Customer(request.getParameter(NAME_PARAM), email, "",
                request.getParameter(PHONE_PARAM),  request.getParameter(COUNTRY_PARAM),
                request.getParameter(CITY_PARAM), request.getParameter(ADDRESS_PARAM));

        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();

        try {
            if (customerService.update(customer)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(MESSAGE_PARAM, UPD_SUCCESS_VALUE)
                        .createPath());
            }
            else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(MESSAGE_PARAM, UPD_FAIL_VALUE)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
