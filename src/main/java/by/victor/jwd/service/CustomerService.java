package by.victor.jwd.service;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface CustomerService {
	Customer authorization(String email, String passport) throws ServiceException;
	Customer getByEmail (String email) throws ServiceException;
	boolean registration(Customer customer) throws ServiceException;
	boolean update(Customer customer) throws ServiceException;
	boolean changeRole(String email, UserRole role) throws ServiceException;
	boolean updatePassword(String email, String password) throws ServiceException;
	String getPasswordByEmail(String email) throws ServiceException;
	List<Customer> getAllCustomers () throws ServiceException;

}