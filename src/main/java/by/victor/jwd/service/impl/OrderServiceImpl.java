package by.victor.jwd.service.impl;

import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.Order;
import by.victor.jwd.bean.OrderStatus;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.OrderDAO;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private static final Logger logger = Logger.getLogger(OrderService.class);
    private static OrderDAO orderDAO;

    public OrderServiceImpl () {
        orderDAO = DAOProvider.getInstance().getOrderDAO();
    }


    @Override
    public boolean createNewOrder(Order order) throws ServiceException {
        boolean success;
        try {
            success = orderDAO.createOrder(order);
        } catch (DAOException e) {
           logger.error(e.getMessage(), e);
           throw new ServiceException(e);
        }
        return success;
    }

    @Override
    public List<Order> getAllOrders(String lang) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.showOrders(lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersOfCustomer(String email, String lang) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.showOrdersOfCustomer(email, lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public List<Order> getOrdersOfStatus(OrderStatus orderStatus, String lang) throws ServiceException {
        List<Order> orders;
        try {
            orders = orderDAO.showOrdersOfStatus(orderStatus, lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return orders;
    }

    @Override
    public boolean setOrderStatus(Integer orderId, OrderStatus status) throws ServiceException {
        boolean updatingSuccess;
        try {
            updatingSuccess = orderDAO.setOrderStatus(orderId, status);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return updatingSuccess;
    }
}
