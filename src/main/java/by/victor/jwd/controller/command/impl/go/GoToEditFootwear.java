package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.bean.Footwear;
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
import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;

public class GoToEditFootwear implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/edit-footwear.jsp";
    private static final String BRANDS_ATTRIBUTE = "brands";
    private static final String CATEGORIES_ATTRIBUTE = "categories";
    private static final String COLORS_ATTRIBUTE = "colors";
    private static final String FOOTWEAR_ATTRIBUTE = "footwear";
    private static final int ERROR_CODE = 404;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        if (art == null || art.isBlank()){
            response.sendError(ERROR_CODE);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        List<String> brands;
        List<String> categories;
        List<String> colors;
        Footwear footwear;
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        try {
            footwear = footwearService.getByArt(art, lang);
            brands = footwearService.getBrands();
            categories = footwearService.getCategories(lang);
            colors = footwearService.getColors(lang);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(FOOTWEAR_ATTRIBUTE, footwear);
        request.setAttribute(BRANDS_ATTRIBUTE, brands);
        request.setAttribute(CATEGORIES_ATTRIBUTE, categories);
        request.setAttribute(COLORS_ATTRIBUTE, colors);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}
