package by.victor.jwd.service.impl;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.dao.DAOProvider;
import by.victor.jwd.dao.FootwearDAO;
import by.victor.jwd.dao.exception.DAOException;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import java.util.List;

public class FootwearServiceImpl implements FootwearService {

    private static final Logger logger = Logger.getLogger(FootwearServiceImpl.class);
    private static FootwearDAO footwearDAO;

    public FootwearServiceImpl () {
        footwearDAO = DAOProvider.getInstance().getFootwearDAO();
    }

    @Override
    public List<Footwear> getAll(String lang) throws ServiceException {
        List<Footwear> footwearList;
        try {
            footwearList = footwearDAO.getAll(lang);
        } catch (DAOException e) {
            logger.error("Get all DAO error", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return footwearList;
    }

    @Override
    public List<Footwear> getByCategory(String category, String lang) throws ServiceException {
        List<Footwear> footwearList;
        try {
            footwearList = footwearDAO.getByCategory(category, lang);
        } catch (DAOException e) {
            logger.error("Get by category DAO error ", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return footwearList;
    }

    @Override
    public List<Footwear> getByForEnum(ForEnum forWhom, String lang) throws ServiceException {
        List<Footwear> footwearList;
        try {
            footwearList = footwearDAO.getByForEnum(forWhom, lang);
        } catch (DAOException e) {
            logger.error("Get by forEnum DAO error ", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return footwearList;
    }


    @Override
    public List<Footwear> getByBrand(String brand, String lang) throws ServiceException {
        List<Footwear> footwearList;
        try {
            footwearList = footwearDAO.getByBrand(brand, lang);
        } catch (DAOException e) {
            logger.error("Get by brand DAO error ", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return footwearList;
    }

    @Override
    public Footwear getByArt(String art, String lang) throws ServiceException {
        Footwear footwear;
        try {
            footwear = footwearDAO.getFootwearByArt(art, lang);
        } catch (DAOException e) {
            logger.error("Get by art DAO error ", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return footwear;
    }

    @Override
    public List<String> getCategories(String lang) throws ServiceException {
        List<String> categoriesList;
        try {
            categoriesList = footwearDAO.getCategories(lang);
        } catch (DAOException e) {
            logger.error("Get categories DAO error", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return categoriesList;
    }

    @Override
    public List<String> getBrands() throws ServiceException {
        List<String> brandList;
        try {
            brandList = footwearDAO.getBrands();
        } catch (DAOException e) {
            logger.error("Get brands DAO error", e);
            throw new ServiceException(e.getMessage(), e);
        }
        return brandList;
    }
}