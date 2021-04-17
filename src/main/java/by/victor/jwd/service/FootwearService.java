package by.victor.jwd.service;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface FootwearService {
    List<Footwear> getByCriteria(FootwearCriteria criteria, String lang) throws ServiceException;
    Footwear getByArt(String art, String lang) throws ServiceException;

    List<String> getCategories (String lang) throws ServiceException;
    List<String> getColors (String lang) throws ServiceException;
    List<String> getBrands () throws ServiceException;
    List<Float> getSizesByArt(String art) throws ServiceException;
    Integer getMaxQuantity(String art, Float size) throws ServiceException;

    boolean createNewFootwear (Footwear footwear, String description_en, String description_ru, String lang) throws ServiceException;
}
