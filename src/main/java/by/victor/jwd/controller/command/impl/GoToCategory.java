package by.victor.jwd.controller.command.impl;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.util.CriteriaCreator;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToCategory implements Command {

    public static final String FORWARD_PATH = "/WEB-INF/jsp/category.jsp";
    public static final String FOOTWEAR_LIST_ATTRIBUTE = "footwearList";
    public static final String BRAND_LIST_ATTRIBUTE = "brandList";
    public static final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
    public static final String FOR_WHOM_ATTRIBUTE = "forWhom";
    public static final String CATEGORY_ATTRIBUTE = "category";
    public static final String BRAND_ATTRIBUTE = "brand";
    public static final String LANG_ATTRIBUTE = "lang";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        List<Footwear> footwearList;
        List<String> categoryList;
        List<String> brandList;

        try {
            categoryList = footwearService.getCategories(lang);
            brandList = footwearService.getBrands();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        FootwearCriteria criteria = CriteriaCreator.createFootwearCriteria(request, categoryList, brandList);

        try {
            footwearList = footwearService.getByCriteria(criteria, lang);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(FOOTWEAR_LIST_ATTRIBUTE, footwearList);
        request.setAttribute(BRAND_LIST_ATTRIBUTE, brandList);
        request.setAttribute(CATEGORY_LIST_ATTRIBUTE, categoryList);
        request.setAttribute(FOR_WHOM_ATTRIBUTE, criteria.getForWhom().toString().toLowerCase());
        request.setAttribute(CATEGORY_ATTRIBUTE, criteria.getCategory());
        request.setAttribute(BRAND_ATTRIBUTE, criteria.getBrand());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}

