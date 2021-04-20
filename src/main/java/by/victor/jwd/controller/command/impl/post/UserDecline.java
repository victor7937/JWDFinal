package by.victor.jwd.controller.command.impl.post;

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

import static by.victor.jwd.controller.constant.GlobalParams.*;

public class UserDecline implements Command {

    private static final String CHANGE_PARAM = "change";
    private static final String ORDER_ID_PARAM = "order_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdValue = request.getParameter(ORDER_ID_PARAM);
        if (orderIdValue == null || orderIdValue.isBlank()) {
            response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                    .addParam(CHANGE_PARAM, FAIL)
                    .createPath());
            return;
        }
        OrderStatus orderStatus = OrderStatus.DECLINE;
        Integer orderId = Integer.parseInt(orderIdValue);
        OrderService orderService = ServiceProvider.getInstance().getOrderService();

        try {
            if (orderService.setOrderStatus(orderId, orderStatus)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(CHANGE_PARAM, SUCCESS)
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPROFILE)
                        .addParam(CHANGE_PARAM, FAIL)
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
