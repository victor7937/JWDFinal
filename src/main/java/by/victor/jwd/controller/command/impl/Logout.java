package by.victor.jwd.controller.command.impl;

import java.io.IOException;



import by.victor.jwd.controller.command.Command;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Logout implements Command{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		
		if(session != null) {
			session.invalidate();
		}

		response.sendRedirect("/lei-shoes");
		
	}

}
