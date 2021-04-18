package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.GlobalParams.EMAIL_ATTRIBUTE;

public class GoToChangePassword implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/change-password.jsp";
    private static final String AUTHORIZATION_ERROR = "Authorization error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String email = (String) request.getSession().getAttribute(EMAIL_ATTRIBUTE);
        if (email == null || email.isBlank()) {
            throw new ControllerException(AUTHORIZATION_ERROR);
        }

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
