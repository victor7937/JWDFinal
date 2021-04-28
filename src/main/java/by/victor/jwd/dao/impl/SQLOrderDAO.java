package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.*;
import by.victor.jwd.dao.OrderDAO;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.util.DAOResourceProvider;
import by.victor.jwd.dao.util.SQLConsumer;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * DAO that manipulates order-related data
 */
public class SQLOrderDAO implements OrderDAO {

    private static final Logger logger = Logger.getLogger(SQLOrderDAO.class);

    private static final String SQL_INSERT_ORDER = "INSERT INTO orders (ord_customer_email, ord_price) VALUES (?, ?)";

    private static final String SQL_INSERT_ORDER_ITEM = "INSERT INTO orders_items(oi_order_id, oi_item_id) VALUES(?, ?)";

    private static final String SQL_UPDATE_ITEM_STATUS = "UPDATE footwear_items SET fi_status = 'SOLVED' WHERE fi_id = ?";

    private static final String SQL_GET_ITEM = "SELECT fi_id FROM footwear_items WHERE fi_art = ? AND fi_size = ? AND fi_status = 'STOCK' LIMIT 1";

    private static final String SQL_GET_ORDERS = "SELECT * FROM orders ORDER BY ord_date DESC LIMIT ?,?";

    private static final String SQL_GET_ORDERS_COUNT = "SELECT COUNT(*) as count FROM orders";

    private static final String SQL_GET_ORDERS_OF_CUSTOMER = "SELECT * FROM orders WHERE ord_customer_email = ? ORDER BY ord_date DESC";

    private static final String SQL_GET_ORDERS_OF_STATUS = "SELECT * FROM orders WHERE ord_status = ? ORDER BY ord_date DESC";

    private static final String SQL_SET_ORDER_STATUS = "UPDATE orders SET ord_status = ? WHERE ord_id = ?";

    private static final String SQL_GET_ORDER_ITEMS = "SELECT oi_item_id FROM orders_items WHERE oi_order_id = ?";

    private static final String SQL_SET_ITEM_STATUS = "UPDATE footwear_items SET fi_status = 'STOCK' WHERE fi_id = ?";

    private static final String SQL_GET_ITEMS_BY_ORDER_ID = "SELECT fi_art, fi_size FROM orders_items oi" +
            " JOIN footwear_items fi ON fi.fi_id = oi.oi_item_id" +
            " JOIN orders o ON o.ord_id = oi.oi_order_id WHERE ord_id = ?";

    private final static int FIRST = 1;

    @Override
    public boolean createOrder(Order order) throws DAOException {
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            resourceProvider.setAutoCommit(false);
            String email = order.getCustomer().getEmail();
            AtomicInteger index = new AtomicInteger();
            ResultSet orderRS = resourceProvider.updateActionWithResultSet(SQL_INSERT_ORDER, ps -> {
                ps.setString(index.incrementAndGet(), email);
                ps.setFloat(index.incrementAndGet(), order.getPrice());
            });
            if (!orderRS.next()) {
                resourceProvider.rollback();
                logger.error("Order not updated");
                return false;
            }

            int orderId = orderRS.getInt(FIRST);
            for (FootwearItem item : order.getItems()) {
                for (int i = 0; i < item.getQuantity(); i++) {
                    index.set(0);
                    ResultSet itemsRS = resourceProvider.createResultSet(SQL_GET_ITEM, ps -> {
                        ps.setString(index.incrementAndGet(), item.getFootwear().getArt());
                        ps.setFloat(index.incrementAndGet(), item.getSize());
                    });

                    if (!itemsRS.next()) {
                        resourceProvider.rollback();
                        logger.error("Item id not found");
                        return false;
                    }
                    int itemId = itemsRS.getInt(ColumnNames.FI_ID);
                    index.set(0);
                   resourceProvider.updateAction(SQL_INSERT_ORDER_ITEM, ps -> {
                        ps.setInt(index.incrementAndGet(), orderId);
                        ps.setInt(index.incrementAndGet(), itemId);
                    });
                    resourceProvider.updateAction(SQL_UPDATE_ITEM_STATUS, ps -> ps.setInt(FIRST, itemId));
                }
            }
            resourceProvider.commit();
            return true;
        } catch (ConnectionException | SQLException e) {
            throw new DAOException("Create order error", e);
        }
    }

    @Override
    public List<Order> showOrders(String lang, int offset, int limit) throws DAOException {
        AtomicInteger i = new AtomicInteger();
        return getOrdersByParams(SQL_GET_ORDERS, ps -> {
            ps.setInt(i.incrementAndGet(), offset);
            ps.setInt(i.incrementAndGet(), limit);
        }, lang);
    }

    @Override
    public List<Order> showOrdersOfCustomer(String email, String lang) throws DAOException {
        return getOrdersByParams(SQL_GET_ORDERS_OF_CUSTOMER, ps -> ps.setString(FIRST, email), lang);
    }

    @Override
    public List<Order> showOrdersOfStatus(OrderStatus orderStatus, String lang) throws DAOException {
        return getOrdersByParams(SQL_GET_ORDERS_OF_STATUS, ps -> ps.setString(FIRST, orderStatus.toString()), lang);
    }

    @Override
    public boolean setOrderStatus(Integer orderId, OrderStatus status) throws DAOException {
        if (status.equals(OrderStatus.DECLINE)) {
            return declineOrder(orderId);
        }
        else {
            boolean successUpdating;
            try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
                AtomicInteger i = new AtomicInteger();
                successUpdating = resourceProvider.updateAction(SQL_SET_ORDER_STATUS, ps -> {
                    ps.setString(i.incrementAndGet(), status.toString());
                    ps.setInt(i.incrementAndGet(), orderId);
                });
            } catch (SQLException | ConnectionException e) {
                throw new DAOException("Setting order status error",e);
            }
            return successUpdating;
        }
    }

    @Override
    public Integer getOrdersCount() throws DAOException {
        Integer count = null;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ORDERS_COUNT);
            if (resultSet.next()) {
                count = resultSet.getInt(ColumnNames.ORD_COUNT);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting orders count error", e);
        }
        return count;
    }

    private boolean declineOrder(Integer orderId) throws DAOException {
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            resourceProvider.setAutoCommit(false);
            AtomicInteger i = new AtomicInteger();
            boolean isStatusUpd = resourceProvider.updateAction(SQL_SET_ORDER_STATUS, ps -> {
                ps.setString(i.incrementAndGet(), OrderStatus.DECLINE.toString());
                ps.setInt(i.incrementAndGet(), orderId);
            });
            if (!isStatusUpd) {
                resourceProvider.rollback();
                logger.error("Order status wasn't updated");
                return false;
            }
            ResultSet idsRS = resourceProvider.createResultSet(SQL_GET_ORDER_ITEMS, ps-> ps.setInt(FIRST, orderId));
            while (idsRS.next()) {
                int id = idsRS.getInt(ColumnNames.OI_ITEM_ID);
                boolean isUpdStatusItem = resourceProvider.updateAction(SQL_SET_ITEM_STATUS, ps -> ps.setInt(FIRST, id));
                if (!isUpdStatusItem) {
                    resourceProvider.rollback();
                    logger.error("Item status wasn't updated");
                    return false;
                }
            }
            resourceProvider.commit();
            return true;
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Updating status error", e);
        }
    }

    private List<Order> getOrdersByParams (String query, SQLConsumer<PreparedStatement> consumer, String lang) throws DAOException {
        List<Order> orderList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet ordersRS;
            if (consumer == null) {
                 ordersRS = resourceProvider.createResultSet(query);
            } else {
                ordersRS = resourceProvider.createResultSet(query, consumer);
            }
            while (ordersRS.next()) {
                int orderId = ordersRS.getInt(ColumnNames.ORD_ID);
                Order order = buildOrder(ordersRS);
                if (order == null) {
                    continue;
                }
                order.setId(orderId);
                ResultSet itemsRS = resourceProvider.createResultSet(SQL_GET_ITEMS_BY_ORDER_ID, ps -> ps.setInt(FIRST, orderId));
                while (itemsRS.next()) {
                    FootwearItem item =  buildItem(itemsRS, lang);
                    if (item == null) {
                        throw new DAOException("Missing data");
                    }
                    order.addItem(item);
                }
                orderList.add(order);
            }
        } catch (ConnectionException | SQLException e) {
            throw new DAOException("Show orders error", e);
        }
        collectEquals(orderList);
        return orderList;
    }

    private Order buildOrder(ResultSet resultSet) throws DAOException {
        Order order = null;
        try {
            String email = resultSet.getString(ColumnNames.ORD_CUSTOMER_EMAIL);
            LocalDateTime date = resultSet.getTimestamp(ColumnNames.ORD_DATE).toLocalDateTime();
            Float price = resultSet.getFloat(ColumnNames.ORD_PRICE);
            OrderStatus orderStatus = OrderStatus.valueOf(resultSet.getString(ColumnNames.ORD_STATUS));
            SQLCustomerDAO customerDAO = new SQLCustomerDAO();
            Customer customer = customerDAO.getCustomerByEmail(email);
            if (customer != null) {
                order = new Order(customer, date);
                order.setPrice(price);
                order.setOrderStatus(orderStatus);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating order",e);
        }
        return order;
    }

    private FootwearItem buildItem(ResultSet resultSet, String lang) throws DAOException {
        FootwearItem item = null;
        try {
            String art = resultSet.getString(ColumnNames.FI_ART);
            Float size = resultSet.getFloat(ColumnNames.FI_SIZE);
            SQLFootwearDAO footwearDAO = new SQLFootwearDAO();
            Footwear footwear = footwearDAO.getFootwearByArt(art, lang);
            if (footwear != null) {
               item = new FootwearItem(footwear, size, FIRST);
            }
        } catch (SQLException e) {
            throw new DAOException("Error while creating footwear item",e);
        }
        return item;
    }

    private void collectEquals (List<Order> orders) {
        for (Order order : orders) {
            List<FootwearItem> items = order.getItems();
            Map<FootwearItem, Long> collectedItems = items.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            List<FootwearItem> newItems = new ArrayList<>();
            collectedItems.forEach((k,v) -> {
                k.setQuantity(v.intValue());
                newItems.add(k);
            });
            order.setItems(newItems);
        }
    }

    static class ColumnNames {
        public static final String FI_SIZE = "fi_size";
        public static final String FI_ART = "fi_art";
        public static final String ORD_STATUS = "ord_status";
        public static final String ORD_PRICE = "ord_price";
        public static final String ORD_DATE = "ord_date";
        public static final String ORD_CUSTOMER_EMAIL = "ord_customer_email";
        public static final String ORD_ID = "ord_id";
        public static final String OI_ITEM_ID = "oi_item_id";
        public static final String FI_ID = "fi_id";
        public static final String ORD_COUNT = "count";
    }

}
