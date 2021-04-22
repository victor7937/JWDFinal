package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.util.ImageUploader;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.FootwearParams.ART_PARAM;
import static by.victor.jwd.controller.constant.GlobalParams.*;

public class DeleteImage implements Command {

    private static final String DELETE_PARAM = "delete";
    private static final int ERROR_CODE = 404;
    private static final String IMAGE_PARAM = "image";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String image = request.getParameter(IMAGE_PARAM);
        String art = request.getParameter(ART_PARAM);
        if (art == null || art.isBlank()) {
            response.sendError(ERROR_CODE);
            return;
        }
        String failPath = CommandPath.createCommand(CommandName.GOTOEDITFOOTWEAR)
                .addParam(ART_PARAM, art)
                .addParam(DELETE_PARAM,FAIL)
                .addParam(SHOW_PARAM,YES_VALUE)
                .createPath();
        if (image == null || image.isBlank()) {
            response.sendRedirect(failPath);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        try {
            if (footwearService.deleteImage(image)) {
                ImageUploader.delete(image);
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOEDITFOOTWEAR)
                        .addParam(ART_PARAM, art)
                        .addParam(DELETE_PARAM, SUCCESS)
                        .addParam(SHOW_PARAM, YES_VALUE)
                        .createPath());
            } else {
                response.sendRedirect(failPath);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }
}
