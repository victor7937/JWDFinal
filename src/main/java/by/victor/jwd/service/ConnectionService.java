package by.victor.jwd.service;

import by.victor.jwd.service.exception.ServiceException;

public interface ConnectionService {
    void initConnectionPool() throws ServiceException;
    void destroyConnectionPool() throws ServiceException;
}
