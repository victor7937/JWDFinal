package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;

public class GoToMainPage implements Command {

	private static final String FORWARD_PATH = "/WEB-INF/jsp/main.jsp";
	private static final String LAST_VALUE = "last";
	private static final String LAST_LIST_ATTRIBUTE = "lastList";
	private static final String POPULAR_LIST_ATTRIBUTE = "popularList";
	private static final int MAX_POPULAR_LENGTH = 4;

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
		Cookie[] cookies = request.getCookies();
		List<Footwear> lastFootwearList = new ArrayList<>();

		if (cookies != null) {
			List<String> lastArts = Arrays.stream(cookies)
					.filter(c -> LAST_VALUE.equals(c.getValue()))
					.map(Cookie::getName)
					.collect(Collectors.toList());
			Collections.reverse(lastArts);

			lastFootwearList = createFootwearList(lastArts, lang);
		}

		ServletContext servletContext = request.getServletContext();
		Map<String, Integer> popularMap = (Map<String, Integer>) servletContext.getAttribute("popular");
		List<String> popularArts = popularMap.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
				.limit(MAX_POPULAR_LENGTH)
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());

		List<Footwear> popularFootwearList = createFootwearList(popularArts, lang);

		request.setAttribute(POPULAR_LIST_ATTRIBUTE, popularFootwearList);
		request.setAttribute(LAST_LIST_ATTRIBUTE, lastFootwearList);
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
		requestDispatcher.forward(request, response);
	}

	private List<Footwear> createFootwearList (List<String> arts, String lang) {
		FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
		List<Footwear> footwearList = new ArrayList<>();

		for (String art : arts) {
			Footwear footwear;
			try {
				footwear = footwearService.getByArt(art, lang);
			} catch (ServiceException e) {
				throw new ControllerException(e);
			}
			if (footwear != null) {
				footwearList.add(footwear);
			}
		}

		return footwearList;
	}

}
