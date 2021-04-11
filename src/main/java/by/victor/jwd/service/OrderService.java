package by.victor.jwd.service;

import by.victor.jwd.bean.Order;
import by.victor.jwd.service.exception.ServiceException;

public interface OrderService {
    boolean createNewOrder(Order order) throws ServiceException;
}
