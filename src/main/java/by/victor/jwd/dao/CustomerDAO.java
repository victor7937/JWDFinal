package by.victor.jwd.dao;


import by.victor.jwd.bean.Customer;
import by.victor.jwd.dao.exceptions.DAOException;

import java.util.List;

public interface CustomerDAO {
    List<Customer> getAll() throws DAOException;
    Customer getCustomerByEmail(String email) throws DAOException;
    boolean addNewCustomer(Customer customer) throws DAOException;
    boolean deleteCustomer(String email) throws DAOException;
    boolean updateCustomer(String email, Customer customer) throws DAOException;
}
