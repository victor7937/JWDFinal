package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.Order;
import by.victor.jwd.dao.OrderDAO;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.util.DAOResourceProvider;
import by.victor.jwd.service.OrderService;
import org.apache.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLOrderDAO implements OrderDAO {

    private static final Logger logger = Logger.getLogger(SQLOrderDAO.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (ord_customer_email, ord_price) VALUES (?, ?)";

    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO orders_items(oi_order_id, oi_item_id) VALUES(?, ?)";

    private static final String SQL_UPDATE_ITEM_STATUS = "UPDATE footwear_items SET fi_status = 'SOLVED' WHERE fi_id = ?";

    private static final String SQL_GET_ITEM = "SELECT fi_id FROM footwear_items WHERE fi_art = ? AND fi_size = ? AND fi_status = 'STOCK' LIMIT 1";

    @Override
    public boolean createOrder(Order order) throws DAOException {
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            resourceProvider.setAutoCommit(false);
            String email = order.getCustomer().getEmail();
            ResultSet orderRS = resourceProvider.updateActionWithResultSet(SQL_INSERT_ORDER, ps -> {
                ps.setString(1, email);
                ps.setFloat(2, order.getPrice());
            });
            if (!orderRS.next()) {
                resourceProvider.rollback();
                logger.error("Order not updated");
                return false;
            }
//            resourceProvider.commit();

            int orderId = orderRS.getInt(1);
            for (FootwearItem item : order.getItems()) {
                for (int i = 0; i < item.getQuantity(); i++) {

                    ResultSet itemsRS = resourceProvider.createResultSet(SQL_GET_ITEM, ps -> {
                        ps.setString(1, item.getFootwear().getArt());
                        ps.setFloat(2, item.getSize());
                    });

                    if (!itemsRS.next()) {
                        resourceProvider.rollback();
                        logger.error("Item id not found");
                        return false;
                    }
                    int itemId = itemsRS.getInt("fi_id");

                   resourceProvider.updateAction(SQL_INSERT_ORDER_ITEM, ps -> {
                        ps.setInt(1, orderId);
                        ps.setInt(2, itemId);
                    });
                    resourceProvider.updateAction(SQL_UPDATE_ITEM_STATUS, ps -> ps.setInt(1, itemId));
                }
            }
            resourceProvider.commit();
            return true;
        } catch (ConnectionException | SQLException e) {
            throw new DAOException("Create order error", e);
        }

    }
}
