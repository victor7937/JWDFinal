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
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdValue = request.getParameter("order_id");
        if (orderIdValue == null || orderIdValue.isBlank()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                    .addParam("change", "fail")
                    .createPath());
            return;
        }
        OrderStatus orderStatus = OrderStatus.DECLINE;
        Integer orderId = Integer.parseInt(orderIdValue);
        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        try {
            if (orderService.setOrderStatus(orderId, orderStatus)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam("change", "success")
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam("change", "fail")
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
