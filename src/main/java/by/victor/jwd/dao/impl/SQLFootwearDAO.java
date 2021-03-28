package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.Customer;
import by.victor.jwd.bean.Footwear;
import by.victor.jwd.dao.FootwearDAO;
import by.victor.jwd.dao.exception.DAOException;

import java.util.List;

public class SQLFootwearDAO implements FootwearDAO {
    @Override
    public List<Footwear> getAll() throws DAOException {
        return null;
    }

    @Override
    public Footwear getFootwearByArt(String art) throws DAOException {
        return null;
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
}
