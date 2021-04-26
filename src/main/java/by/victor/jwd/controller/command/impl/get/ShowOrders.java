package by.victor.jwd.controller.command.impl.get;

import by.victor.jwd.bean.Order;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.victor.jwd.controller.constant.GlobalParams.*;
import static by.victor.jwd.controller.constant.GlobalParams.ERROR_CODE_FORBIDDEN;

public class ShowOrders implements Command {

    private static final String ORDERS_ATTRIBUTE = "orders";
    public static final String FORWARD_PATH = "/WEB-INF/jsp/admin-page.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        OrderService orderService = ServiceProvider.getInstance().getOrderService();
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        List<Order> orders;
        try {
            orders = orderService.getAllOrders(lang);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(ORDERS_ATTRIBUTE, orders);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
