package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.Order;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.CustomerService;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateNewOrder implements Command {

    private final static Logger logger = Logger.getLogger(CreateNewOrder.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String customerEmail = (String) request.getSession().getAttribute("email");

        if (customerEmail == null || customerEmail.isEmpty()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam("message","not_logged_in").createPath());
            return;
        }

        String[] arts = request.getParameterValues("art");
        String[] sizes = request.getParameterValues("size");
        String[] quantities = request.getParameterValues("quantity");

        if ((arts == null || sizes == null || quantities == null) || (arts.length == 0 || sizes.length == 0 || quantities.length == 0) ||
                (arts.length != sizes.length || quantities.length != sizes.length)){
            logger.error("Illegal order params");
            throw new ControllerException("Illegal order params");
        }

        List<FootwearItem> items = new ArrayList<>();
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        CustomerService customerService = ServiceProvider.getInstance().getCustomerService();

        try {
            for (int i = 0; i < arts.length; i++) {
                Footwear footwear = footwearService.getByArt(arts[i],"en");
                FootwearItem footwearItem = new FootwearItem(footwear, Float.parseFloat(sizes[i]), Integer.parseInt(quantities[i]));
                items.add(footwearItem);
            }
        } catch (ServiceException e) {
            logger.error("Error getting footwears for order");
            throw new ControllerException("Error getting footwears for order");
        }

        Customer customer;
        try {
            customer = customerService.getByEmail(customerEmail);
        } catch (ServiceException e) {
            logger.error("Error getting customer for order");
            throw new ControllerException("Error getting customer for order");
        }

        if (customer == null) {
            logger.error("Customer don't find");
            throw new ControllerException("Customer don't find");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime currentDate = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);

        Order order = new Order(customer, currentDate, items);
        order.setPrice(countPrice(items));

        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        try {
            if (orderService.createNewOrder(order)) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().contains("art_")) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam("message","success").createPath());

            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam("message","fail").createPath());
            }

        } catch (ServiceException e) {
            throw new ControllerException("Unable to create order");
        }

    }

    private Float countPrice(List<FootwearItem> items) {
        float total = 0.0f;
        for (FootwearItem item: items) {
            total += item.getQuantity() * item.getFootwear().getPrice();
        }
        return total;
    }
}
