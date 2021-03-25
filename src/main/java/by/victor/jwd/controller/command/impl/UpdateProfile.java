package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.controller.command.Command;
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
    private final static String EMAIL_EMPTY = "Controller?command=gotoprofile&message=email_empty";

    private final static String EMAIL_PARAM = "email";
    private final static String NAME_PARAM = "name";
    private final static String COUNTRY_PARAM = "country";
    private final static String CITY_PARAM = "city";
    private final static String ADDRESS_PARAM = "address";
    private final static String PHONE_PARAM = "phone";
    private final static String PASSWORD_PARAM = "newpass";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(EMAIL_PARAM);
        if (email == null || "".equals(email)) {
            response.sendRedirect(EMAIL_EMPTY);
            return;
        }
        Customer customer = (Customer) request.getSession().getAttribute("customer");
        String currentEmail = customer.getEmail();
        String[] passwords = request.getParameterValues(PASSWORD_PARAM);

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
        customer.setEmail(request.getParameter(EMAIL_PARAM));
        customer.setName(request.getParameter(NAME_PARAM));
        customer.setCountry(request.getParameter(COUNTRY_PARAM));
        customer.setCity(request.getParameter(CITY_PARAM));
        customer.setAddress(request.getParameter(ADDRESS_PARAM));
        customer.setPhone(request.getParameter(PHONE_PARAM));
    }
}
