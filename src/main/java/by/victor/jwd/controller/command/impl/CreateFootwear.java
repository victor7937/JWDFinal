package by.victor.jwd.controller.command.impl;

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
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CreateFootwear implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestValidator validator = ValidationProvider.getInstance().getFootwearValidator();
        if (!validator.validate(request)) {
            response.sendRedirect(CommandPath.createCommand(CommandName.ADDFOOTWEAR)
                    .addParam("message","inv_data")
                    .createPath());
            return;
        }

        Footwear footwear = new Footwear(request.getParameter("art"));
        buildFootwear(footwear, request);
        footwear.setImageLink(ImageUploader.upload(request).get(0));
        String description_en = request.getParameter("description_en");
        String description_ru = request.getParameter("description_ru");

        String lang = (String)request.getSession().getAttribute("lang");

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();
        try {
            if (footwearService.createNewFootwear(footwear, description_en, description_ru, lang)) {
                response.sendRedirect(CommandPath.createCommand(CommandName.GOTOPRODUCT)
                        .addParam("art", footwear.getArt())
                        .addParam("message","created_success")
                        .createPath());
            } else {
                response.sendRedirect(CommandPath.createCommand(CommandName.ADDFOOTWEAR)
                        .addParam("message","not_create")
                        .createPath());
            }
        } catch (ServiceException e) {
            throw new ControllerException("Error adding footwear", e);
        }


    }

    private void buildFootwear(Footwear footwear, HttpServletRequest request ){
        footwear.setName(request.getParameter("name"));
        footwear.setBrand(request.getParameter("brand"));
        footwear.setCategory(request.getParameter("category"));
        footwear.setColor(request.getParameter("color"));
        footwear.setPrice(Float.parseFloat(request.getParameter("price")));
        footwear.setForWhom(ForEnum.valueOf(request.getParameter("for")));
    }
}
