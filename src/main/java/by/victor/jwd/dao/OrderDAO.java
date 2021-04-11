package by.victor.jwd.dao;

import by.victor.jwd.bean.Order;
import by.victor.jwd.dao.exception.DAOException;

public interface OrderDAO {
    boolean createOrder (Order order) throws DAOException;
}
