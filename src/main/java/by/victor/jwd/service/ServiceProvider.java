package by.victor.jwd.service;

import by.victor.jwd.service.impl.CustomerServiceImpl;
import by.victor.jwd.service.impl.FootwearServiceImpl;

public final class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider(); 

	private ServiceProvider() {}
	
	private final CustomerService customerService = new CustomerServiceImpl();

	private final FootwearService footwearService = new FootwearServiceImpl();

	public static ServiceProvider getInstance() {
		return instance;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public FootwearService getFootwearService() {
		return footwearService;
	}
}
