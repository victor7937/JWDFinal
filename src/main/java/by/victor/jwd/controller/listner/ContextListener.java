package by.victor.jwd.controller.listner;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exception.ConnectionException;
import org.apache.log4j.Logger;

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
    private static final int CONNECTION_LIMIT = 10;
    private static final int IDLE_CONNECTION_LIMIT = 5;
    private final static Logger logger = Logger.getLogger(ContextListener.class);


    public ContextListener() { }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.setUrl(DB_URL);
        pool.setUser(DB_USER);
        pool.setPassword(DB_PASSWORD);
        pool.setDriverName(DB_DRIVER_NAME);
        try {
            pool.init(CONNECTION_LIMIT,IDLE_CONNECTION_LIMIT);
            logger.info("Pool has been created");
        } catch (ConnectionException e) {
            logger.fatal("Error while initializing pool " + e.toString());
        }

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ConnectionPool.getInstance().destroy();
            logger.info("Pool has been destroyed");
        } catch (ConnectionException e) {
            logger.fatal("Error while destroying pool " + e.toString());
        }
    }


}
