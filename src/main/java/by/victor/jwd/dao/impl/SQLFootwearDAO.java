package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.*;
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
import java.util.concurrent.atomic.AtomicInteger;

/**
 * DAO that manipulates footwear-related data
 */
public class SQLFootwearDAO implements FootwearDAO {

    private static final String SQL_GET_FOOTWEAR = "SELECT f_art, f_name, f_price, c_name_%s AS category," +
            " f_description_%s AS description, cl_name_%s AS color, b_name AS brand, f_for FROM footwears f" +
            " JOIN categories c ON c.c_id = f.f_category" +
            " JOIN brands b ON b.b_id = f.f_brand" +
            " JOIN colors c2 ON c2.cl_id = f.f_color";

    private static final String SQL_GET_ACTUAL_FOOTWEAR =
    "SELECT DISTINCT f_art, f_name, f_price, c_name_%s AS category, f_description_%s AS description, cl_name_%s AS color, b_name AS brand, f_for FROM footwears f" +
    " JOIN categories c ON c.c_id = f.f_category" +
    " JOIN brands b ON b.b_id = f.f_brand" +
    " JOIN colors c2 ON c2.cl_id = f.f_color" +
    " JOIN footwear_items fi on f.f_art = fi.fi_art WHERE fi_status='STOCK'";

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

    private static final String SQL_UPDATE_FOOTWEAR = "UPDATE footwears SET f_name = ?, f_price = ?, f_category = ?," +
            " f_for = ?, f_color = ?, f_brand = ?, f_description_%s = ? WHERE f_art = ?";

    private static final String SQL_ADD_IMAGE = "INSERT INTO footwear_images (img_name, img_art) VALUES(?, ?)";

    private static final String SQL_GET_IMAGES = "SELECT img_name FROM footwear_images WHERE img_art = ? ORDER BY img_name";

    private static final String SQL_ADD_ITEM = "INSERT INTO footwear_items(fi_art, fi_size) VALUES (?, ?)";

    private static final String SQL_ADD_BRAND = "INSERT INTO brands (b_name) VALUES (?)";

    private static final String SQL_ADD_CATEGORY = "INSERT INTO categories (c_name_en, c_name_ru) VALUES (?, ?)";

    private static final String SQL_ADD_COLOR = "INSERT INTO colors (cl_name_en, cl_name_ru) VALUES (?, ?)";

    private static final String SQL_GET_ITEMS_BY_ART = "SELECT fi_id, fi_size, fi_status FROM footwear_items WHERE fi_art = ?";

    private static final String SQL_GET_ITEM_STATUS = "SELECT fi_status FROM footwear_items WHERE fi_id = ?";

    private static final String SQL_UPDATE_ITEM_STATUS = "UPDATE footwear_items SET fi_status = ? WHERE fi_id = ?";

    private static final String SQL_GET_COUNT = "SELECT COUNT(f_art) as count FROM footwears f" +
            "  JOIN categories c ON c.c_id = f.f_category" +
            "  JOIN brands b ON b.b_id = f.f_brand";

    private static final String SQL_GET_COUNT_ACTUAL = "SELECT COUNT(DISTINCT f_art) as count FROM footwears f" +
            " JOIN categories c ON c.c_id = f.f_category" +
            " JOIN brands b ON b.b_id = f.f_brand" +
            " JOIN footwear_items fi on f.f_art = fi.fi_art WHERE fi_status='STOCK'";

    private static final String SQL_DELETE_IMAGE = "DELETE FROM footwear_images WHERE img_name = ?";

    private final static int FIRST = 1;

    private static final int DEFAULT_LIMIT = 501;

    @Override
    public List<Footwear> getFootwearByCriteria(FootwearCriteria criteria, String lang, int offset, int limit) throws DAOException {
        String query = String.format(SQL_GET_FOOTWEAR, lang, lang, lang);
        FootwearQueryCreator queryCreator = FootwearQueryCreator.create(criteria, query, lang, offset, limit);

        return getFootwearList(queryCreator.getQuery(), queryCreator.getConsumer());
    }

    @Override
    public List<Footwear> getActualFootwearByCriteria(FootwearCriteria criteria, String lang, int offset, int limit) throws DAOException {
        String query = String.format(SQL_GET_ACTUAL_FOOTWEAR, lang, lang, lang);
        FootwearQueryCreator queryCreator = FootwearQueryCreator.create(criteria, query, lang, offset, limit);

        return getFootwearList(queryCreator.getQuery(), queryCreator.getConsumer());
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
            ResultSet resultSet = resourceProvider.createResultSet(String.format(SQL_GET_FOOTWEAR, lang, lang, lang) + SQL_BY_ART,
                    ps -> ps.setString(FIRST, art));
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
                String category = resultSet.getString(ColumnNames.NAME);
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
                String category = resultSet.getString(ColumnNames.NAME);
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
                String brand = resultSet.getString(ColumnNames.B_NAME);
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
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ITEM_STATUS, ps -> ps.setInt(FIRST, id));
            if (resultSet.next()) {
                itemStatus = ItemStatus.valueOf(resultSet.getString(ColumnNames.FI_STATUS));
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting item status error", e);
        }
        return itemStatus;
    }

    @Override
    public Integer getFootwearQuantity(FootwearCriteria criteria, String lang) throws DAOException {
        FootwearQueryCreator queryCreator = FootwearQueryCreator.create(criteria, SQL_GET_COUNT, lang, 0, DEFAULT_LIMIT);
        return getFootwearCount(queryCreator.getQuery(), queryCreator.getConsumer());
    }

    @Override
    public Integer getActualFootwearQuantity(FootwearCriteria criteria, String lang) throws DAOException {
        FootwearQueryCreator queryCreator = FootwearQueryCreator.create(criteria, SQL_GET_COUNT_ACTUAL, lang, 0, DEFAULT_LIMIT);
        return getFootwearCount(queryCreator.getQuery(), queryCreator.getConsumer());
    }

    private Integer getFootwearCount (String query, SQLConsumer<PreparedStatement> ps) throws DAOException {
        Integer count = null;

        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet;
            if (ps == null) {
                resultSet = resourceProvider.createResultSet(query);
            } else {
                resultSet = resourceProvider.createResultSet(query, ps);
            }
            if (resultSet.next()) {
                count = resultSet.getInt(ColumnNames.F_COUNT);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting footwear by params error", e);
        }
        return count;
    }

    @Override
    public List<FootwearItem> getItemsByArt(String art) throws DAOException {
        List<FootwearItem> itemsList = new ArrayList<>();
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_ITEMS_BY_ART, ps -> ps.setString(FIRST, art));
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
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_SIZES, ps -> ps.setString(FIRST, art));
            while (resultSet.next()) {
                Float size = resultSet.getFloat(ColumnNames.FI_SIZE);
                sizesList.add(size);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting sizes error", e);
        }
        return sizesList;
    }

    @Override
    public Integer getMaxQuantity(String art, Float size) throws DAOException {
        Integer quantity = null;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()) {
            AtomicInteger i = new AtomicInteger();
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_QUANTITY, ps -> {
                ps.setString(i.incrementAndGet(), art);
                ps.setFloat(i.incrementAndGet(), size);
            });
            if (resultSet.next()) {
                quantity = resultSet.getInt(ColumnNames.QUANTITY);
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
                    ps -> ps.setString(FIRST, footwear.getCategory()));
            if (!categoryIdRS.next()) {
                return false;
            }
            int categoryId = categoryIdRS.getInt(ColumnNames.C_ID);

            ResultSet colorIdRS = resourceProvider.createResultSet(String.format(SQL_GET_COLOR_ID, lang),
                    ps -> ps.setString(FIRST, footwear.getColor()));
            if (!colorIdRS.next()) {
                return false;
            }
            int colorId = colorIdRS.getInt(ColumnNames.CL_ID);

            ResultSet brandIdRS = resourceProvider.createResultSet(SQL_GET_BRAND_ID,
                    ps -> ps.setString(FIRST, footwear.getBrand()));
            if (!brandIdRS.next()) {
                return false;
            }
            int brandId = brandIdRS.getInt(ColumnNames.B_ID);
            AtomicInteger i = new AtomicInteger();
            boolean updateSuccess = resourceProvider.updateAction(SQL_ADD_FOOTWEAR, ps -> {
                ps.setString(i.incrementAndGet(), footwear.getArt());
                ps.setString(i.incrementAndGet(), footwear.getName());
                ps.setFloat(i.incrementAndGet(), footwear.getPrice());
                ps.setInt(i.incrementAndGet(), categoryId);
                ps.setString(i.incrementAndGet(), footwear.getForWhom().toString());
                ps.setInt(i.incrementAndGet(), colorId);
                ps.setInt(i.incrementAndGet(), brandId);
                ps.setString(i.incrementAndGet(), description_en);
                ps.setString(i.incrementAndGet(), description_ru);
            });

            if (!updateSuccess) {
                resourceProvider.rollback();
                return false;
            }
            i.set(0);
            for (String image : footwear.getImageLinks()) {
                boolean imageAdding = resourceProvider.updateAction(SQL_ADD_IMAGE, ps -> {
                    ps.setString(i.incrementAndGet(), image);
                    ps.setString(i.incrementAndGet(), footwear.getArt());
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
            AtomicInteger i = new AtomicInteger();
            successAdding = resourceProvider.updateAction(SQL_ADD_ITEM, ps -> {
               ps.setString(i.incrementAndGet(), art);
               ps.setFloat(i.incrementAndGet(), size);
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
            AtomicInteger i = new AtomicInteger();
            successAdding = resourceProvider.updateAction(SQL_ADD_CATEGORY, ps -> {
                ps.setString(i.incrementAndGet(), category_en);
                ps.setString(i.incrementAndGet(), category_ru);
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
            AtomicInteger i = new AtomicInteger();
            successAdding = resourceProvider.updateAction(SQL_ADD_COLOR, ps -> {
                ps.setString(i.incrementAndGet(), color_en);
                ps.setString(i.incrementAndGet(), color_ru);
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
            successAdding = resourceProvider.updateAction(SQL_ADD_BRAND, ps -> ps.setString(FIRST, brand));
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Adding new brand error", e);
        }
        return successAdding;
    }

    /**
     * @param itemId - id of footwear item for updating
     * @param status - status that would be set
     * @return successUpdating - true if update is success
     * @throws DAOException
     */
    @Override
    public boolean updateItemStatus(Integer itemId, ItemStatus status) throws DAOException {
        boolean successUpdating;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            AtomicInteger i = new AtomicInteger();
            successUpdating = resourceProvider.updateAction(SQL_UPDATE_ITEM_STATUS, ps -> {
                ps.setString(i.incrementAndGet(), status.toString());
                ps.setInt(i.incrementAndGet(), itemId);
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

    /**
     * @param footwear - footwear with data for update
     * @param lang - language for localized fields
     * @return true if update success, else false
     * @throws DAOException
     */
    @Override
    public boolean updateFootwear(Footwear footwear, String lang) throws DAOException {
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            resourceProvider.setAutoCommit(false);
            ResultSet categoryIdRS = resourceProvider.createResultSet(String.format(SQL_GET_CATEGORY_ID, lang),
                    ps -> ps.setString(FIRST, footwear.getCategory()));
            if (!categoryIdRS.next()) {
                return false;
            }
            int categoryId = categoryIdRS.getInt(ColumnNames.C_ID);

            ResultSet colorIdRS = resourceProvider.createResultSet(String.format(SQL_GET_COLOR_ID, lang),
                    ps -> ps.setString(FIRST, footwear.getColor()));
            if (!colorIdRS.next()) {
                return false;
            }
            int colorId = colorIdRS.getInt(ColumnNames.CL_ID);

            ResultSet brandIdRS = resourceProvider.createResultSet(SQL_GET_BRAND_ID,
                    ps -> ps.setString(FIRST, footwear.getBrand()));
            if (!brandIdRS.next()) {
                return false;
            }
            int brandId = brandIdRS.getInt(ColumnNames.B_ID);

            AtomicInteger i = new AtomicInteger();
            boolean updateSuccess = resourceProvider.updateAction(String.format(SQL_UPDATE_FOOTWEAR, lang), ps -> {
                ps.setString(i.incrementAndGet(), footwear.getName());
                ps.setFloat(i.incrementAndGet(), footwear.getPrice());
                ps.setInt(i.incrementAndGet(), categoryId);
                ps.setString(i.incrementAndGet(), footwear.getForWhom().toString());
                ps.setInt(i.incrementAndGet(), colorId);
                ps.setInt(i.incrementAndGet(), brandId);
                ps.setString(i.incrementAndGet(), footwear.getDescription());
                ps.setString(i.incrementAndGet(), footwear.getArt());
            });

            if (!updateSuccess) {
                resourceProvider.rollback();
                return false;
            }

            for (String image : footwear.getImageLinks()) {
                i.set(0);
                boolean imageAdding = resourceProvider.updateAction(SQL_ADD_IMAGE, ps -> {
                    ps.setString(i.incrementAndGet(), image);
                    ps.setString(i.incrementAndGet(), footwear.getArt());
                });
                if (!imageAdding) {
                    resourceProvider.rollback();
                    return false;
                }
            }

            resourceProvider.commit();
            return true;

        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Updating footwear error",e);
        }
    }

    @Override
    public boolean deleteImage(String imageName) throws DAOException {
        boolean successDeleting;
        try (DAOResourceProvider resourceProvider = new DAOResourceProvider()){
            successDeleting = resourceProvider.updateAction(SQL_DELETE_IMAGE, ps -> ps.setString(FIRST, imageName));
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Deleting image error", e);
        }
        return successDeleting;
    }

    private FootwearItem buildItem(ResultSet resultSet, String art) throws DAOException {
        FootwearItem item;
        try {
            Integer id = resultSet.getInt(ColumnNames.FI_ID);
            Float size = resultSet.getFloat(ColumnNames.FI_SIZE);
            ItemStatus status = ItemStatus.valueOf(resultSet.getString(ColumnNames.FI_STATUS));
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
            ResultSet resultSet = resourceProvider.createResultSet(SQL_GET_IMAGES, ps -> ps.setString(FIRST, art));
            while (resultSet.next()) {
                String imageName = resultSet.getString(ColumnNames.IMG_NAME);
                imageList.add(imageName);
            }
        } catch (SQLException | ConnectionException e) {
            throw new DAOException("Getting images error", e);
        }
        return imageList;
    }

    private Footwear buildFootwear(ResultSet resultSet) throws DAOException {
        Footwear footwear;
        try {
            String art = resultSet.getString(ColumnNames.F_ART);
            String name = resultSet.getString(ColumnNames.F_NAME);
            Float price = resultSet.getFloat(ColumnNames.F_PRICE);
            String category = resultSet.getString(ColumnNames.F_CATEGORY);
            String description = resultSet.getString(ColumnNames.F_DESCRIPTION);
            String color = resultSet.getString(ColumnNames.F_COLOR);
            String brand = resultSet.getString(ColumnNames.F_BRAND);
            String forWhom = resultSet.getString(ColumnNames.F_FOR);
            List<String> imageList = getImages(art);
            footwear = new Footwear(art, name, price, color, category,
                    description, imageList, brand,  ForEnum.valueOf(forWhom));

        } catch (SQLException e) {
            throw new DAOException("Building footwear error", e);
        }
        return footwear;
    }

    static class ColumnNames {
        public static final String F_ART = "f_art";
        public static final String F_NAME = "f_name";
        public static final String F_PRICE = "f_price";
        public static final String F_CATEGORY = "category";
        public static final String F_DESCRIPTION = "description";
        public static final String F_COLOR = "color";
        public static final String F_BRAND = "brand";
        public static final String F_FOR = "f_for";
        public static final String IMG_NAME = "img_name";
        public static final String FI_ID = "fi_id";
        public static final String FI_SIZE = "fi_size";
        public static final String FI_STATUS = "fi_status";
        public static final String B_ID = "b_id";
        public static final String CL_ID = "cl_id";
        public static final String C_ID = "c_id";
        public static final String QUANTITY = "quantity";
        public static final String F_COUNT = "count";
        public static final String B_NAME = "b_name";
        public static final String NAME = "name";
    }

}
