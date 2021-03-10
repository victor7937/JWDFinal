package by.victor.jwd.service.impl;

import by.victor.jwd.bean.RegistrationInfo;
import by.victor.jwd.bean.User;

import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.UserService;

public class UserServiceImpl implements UserService {
	
	@Override
	public User authorization(String email, String password) throws ServiceException {
		// validation
		/*if(login == null || "".equals(login) ) {// to bo cont....
			throw new ServiceException("wrong login or password");
		}
		
		DAOProvider provider = DAOProvider.getInstance();
        UserDAO userDAO = provider.getUserdao();
        
		User user = null;
		try {
			user = userDAO.authorization(login, password);
		}catch(DAOException e) {
			throw new ServiceException("error message", e);
		}*/
		if (email.equals("123@mail.ru") && password.equals("123")){
			return new User("Tom","123@mail.ru","123");
		}
		return null;
	}

	@Override
	public boolean registration(RegistrationInfo regInfo) {
		// TODO Auto-generated method stub
		return false;
	}

}
