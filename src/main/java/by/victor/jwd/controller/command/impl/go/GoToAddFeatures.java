package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.GlobalParams.*;

public class GoToAddFeatures implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/add-features.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
