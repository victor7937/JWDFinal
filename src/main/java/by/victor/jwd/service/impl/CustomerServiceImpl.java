package by.victor.jwd.service.impl;
import by.victor.jwd.bean.Customer;

import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.exceptions.DAOException;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.CustomerService;


public class CustomerServiceImpl implements CustomerService {

	private static CustomerDAO customerDAO;

	public CustomerServiceImpl () {
		customerDAO = DAOProvider.getInstance().getCustomerDAO();
	}

	@Override
	public Customer authorization(String email, String password) throws ServiceException {

		Customer customer = null;
		try {
			customer = customerDAO.getCustomerByEmail(email);
			if (customer != null && password.equals(customer.getPassword())) {
				return customer;
			}
		} catch (DAOException e) {
			throw new ServiceException("Can't get data from database", e);
		}

		return null;
	}

	@Override
	public boolean registration(Customer customer) throws ServiceException {
		boolean successAdding = false;
		try {
			successAdding = customerDAO.addNewCustomer(customer);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return successAdding;

	}

	@Override
	public boolean update(String email, Customer customer) throws ServiceException {
		boolean successUpdating = false;
		try {
			successUpdating = customerDAO.updateCustomer(email, customer);
		} catch (DAOException e) {
			throw new ServiceException(e.getMessage(), e);
		}
		return successUpdating;
	}

	@Override
	public boolean delete(String email) throws ServiceException {
		boolean successDeleting = false;
		try {
			successDeleting = customerDAO.deleteCustomer(email);
		} catch (DAOException e) {
			throw new ServiceException("Can't delete customer", e);
		}
		return successDeleting;
	}

}
