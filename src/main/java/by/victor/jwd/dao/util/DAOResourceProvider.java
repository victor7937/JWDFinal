package by.victor.jwd.dao.util;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;


import java.sql.*;

public class DAOResourceProvider implements AutoCloseable {

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private Boolean autoCommit;
    private Boolean committed;

    public DAOResourceProvider() {
        connection = null;
        statement = null;
        resultSet = null;
        autoCommit = true;
        committed = false;
    }

    public boolean isAutoCommit() {
        return autoCommit;
    }

    public void setAutoCommit(boolean autoCommit) {
        this.autoCommit = autoCommit;
    }

    public void commit() throws SQLException {
        if (connection != null) {
            connection.commit();
            committed = true;
        }
    }

    public void rollback() throws SQLException {
        if (connection != null) {
            connection.rollback();
            committed = true;
        }
    }

    private void createConnection() throws ConnectionException, SQLException {
        if (connection == null ) {
            connection = connectionPool.getConnection();
        }
        if (autoCommit != connection.getAutoCommit()) {
            connection.setAutoCommit(autoCommit);
        }
        committed = false;
    }

    private PreparedStatement createPreparedStatement(String s) throws SQLException {
        statement = connection.prepareStatement(s);
        return (PreparedStatement) statement;
    }

    private PreparedStatement createPreparedStatementWithGK(String s) throws SQLException {
        statement = connection.prepareStatement(s, Statement.RETURN_GENERATED_KEYS);
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

    public ResultSet updateActionWithResultSet(String query, SQLConsumer<PreparedStatement> consumer) throws SQLException, ConnectionException {
        createConnection();
        PreparedStatement ps = createPreparedStatementWithGK(query);
        consumer.accept(ps);
        ps.executeUpdate();
        return ps.getGeneratedKeys();
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
                if (!connection.getAutoCommit()) {
                    if (!committed) {
                        connection.rollback();
                    }
                    connection.setAutoCommit(true);
                }
                connectionPool.releaseConnection(connection);
            } catch (ConnectionException | SQLException e) {
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
