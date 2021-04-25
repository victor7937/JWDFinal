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
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;

public class GoToProduct implements Command {

    private static final String FOOTWEAR_ATTRIBUTE = "footwear";
    private static final String SIZES_ATTRIBUTE = "sizes";
    private static final String FORWARD_PATH = "/WEB-INF/jsp/product.jsp";
    private static final int ERROR_CODE = 404;
    private static final int MAX_AGE = 60 * 60;
    private static final String LAST_VALUE = "last";
    private static final int MAX_LAST_PRODUCTS_SIZE = 4;
    private static final String POPULAR_ATTRIBUTE = "popular";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        if (art == null || art.isBlank()) {
            response.sendError(ERROR_CODE);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        Footwear footwear;
        List<Float> sizes;
        try {
            footwear = footwearService.getByArt(art,lang);
            sizes = footwearService.getSizesByArt(art);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
        if (footwear == null) {
            response.sendError(ERROR_CODE);
        } else {
            request.setAttribute(FOOTWEAR_ATTRIBUTE, footwear);
            request.setAttribute(SIZES_ATTRIBUTE, sizes);
            addCookie(request.getCookies(), response, art);
            addVisit(art, request.getServletContext());
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
            requestDispatcher.forward(request, response);
        }
    }

    private void addCookie(Cookie[] cookies, HttpServletResponse response, String art){
        if (cookies != null) {
            List<String> lastCookies = Arrays.stream(cookies)
                    .filter(c -> LAST_VALUE.equals(c.getValue()))
                    .map(Cookie::getName)
                    .collect(Collectors.toList());
            if (lastCookies.size() == MAX_LAST_PRODUCTS_SIZE && !lastCookies.contains(art)) {
                Cookie cookie = new Cookie(lastCookies.get(0), LAST_VALUE);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        Cookie cookie = new Cookie(art, LAST_VALUE);
        cookie.setMaxAge(MAX_AGE);
        response.addCookie(cookie);
    }

    private void addVisit (String art, ServletContext context) {
        Map<String, Integer> popularMap =  (Map<String, Integer>) context.getAttribute(POPULAR_ATTRIBUTE);
        Integer clicksCount = popularMap.get(art);
        if (clicksCount != null) {
            popularMap.put(art, ++clicksCount);
        } else {
            popularMap.put(art,1);
        }
    }

}
