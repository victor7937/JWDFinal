package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.util.RegionsAssistant;
import by.victor.jwd.controller.validator.RequestValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import static by.victor.jwd.controller.constant.ParamValues.*;

public class RegistrationValidator implements RequestValidator {

    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    private static final String ERROR_MSG_TEXT_EMPTY = "Sorry, necessary field are empty!";
    private static final String ERROR_MSG_TEXT_E_OR_P = "Sorry, email or password is invalid!";
    private static final String ERROR_MSG_TEXT_PHONE = "Sorry, this phone is not valid!";

    Logger logger = Logger.getLogger(RegistrationValidator.class);

    public RegistrationValidator () {}

    @Override
    public boolean validate(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM);
        String name = request.getParameter(NAME_PARAM);
        String phoneCode = request.getParameterValues(PHONE_PARAM)[0];
        String phoneNumber = request.getParameterValues(PHONE_PARAM)[1];
        String password = request.getParameterValues(PASSWORD_PARAM)[0];
        String passwordRepeat = request.getParameterValues(PASSWORD_PARAM)[1];

        if (email == null || password == null || passwordRepeat == null || name == null || "".equals(email) ||
                "".equals(name) || "".equals(password) || "".equals(passwordRepeat)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMPTY);
            logger.info("Empty fields");
            return false;
        }
        if (!password.equals(passwordRepeat) || !RequestValidator.isEmailValid(email) || !RequestValidator.isPasswordValid(password)){
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_E_OR_P);
            logger.info("Email or password is invalid");
            return false;
        }
        if (!RegionsAssistant.isPhoneValid(phoneCode, phoneNumber)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_PHONE);
            logger.info("Phone is not valid");
            return false;
        }

        return true;
    }

}
