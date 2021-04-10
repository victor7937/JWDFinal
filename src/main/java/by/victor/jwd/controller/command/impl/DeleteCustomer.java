package by.victor.jwd.controller.command.impl;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.ConnectException;

public class DeleteCustomer implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        try {
            if (customerService.delete(email)) {
                response.sendRedirect("Controller?command=gotoadminpage&deleted=success&show=yes");
            }
            else {
                response.sendRedirect("Controller?command=gotoadminpage&deleted=fail&show=yes");
            }
        } catch (ServiceException e) {
            throw new ControllerException("Delete customer error", e);
        }
    }
}
