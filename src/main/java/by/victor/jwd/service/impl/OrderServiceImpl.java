package by.victor.jwd.service.impl;

import by.victor.jwd.bean.Order;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.FootwearDAO;
import by.victor.jwd.dao.OrderDAO;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.service.OrderService;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

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
}
