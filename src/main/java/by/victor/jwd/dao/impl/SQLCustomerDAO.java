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
import java.util.concurrent.atomic.AtomicInteger;


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

	private static final String SQL_CHANGE_ROLE =
			"UPDATE customers SET cu_role = ? WHERE cu_email = ?";

	private final static int FIRST = 1;

	public SQLCustomerDAO() { }

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
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_CUSTOMER_BY_EMAIL, ps -> ps.setString(FIRST, email));
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
			AtomicInteger i = new AtomicInteger();
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_CUSTOMER_BY_EMAIL_AND_PASSWORD, ps -> {
				ps.setString(i.incrementAndGet(), email);
				ps.setString(i.incrementAndGet(), password);
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
			ResultSet resultSet = resourceProvider.createResultSet(SQL_CHECK_EMAIL, ps -> ps.setString(FIRST, email));
			if (resultSet.next() && resultSet.getInt(ColumnNames.CU_HAS_EMAIL) == FIRST) {
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
			AtomicInteger i = new AtomicInteger();
			operationSuccess = resourceProvider.updateAction(SQL_INSERT_CUSTOMER, ps -> {
				ps.setString(i.incrementAndGet(), customer.getName());
				ps.setString(i.incrementAndGet(), customer.getEmail());
				ps.setString(i.incrementAndGet(), customer.getPassword());
				ps.setString(i.incrementAndGet(), customer.getPhone());
				ps.setString(i.incrementAndGet(), customer.getCountry());
				ps.setString(i.incrementAndGet(), customer.getCity());
				ps.setString(i.incrementAndGet(), customer.getAddress());
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Adding new customer error", e);
		}
		return operationSuccess;
	}

	@Override
	public boolean changeRole(String email, UserRole role) throws DAOException {
		boolean operationSuccess;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			AtomicInteger i = new AtomicInteger();
			operationSuccess = resourceProvider.updateAction(SQL_CHANGE_ROLE, ps -> {
				ps.setString(i.incrementAndGet(), role.toString().toLowerCase());
				ps.setString(i.incrementAndGet(), email);
			});
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Changing customers role error", e);
		}
		return operationSuccess;
	}


	@Override
	public boolean updateCustomer(Customer customer) throws DAOException {
		boolean operationSuccess;
		try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
			AtomicInteger i = new AtomicInteger();
			operationSuccess = resourceProvider.updateAction(SQL_UPDATE_CUSTOMER, ps -> {
				ps.setString(i.incrementAndGet(), customer.getName());
				ps.setString(i.incrementAndGet(), customer.getPhone());
				ps.setString(i.incrementAndGet(), customer.getCountry());
				ps.setString(i.incrementAndGet(), customer.getCity());
				ps.setString(i.incrementAndGet(), customer.getAddress());
				ps.setString(i.incrementAndGet(), customer.getEmail());
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
			AtomicInteger i = new AtomicInteger();
			operationSuccess = resourceProvider.updateAction(SQL_UPDATE_PASSWORD, ps -> {
				ps.setString(i.incrementAndGet(), password);
				ps.setString(i.incrementAndGet(), email);
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
			ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_PASSWORD_BY_EMAIL, ps -> ps.setString(FIRST, email));
			if (resultSet.next()) {
				password = resultSet.getString(ColumnNames.CU_PASSWORD);
			}
		} catch (SQLException | ConnectionException e) {
			throw new DAOException("Getting password error", e);
		}
		return password;
	}

	private Customer buildCustomer(ResultSet resultSet) throws DAOException {
		Customer customer;
		try {
			String name = resultSet.getString(ColumnNames.CU_NAME);
			String email = resultSet.getString(ColumnNames.CU_EMAIL);
			String password = resultSet.getString(ColumnNames.CU_PASSWORD);
			String phone = resultSet.getString(ColumnNames.CU_PHONE);
			String country = resultSet.getString(ColumnNames.CU_COUNTRY);
			String city = resultSet.getString(ColumnNames.CU_CITY);
			String address = resultSet.getString(ColumnNames.CU_ADDRESS);
			String role = resultSet.getString(ColumnNames.CU_ROLE);
			customer = new Customer(name, email, password, phone, country, city, address);
			customer.setRole(UserRole.valueOf(role.toUpperCase()));
		} catch (SQLException e) {
			throw new DAOException("Building customer error", e);
		}
		return customer;
	}

	static class ColumnNames {
		public final static String CU_EMAIL = "cu_email";
		public static final String CU_NAME = "cu_name";
		public static final String CU_PASSWORD = "cu_password";
		public static final String CU_PHONE = "cu_phone";
		public static final String CU_COUNTRY = "cu_country";
		public static final String CU_CITY = "cu_city";
		public static final String CU_ADDRESS = "cu_address";
		public static final String CU_ROLE = "cu_role";
		public static final String CU_HAS_EMAIL = "has_email";
	}
}
