package by.victor.jwd.service;

import by.victor.jwd.bean.Order;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface OrderService {
    boolean createNewOrder(Order order) throws ServiceException;
    List<Order> getAllOrders(String lang) throws ServiceException;
}
