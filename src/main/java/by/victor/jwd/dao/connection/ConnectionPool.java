package by.victor.jwd.dao.connection;

import by.victor.jwd.dao.exception.ConnectionException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;


/**
 * Connection pool that is needed for getting connections to SQL database and
 * release them back to pool.
 * Can create given quantity of connections and destroy them all when it needed
 */
public final class ConnectionPool {

    private static final String DB_CONNECTION_EXCEPTION_TEXT = "Connection data is incorrect";

    private final static int MAX_CONNECTIONS_DEFAULT = 20;
    private final static int MAX_IDLE_CONNECTIONS_DEFAULT = 10;

    private int connectionsLimit;
    private int idleConnectionsCount;
    private String driverName;
    private String url;
    private String user;
    private String password;
    private String locale;

    private final ReentrantLock lock = new ReentrantLock(true);
    private Semaphore semaphore;
    private List<RentedConnection> connections;

    private static class InstanceCreator {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return InstanceCreator.INSTANCE;
    }

    private ConnectionPool() {
        connectionsLimit = MAX_CONNECTIONS_DEFAULT;
        idleConnectionsCount = MAX_IDLE_CONNECTIONS_DEFAULT;
    }
    
    private void loadDriver() throws ConnectionException {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            throw new ConnectionException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
    }

    private RentedConnection createNewConnection() throws ConnectionException {
        try {
            return new RentedConnection(DriverManager.getConnection(url, user, password));
        } catch (SQLException e) {
            throw new ConnectionException(DB_CONNECTION_EXCEPTION_TEXT, e);
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

    public int getAvailableConnectionsCount() {
        return (int) connections.stream().filter(RentedConnection::isAvailable).count();
    }

    public void init(int connectionsLimit, int idleConnectionsCount) throws ConnectionException {
        loadDriver();
        this.connectionsLimit = connectionsLimit;
        this.idleConnectionsCount = idleConnectionsCount;
        createIdleConnections();
    }

    public void init () throws ConnectionException {
        loadDriver();
        createIdleConnections();
    }

    private void createIdleConnections () throws ConnectionException {
        semaphore = new Semaphore(connectionsLimit, true);
        connections = new CopyOnWriteArrayList<>();
        for (int i = 0; i < idleConnectionsCount; i++) {
            connections.add(createNewConnection());
        }
    }

    public Connection getConnection() throws ConnectionException {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new ConnectionException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
        lock.lock();
        RentedConnection connection = findAvailableConnection();
        if (connection != null) {
            connection.setBusy();
        }
        lock.unlock();
        if (connection == null) {
            connection = createNewConnection();
            connection.setBusy();
            connections.add(connection);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) throws ConnectionException {
        lock.lock();
        int available = getAvailableConnectionsCount();

        if (available < idleConnectionsCount){
            ((RentedConnection) connection).setAvailable();
        }
        lock.unlock();

        if (available >= idleConnectionsCount) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new ConnectionException(DB_CONNECTION_EXCEPTION_TEXT, e);
            }

            connections.remove(connection);
        }
        semaphore.release();
    }

    public void destroy() throws ConnectionException {
        try {
            for (RentedConnection connection : connections) {
                connection.close();
                connections.remove(connection);
            }
        } catch (SQLException e) {
            throw new ConnectionException(DB_CONNECTION_EXCEPTION_TEXT, e);
        }
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }

    public int getIdleConnectionsCount() {
        return idleConnectionsCount;
    }

    public void setIdleConnectionsCount(int idleConnectionsCount) {
        this.idleConnectionsCount = idleConnectionsCount;
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