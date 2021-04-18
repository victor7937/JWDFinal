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

import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;

public class GoToCategory implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/category.jsp";
    private static final String FOOTWEAR_LIST_ATTRIBUTE = "footwearList";
    private static final String BRAND_LIST_ATTRIBUTE = "brandList";
    private static final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
    private static final String FOR_WHOM_ATTRIBUTE = "forWhom";
    private static final String CATEGORY_ATTRIBUTE = "category";
    private static final String BRAND_ATTRIBUTE = "brand";

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

