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
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class GoToCategory implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryParam = request.getParameter("category");
        String brandParam = request.getParameter("brand");
        if (categoryParam == null || "".equals(categoryParam)) {
            categoryParam = "all";
        }
        if (brandParam == null || "".equals(brandParam)) {
            brandParam = "all";
        }


        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        String lang = (String)request.getSession().getAttribute("lang");
        List<Footwear> footwearList = new ArrayList<>();
        List<String> categoryList;
        List<String> brandList;
        try {
            categoryList = footwearService.getCategories(lang);
            brandList = footwearService.getBrands();
        } catch (ServiceException e) {
            throw new ControllerException("Error while loading category and brand");
        }
        if (categoryParam.equals("all") && brandParam.equals("all")) {
            try {
                footwearList = footwearService.getAll(lang);
            } catch (ServiceException e) {
                throw new ControllerException("Error while loading all footwear");
            }
        }
        request.setAttribute("footwearList", footwearList);
        request.setAttribute("brandList", brandList);
        request.setAttribute("categoryList", categoryList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/category.jsp");
        requestDispatcher.forward(request, response);
    }
}

