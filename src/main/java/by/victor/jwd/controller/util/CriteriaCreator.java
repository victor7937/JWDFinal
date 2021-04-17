package by.victor.jwd.controller.util;

import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.bean.ForEnum;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class CriteriaCreator {

    public static final String CATEGORY_PARAM = "category";
    public static final String BRAND_PARAM = "brand";
    public static final String FOR_PARAM = "for";

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
