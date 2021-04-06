package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
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

    private final static String ALL_PARAM = "all";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String categoryParam = request.getParameter("category");
        String brandParam = request.getParameter("brand");
        String forParam = request.getParameter("for");
        if (categoryParam == null || "".equals(categoryParam)) {
            categoryParam = ALL_PARAM;
        }
        if (brandParam == null || "".equals(brandParam)) {
            brandParam = ALL_PARAM;
        }
        if (forParam == null || "".equals(forParam) || !(ForEnum.HIM.toString().toLowerCase().equals(forParam) ||
                ForEnum.HER.toString().toLowerCase().equals(forParam) || ALL_PARAM.equals(forParam))) {
            forParam = ALL_PARAM;
        }
        ForEnum forParamEnum = ForEnum.valueOf(forParam.toUpperCase());

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        String lang = (String)request.getSession().getAttribute("lang");
        List<Footwear> footwearList;
        List<String> categoryList;
        List<String> brandList;
        try {
            categoryList = footwearService.getCategories(lang);
            brandList = footwearService.getBrands();
        } catch (ServiceException e) {
            throw new ControllerException("Error while loading category and brand");
        }

        if(!categoryParam.equals(ALL_PARAM) && !categoryList.contains(categoryParam)) {
            categoryParam = ALL_PARAM;
        }

        if(!brandParam.equals(ALL_PARAM) && !brandList.contains(brandParam)) {
            brandParam = ALL_PARAM;
        }

        if (categoryParam.equals(ALL_PARAM) && brandParam.equals(ALL_PARAM)) {
            try {
                footwearList = footwearService.getAll(forParamEnum, lang);
            } catch (ServiceException e) {
                throw new ControllerException("Error while loading all footwear");
            }
        } else if (categoryParam.equals(ALL_PARAM)) {
            try {
                footwearList = footwearService.getByBrand(brandParam, forParamEnum, lang);
            } catch (ServiceException e) {
                throw new ControllerException("Error while loading footwear by brand");
            }
        } else if (brandParam.equals(ALL_PARAM)) {
            try {
                footwearList = footwearService.getByCategory(categoryParam, forParamEnum, lang);
            } catch (ServiceException e) {
                throw new ControllerException("Error while loading footwear by category");
            }
        } else {
            try {
                footwearList = footwearService.getByCategoryAndBrand(categoryParam, brandParam, forParamEnum, lang);
            } catch (ServiceException e) {
                throw new ControllerException("Error while loading footwear by category and brand");
            }
        }

        request.setAttribute("footwearList", footwearList);
        request.setAttribute("brandList", brandList);
        request.setAttribute("categoryList", categoryList);
        request.setAttribute("forWhom", forParam);
        request.setAttribute("category", categoryParam);
        request.setAttribute("brand", brandParam);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/WEB-INF/jsp/category.jsp");
        requestDispatcher.forward(request, response);
    }
}

