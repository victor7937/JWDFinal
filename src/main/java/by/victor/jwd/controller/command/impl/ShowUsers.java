package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ShowUsers implements Command {

    private static final String CUSTOMERS_ATTRIBUTE = "customers";
    public static final String FORWARD_PATH = "/WEB-INF/jsp/admin-page.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        List<Customer> customers;
        try {
            customers = customerService.getAllCustomers();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        customers = customers.stream()
                .filter(c -> !c.getRole().equals(UserRole.ADMIN))
                .collect(Collectors.toList());
        request.setAttribute(CUSTOMERS_ATTRIBUTE, customers);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
