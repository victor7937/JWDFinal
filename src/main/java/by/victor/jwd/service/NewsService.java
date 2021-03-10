package by.victor.jwd.service;

import java.util.List;
import by.victor.jwd.bean.News;

public interface NewsService {
	List<News> takeAll() throws ServiceException;
}
