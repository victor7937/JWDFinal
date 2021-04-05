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
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;




public class SQLFootwearDAO implements FootwearDAO {

    private static final String SQL_GET_FOOTWEARS = "SELECT f_art, f_name, f_price, c_name_%s AS category," +
            " f_description_%s AS description, f_image_link, cl_name_%s AS color, b_name AS brand, f_for FROM footwears f JOIN " +
            "categories c ON c.c_id = f.f_category JOIN brands b ON b.b_id = f.f_brand JOIN colors c2 ON " +
            "c2.cl_id = f.f_color";

    private static final String SQL_BY_CATEGORY = " WHERE c_name_%s = ?";
    private static final String SQL_BY_BRAND = " WHERE b_name = ?";
    private static final String SQL_BY_FOR_WHOM = " WHERE f_for = ?";
    private static final String SQL_BY_ART = " WHERE f_art = ?";

    private static final String SQL_GET_CATEGORIES = "SELECT c_name_%s AS name FROM categories";
    private static final String SQL_GET_BRANDS = "SELECT b_name FROM brands";

    private static final String DATA_ACCESS_EXCEPTION_TEXT =
            "It's impossible to get a connection from the connection pool, "
                    + "execute a query, build a bean object, close the statement "
                    + "or return the connection back";

    @Override
    public List<Footwear> getAll(String lang) throws DAOException {
        List<Footwear> footwearList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEARS, lang, lang, lang));
            while (resultSet.next()) {
                Footwear footwear = buildFootwear(resultSet);
                footwearList.add(footwear);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwearList;
    }

    @Override
    public List<Footwear> getByCategory(String category, String lang) throws DAOException {
        List<Footwear> footwearList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEARS, lang, lang, lang) +
                    String.format(SQL_BY_CATEGORY, lang), ps -> ps.setString(1, category));
            while (resultSet.next()) {
                Footwear footwear = buildFootwear(resultSet);
                footwearList.add(footwear);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwearList;
    }

    @Override
    public List<Footwear> getByForEnum(ForEnum forWhom, String lang) throws DAOException {
        List<Footwear> footwearList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + SQL_BY_FOR_WHOM,
                    ps -> ps.setString(1, forWhom.toString().toLowerCase()));
            while (resultSet.next()) {
                Footwear footwear = buildFootwear(resultSet);
                footwearList.add(footwear);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwearList;
    }

    @Override
    public List<Footwear> getByBrand(String brand, String lang) throws DAOException {
        List<Footwear> footwearList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + SQL_BY_BRAND,
                    ps -> ps.setString(1, brand));
            while (resultSet.next()) {
                Footwear footwear = buildFootwear(resultSet);
                footwearList.add(footwear);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwearList;
    }

    @Override
    public Footwear getFootwearByArt(String art, String lang) throws DAOException {
        Footwear footwear = null;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + SQL_BY_ART,
                    ps -> ps.setString(1, art));
            if (resultSet.next()) {
                footwear = buildFootwear(resultSet);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return footwear;
    }

    @Override
    public List<String> getCategories(String lang) throws DAOException {
        List<String> categoryList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_CATEGORIES, lang));
            while (resultSet.next()) {
                String category = resultSet.getString("name");
                categoryList.add(category);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return categoryList;
    }

    @Override
    public List<String> getBrands() throws DAOException {
        List<String> brandList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_BRANDS);
            while (resultSet.next()) {
                String brand = resultSet.getString("b_name");
                brandList.add(brand);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException(DATA_ACCESS_EXCEPTION_TEXT, e);
        }
        return brandList;
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
            String description = resultSet.getString("description");
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
