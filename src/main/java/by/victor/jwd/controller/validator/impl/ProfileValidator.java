package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.util.RegionsAssistant;
import by.victor.jwd.controller.validator.RequestValidator;

import javax.servlet.http.HttpServletRequest;

import static by.victor.jwd.controller.constant.CustomerParams.*;


public class ProfileValidator implements RequestValidator {
    private static final String ERROR_MSG_ATTRIBUTE = "err_message";
    private static final String ERROR_MSG_TEXT_EMPTY = "err_empty";
    private static final String ERROR_MSG_TEXT_EMAIL = "err_email";
    private static final String ERROR_MSG_TEXT_PHONE = "err_phone";

    public ProfileValidator () {}

    @Override
    public boolean validate(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PARAM);
        String name = request.getParameter(NAME_PARAM);
        String phoneNumber = request.getParameter(PHONE_PARAM);

        if (email == null || name == null || email.isBlank() || name.isBlank()) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMPTY);
            return false;
        }
        if (!RequestValidator.isEmailValid(email)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_EMAIL);
            return false;
        }
        if (!RegionsAssistant.isPhoneValid(phoneNumber)) {
            request.setAttribute(ERROR_MSG_ATTRIBUTE, ERROR_MSG_TEXT_PHONE);
            return false;
        }

        return true;
    }
}
