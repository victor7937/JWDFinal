package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.dao.FootwearDAO;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.util.DAOResourceProvider;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;




public class SQLFootwearDAO implements FootwearDAO {


    private static final String SQL_GET_FOOTWEAR_BY_ART = "SELECT f_art, f_name, f_price, c_name_en AS category," +
            " f_description, f_image_link, cl_name_en AS color, b_name AS brand, f_for FROM footwears f JOIN " +
            "categories c ON c.c_id = f.f_category JOIN brands b ON b.b_id = f.f_brand JOIN colors c2 ON " +
            "c2.cl_id = f.f_color WHERE f_art = ?";

    private static final String DATA_ACCESS_EXCEPTION_TEXT =
            "It's impossible to get a connection from the connection pool, "
                    + "execute a query, build a bean object, close the statement "
                    + "or return the connection back";

    @Override
    public List<Footwear> getAll() throws DAOException {
        return null;
    }

    @Override
    public Footwear getFootwearByArt(String art) throws DAOException {
        Footwear footwear = null;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_FOOTWEAR_BY_ART, ps -> ps.setString(1, art));
            if (resultSet.next()) {
                footwear = buildFootwear(resultSet);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwear;
    }

    @Override
    public boolean addNewFootwear(Footwear footwear) throws DAOException {
        return false;
    }

    @Override
    public boolean deleteFootwear(String art) throws DAOException {
        return false;
    }

    @Override
    public boolean updateFootwear(String art, Customer footwear) throws DAOException {
        return false;
    }

    private Footwear buildFootwear(ResultSet resultSet) throws DAOException {
        Footwear footwear = null;
        try {
            String art = resultSet.getString("f_art");
            String name = resultSet.getString("f_name");
            Float price = resultSet.getFloat("f_price");
            String category = resultSet.getString("category");
            String description = resultSet.getString("f_description");
            String imageLink = resultSet.getString("f_image_link");
            String color = resultSet.getString("color");
            String brand = resultSet.getString("brand");
            String forWhom = resultSet.getString("f_for");
            footwear = new Footwear(art, name, price, color, category,
                    description, imageLink, brand,  ForEnum.valueOf(forWhom.toUpperCase()));

        } catch (SQLException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwear;
    }
}
