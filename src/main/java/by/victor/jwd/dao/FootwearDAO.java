package by.victor.jwd.dao;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

public interface FootwearDAO {

    List<Footwear> getAll(ForEnum forEnum, String lang) throws DAOException;
    List<Footwear> getByCategory(String category, ForEnum forEnum, String lang) throws DAOException;
    List<Footwear> getByCategoryAndBrand(String category, String brand, ForEnum forEnum, String lang) throws DAOException;
    //List<Footwear> getByForEnum(ForEnum forWhom, String lang) throws DAOException;
    List<Footwear> getByBrand(String brand, ForEnum forEnum, String lang) throws DAOException;
    Footwear getFootwearByArt(String art, String lang) throws DAOException;
    List<String> getCategories (String lang) throws DAOException;
    List<String> getBrands () throws DAOException;
    boolean addNewFootwear(Footwear footwear) throws DAOException;
    boolean deleteFootwear(String art) throws DAOException;
    boolean updateFootwear(String art, Customer footwear) throws DAOException;

}
