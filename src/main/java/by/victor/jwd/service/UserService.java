package by.victor.jwd.service;

import by.victor.jwd.bean.User;

public interface UserService {
	User authorization(String email, String passport) throws ServiceException;
	boolean registration(User user) throws ServiceException;
}
