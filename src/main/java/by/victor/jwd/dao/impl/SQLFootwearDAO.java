package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.bean.ItemStatus;
import by.victor.jwd.dao.FootwearDAO;
import by.victor.jwd.dao.exception.ConnectionException;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.dao.util.DAOResourceProvider;
import by.victor.jwd.dao.util.SQLConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLFootwearDAO implements FootwearDAO {

    private static final String SQL_GET_FOOTWEARS = "SELECT f_art, f_name, f_price, c_name_%s AS category," +
            " f_description_%s AS description, cl_name_%s AS color, b_name AS brand, f_for FROM footwears f JOIN " +
            "categories c ON c.c_id = f.f_category JOIN brands b ON b.b_id = f.f_brand JOIN colors c2 ON " +
            "c2.cl_id = f.f_color";

    private static final String SQL_BY_CATEGORY = " WHERE c_name_%s = ?";

    private static final String SQL_BY_BRAND = " WHERE b_name = ?";

    private static final String SQL_BY_CATEGORY_AND_BRAND = " WHERE c_name_%s = ? AND b_name = ?";

    private static final String SQL_FOR_ADDING = " AND f_for = ?";

    private static final String SQL_FOR = " WHERE f_for = ?";

    private static final String SQL_BY_ART = " WHERE f_art = ?";

    private static final String SQL_GET_CATEGORIES = "SELECT c_name_%s AS name FROM categories";

    private static final String SQL_GET_COLORS = "SELECT cl_name_%s AS name FROM colors";

    private static final String SQL_GET_CATEGORY_ID = "SELECT c_id FROM categories WHERE c_name_%s = ?";

    private static final String SQL_GET_BRANDS = "SELECT b_name FROM brands";

    private static final String SQL_GET_BRAND_ID = "SELECT b_id FROM brands WHERE b_name = ?";

    private static final String SQL_GET_SIZES = "SELECT DISTINCT fi_size FROM footwear_items where fi_art = ? AND fi_status = 'STOCK'";

    private static final String SQL_GET_COLOR_ID = "SELECT cl_id FROM colors WHERE cl_name_%s = ?";

    private static final String SQL_GET_QUANTITY = "SELECT COUNT(fi_id) AS quantity FROM footwear_items WHERE fi_art = ? " +
            "AND fi_size = ? AND fi_status = 'STOCK'";

    private static final String SQL_ADD_FOOTWEAR = "INSERT INTO footwears (f_art, f_name, f_price, f_category, f_for, f_color, f_brand, f_description_en, f_description_ru) "+
    "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_ADD_IMAGE = "INSERT INTO footwear_images (img_name, img_art) VALUES(?, ?)";

    private static final String SQL_GET_IMAGES = "SELECT img_name FROM footwear_images WHERE img_art = ? ORDER BY img_name";

    private static final String SQL_ADD_ITEM = "INSERT INTO footwear_items(fi_art, fi_size) VALUES (?, ?)";

    private static final String SQL_ADD_BRAND = "INSERT INTO brands (b_name) VALUES (?)";

    private static final String SQL_ADD_CATEGORY = "INSERT INTO categories (c_name_en, c_name_ru) VALUES (?, ?)";

    private static final String SQL_ADD_COLOR = "INSERT INTO colors (cl_name_en, cl_name_ru) VALUES (?, ?)";

    private static final String SQL_GET_ITEMS_BY_ART = "SELECT fi_id, fi_size, fi_status FROM footwear_items WHERE fi_art = ?";

    private static final String SQL_GET_ITEM_STATUS = "SELECT fi_status FROM footwear_items WHERE fi_id = ?";

    private static final String SQL_UPDATE_ITEM_STATUS = "UPDATE footwear_items SET fi_status = ? WHERE fi_id = ?";

    @Override
    public List<Footwear> getAll(ForEnum forEnum, String lang) throws DAOException {
        String query = String.format(SQL_GET_FOOTWEARS, lang, lang, lang);
        if (forEnum == ForEnum.ALL) {
            return getFootwearList(query,null);
        }
        else {
            return getFootwearList(query + SQL_FOR, ps -> ps.setString(1, forEnum.toString()));
        }
    }

    @Override
    public List<Footwear> getByCategory(String category, ForEnum forEnum, String lang) throws DAOException {
        String query = String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + String.format(SQL_BY_CATEGORY, lang)
                + (forEnum == ForEnum.ALL ? "" : SQL_FOR_ADDING);

        return getFootwearList(query, ps -> {
            ps.setString(1, category);
            if (forEnum != ForEnum.ALL)
                ps.setString(2, forEnum.toString());
        });
    }

    @Override
    public List<Footwear> getByCategoryAndBrand(String category, String brand, ForEnum forEnum, String lang) throws DAOException {
        String query = String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + String.format(SQL_BY_CATEGORY_AND_BRAND, lang)
                + (forEnum == ForEnum.ALL ? "" : SQL_FOR_ADDING);

        return getFootwearList(query, ps -> {
            ps.setString(1, category);
            ps.setString(2, brand);
            if (forEnum != ForEnum.ALL)
                ps.setString(3, forEnum.toString());
        });
    }

    @Override
    public List<Footwear> getByBrand(String brand, ForEnum forEnum, String lang) throws DAOException {
        String query = String.format(SQL_GET_FOOTWEARS, lang, lang, lang) + SQL_BY_BRAND
                + (forEnum == ForEnum.ALL ? "" : SQL_FOR_ADDING);

        return getFootwearList(query, ps -> {
            ps.setString(1, brand);
            if (forEnum != ForEnum.ALL)
                ps.setString(2, forEnum.toString());
        });
    }

    private List<Footwear> getFootwearList(String query, SQLConsumer<PreparedStatement> ps) throws DAOException{
        List<Footwear> footwearList = new ArrayList<>();

        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet;
            if (ps == null) {
                resultSet = resourceProvider.createResultSet(query);
            } else {
                resultSet = resourceProvider.createResultSet(query, ps);
            }
            while (resultSet.next()) {
                Footwear footwear = buildFootwear(resultSet);
                footwearList.add(footwear);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting footwear by params error", e);
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
            throw new DAOException("Getting footwear by art error", e);
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
            throw new DAOException("Getting categories error", e);
        }
        return categoryList;
    }

    @Override
    public List<String> getColors(String lang) throws DAOException {
        List<String> colorList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_COLORS, lang));
            while (resultSet.next()) {
                String category = resultSet.getString("name");
                colorList.add(category);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting colors error", e);
        }
        return colorList;
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
            throw new DAOException("Getting brands error", e);
        }
        return brandList;
    }

    @Override
    public ItemStatus getItemStatus(Integer id) throws DAOException {
        ItemStatus itemStatus = null;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ITEM_STATUS, ps -> ps.setInt(1, id));
            if (resultSet.next()) {
                itemStatus = ItemStatus.valueOf(resultSet.getString("fi_status"));
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting item status error", e);
        }
        return itemStatus;
    }

    @Override
    public List<FootwearItem> getItemsByArt(String art) throws DAOException {
        List<FootwearItem> itemsList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ITEMS_BY_ART, ps -> ps.setString(1, art));
            while (resultSet.next()) {
               FootwearItem item = buildItem(resultSet, art);
               itemsList.add(item);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting footwear items error", e);
        }
        return itemsList;
    }

    @Override
    public List<Float> getSizes(String art) throws DAOException {
        List<Float> sizesList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_SIZES, ps -> ps.setString(1, art));
            while (resultSet.next()) {
                Float size = resultSet.getFloat("fi_size");
                sizesList.add(size);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting sizes error", e);
        }
        return sizesList;
    }

    @Override
    public Integer getMaxQuantity(String art, Float size) throws DAOException {
        int quantity = 0;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_QUANTITY, ps -> {
                ps.setString(1, art);
                ps.setFloat(2, size);
            });
            if (resultSet.next()) {
                quantity = resultSet.getInt("quantity");
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting max quantity error", e);
        }
        return quantity;
    }


    @Override
    public boolean addNewFootwear(Footwear footwear, String description_en, String description_ru, String lang) throws DAOException {
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            resourceProvider.setAutoCommit(false);
            ResultSet categoryIdRS = resourceProvider.createResultSet(String.format(SQL_GET_CATEGORY_ID, lang),
                    ps -> ps.setString(1, footwear.getCategory()));
            if (!categoryIdRS.next()) {
                return false;
            }
            int categoryId = categoryIdRS.getInt("c_id");

            ResultSet colorIdRS = resourceProvider.createResultSet(String.format(SQL_GET_COLOR_ID, lang),
                    ps -> ps.setString(1, footwear.getColor()));
            if (!colorIdRS.next()) {
                return false;
            }
            int colorId = colorIdRS.getInt("cl_id");

            ResultSet brandIdRS = resourceProvider.createResultSet(SQL_GET_BRAND_ID,
                    ps -> ps.setString(1, footwear.getBrand()));
            if (!brandIdRS.next()) {
                return false;
            }
            int brandId = brandIdRS.getInt("b_id");

            boolean updateSuccess = resourceProvider.updateAction(SQL_ADD_FOOTWEAR, ps -> {
                ps.setString(1, footwear.getArt());
                ps.setString(2, footwear.getName());
                ps.setFloat(3, footwear.getPrice());
                ps.setInt(4, categoryId);
                ps.setString(5, footwear.getForWhom().toString());
                ps.setInt(6, colorId);
                ps.setInt(7, brandId);
                ps.setString(8, description_en);
                ps.setString(9, description_ru);
            });

            if (!updateSuccess) {
                resourceProvider.rollback();
                return false;
            }

            for (String image : footwear.getImageLinks()) {
                boolean imageAdding = resourceProvider.updateAction(SQL_ADD_IMAGE, ps -> {
                    ps.setString(1, image);
                    ps.setString(2, footwear.getArt());
                });
                if (!imageAdding) {
                    resourceProvider.rollback();
                    return false;
                }
            }

            resourceProvider.commit();
            return true;

        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Creating footwear error",e);
        }
    }

    @Override
    public boolean addNewItem(String art, Float size) throws DAOException {
        boolean successAdding;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successAdding = resourceProvider.updateAction(SQL_ADD_ITEM, ps -> {
               ps.setString(1, art);
               ps.setFloat(2, size);
            });
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Adding new item error", e);
        }
        return successAdding;
    }

    @Override
    public boolean addNewCategory(String category_en, String category_ru) throws DAOException {
        boolean successAdding;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successAdding = resourceProvider.updateAction(SQL_ADD_CATEGORY, ps -> {
                ps.setString(1, category_en);
                ps.setString(2, category_ru);
            });
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Adding new category error", e);
        }
        return successAdding;
    }

    @Override
    public boolean addNewColor(String color_en, String color_ru) throws DAOException {
        boolean successAdding;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successAdding = resourceProvider.updateAction(SQL_ADD_COLOR, ps -> {
                ps.setString(1, color_en);
                ps.setString(2, color_ru);
            });
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Adding new color error", e);
        }
        return successAdding;
    }

    @Override
    public boolean addNewBrand(String brand) throws DAOException {
        boolean successAdding;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successAdding = resourceProvider.updateAction(SQL_ADD_BRAND, ps -> ps.setString(1, brand));
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Adding new brand error", e);
        }
        return successAdding;
    }

    @Override
    public boolean updateItemStatus(Integer itemId, ItemStatus status) throws DAOException {
        boolean successUpdating;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successUpdating = resourceProvider.updateAction(SQL_UPDATE_ITEM_STATUS, ps -> {
                ps.setString(1, status.toString());
                ps.setInt(2, itemId);
            });
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Updating item status error", e);
        }
        return successUpdating;
    }

    @Override
    public boolean deleteFootwear(String art) throws DAOException {
        return false;
    }

    @Override
    public boolean updateFootwear(String art, Footwear footwear) throws DAOException {
        return false;
    }

    private Footwear buildFootwear(ResultSet resultSet) throws DAOException {
        Footwear footwear;
        try {
            String art = resultSet.getString("f_art");
            String name = resultSet.getString("f_name");
            Float price = resultSet.getFloat("f_price");
            String category = resultSet.getString("category");
            String description = resultSet.getString("description");
            String color = resultSet.getString("color");
            String brand = resultSet.getString("brand");
            String forWhom = resultSet.getString("f_for");
            List<String> imageList = getImages(art);
            footwear = new Footwear(art, name, price, color, category,
                    description, imageList, brand,  ForEnum.valueOf(forWhom));

        } catch (SQLException e) {
            throw new DAOException("Building footwear error", e);
        }
        return footwear;
    }

    private FootwearItem buildItem(ResultSet resultSet, String art) throws DAOException {
        FootwearItem item;
        try {
            Integer id = resultSet.getInt("fi_id");
            Float size = resultSet.getFloat("fi_size");
            ItemStatus status = ItemStatus.valueOf(resultSet.getString("fi_status"));
            item = new FootwearItem(new Footwear(art), size);
            item.setId(id);
            item.setStatus(status);
        } catch (SQLException e) {
            throw new DAOException("Getting items error", e);
        }
        return item;
    }

    private List<String> getImages(String art) throws DAOException {
        List<String> imageList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_IMAGES, ps -> ps.setString(1, art));
            while (resultSet.next()) {
                String imageName = resultSet.getString("img_name");
                imageList.add(imageName);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting images error", e);
        }
        return imageList;
    }
}
