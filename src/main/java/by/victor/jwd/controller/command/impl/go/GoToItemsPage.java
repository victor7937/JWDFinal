package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearItem;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class GoToItemsPage implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/items.jsp";
    private static final String ITEMS_ATTRIBUTE = "items";
    private static final String FOOTWEAR_ATTRIBUTE = "footwear";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        String art = request.getParameter(ART_PARAM);
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        if (art == null || art.isBlank()) {
            response.sendError(ERROR_CODE_NOT_FOUND);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        List<FootwearItem> items;
        Footwear footwear;
        try {
            items = footwearService.getItemsByArt(art);
            footwear = footwearService.getByArt(art, lang);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(ITEMS_ATTRIBUTE, items);
        request.setAttribute(FOOTWEAR_ATTRIBUTE, footwear);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
