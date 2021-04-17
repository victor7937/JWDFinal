package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Order;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToProfile implements Command {
    private final static Logger logger = Logger.getLogger(GoToProfile.class);
    private static final String CUSTOMER_ATTRIBUTE = "customer";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String FORWARD_PATH = "/WEB-INF/jsp/profile.jsp";
    private static final String LANG_ATTRIBUTE = "lang";
    private static final String ORDERS_ATTRIBUTE = "orders";
    public static final String PROFILE_INFO_ERROR = "Error while getting profile info";


    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = (String) request.getSession().getAttribute(EMAIL_ATTRIBUTE);
        String lang = (String) request.getSession().getAttribute(LANG_ATTRIBUTE);
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        Customer customer;
        try {
            customer = customerService.getByEmail(email);
        } catch (ServiceException e) {
            throw new ControllerException(PROFILE_INFO_ERROR,e);
        }
        if (customer != null) {
            OrderService orderService = ServiceProvider.getInstance().getOrderService();
            List<Order> orders;
            try {
                orders = orderService.getOrdersOfCustomer(email, lang);
            } catch (ServiceException e) {
               throw new ControllerException(e);
            }
            request.setAttribute(ORDERS_ATTRIBUTE, orders);
            request.setAttribute(CUSTOMER_ATTRIBUTE, customer);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
            requestDispatcher.forward(request, response);
        }
        else {
            logger.error(PROFILE_INFO_ERROR);
            throw new ControllerException(PROFILE_INFO_ERROR);
        }
    }
}
