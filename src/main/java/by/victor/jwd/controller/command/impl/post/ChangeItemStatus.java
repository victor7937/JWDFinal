package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.bean.ItemStatus;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class ChangeItemStatus implements Command {

    public static final String CHANGE_PARAM = "change";
    public static final String ID_PARAM = "id";
    public static final String STATUS_PARAM = "status";
    public static final String SAME_VALUE = "same";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        String art = request.getParameter(ART_PARAM);
        String idString = request.getParameter(ID_PARAM);
        String statusString = request.getParameter(STATUS_PARAM);

        String fail_path = CommandPath.createCommand(CommandName.GOTOITEMSPAGE)
                .addParam(ART_PARAM, art)
                .addParam(CHANGE_PARAM, FAIL)
                .addParam(SHOW_PARAM, YES_VALUE)
                .createPath();

        if (idString == null || !RequestValidator.isInteger(idString) || statusString == null || statusString.isBlank()
                || art == null || art.isBlank()) {
             response.sendRedirect(fail_path);
             return;
        }

        Integer id = Integer.parseInt(idString);
        ItemStatus status = ItemStatus.valueOf(statusString.toUpperCase());
        ItemStatus currentStatus;
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        try {
             currentStatus = footwearService.getItemStatus(id);
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (Objects.equals(status, currentStatus)){
             response.sendRedirect(CommandPath.createCommand(CommandName.GOTOITEMSPAGE)
                    .addParam(ART_PARAM, art)
                    .addParam(CHANGE_PARAM, SAME_VALUE)
                    .addParam(SHOW_PARAM, YES_VALUE)
                    .createPath());
            return;
        }


        try {
            if (footwearService.updateItemStatus(id, status)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOITEMSPAGE)
                        .addParam(ART_PARAM, art)
                        .addParam(CHANGE_PARAM, SUCCESS)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(fail_path);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
