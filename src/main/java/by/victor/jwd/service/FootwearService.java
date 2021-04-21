package by.victor.jwd.service;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.bean.ItemStatus;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface FootwearService {
    List<Footwear> getByCriteria(FootwearCriteria criteria, String lang, int offset, int limit) throws ServiceException;
    List<Footwear> getByCriteriaActual(FootwearCriteria criteria, String lang, int offset, int limit) throws ServiceException;
    Footwear getByArt(String art, String lang) throws ServiceException;
    List<String> getCategories (String lang) throws ServiceException;
    List<String> getColors (String lang) throws ServiceException;
    List<String> getBrands () throws ServiceException;
    List<Float> getSizesByArt(String art) throws ServiceException;
    Integer getMaxQuantity(String art, Float size) throws ServiceException;
    List<FootwearItem> getItemsByArt(String art) throws ServiceException;
    ItemStatus getItemStatus(Integer id) throws ServiceException;
    Integer getFootwearQuantity(FootwearCriteria criteria, String lang) throws ServiceException;
    Integer getActualFootwearQuantity(FootwearCriteria criteria, String lang) throws ServiceException;

    boolean createNewItem (String art, Float size) throws ServiceException;
    boolean createNewColor(String color_en, String color_ru) throws ServiceException;
    boolean createNewCategory(String category_en, String category_ru) throws ServiceException;
    boolean createNewBrand(String brand) throws ServiceException;
    boolean createNewFootwear (Footwear footwear, String description_en, String description_ru, String lang) throws ServiceException;

    boolean updateItemStatus (Integer id, ItemStatus status) throws ServiceException;
}
