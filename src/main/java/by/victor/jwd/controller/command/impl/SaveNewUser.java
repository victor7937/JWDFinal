package by.victor.jwd.controller.command.impl;

import java.io.IOException;



import by.victor.jwd.bean.RegistrationInfo;
import by.victor.jwd.controller.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SaveNewUser implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println(request.getParameter("name"));
		System.out.println(request.getParameter("surname"));
		
		RegistrationInfo regInfo = new RegistrationInfo();
		
		//regInfo - show in console
		//System.out.println("class SaveNewUser implements Command");
		
		//request.setAttribute("message", "Registration OK");
		
		response.sendRedirect("Controller?command=gotoindexpage&message=Registration ok");
		//RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
		//requestDispatcher.forward(request, response);
		
	}

}
