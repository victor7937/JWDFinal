package by.victor.jwd.dao;

import by.victor.jwd.bean.Order;
import by.victor.jwd.bean.OrderStatus;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

public interface OrderDAO {
    boolean createOrder (Order order) throws DAOException;
    List<Order> showOrders (String lang) throws DAOException;
    List<Order> showOrdersOfCustomer (String email, String lang) throws DAOException;
    List<Order> showOrdersOfStatus (OrderStatus orderStatus, String lang) throws DAOException;
    boolean setOrderStatus(Integer orderId, OrderStatus status) throws DAOException;
}
