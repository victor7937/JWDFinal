package by.victor.jwd.controller.command.impl.post;

import by.victor.jwd.bean.Footwear;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.controller.command.Command;
import by.victor.jwd.controller.command.CommandName;
import by.victor.jwd.controller.command.CommandPath;
import by.victor.jwd.controller.exception.ControllerException;
import by.victor.jwd.controller.util.ImageUploader;
import by.victor.jwd.controller.validator.RequestValidator;
import by.victor.jwd.controller.validator.ValidationProvider;
import by.victor.jwd.service.FootwearService;
import by.victor.jwd.service.ServiceProvider;
import by.victor.jwd.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.victor.jwd.controller.constant.FootwearParams.*;
import static by.victor.jwd.controller.constant.GlobalParams.*;


public class EditFootwear implements Command {

    private static final String EDIT_PARAM = "edit";
    private static final int ERROR_CODE = 404;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String art = request.getParameter(ART_PARAM);
        if (art == null || art.isBlank()) {
            response.sendError(ERROR_CODE);
            return;
        }
        String failPath = CommandPath.createCommand(CommandName.GOTOEDITFOOTWEAR)
                .addParam(EDIT_PARAM, FAIL)
                .addParam(SHOW_PARAM,YES_VALUE)
                .addParam(ART_PARAM, art)
                .createPath();
        RequestValidator validator = ValidationProvider.getInstance().getFootwearValidator();
        if (!validator.validate(request)) {
            response.sendRedirect(failPath);
            return;
        }

        Footwear footwear = new Footwear(art);
        buildFootwear(footwear, request);
        footwear.setImageLinks(ImageUploader.upload(request));

        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);
        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        try {
            if (footwearService.updateFootwear(footwear, lang)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOEDITFOOTWEAR)
                        .addParam(EDIT_PARAM, SUCCESS)
                        .addParam(SHOW_PARAM,YES_VALUE)
                        .addParam(ART_PARAM, art)
                        .createPath());
            } else {
                response.sendRedirect(failPath);
            }
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }
    }

    private void buildFootwear(Footwear footwear, HttpServletRequest request ){
        footwear.setName(request.getParameter(NAME_PARAM));
        footwear.setBrand(request.getParameter(BRAND_PARAM));
        footwear.setCategory(request.getParameter(CATEGORY_PARAM));
        footwear.setColor(request.getParameter(COLOR_PARAM));
        footwear.setPrice(Float.parseFloat(request.getParameter(PRICE_PARAM)));
        footwear.setForWhom(ForEnum.valueOf(request.getParameter(FOR_PARAM)));
        footwear.setDescription(request.getParameter(DESCRIPTION_PARAM));
    }

}
