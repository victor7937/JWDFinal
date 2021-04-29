package by.victor.jwd.dao;

import by.victor.jwd.dao.impl.SQLCustomerDAO;
import by.victor.jwd.dao.impl.SQLFootwearDAO;
import by.victor.jwd.dao.impl.SQLOrderDAO;

/**
 * Provider class for getting reference to necessary dao objects
 */
public final class DAOProvider {

	private static final DAOProvider instance = new DAOProvider();	
	
	private final CustomerDAO customerDAO = new SQLCustomerDAO();

	private final FootwearDAO footwearDAO = new SQLFootwearDAO();

	private final OrderDAO orderDAO = new SQLOrderDAO();

	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}

	public CustomerDAO getCustomerDAO() { return customerDAO; }

	public FootwearDAO getFootwearDAO() {
		return footwearDAO;
	}

	public OrderDAO getOrderDAO() {
		return orderDAO;
	}
}
