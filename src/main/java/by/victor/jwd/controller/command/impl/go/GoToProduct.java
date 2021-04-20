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

public class GoToProduct implements Command {

    private static final String FOOTWEAR_ATTRIBUTE = "footwear";
    private static final String SIZES_ATTRIBUTE = "sizes";
    private static final String FORWARD_PATH = "/WEB-INF/jsp/product.jsp";
    private static final int ERROR_CODE = 404;

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
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
            requestDispatcher.forward(request, response);
        }
    }
}
