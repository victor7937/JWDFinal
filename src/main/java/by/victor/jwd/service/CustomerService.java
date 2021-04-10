package by.victor.jwd.service;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface CustomerService {
	Customer authorization(String email, String passport) throws ServiceException;

	Customer getByEmail (String email) throws ServiceException;

	boolean registration(Customer customer) throws ServiceException;

	boolean update(String email, Customer customer) throws ServiceException;

	boolean delete(String email) throws ServiceException;

	List<Customer> getAllCustomers () throws ServiceException;
}