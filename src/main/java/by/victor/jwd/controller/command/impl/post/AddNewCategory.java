package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.victor.jwd.controller.constant.GlobalParams.*;

public class AddNewCategory implements Command {

    private static final String EN_LANG = "en";
    private static final String RU_LANG = "ru";
    private static final String FAIL_PATH =  CommandPath.createCommand(CommandName.GOTOADDFEATURES).addParam(MESSAGE_PARAM, FAIL)
            .addParam(SHOW_PARAM, YES_VALUE).createPath();
    private static final String CATEGORY_EN_PARAM = "category_en";
    private static final String CATEGORY_RU_PARAM = "category_ru";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        String category_en = request.getParameter(CATEGORY_EN_PARAM);
        String category_ru = request.getParameter(CATEGORY_RU_PARAM);

        if (category_en == null || category_ru == null || category_en.isBlank() || category_ru.isBlank()){
            response.sendRedirect(FAIL_PATH);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        List<String> categoriesEn;
        List<String> categoriesRu;
        try {
            categoriesEn = footwearService.getCategories(EN_LANG);
            categoriesRu = footwearService.getCategories(RU_LANG);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (categoriesEn.contains(category_en) || categoriesRu.contains(category_ru)) {
            response.sendRedirect(FAIL_PATH);
            return;
        }

        try {
            if (footwearService.createNewCategory(category_en, category_ru)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOADDFEATURES)
                        .addParam(MESSAGE_PARAM, SUCCESS)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(FAIL_PATH);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
