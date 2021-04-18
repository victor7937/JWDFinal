package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.OrderStatus;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserDecline implements Command {

    private static final String CHANGE_PARAM = "change";
    private static final String FAIL_VALUE = "fail";
    private static final String SUCCESS_VALUE = "success";
    private static final String ORDER_ID_PARAM = "order_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdValue = request.getParameter(ORDER_ID_PARAM);
        if (orderIdValue == null || orderIdValue.isBlank()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                    .addParam(CHANGE_PARAM, FAIL_VALUE)
                    .createPath());
            return;
        }
        OrderStatus orderStatus = OrderStatus.DECLINE;
        Integer orderId = Integer.parseInt(orderIdValue);
        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        try {
            if (orderService.setOrderStatus(orderId, orderStatus)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(CHANGE_PARAM, SUCCESS_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(CHANGE_PARAM, FAIL_VALUE)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
