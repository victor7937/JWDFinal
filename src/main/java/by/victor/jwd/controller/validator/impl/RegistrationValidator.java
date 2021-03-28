package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.util.RegionsAssistant;
import by.victor.jwd.controller.validator.RequestValidator;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Pattern;
import static by.victor.jwd.controller.constant.ParamValues.*;

public class RegistrationValidator implements RequestValidator {

    private final static String EMAIL_PATTERN = "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
    private final static String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])[a-zA-Z0-9-_!@]{7,40}$";
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
        if (!password.equals(passwordRepeat) || !isEmailValid(email) || !isPasswordValid(password)){
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

    public static boolean isEmailValid(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        return pattern.matcher(password).matches();
    }
}
