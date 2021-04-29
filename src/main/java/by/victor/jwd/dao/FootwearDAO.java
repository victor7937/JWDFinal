package by.victor.jwd.dao;

import by.victor.jwd.bean.*;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

/**
 * DAO that manipulates footwear-related data
 */
public interface FootwearDAO {

    List<Footwear> getFootwearByCriteria (FootwearCriteria criteria, String lang, int offset, int limit) throws DAOException;
    List<Footwear> getActualFootwearByCriteria (FootwearCriteria criteria, String lang, int offset, int limit) throws DAOException;
    Footwear getFootwearByArt (String art, String lang) throws DAOException;
    List<String> getCategories (String lang) throws DAOException;
    List<String> getColors (String lang) throws DAOException;
    List<String> getBrands () throws DAOException;
    ItemStatus getItemStatus (Integer id) throws DAOException;
    Integer getFootwearQuantity (FootwearCriteria criteria, String lang) throws DAOException;
    Integer getActualFootwearQuantity (FootwearCriteria criteria, String lang) throws DAOException;
    List<FootwearItem> getItemsByArt (String art) throws DAOException;


    List<Float> getSizes (String art) throws DAOException;
    Integer getMaxQuantity (String art, Float size) throws DAOException;

    boolean addNewFootwear (Footwear footwear, String description_en, String description_ru, String lang) throws DAOException;
    boolean addNewItem (String art, Float size) throws DAOException;
    boolean addNewCategory (String category_en, String category_ru) throws DAOException;
    boolean addNewColor (String color_en, String color_ru) throws DAOException;
    boolean addNewBrand (String brand) throws DAOException;

    boolean updateItemStatus (Integer itemId, ItemStatus status) throws DAOException;
    boolean deleteFootwear (String art) throws DAOException;
    boolean updateFootwear (Footwear footwear, String lang) throws DAOException;
    boolean deleteImage (String imageName) throws DAOException;

}
