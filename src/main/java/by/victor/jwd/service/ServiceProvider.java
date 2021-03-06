package by.victor.jwd.service;

import by.victor.jwd.service.impl.ConnectionServiceImpl;
import by.victor.jwd.service.impl.CustomerServiceImpl;
import by.victor.jwd.service.impl.FootwearServiceImpl;
import by.victor.jwd.service.impl.OrderServiceImpl;

/**
 * Provider class for getting reference to necessary service objects
 */
public final class ServiceProvider {
	
	private static final ServiceProvider instance = new ServiceProvider(); 

	private ServiceProvider() {}
	
	private final CustomerService customerService = new CustomerServiceImpl();

	private final FootwearService footwearService = new FootwearServiceImpl();

	private final OrderService orderService = new OrderServiceImpl();

	private final ConnectionService connectionService = new ConnectionServiceImpl();

	public static ServiceProvider getInstance() {
		return instance;
	}

	public CustomerService getCustomerService() {
		return customerService;
	}

	public FootwearService getFootwearService() {
		return footwearService;
	}

	public OrderService getOrderService() {
		return orderService;
	}

	public ConnectionService getConnectionService() {
		return connectionService;
	}
}
