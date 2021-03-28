package by.victor.jwd.controller.validator;

import javax.servlet.http.HttpServletRequest;

public interface RequestValidator {
    public boolean validate(HttpServletRequest request);
}
