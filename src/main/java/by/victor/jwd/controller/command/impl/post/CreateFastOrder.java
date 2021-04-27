package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.Order;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static by.victor.jwd.controller.constant.FootwearParams.*;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class CreateFastOrder implements Command {

    private static final String NOT_LOGGED_IN_VALUE = "not_logged_in";
    private static final String FAST_ORDER_PARAM = "fast";
    private static final int FAST_ORDER_QUANTITY = 1;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String customerEmail = (String) request.getSession().getAttribute(EMAIL_ATTRIBUTE);
        String art = request.getParameter(ART_PARAM);
        String size = request.getParameter(SIZE_PARAM);
        String price = request.getParameter(PRICE_PARAM);

        if (customerEmail == null || customerEmail.isBlank()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                    .addParam(FAST_ORDER_PARAM, NOT_LOGGED_IN_VALUE)
                    .addParam(ART_PARAM, art)
                    .addParam(SHOW_PARAM, YES_VALUE)
                    .createPath());
            return;
        }

        String failPath = CommandPath.createCommand(CommandName.GOTOPRODUCT)
                .addParam(FAST_ORDER_PARAM, FAIL)
                .addParam(SHOW_PARAM, YES_VALUE)
                .addParam(ART_PARAM, art)
                .createPath();

        if (art == null || size == null || art.isBlank() || !RequestValidator.isSizeValid(size)
                || price == null || !RequestValidator.isPriceValid(price)){
            response.sendRedirect(failPath);
            return;
        }

        FootwearItem item = new FootwearItem(new Footwear(art), Float.parseFloat(size), FAST_ORDER_QUANTITY);
        List<FootwearItem> items = new ArrayList<>();
        items.add(item);

        Order order = new Order(new Customer(customerEmail), items);
        order.setPrice(Float.parseFloat(price));

        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        try {
            if (orderService.createNewOrder(order)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                        .addParam(FAST_ORDER_PARAM, SUCCESS)
                        .addParam(ART_PARAM, art)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(failPath);
            }
        } catch (ServiceException e) {
            throw new ControllerException("Unable to create order",e);
        }
    }
}
