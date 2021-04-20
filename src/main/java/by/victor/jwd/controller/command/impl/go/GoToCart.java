package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;

public class GoToCart implements Command {

    private final static Logger logger = Logger.getLogger(GoToCart.class);
    private static final String ART_PREFIX = "art_";
    private static final String COOKIE_DELIMITER = "\\|";
    private static final String ITEMS_LIST_ATTRIBUTE = "itemsList";
    private static final String FORWARD_PATH = "/WEB-INF/jsp/cart.jsp";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        List<FootwearItem> footwearItems = new ArrayList<>();
        if (cookies == null) {
            request.setAttribute(ITEMS_LIST_ATTRIBUTE, footwearItems);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
            requestDispatcher.forward(request, response);
            return;
        }
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        for (Cookie cookie : cookies) {
            if (cookie.getName().matches(ART_PREFIX + ".+")) {
                String art = cookie.getName().replace(ART_PREFIX,"");
                Footwear footwear;
                try {
                    footwear = footwearService.getByArt(art, lang);
                } catch (ServiceException e) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    logger.error("Can't add footwear to cart, cookie deleted");
                    continue;
                }
                if (footwear != null) {
                    List<Float> sizes = Arrays.stream(cookie.getValue().split(COOKIE_DELIMITER))
                            .map(Float::valueOf)
                            .collect(Collectors.toList());
                    for (Float size : sizes) {
                        footwearItems.add(new FootwearItem(footwear, size));
                    }
                }
                else {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        }
        try {
            for (FootwearItem item : footwearItems) {
                Integer maxQuantity = footwearService.getMaxQuantity(item.getFootwear().getArt(), item.getSize());
                item.setMaxQuantity(maxQuantity);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(ITEMS_LIST_ATTRIBUTE, footwearItems);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
