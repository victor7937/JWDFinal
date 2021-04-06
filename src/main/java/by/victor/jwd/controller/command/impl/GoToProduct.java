package by.victor.jwd.controller.command.impl;

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

public class GoToProduct implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter("art");
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        String lang = (String)request.getSession().getAttribute("lang");
        Footwear footwear;
        try {
            footwear = footwearService.getByArt(art,lang);
        } catch (ServiceException e) {
            throw new ControllerException("Error while loading footwears");
        }
        if (footwear == null) {
            response.sendError(404);
        } else {
            request.setAttribute("footwear", footwear);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/product.jsp");
            requestDispatcher.forward(request, response);
        }
    }
}
