package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToProfile implements Command {
    private final static Logger logger = Logger.getLogger(GoToProfile.class);
    private static final String CUSTOMER_ATTRIBUTE = "customer";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String FORWARD_PATH = "/WEB-INF/jsp/profile.jsp";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL_ATTRIBUTE);
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        Customer customer = null;
        try {
            customer = customerService.getByEmail(email);
        } catch (ServiceException e) {
            logger.error("Getting profile info error", e);
            response.sendRedirect("/lei-shoes");
        }
        if (customer != null) {
            request.setAttribute(CUSTOMER_ATTRIBUTE, customer);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
            requestDispatcher.forward(request, response);
        }
        else {
            response.sendRedirect("/lei-shoes");
        }
    }
}
