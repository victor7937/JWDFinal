package by.victor.jwd.service;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.service.exception.ServiceException;

import java.util.List;

public interface FootwearService {

    List<Footwear> getAll(ForEnum forEnum, String lang) throws ServiceException;
    List<Footwear> getByCategory(String category, ForEnum forEnum, String lang) throws ServiceException;
    List<Footwear> getByCategoryAndBrand(String category, String brand, ForEnum forEnum, String lang) throws ServiceException;
    // List<Footwear> getByForEnum(ForEnum forWhom, String lang) throws ServiceException;
    List<Footwear> getByBrand(String brand, ForEnum forEnum, String lang) throws ServiceException;
    Footwear getByArt(String art, String lang) throws ServiceException;

    List<String> getCategories (String lang) throws ServiceException;
    List<String> getColors (String lang) throws ServiceException;
    List<String> getBrands () throws ServiceException;
    List<Float> getSizesByArt(String art) throws ServiceException;
    Integer getMaxQuantity(String art, Float size) throws ServiceException;

    boolean createNewFootwear (Footwear footwear, String description_en, String description_ru, String lang) throws ServiceException;
}
