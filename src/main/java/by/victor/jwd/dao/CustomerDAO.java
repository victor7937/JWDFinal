package by.victor.jwd.dao;


import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAll() throws DAOException;
    Customer getCustomerByEmail(String email) throws DAOException;
    Customer getCustomerByEmailAndPassword (String email, String password) throws DAOException;
    boolean isCustomerExists(String email) throws DAOException;
    boolean addNewCustomer(Customer customer) throws DAOException;
    boolean changeRole(String email, UserRole role) throws DAOException;
    boolean updateCustomer(Customer customer) throws DAOException;
    boolean updatePassword(String email, String password) throws DAOException;
    String getPassword(String email) throws DAOException;
    boolean deleteCustomer(String email) throws DAOException;
}
