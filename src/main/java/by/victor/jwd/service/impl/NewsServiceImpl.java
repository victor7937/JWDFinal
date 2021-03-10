package by.victor.jwd.service.impl;

import java.util.ArrayList;
import java.util.List;

import by.victor.jwd.bean.News;

import by.victor.jwd.service.NewsService;
import by.victor.jwd.service.ServiceException;

public class NewsServiceImpl implements NewsService {

	@Override
	public List<News> takeAll() throws ServiceException {

		/*DAOProvider provider = DAOProvider.getInstance();
		NewsDAO newsDAO = provider.getNewsDAO();
		
		List<News> news;
		try {
			news = newsDAO.all();
		} catch (DAOException e) {
			throw new ServiceException(e);
		}
		*/
		List<News> nLs = new ArrayList<>();
		nLs.add(new News(1,"News","breif"));
		nLs.add(new News(2,"News2","breif2"));
		nLs.add(new News(3,"News3","breif3"));
		return nLs;
	}
}
