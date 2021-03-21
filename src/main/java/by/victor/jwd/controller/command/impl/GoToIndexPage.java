package by.victor.jwd.controller.command.impl;

import java.io.IOException;
import java.util.List;


import by.victor.jwd.bean.News;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.service.ServiceException;
import by.victor.jwd.service.ServiceProvider;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GoToIndexPage implements Command {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*ServiceProvider provider = ServiceProvider.getInstance();
		NewsService newsService = provider.getNewsService();
		
		try {
			List<News> news = newsService.takeAll();
			
			request.setAttribute("news", news);
			
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/main_index.jsp");
			requestDispatcher.forward(request, response);
			
		} catch (ServiceException e) {
			// TODO перейти на глобальную страницу ошибок
			e.printStackTrace();
		}	*/
	

	}

}
