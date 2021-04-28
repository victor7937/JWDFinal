package by.victor.jwd.controller.command.impl.get;

import by.victor.jwd.bean.Order;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
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

public class ShowOrders implements Command {

    private static final String ORDERS_ATTRIBUTE = "orders";
    public static final String FORWARD_PATH = "/WEB-INF/jsp/admin-page.jsp";
    private static final Integer PAGE_LIMIT = 5;
    private static final String PAGE_PARAM = "page";
    private static final int START_PAGE = 1;
    private static final String PAGE_COUNT_ATTRIBUTE = "pageCount";
    private static final String CURRENT_PAGE_ATTRIBUTE = "currentPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }
        String pageString = request.getParameter(PAGE_PARAM);

        int page;
        if (pageString == null || !RequestValidator.isInteger(pageString)) {
            page = START_PAGE;
        } else {
            page = Integer.parseInt(pageString);
        }

        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        Integer ordersCount;
        try {
           ordersCount = orderService.getOrdersCount();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        int pageCount = (int) Math.ceil(ordersCount / (double) PAGE_LIMIT);
        if (page > pageCount) {
            page = START_PAGE;
        }
        if (pageCount == 0) {
            pageCount = 1;
        }
        int offset = (page - 1) * PAGE_LIMIT;

        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        List<Order> orders;
        try {
            orders = orderService.getAllOrders(lang, offset, PAGE_LIMIT);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(PAGE_COUNT_ATTRIBUTE, pageCount);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE, page);
        request.setAttribute(ORDERS_ATTRIBUTE, orders);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
