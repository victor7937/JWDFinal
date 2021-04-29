package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.validator.RequestValidator;

import javax.servlet.http.HttpServletRequest;

import static by.victor.jwd.controller.constant.CustomerParams.NEW_PASSWORD_PARAM;
import static by.victor.jwd.controller.constant.CustomerParams.PASSWORD_PARAM;

/**
 * Validates password changing form
 */
public class PasswordValidator implements RequestValidator {

    @Override
    public boolean validate(HttpServletRequest request) {
        String password = request.getParameterValues(NEW_PASSWORD_PARAM)[0];
        String passwordRepeat = request.getParameterValues(NEW_PASSWORD_PARAM)[1];
        String currentPassword = request.getParameter(PASSWORD_PARAM);

        if (password == null || passwordRepeat == null || password.isBlank() || passwordRepeat.isBlank() ||
                !password.equals(passwordRepeat)) {
            return false;
        }
        if (password.equals(currentPassword)) {
            return false;
        }

        return RequestValidator.isPasswordValid(password);
    }
}
