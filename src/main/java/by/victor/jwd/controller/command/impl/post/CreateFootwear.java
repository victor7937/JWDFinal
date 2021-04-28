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

public class CreateFootwear implements Command {

    private static final String NOT_CREATED_VALUE = "not_created";
    private static final String INV_DATA_VALUE = "inv_data";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!ADMIN_VALUE.equals(request.getSession().getAttribute(ROLE_ATTRIBUTE))) {
            response.sendError(ERROR_CODE_FORBIDDEN);
            return;
        }

        RequestValidator validator = ValidationProvider.getInstance().getFootwearValidator();
        if (!validator.validate(request)) {
            response.sendRedirect(CommandPath.createCommand(CommandName.ADDFOOTWEAR)
                    .addParam(MESSAGE_PARAM, INV_DATA_VALUE)
                    .createPath());
            return;
        }

        Footwear footwear = new Footwear(request.getParameter(ART_PARAM));
        buildFootwear(footwear, request);
        footwear.setImageLinks(ImageUploader.upload(request));
        String description_en = request.getParameter(DESCRIPTION_EN_PARAM);
        String description_ru = request.getParameter(DESCRIPTION_RU_PARAM);

        String lang = (String)request.getSession().getAttribute(LANG_ATTRIBUTE);

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        try {
            if (footwearService.createNewFootwear(footwear, description_en, description_ru, lang)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                        .addParam(ART_PARAM, footwear.getArt())
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.ADDFOOTWEAR)
                        .addParam(MESSAGE_PARAM, NOT_CREATED_VALUE)
                        .createPath());
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
    }
}
