package by.victor.jwd.controller.listner;

import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.ConnectionService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * This Listener is used to create and destroy connections pool
 */
@WebListener
public class ContextListener implements ServletContextListener {

    private static final ResourceBundle db_bundle = ResourceBundle.getBundle("application");
    private static final String BEST_PRODUCTS_PATH = db_bundle.getString("path.to.best.products");
    private static final String POPULAR_ATTRIBUTE = "popular";

    Logger logger = Logger.getLogger(ContextListener.class);

    public ContextListener() { }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ConnectionService connectionService = ServiceProvider.getInstance().getConnectionService();
        try {
           connectionService.initConnectionPool();
        } catch (ServiceException e) {
            throw new ControllerException("Sorry, we have some technical problems and working to fix them soon.\n Please try to connect later.");
        }

        Map<String, Integer> popularMap;
        try(BufferedReader reader = new BufferedReader(new FileReader(BEST_PRODUCTS_PATH))) {
            popularMap = new Gson().fromJson(reader, new TypeToken<Map<String, Integer>>() {}.getType());
        } catch (IOException e) {
            logger.error("Error while reading best products", e);
            throw new ControllerException("Technical problems with getting best products");
        }
        sce.getServletContext().setAttribute(POPULAR_ATTRIBUTE, popularMap);
//

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        Map<String, Integer> popularMap = (Map<String, Integer>) sce.getServletContext().getAttribute(POPULAR_ATTRIBUTE);

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(BEST_PRODUCTS_PATH))) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(popularMap, writer);
        } catch (IOException e) {
            logger.error("Error while reading best products", e);
            throw new ControllerException("Technical problems with writing best products");
        }

        ConnectionService connectionService = ServiceProvider.getInstance().getConnectionService();
        try {
            connectionService.destroyConnectionPool();
        } catch (ServiceException e) {
            throw new ControllerException("Sorry, we have some technical problems and working to fix them soon.\n Please try to connect later.");
        }
    }


}
