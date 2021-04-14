package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.controller.validator.RequestValidator;

import javax.servlet.http.HttpServletRequest;

public class FootwearValidator implements RequestValidator {
    @Override
    public boolean validate(HttpServletRequest request) {
        return true;
    }
}
