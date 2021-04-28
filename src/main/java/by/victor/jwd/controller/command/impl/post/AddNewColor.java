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

public class AddNewColor implements Command {

    private static final String EN_LANG = "en";
    private static final String RU_LANG = "ru";
    private static final String FAIL_PATH =  CommandPath.createCommand(CommandName.GOTOADDFEATURES).addParam(MESSAGE_PARAM, FAIL)
            .addParam(SHOW_PARAM, YES_VALUE).createPath();
    private static final String COLOR_EN_PARAM = "color_en";
    private static final String COLOR_RU_PARAM = "color_ru";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        String color_en = request.getParameter(COLOR_EN_PARAM);
        String color_ru = request.getParameter(COLOR_RU_PARAM);

        if (color_en == null || color_ru == null || color_en.isBlank() || color_ru.isBlank()){
            response.sendRedirect(FAIL_PATH);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        List<String> colorsEn;
        List<String> colorsRu;
        try {
            colorsEn = footwearService.getColors(EN_LANG);
            colorsRu = footwearService.getColors(RU_LANG);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (colorsEn.contains(color_en) || colorsRu.contains(color_ru)) {
            response.sendRedirect(FAIL_PATH);
            return;
        }

        try {
            if (footwearService.createNewColor(color_en, color_ru)){
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
