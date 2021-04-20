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

public class AddNewBrand implements Command {

    private static final String FAIL_PATH =  CommandPath.createCommand(CommandName.GOTOADDFEATURES).addParam(MESSAGE_PARAM, FAIL)
            .addParam(SHOW_PARAM, YES_VALUE).createPath();
    private static final String BRAND_PARAM = "brand";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String brand = request.getParameter(BRAND_PARAM);

        if (brand == null || brand.isBlank()){
            response.sendRedirect(FAIL_PATH);
            return;
        }

        FootwearService footwearService = ServiceProvider.getInstance().getFootwearService();

        List<String> brands;
        try {
            brands = footwearService.getBrands();
        } catch (ServiceException e) {
            throw new ControllerException(e);
        }

        if (brands.contains(brand)) {
            response.sendRedirect(FAIL_PATH);
            return;
        }

        try {
            if (footwearService.createNewBrand(brand)){
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
