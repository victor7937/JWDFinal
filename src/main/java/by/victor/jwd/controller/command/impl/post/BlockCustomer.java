package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.bean.UserRole;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.CustomerParams.EMAIL_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class BlockCustomer implements Command {

    private static final String BLOCKED_PARAM = "blocked";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAM);
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        try {
            if (customerService.changeRole(email, UserRole.BLOCK)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.SHOWUSERS)
                        .addParam(BLOCKED_PARAM, SUCCESS)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            }
            else {
                response.sendRedirect(CommandPath.createCommand(CommandName.SHOWUSERS)
                        .addParam(BLOCKED_PARAM, FAIL)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
