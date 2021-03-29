package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.util.RegionsAssistant;
import by.victor.jwd.controller.validator.RequestValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static by.victor.jwd.controller.constant.ParamValues.*;


public class ProfileValidator implements RequestValidator {
    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    private static final String ERROR_MSG_TEXT_EMPTY = "Sorry, necessary fields are empty!";
    private static final String ERROR_MSG_TEXT_PASSWORD = "Sorry, password is invalid!";
    private static final String ERROR_MSG_TEXT_EMAIL = "Sorry, email is invalid!";
    private static final String ERROR_MSG_TEXT_PHONE = "Sorry, this phone is not valid!";

    Logger logger = Logger.getLogger(ProfileValidator.class);

    public ProfileValidator () {}

    @Override
    public boolean validate(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM);
        String name = request.getParameter(NAME_PARAM);
        String phoneNumber = request.getParameter(PHONE_PARAM);
        String password = request.getParameterValues(NEW_PASSWORD_PARAM)[0];
        String passwordRepeat = request.getParameterValues(NEW_PASSWORD_PARAM)[1];

        if (email == null || name == null || "".equals(email) || "".equals(name)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMPTY);
            logger.info("Empty fields");
            return false;
        }

        if (!((password == null && passwordRepeat == null) || ("".equals(password) && "".equals(passwordRepeat))) &&
                (!Objects.equals(password, passwordRepeat) || !RequestValidator.isPasswordValid(password))) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_PASSWORD);
            logger.info("Password is invalid");
            return false;
        }
        if (!RequestValidator.isEmailValid(email)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMAIL);
            logger.info("Email is invalid");
            return false;
        }
        if (!RegionsAssistant.isPhoneValid(phoneNumber)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_PHONE);
            logger.info("Phone is invalid");
            return false;
        }

        return true;
    }
}
