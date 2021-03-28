package by.victor.jwd.dao;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

public interface FootwearDAO {

    List<Footwear> getAll() throws DAOException;
    Footwear getFootwearByArt(String art) throws DAOException;
    boolean addNewFootwear(Footwear footwear) throws DAOException;
    boolean deleteFootwear(String art) throws DAOException;
    boolean updateFootwear(String art, Customer footwear) throws DAOException;
}
