package by.victor.jwd.controller.command.impl.post;

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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.FootwearParams.SIZE_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class CreateNewOrder implements Command {

    private final static Logger logger = Logger.getLogger(CreateNewOrder.class);
    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String ART_PREFIX = "art_";
    private static final String QUANTITY_PARAM = "quantity";
    private static final String EMAIL_PARAM = "email";
    private static final String LANG_DEFAULT = "en";
    private static final String NOT_LOGGED_IN_VALUE = "not_logged_in";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String customerEmail = (String) request.getSession().getAttribute(EMAIL_PARAM);

        if (customerEmail == null || customerEmail.isBlank()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam(MESSAGE_PARAM, NOT_LOGGED_IN_VALUE).createPath());
            return;
        }

        String[] arts = request.getParameterValues(ART_PARAM);
        String[] sizes = request.getParameterValues(SIZE_PARAM);
        String[] quantities = request.getParameterValues(QUANTITY_PARAM);

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
                Footwear footwear = footwearService.getByArt(arts[i], LANG_DEFAULT);
                FootwearItem footwearItem = new FootwearItem(footwear, Float.parseFloat(sizes[i]), Integer.parseInt(quantities[i]));
                items.add(footwearItem);
            }
        } catch (ServiceException e) {
            throw new ControllerException("Error getting footwear for order", e);
        }

        Customer customer;
        try {
            customer = customerService.getByEmail(customerEmail);
        } catch (ServiceException e) {
            throw new ControllerException("Error getting customer for order", e);
        }

        if (customer == null) {
            logger.error("Customer not found");
            throw new ControllerException("Customer not found");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_PATTERN);
        LocalDateTime currentDate = LocalDateTime.parse(LocalDateTime.now().format(formatter), formatter);

        Order order = new Order(customer, currentDate, items);
        order.setPrice(countPrice(items));

        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        try {
            if (orderService.createNewOrder(order)) {
                Cookie[] cookies = request.getCookies();
                if (cookies != null) {
                    for (Cookie cookie : cookies) {
                        if (cookie.getName().contains(ART_PREFIX)) {
                            cookie.setMaxAge(0);
                            response.addCookie(cookie);
                        }
                    }
                }
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam(MESSAGE_PARAM, SUCCESS).createPath());

            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOCART).addParam(MESSAGE_PARAM, FAIL).createPath());
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
