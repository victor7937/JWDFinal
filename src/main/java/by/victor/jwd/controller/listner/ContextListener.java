package by.victor.jwd.controller.listner;

import by.victor.jwd.dao.connection.ConnectionPool;
import by.victor.jwd.dao.exceptions.ConnectionException;

import javax.servlet.*;
import javax.servlet.annotation.*;

@WebListener
public class ContextListener implements ServletContextListener {



    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionPool pool = ConnectionPool.getInstance();
        pool.setUrl("jdbc:mysql://localhost:3306/footware_db");
        pool.setUser("root");
        pool.setPassword("123");
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
