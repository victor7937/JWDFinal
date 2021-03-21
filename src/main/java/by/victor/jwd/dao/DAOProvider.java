package by.victor.jwd.dao;

import by.victor.jwd.dao.impl.SQLCustomerDAO;

public final class DAOProvider {

	private static final DAOProvider instance = new DAOProvider();	
	
	private final CustomerDAO customerDAO = new SQLCustomerDAO();

	
	private DAOProvider() {}
	
	public static DAOProvider getInstance() {
		return instance;
	}

	public CustomerDAO getCustomerDAO() { return customerDAO; }


}
