package by.victor.jwd.dao.util;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exceptions.ConnectionException;
import by.victor.jwd.dao.exceptions.DAOException;


import java.sql.*;

public class DAOResourceProvider implements AutoCloseable {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public DAOResourceProvider() {
        connection = null;
        statement = null;
        resultSet = null;
    }

    private void createConnection() throws ConnectionException {
        connection = connectionPool.getConnection();
    }

    private PreparedStatement createPreparedStatement(String s) throws SQLException {
        statement = connection.prepareStatement(s);
        return (PreparedStatement) statement;
    }

    private Statement createStatement() throws SQLException {
        statement = connection.createStatement();
        return statement;
    }

    public ResultSet createResultSet(String query) throws SQLException, ConnectionException {
        createConnection();
        resultSet = createStatement().executeQuery(query);
        return resultSet;
    }

    public ResultSet createResultSet(String query, SQLConsumer<PreparedStatement> consumer) throws SQLException, ConnectionException {
        createConnection();
        PreparedStatement ps = createPreparedStatement(query);
        consumer.accept(ps);
        return ps.executeQuery();
    }

    public boolean updateAction(String query, SQLConsumer<PreparedStatement> consumer) throws SQLException, ConnectionException {
        createConnection();
        PreparedStatement ps = createPreparedStatement(query);
        consumer.accept(ps);
        return ps.executeUpdate() > 0;
    }

    @Override
    public void close() throws DAOException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DAOException("Exception while closing statement", e);
            }
        }
        if (connection != null) {
            try {
                connectionPool.releaseConnection(connection);
            } catch (ConnectionException e) {
                throw new DAOException("Exception while releasing connection", e);
            }
        }

        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DAOException("Exception while closing resultSet", e);
            }
        }
    }
}
