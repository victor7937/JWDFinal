package by.victor.jwd.service.impl;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearCriteria;
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
    public List<Footwear> getByCriteria(FootwearCriteria criteria, String lang) throws ServiceException {
        List<Footwear> footwearList;
        String category = criteria.getCategory();
        String brand = criteria.getBrand();
        ForEnum forWhom = criteria.getForWhom();
        if (category.equals(FootwearCriteria.ALL) && brand.equals(FootwearCriteria.ALL)) {
            try {
                footwearList = footwearDAO.getAll(forWhom, lang);
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
                throw new ServiceException(e);
            }
        } else if (category.equals(FootwearCriteria.ALL)) {
            try {
                footwearList = footwearDAO.getByBrand(brand, forWhom, lang);
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
                throw new ServiceException(e);
            }
        } else if (brand.equals(FootwearCriteria.ALL)) {
            try {
                footwearList = footwearDAO.getByCategory(category, forWhom, lang);
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
                throw new ServiceException(e);
            }
        } else {
            try {
                footwearList = footwearDAO.getByCategoryAndBrand(category, brand, forWhom, lang);
            } catch (DAOException e) {
                logger.error(e.getMessage(), e);
                throw new ServiceException(e);
            }
        }
        return footwearList;
    }

    @Override
    public Footwear getByArt(String art, String lang) throws ServiceException {
        Footwear footwear;
        try {
            footwear = footwearDAO.getFootwearByArt(art, lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return footwear;
    }

    @Override
    public List<String> getCategories(String lang) throws ServiceException {
        List<String> categoriesList;
        try {
            categoriesList = footwearDAO.getCategories(lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return categoriesList;
    }

    @Override
    public List<String> getColors(String lang) throws ServiceException {
        List<String> colorList;
        try {
            colorList = footwearDAO.getColors(lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return colorList;
    }

    @Override
    public List<String> getBrands() throws ServiceException {
        List<String> brandList;
        try {
            brandList = footwearDAO.getBrands();
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return brandList;
    }

    @Override
    public List<Float> getSizesByArt(String art) throws ServiceException {
        List<Float> sizesList;
        try {
            sizesList = footwearDAO.getSizes(art);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return sizesList;
    }

    @Override
    public Integer getMaxQuantity(String art, Float size) throws ServiceException {
        Integer quantity;
        try {
            quantity = footwearDAO.getMaxQuantity(art, size);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return quantity;
    }

    @Override
    public boolean createNewFootwear(Footwear footwear, String description_en, String description_ru, String lang) throws ServiceException {
        boolean ifAdded;
        try {
            ifAdded = footwearDAO.addNewFootwear(footwear, description_en, description_ru, lang);
        } catch (DAOException e) {
            logger.error(e.getMessage(), e);
            throw new ServiceException(e);
        }
        return ifAdded;
    }
}
