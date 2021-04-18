package by.victor.jwd.controller.command.impl;

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

import static by.victor.jwd.controller.constant.CustomerParams.NEW_PASSWORD_PARAM;
import static by.victor.jwd.controller.constant.CustomerParams.PASSWORD_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.EMAIL_ATTRIBUTE;
import static by.victor.jwd.controller.constant.GlobalParams.MESSAGE_PARAM;

public class ChangePassword implements Command {

    private static final String AUTHORIZATION_ERROR = "Authorization error";
    private static final String NOT_EQ_VALUE = "not_eq";
    private static final String CHANGE_SUCCESS_VALUE = "change_psw_success";
    private static final String NOT_CHANGED_VALUE = "not_changed";
    private static final String INCORRECT_DATA_VALUE = "incorrect_data";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL_ATTRIBUTE);
        if (email == null || email.isBlank()) {
            throw new ControllerException(AUTHORIZATION_ERROR);
        }

        String currentPassword = request.getParameter(PASSWORD_PARAM);

        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        String realPassword;
        try {
            realPassword = customerService.getPasswordByEmail(email);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (!realPassword.equals(currentPassword)) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCHANGEPASSWORD)
                    .addParam(MESSAGE_PARAM, NOT_EQ_VALUE)
                    .createPath());
            return;
        }

        RequestValidator validator = ValidationProvider.getInstance().getPasswordValidator();

        if (!validator.validate(request)) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCHANGEPASSWORD)
                    .addParam(MESSAGE_PARAM, INCORRECT_DATA_VALUE)
                    .createPath());
            return;
        }

        String newPassword = request.getParameterValues(NEW_PASSWORD_PARAM)[0];
        try {
            if (customerService.updatePassword(email, newPassword)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(MESSAGE_PARAM, CHANGE_SUCCESS_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCHANGEPASSWORD)
                        .addParam(MESSAGE_PARAM, NOT_CHANGED_VALUE)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

    }
}
