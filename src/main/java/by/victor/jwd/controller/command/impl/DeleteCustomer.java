package by.victor.jwd.controller.command.impl;

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

import static by.victor.jwd.controller.constant.ParamValues.EMAIL_PARAM;

public class DeleteCustomer implements Command {

    private static final String DELETED_PARAM = "deleted";
    private static final String SHOW_PARAM = "show";
    private static final String SUCCESS_VALUE = "success";
    private static final String YES_VALUE = "yes";
    private static final String FAIL_VALUE = "fail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAM);
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        try {
            if (customerService.delete(email)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOADMINPAGE)
                        .addParam(DELETED_PARAM, SUCCESS_VALUE)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            }
            else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOADMINPAGE)
                        .addParam(DELETED_PARAM, FAIL_VALUE)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException("Delete customer error", e);
        }
    }
}
