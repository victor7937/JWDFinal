package by.victor.jwd.service;

import by.victor.jwd.bean.Customer;

public interface CustomerService {
	Customer authorization(String email, String passport) throws ServiceException;

	boolean registration(Customer customer) throws ServiceException;

	boolean update(String email, Customer customer) throws ServiceException;

	boolean delete(String email) throws ServiceException;
}