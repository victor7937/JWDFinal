package by.victor.jwd.service.impl;
import by.victor.jwd.bean.User;

import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {

	 private static List<User> users = new ArrayList<>();

	static {
		users.add(new User("Tom","tom@mail.ru","123"));
		users.add(new User("Bil","bil@mail.ru","123"));
		users.add(new User("Stefan","stefan@mail.ru","123"));
	}

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
		for (User user : users){
			if (email.equals(user.getEmail()) && password.equals(user.getPassword())){
				return user;
			}
		}
		return null;
	}

	@Override
	public boolean registration(User user) {
		if (users.stream().noneMatch(u -> u.getEmail().equals(user.getEmail()))) {
			users.add(user);
			return true;
		}
		return false;

	}

}
