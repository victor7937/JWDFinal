package by.victor.jwd.controller.command.impl;

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

public class GoToAddingFootwear implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        List<String> brands;
        List<String> categories;
        List<String> colors;
        String lang = (String)request.getSession().getAttribute("lang");
        try {
            brands = footwearService.getBrands();
            categories = footwearService.getCategories(lang);
            colors = footwearService.getColors(lang);
        } catch (ServiceException e) {
            throw new ControllerException("Error getting data needed for adding");
        }

        request.setAttribute("brands", brands);
        request.setAttribute("categories", categories);
        request.setAttribute("colors", colors);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/add_footwear.jsp");
        requestDispatcher.forward(request, response);
    }
}
