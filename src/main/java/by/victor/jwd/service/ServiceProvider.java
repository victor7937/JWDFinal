package by.victor.jwd.service;

import by.victor.jwd.service.impl.CustomerServiceImpl;

public final class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider(); 

	private ServiceProvider() {}
	
	private final CustomerService customerService = new CustomerServiceImpl();

	public static ServiceProvider getInstance() {
		return instance;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}
}
