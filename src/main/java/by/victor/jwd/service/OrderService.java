package by.victor.jwd.service;

import by.victor.jwd.bean.Order;
import by.victor.jwd.bean.OrderStatus;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
    boolean createNewOrder(Order order) throws ServiceException;
    List<Order> getAllOrders(String lang) throws ServiceException;
    List<Order> getOrdersOfCustomer(String email, String lang) throws ServiceException;
    List<Order> getOrdersOfStatus(OrderStatus orderStatus, String lang) throws ServiceException;
    boolean setOrderStatus (Integer orderId, OrderStatus status) throws ServiceException;
}
