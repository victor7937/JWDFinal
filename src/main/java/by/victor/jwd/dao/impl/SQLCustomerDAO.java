package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.util.DAOResourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class SQLCustomerDAO implements CustomerDAO {

	private static final String DATA_ACCESS_EXCEPTION_TEXT =
				"It's impossible to get a connection from the connection pool, "
						+ "execute a query, build a bean object, close the statement "
						+ "or return the connection back";
		
	
	private static final String SQL_GET_ALL_CUSTOMERS =
			"SELECT * FROM customers ORDER BY cu_firstname";

	private static final String SQL_GET_CUSTOMER_BY_EMAIL =
			"SELECT * FROM customers WHERE cu_email = ?";

	private static final String SQL_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD =
			"SELECT * FROM customers WHERE cu_email = ? AND cu_password = ?";

	private static final String SQL_INSERT_CUSTOMER =
			"INSERT INTO customers (cu_firstname, cu_email, cu_password, cu_phone, cu_country, cu_city, cu_address) VALUES (?, ?, ?, ?, ?, ?, ?) ";

	private static final String SQL_CHECK_EMAIL = "SELECT EXISTS (SELECT 1 FROM customers WHERE cu_email = ?) AS has_email";

	private static final String SQL_UPDATE_CUSTOMER =
			"UPDATE customers SET cu_firstname = ?, cu_email = ?, cu_password = ?, cu_phone = ?, cu_country = ?, cu_city = ?, cu_address = ? "
					+ "WHERE cu_email = ? ";

	private static final String SQL_DELETE_CUSTOMER_BY_EMAIL =
			"DELETE FROM customers WHERE cu_email = ?";

	public SQLCustomerDAO() {}

	@Override
	public List<Customer> getAll() throws DAOException {
		List<Customer> customerList = new ArrayList<>();
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ALL_CUSTOMERS);
			while (resultSet.next()) {
				Customer customer = buildCustomer(resultSet);
				customerList.add(customer);
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return customerList;
	}

	@Override
	public Customer getCustomerByEmail(String email) throws DAOException {
		Customer customer = null;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_CUSTOMER_BY_EMAIL, ps -> ps.setString(1, email));
			if (resultSet.next()) {
				customer = buildCustomer(resultSet);
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return customer;
	}

	@Override
	public Customer getCustomerByEmailAndPassword(String email, String password) throws DAOException {
		Customer customer = null;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD, ps -> {
				ps.setString(1, email);
				ps.setString(2, password);
			});
			if (resultSet.next()) {
				customer = buildCustomer(resultSet);
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return customer;
	}

	@Override
	public boolean isCustomerExists(String email) throws DAOException {
		boolean isEmailExists = false;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			ResultSet resultSet = resourceProvider.createResultSet(SQL_CHECK_EMAIL, ps -> ps.setString(1, email));
			if (resultSet.next() && resultSet.getInt("has_email") == 1) {
				isEmailExists = true;
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return isEmailExists;
	}

	@Override
	public boolean addNewCustomer(Customer customer) throws DAOException {
		boolean operationSuccess = false;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_INSERT_CUSTOMER, ps -> {
				ps.setString(1, customer.getName());
				ps.setString(2, customer.getEmail());
				ps.setString(3, customer.getPassword());
				ps.setString(4, customer.getPhone());
				ps.setString(5, customer.getCountry());
				ps.setString(6, customer.getCity());
				ps.setString(7, customer.getAddress());
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return operationSuccess;
	}

	@Override
	public boolean deleteCustomer(String email) throws DAOException {
		boolean operationSuccess = false;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_DELETE_CUSTOMER_BY_EMAIL, ps -> ps.setString(1, email));
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return operationSuccess;
	}

	@Override
	public boolean updateCustomer(String email, Customer customer) throws DAOException {
		boolean operationSuccess = false;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_UPDATE_CUSTOMER, ps -> {
				ps.setString(1, customer.getName());
				ps.setString(2, customer.getEmail());
				ps.setString(3, customer.getPassword());
				ps.setString(4, customer.getPhone());
				ps.setString(5, customer.getCountry());
				ps.setString(6, customer.getCity());
				ps.setString(7, customer.getAddress());
				ps.setString(8, email);
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return operationSuccess;
	}

	private Customer buildCustomer(ResultSet resultSet) throws DAOException {
		Customer customer = null;
		try {
			String name = resultSet.getString("cu_firstname");
			String email = resultSet.getString("cu_email");
			String password = resultSet.getString("cu_password");
			String phone = resultSet.getString("cu_phone");
			String country = resultSet.getString("cu_country");
			String city = resultSet.getString("cu_city");
			String address = resultSet.getString("cu_address");
			customer = new Customer(name, email, password, phone, country, city, address);

		} catch (SQLException e) {
			throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
		}
		return customer;
	}
}
