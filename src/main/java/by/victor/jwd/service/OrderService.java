package by.victor.jwd.service;

import by.victor.jwd.bean.Order;
import by.victor.jwd.bean.OrderStatus;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

/**
 * Service for getting access to order dao level
 */
public interface OrderService {
    boolean createNewOrder(Order order) throws ServiceException;
    List<Order> getAllOrders(String lang, int offset, int limit) throws ServiceException;
    List<Order> getOrdersOfCustomer(String email, String lang) throws ServiceException;
    List<Order> getOrdersOfStatus(OrderStatus orderStatus, String lang) throws ServiceException;
    Integer getOrdersCount() throws ServiceException;
    boolean setOrderStatus (Integer orderId, OrderStatus status) throws ServiceException;
}
