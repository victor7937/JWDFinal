package by.victor.jwd.service.impl;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.service.ConnectionService;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

public class ConnectionServiceImpl implements ConnectionService {
    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("database");
    private static final String DB_URL = db_bundle.getString("db.url");
    private static final String DB_USER = db_bundle.getString("db.user");
    private static final String DB_PASSWORD = db_bundle.getString("db.password");
    private static final String DB_DRIVER_NAME = db_bundle.getString("db.driver.name");
    private static final int CONNECTION_LIMIT = Integer.parseInt(db_bundle.getString("db.connections.limit"));
    private static final int IDLE_CONNECTION_LIMIT = Integer.parseInt(db_bundle.getString("db.connections.idle"));;
    private final static Logger logger = Logger.getLogger(ConnectionServiceImpl.class);

    private static ConnectionPool connectionPool;

    public ConnectionServiceImpl(){
       connectionPool = ConnectionPool.getInstance();
    }

    @Override
    public void initConnectionPool() throws ServiceException {
        connectionPool.setUrl(DB_URL);
        connectionPool.setUser(DB_USER);
        connectionPool.setPassword(DB_PASSWORD);
        connectionPool.setDriverName(DB_DRIVER_NAME);
        try {
            connectionPool.init(CONNECTION_LIMIT, IDLE_CONNECTION_LIMIT);
            logger.info("Pool has been created");
        } catch (ConnectionException e) {
            logger.fatal("Error while initializing pool ", e);
            throw new ServiceException(e);
        }
    }

    @Override
    public void destroyConnectionPool() throws ServiceException {
        try {
            connectionPool.destroy();
            logger.info("Pool has been destroyed");
        } catch (ConnectionException e) {
            logger.fatal("Error while destroying pool ",e);
            throw new ServiceException(e);
        }
    }
}





/*public ContextListener() { }

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
        logger.fatal("Error while initializing pool ", e);
        throw new ControllerException("Sorry, we have some technical problems and working to fix them soon.\n Please try to connect later.");
        }

        }

@Override
public void contextDestroyed(ServletContextEvent sce) {
        try {
        ConnectionPool.getInstance().destroy();
        logger.info("Pool has been destroyed");
        } catch (ConnectionException e) {
        logger.fatal("Error while destroying pool ",e);
        throw new ControllerException("Sorry, we have some technical problems and working to fix them soon.\n Please try to connect later.");
        }
        }*/