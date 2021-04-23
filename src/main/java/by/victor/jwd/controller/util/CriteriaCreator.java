package by.victor.jwd.controller.util;

import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.bean.ForEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class for creating valid FootwearCriteria object by request
 */
public class CriteriaCreator {

    public static final String CATEGORY_PARAM = "category";
    public static final String BRAND_PARAM = "brand";
    public static final String FOR_PARAM = "for";

    /**
     * Gets params from request, validate them and creates footwearCriteria object.
     * @param request - request for getting parameters
     * @param categoryList - list of all categories to check if current is valid
     * @param brandList - list of all brands to check if current is valid
     * @return valid criteria
     */
    public static FootwearCriteria createFootwearCriteria(HttpServletRequest request, List<String> categoryList, List<String> brandList){
        String categoryParam = request.getParameter(CATEGORY_PARAM);
        String brandParam = request.getParameter(BRAND_PARAM);
        String forParam = request.getParameter(FOR_PARAM);

        FootwearCriteria criteria = new FootwearCriteria();

        if (!(categoryParam == null || categoryParam.isBlank())) {
            criteria.setCategory(categoryParam);
        }
        if (!(brandParam == null || brandParam.isBlank())) {
            criteria.setBrand(brandParam);
        }
        if (ForEnum.HIM.toString().toLowerCase().equals(forParam)) {
            criteria.setForWhom(ForEnum.HIM);
        }
        if (ForEnum.HER.toString().toLowerCase().equals(forParam)){
            criteria.setForWhom(ForEnum.HER);
        }

        if(!criteria.getCategory().equals(FootwearCriteria.ALL) && !categoryList.contains(criteria.getCategory())) {
            criteria.setCategory(FootwearCriteria.ALL);
        }
        if(!criteria.getBrand().equals(FootwearCriteria.ALL) && !brandList.contains(criteria.getBrand())) {
            criteria.setBrand(FootwearCriteria.ALL);
        }

        return criteria;
    }
}
