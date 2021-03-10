package by.victor.jwd.service;

import by.victor.jwd.bean.RegistrationInfo;
import by.victor.jwd.bean.User;

public interface UserService {
	User authorization(String login, String passport) throws ServiceException;
	boolean registration(RegistrationInfo regInfo) throws ServiceException;
}
