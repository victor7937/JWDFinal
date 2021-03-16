package by.victor.jwd.controller.command.impl;

import java.io.IOException;
import java.util.Arrays;

import by.victor.jwd.bean.User;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveNewUser implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		System.out.println(request.getParameter("name"));
//		System.out.println(request.getParameter("email"));
//		System.out.println(Arrays.toString(request.getParameterValues("phone")));
//		System.out.println(request.getParameter("country"));
//		System.out.println(request.getParameter("city"));
//		System.out.println(request.getParameter("address"));
//		System.out.println(Arrays.toString(request.getParameterValues("password")));

		if (request.getParameter("email") == null || request.getParameter("password") == null ||
		request.getParameter("name") == null) {
			response.sendRedirect("Controller?command=registration&message=wrong_input");
			return;
		}
		if (request.getParameter("email").isEmpty() || request.getParameter("password").isEmpty() ||
				request.getParameter("name").isEmpty()) {
			response.sendRedirect("Controller?command=registration&message=wrong_input");
			return;
		}

		if (!request.getParameterValues("password")[0].equals(request.getParameterValues("password")[1])){
			response.sendRedirect("Controller?command=registration&message=wrong_passw");
			return;
		}

		User user = new User(request.getParameter("name"), request.getParameter("email"),
				request.getParameterValues("password")[0], request.getParameterValues("phone")[0] + request.getParameterValues("phone")[1],  request.getParameter("country"),
				request.getParameter("city"),request.getParameter("address"));

		UserService userService = ServiceProvider.getInstance().getUserService();
		try {
			if (userService.registration(user)) {
				response.sendRedirect("Controller?command=auth&email=" + user.getEmail() + "&password=" +
						user.getPassword());
				return;
			}
		} catch (ServiceException e) {
			response.sendRedirect("Controller?command=registration&message=reg_fail");
			return;
		}


		//regInfo - show in console
		//System.out.println("class SaveNewUser implements Command");
		
		//request.setAttribute("message", "Registration OK");
		
		response.sendRedirect("/lei-shoes");
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
		//requestDispatcher.forward(request, response);
		
	}

}
