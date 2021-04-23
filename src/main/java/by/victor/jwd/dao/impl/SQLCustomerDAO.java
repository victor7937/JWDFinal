package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.CustomerDAO;
import by.victor.jwd.dao.util.DAOResourceProvider;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * DAO that manipulates customer-related data
 */
public class SQLCustomerDAO implements CustomerDAO {

	private static final String SQL_GET_ALL_CUSTOMERS =
			"SELECT * FROM customers ORDER BY cu_name";

	private static final String SQL_GET_CUSTOMER_BY_EMAIL =
			"SELECT * FROM customers WHERE cu_email = ?";

	private static final String SQL_GET_PASSWORD_BY_EMAIL =
			"SELECT cu_password FROM customers WHERE cu_email = ?";

	private static final String SQL_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD =
			"SELECT * FROM customers WHERE cu_email = ? AND cu_password = ?";

	private static final String SQL_INSERT_CUSTOMER =
			"INSERT INTO customers (cu_name, cu_email, cu_password, cu_phone, cu_country, cu_city, cu_address) VALUES (?, ?, ?, ?, ?, ?, ?) ";

	private static final String SQL_CHECK_EMAIL = "SELECT EXISTS (SELECT 1 FROM customers WHERE cu_email = ?) AS has_email";

	private static final String SQL_UPDATE_CUSTOMER =
			"UPDATE customers SET cu_name = ?, cu_phone = ?, cu_country = ?, cu_city = ?, cu_address = ? WHERE cu_email = ?";

	private static final String SQL_UPDATE_PASSWORD =
			"UPDATE customers SET cu_password = ? WHERE cu_email = ?";

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
			throw new DAOException("Getting all customers error", e);
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
			throw new DAOException("Getting customer by email error", e);
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
			throw new DAOException("Getting customer by email and password error", e);
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
			throw new DAOException("Checking if customer exists error", e);
		}
		return isEmailExists;
	}

	@Override
	public boolean addNewCustomer(Customer customer) throws DAOException {
		boolean operationSuccess;
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
			throw new DAOException("Adding new customer error", e);
		}
		return operationSuccess;
	}

	@Override
	public boolean deleteCustomer(String email) throws DAOException {
		boolean operationSuccess;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_DELETE_CUSTOMER_BY_EMAIL, ps -> ps.setString(1, email));
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Deleting customer error", e);
		}
		return operationSuccess;
	}

	@Override
	public boolean updateCustomer(Customer customer) throws DAOException {
		boolean operationSuccess;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_UPDATE_CUSTOMER, ps -> {
				ps.setString(1, customer.getName());
				ps.setString(2, customer.getPhone());
				ps.setString(3, customer.getCountry());
				ps.setString(4, customer.getCity());
				ps.setString(5, customer.getAddress());
				ps.setString(6, customer.getEmail());
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Updating customer error", e);
		}
		return operationSuccess;
	}

	@Override
	public boolean updatePassword(String email, String password) throws DAOException {
		boolean operationSuccess;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			operationSuccess = resourceProvider.updateAction(SQL_UPDATE_PASSWORD, ps -> {
				ps.setString(1, password);
				ps.setString(2, email);
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Updating password error", e);
		}
		return operationSuccess;
	}

	@Override
	public String getPassword(String email) throws DAOException {
		String password = null;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_PASSWORD_BY_EMAIL, ps -> ps.setString(1, email));
			if (resultSet.next()) {
				password = resultSet.getString("cu_password");
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Getting password error", e);
		}
		return password;
	}

	private Customer buildCustomer(ResultSet resultSet) throws DAOException {
		Customer customer;
		try {
			String name = resultSet.getString("cu_name");
			String email = resultSet.getString("cu_email");
			String password = resultSet.getString("cu_password");
			String phone = resultSet.getString("cu_phone");
			String country = resultSet.getString("cu_country");
			String city = resultSet.getString("cu_city");
			String address = resultSet.getString("cu_address");
			String role = resultSet.getString("cu_role");
			customer = new Customer(name, email, password, phone, country, city, address);
			if (UserRole.ADMIN == UserRole.valueOf(role.toUpperCase())) {
				customer.setRole(UserRole.ADMIN);
			}

		} catch (SQLException e) {
			throw new DAOException("Building customer error", e);
		}
		return customer;
	}
}
