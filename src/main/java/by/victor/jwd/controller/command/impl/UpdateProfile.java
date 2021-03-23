package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.listner.ContextListener;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class UpdateProfile implements Command {
    private final static Logger logger = Logger.getLogger(UpdateProfile.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        if (email == null || "".equals(email)) {
            response.sendRedirect("Controller?command=gotoprofile&message=email_empty");
            return;
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String currentEmail = customer.getEmail();
        String[] passwords = request.getParameterValues("newpass");

        if (!Objects.equals(passwords[0], passwords[1])) {
            response.sendRedirect("Controller?command=gotoprofile&message=password_not_equals");
            return;
        }
        customerSetter(customer, request);

        if (!((passwords[0] == null ) || ("".equals(passwords[0])))) {
            customer.setPassword(passwords[0]);
        }

        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();
        try {
            if (customerService.update(currentEmail, customer)){
                request.getSession().removeAttribute("customer");
                request.getSession().setAttribute("customer", customer);
                response.sendRedirect("/lei-shoes");
            }
            else {
                response.sendRedirect("Controller?command=gotoprofile&message=smth_is_wrong");
            }
        } catch (ServiceException e) {
            //goToExceptionsPage
            logger.error("Can't update customer",e);
            response.sendRedirect("/lei-shoes");
        }

    }

    private static void customerSetter (Customer customer, HttpServletRequest request ){
        customer.setEmail(request.getParameter("email"));
        customer.setName(request.getParameter("name"));
        customer.setCountry(request.getParameter("country"));
        customer.setCity(request.getParameter("city"));
        customer.setAddress(request.getParameter("address"));
        customer.setPhone(request.getParameter("phone"));
    }
}
