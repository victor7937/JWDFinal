package by.victor.jwd.controller.command.impl.post;

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

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.FootwearParams.SIZE_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class AddNewItem implements Command {

    private static final String ADD_PARAM = "add";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        String sizeString = request.getParameter(SIZE_PARAM);

        String fail_path = CommandPath.createCommand(CommandName.GOTOITEMSPAGE)
                .addParam(ART_PARAM, art)
                .addParam(ADD_PARAM, FAIL)
                .addParam(SHOW_PARAM, YES_VALUE)
                .createPath();

        if (art == null || art.isBlank() || sizeString == null || !RequestValidator.isSizeValid(sizeString)) {
            response.sendRedirect(fail_path);
            return;
        }

        Float size = Float.parseFloat(sizeString);
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        try {
            if (footwearService.createNewItem(art, size)){
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOITEMSPAGE)
                        .addParam(ART_PARAM, art)
                        .addParam(ADD_PARAM, SUCCESS)
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
