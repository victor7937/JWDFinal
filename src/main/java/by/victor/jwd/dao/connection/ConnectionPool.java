package by.victor.jwd.dao.connection;

import by.victor.jwd.dao.DAOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {

    private static final String DB_CONNECTION_EXCEPTION_TEXT = "sd";

    private int maxConnections = 10;

    private int maxIdleConnections;

    private String driverName;

    private String url;

    private final static String URL = "jdbc:mysql://localhost:3306/footware_db";
    private final static String USER = "root";
    private final static String PASSWORD = "123";

    private String user;

    private String password;

    private String locale;

    private final ReentrantLock lock = new ReentrantLock(Boolean.TRUE);

    private Semaphore semaphore;

    private CopyOnWriteArrayList<RentedConnection> connections;

    private static ConnectionPool instance = null;

    static {
        try {
            instance = new ConnectionPool();
        } catch (DAOException e) {
            e.printStackTrace();
        }
    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    private ConnectionPool() throws DAOException {
        init();
    }
    
    private void loadDriverClass() throws DAOException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new DAOException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
    }

    
    private RentedConnection createNewConnection()throws DAOException {
        try {
            //Locale.setDefault(new Locale(this.locale));
            RentedConnection rc = new RentedConnection(DriverManager.getConnection(URL, USER, PASSWORD));
            //One connection created! DB locale set as this.locale
            return rc;
        } catch (SQLException e) {
            throw new DAOException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
    }


    private RentedConnection findAvailableConnection() {
        for (RentedConnection connection : connections) {
            if (connection.isAvailable()) {
                return connection;
            }
        }
        return null;
    }


    private int getAvailableConnectionsNumber() {
        int number = 0;
        for (RentedConnection connection : connections) {
            if (connection.isAvailable()) {
                number++;
            }
        }
        return number;
    }

    public void init() throws DAOException {
        //loadDriverClass();
        semaphore = new Semaphore(maxConnections, true);
        connections = new CopyOnWriteArrayList<RentedConnection>();
        for (int i = 0; i < maxIdleConnections; i++) {
            connections.add(createNewConnection());
        }
    }

    public Connection getConnection() throws DAOException {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new DAOException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
        lock.lock();
        RentedConnection connection = findAvailableConnection();
        if (connection != null) {
            connection.setBusy();
            //One connection obtained from pool
        }
        lock.unlock();
        if (connection == null) {
            connection = createNewConnection();
            connection.setBusy();
            connections.add(connection);
            //Pool Increased, new Connection is created!
        }
        return connection;
    }

    public void returnConnection(Connection connection) throws DAOException {
        lock.lock();
        int available = getAvailableConnectionsNumber();

        if (available < maxIdleConnections){
            ((RentedConnection) connection).setAvailable();
            //One connection marked as available...
        }
        lock.unlock();

        if (available >= maxIdleConnections) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DAOException(DB_CONNECTION_EXCEPTION_TEXT, e);
            }

            connections.remove(connection);
            //Connection removed!
        }
        semaphore.release();
    }

    public void destroy() throws DAOException {
        try {
            for (RentedConnection connection : connections) {
                connection.close();
                connections.remove(connection);
            }
        } catch (SQLException e) {

            throw new DAOException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
    }


    public int getMaxConnections() {
        return maxConnections;
    }


    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }


    public int getMaxIdleConnections() {
        return maxIdleConnections;
    }


    public void setMaxIdleConnections(int maxIdleConnections) {
        this.maxIdleConnections = maxIdleConnections;
    }


    public String getDriverName() {
        return driverName;
    }


    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    public String getUser() {
        return user;
    }


    public void setUser(String user) {
        this.user = user;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(String password) {
        this.password = password;
    }


    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

}