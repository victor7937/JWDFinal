package by.victor.jwd.controller.command.impl.go;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.bean.UserRole;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.util.CriteriaCreator;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static by.victor.jwd.controller.constant.GlobalParams.LANG_ATTRIBUTE;
import static by.victor.jwd.controller.constant.GlobalParams.ROLE_ATTRIBUTE;

public class GoToCategory implements Command {

    private static final String FORWARD_PATH = "/WEB-INF/jsp/category.jsp";
    private static final String FOOTWEAR_LIST_ATTRIBUTE = "footwearList";
    private static final String BRAND_LIST_ATTRIBUTE = "brandList";
    private static final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
    private static final String FOR_WHOM_ATTRIBUTE = "forWhom";
    private static final String CATEGORY_ATTRIBUTE = "category";
    private static final String BRAND_ATTRIBUTE = "brand";
    private static final Integer PAGE_LIMIT = 9;
    private static final String PAGE_PARAM = "page";
    private static final int START_PAGE = 1;
    private static final String PAGE_COUNT_ATTRIBUTE = "pageCount";
    private static final String CURRENT_PAGE_ATTRIBUTE = "currentPage";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        String role = (String)request.getSession().getAttribute(ROLE_ATTRIBUTE);
        String pageString = request.getParameter(PAGE_PARAM);

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
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
        int page;
        if (pageString == null || !RequestValidator.isInteger(pageString)) {
           page = START_PAGE;
        } else {
            page = Integer.parseInt(pageString);
        }

        Integer quantityOfAll;
        boolean isAdmin = Objects.equals(role, UserRole.ADMIN.toString().toLowerCase());
        try {
            if (isAdmin) {
                quantityOfAll = footwearService.getFootwearQuantity(criteria, lang);
            } else {
                quantityOfAll = footwearService.getActualFootwearQuantity(criteria, lang);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        int pageCount = (int) Math.ceil(quantityOfAll / (double) PAGE_LIMIT);
        if (page > pageCount) {
            page = START_PAGE;
        }
        if (pageCount == 0) {
            pageCount = 1;
        }
        int offset = (page - 1) * PAGE_LIMIT;

        try {
            if (isAdmin) {
                footwearList = footwearService.getByCriteria(criteria, lang, offset, PAGE_LIMIT);
            } else {
                footwearList = footwearService.getByCriteriaActual(criteria, lang, offset, PAGE_LIMIT);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        request.setAttribute(FOOTWEAR_LIST_ATTRIBUTE, footwearList);
        request.setAttribute(BRAND_LIST_ATTRIBUTE, brandList);
        request.setAttribute(CATEGORY_LIST_ATTRIBUTE, categoryList);
        request.setAttribute(FOR_WHOM_ATTRIBUTE, criteria.getForWhom().toString().toLowerCase());
        request.setAttribute(CATEGORY_ATTRIBUTE, criteria.getCategory());
        request.setAttribute(BRAND_ATTRIBUTE, criteria.getBrand());
        request.setAttribute(PAGE_COUNT_ATTRIBUTE, pageCount);
        request.setAttribute(CURRENT_PAGE_ATTRIBUTE, page);

        RequestDispatcher requestDispatcher = request.getRequestDispatcher(FORWARD_PATH);
        requestDispatcher.forward(request, response);
    }
}

