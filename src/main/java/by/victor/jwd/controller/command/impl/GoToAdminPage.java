package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.GlobalParams.ROLE_ATTRIBUTE;


public class GoToAdminPage implements Command {

    private static final String ADMIN_VALUE = "admin";
    private static final int ERROR_CODE = 403;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE);
            return;
        }
        response.sendRedirect(CommandPath.createCommand(CommandName.SHOWUSERS).createPath());
    }
}
