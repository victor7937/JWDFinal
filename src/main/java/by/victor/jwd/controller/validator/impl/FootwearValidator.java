package by.victor.jwd.controller.validator.impl;

import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.controller.validator.RequestValidator;

import javax.servlet.http.HttpServletRequest;

import static by.victor.jwd.controller.constant.FootwearParams.*;

/**
 * Validates footwear adding form
 */
public class FootwearValidator implements RequestValidator {
    @Override
    public boolean validate(HttpServletRequest request) {
        String art = request.getParameter(ART_PARAM);
        String name = request.getParameter(NAME_PARAM);
        String brand = request.getParameter(BRAND_PARAM);
        String category = request.getParameter(CATEGORY_PARAM);
        String color = request.getParameter(COLOR_PARAM);
        String price = request.getParameter(PRICE_PARAM);
        String forWhom = request.getParameter(FOR_PARAM);

        if (art == null || name == null || brand == null || category == null || color == null || price == null || forWhom == null ||
        art.isBlank() || name.isBlank() || brand.isBlank() || color.isBlank() || color.isBlank() || price.isBlank() || forWhom.isBlank()) {
            return false;
        }
        if (!RequestValidator.isPriceValid(price)){
            return false;
        }
        if (!(ForEnum.HIM.toString().equals(forWhom) || ForEnum.HER.toString().equals(forWhom))){
            return false;
        }

        return true;
    }
}
