package by.victor.jwd.controller.command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Command class should execute some actions with HttpServletRequest and HttpServletResponse
 */
public interface Command {
	
	void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
