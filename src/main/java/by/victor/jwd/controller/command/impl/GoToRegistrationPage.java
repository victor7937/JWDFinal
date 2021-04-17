package by.victor.jwd.controller.command.impl;

import java.io.IOException;


import by.victor.jwd.controller.command.Command;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToRegistrationPage implements Command {

	public static final String FORWARD_PATH = "/WEB-INF/jsp/register.jsp";

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
		requestDispatcher.forward(request, response);
	}

}
