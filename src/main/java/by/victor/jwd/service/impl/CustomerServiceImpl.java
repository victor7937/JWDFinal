package by.victor.jwd.service.impl;
import by.victor.jwd.bean.Customer;

import by.victor.jwd.bean.UserRole;
import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.service.exception.EmailExistsException;
import by.victor.jwd.service.exception.ServiceException;
import by.victor.jwd.service.CustomerService;
import org.apache.log4j.Logger;

import java.util.List;


public class CustomerServiceImpl implements CustomerService {

	private static CustomerDAO customerDAO;
	private static final Logger logger = Logger.getLogger(CustomerServiceImpl.class);
	public CustomerServiceImpl () {
		customerDAO = DAOProvider.getInstance().getCustomerDAO();
	}

	private static final String EMAIL_EXISTS = "Such email exists in database";

	@Override
	public Customer authorization(String email, String password) throws ServiceException {
		try {
			Customer customer = customerDAO.getCustomerByEmailAndPassword(email, password);
			if (customer != null) {
				return customer;
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}

		return null;
	}

	@Override
	public Customer getByEmail(String email) throws ServiceException {
		try {
			Customer customer = customerDAO.getCustomerByEmail(email);
			if (customer != null) {
				return customer;
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return null;
	}

	@Override
	public boolean registration(Customer customer) throws ServiceException {
		boolean successAdding;

		try {
			if (!customerDAO.isCustomerExists(customer.getEmail())) {
				successAdding = customerDAO.addNewCustomer(customer);
			} else {
				throw new EmailExistsException(EMAIL_EXISTS);
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return successAdding;

	}

	@Override
	public boolean update(Customer customer) throws ServiceException {
		boolean successUpdating;
		try {
			successUpdating = customerDAO.updateCustomer(customer);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return successUpdating;
	}

	@Override
	public boolean changeRole(String email, UserRole role) throws ServiceException {
		boolean successBlocking;
		try {
			successBlocking = customerDAO.changeRole(email, role);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return successBlocking;
	}

	@Override
	public boolean updatePassword(String email, String password) throws ServiceException {
		boolean successUpdating;
		try {
			successUpdating = customerDAO.updatePassword(email, password);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return successUpdating;
	}

	@Override
	public String getPasswordByEmail(String email) throws ServiceException {
		String password;
		try {
			password = customerDAO.getPassword(email);
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return password;
	}

	@Override
	public List<Customer> getAllCustomers() throws ServiceException {
		List<Customer> customers;
		try {
			 customers = customerDAO.getAll();
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			throw new ServiceException(e);
		}
		return customers;

	}

}
