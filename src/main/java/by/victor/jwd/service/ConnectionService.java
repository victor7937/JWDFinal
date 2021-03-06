package by.victor.jwd.service;

import by.victor.jwd.service.exception.ServiceException;

/**
 * Service for manipulations with connections pool
 * that working at dao level
 */
public interface ConnectionService {
    void initConnectionPool() throws ServiceException;
    void destroyConnectionPool() throws ServiceException;
}
