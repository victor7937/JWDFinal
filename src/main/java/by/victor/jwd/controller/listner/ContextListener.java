package by.victor.jwd.controller.listner;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exceptions.ConnectionException;

import javax.servlet.*;
import javax.servlet.annotation.*;
import java.util.ResourceBundle;

@WebListener
public class ContextListener implements ServletContextListener {

    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("database");
    private static final String DB_URL = db_bundle.getString("db.url");
    private static final String DB_USER = db_bundle.getString("db.user");
    private static final String DB_PASSWORD = db_bundle.getString("db.password");
    private static final String DB_DRIVER_NAME = db_bundle.getString("db.driver.name");

    public ContextListener() { }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.setUrl(DB_URL);
        pool.setUser(DB_USER);
        pool.setPassword(DB_PASSWORD);
        pool.setDriverName(DB_DRIVER_NAME);
        try {
            pool.init(10,5);
        } catch (ConnectionException e) {
            System.out.println("Error while initializing pool");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().destroy();
        } catch (ConnectionException e) {
            System.out.println("Error while destroying pool");
        }
    }


}
