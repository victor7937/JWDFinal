package by.victor.jwd.dao.impl;

import by.victor.jwd.bean.FootwearCriteria;
import by.victor.jwd.bean.ForEnum;
import by.victor.jwd.dao.util.SQLConsumer;

import java.sql.PreparedStatement;
import java.util.concurrent.atomic.AtomicInteger;

public class FootwearQueryCreator {

    private static final String SQL_BY_CATEGORY = " AND c_name_%s = ?";
    private static final String SQL_BY_BRAND = " AND b_name = ?";
    private static final String SQL_FOR_ADDING = " AND f_for = ?";
    private static final String SQL_LIMIT_ADDING = " ORDER BY f_art LIMIT ?, ?";

    private FootwearCriteria criteria;
    private  SQLConsumer<PreparedStatement> consumer;
    private String query;
    private String lang;
    private Integer offset;
    private Integer limit;

    private FootwearQueryCreator (FootwearCriteria criteria, String query, String lang, int offset, int limit) {
        this.criteria = criteria;
        this.query = query;
        this.lang = lang;
        this.offset = offset;
        this.limit = limit;
        creatingQueryProcess();
    }

    public static FootwearQueryCreator create (FootwearCriteria criteria, String query, String lang, int offset, int limit) {
        return new FootwearQueryCreator(criteria, query, lang, offset, limit);
    }

    private void creatingQueryProcess () {
        String category = criteria.getCategory();
        String brand = criteria.getBrand();
        ForEnum forWhom = criteria.getForWhom();
        AtomicInteger i = new AtomicInteger();
        if (category.equals(FootwearCriteria.ALL) && brand.equals(FootwearCriteria.ALL)) {
                consumer = ps -> {
                    if (forWhom != ForEnum.ALL)
                        ps.setString(i.incrementAndGet(), forWhom.toString());
                    ps.setInt(i.incrementAndGet(), offset);
                    ps.setInt(i.incrementAndGet(), limit);
                };
        } else if (category.equals(FootwearCriteria.ALL)) {
            query = query + SQL_BY_BRAND;
            consumer = ps -> {
                ps.setString(i.incrementAndGet(), brand);
                if (forWhom != ForEnum.ALL)
                    ps.setString(i.incrementAndGet(), forWhom.toString());
                ps.setInt(i.incrementAndGet(), offset);
                ps.setInt(i.incrementAndGet(), limit);
            };
        } else if (brand.equals(FootwearCriteria.ALL)) {
            query = query + String.format(SQL_BY_CATEGORY, lang);
            consumer = ps -> {
                ps.setString(i.incrementAndGet(), category);
                if (forWhom != ForEnum.ALL)
                    ps.setString(i.incrementAndGet(), forWhom.toString());
                ps.setInt(i.incrementAndGet(), offset);
                ps.setInt(i.incrementAndGet(), limit);
            };
        } else {
            query = query + String.format(SQL_BY_CATEGORY + SQL_BY_BRAND, lang);
            consumer = ps -> {
                ps.setString(i.incrementAndGet(), category);
                ps.setString(i.incrementAndGet(), brand);
                if (forWhom != ForEnum.ALL)
                    ps.setString(i.incrementAndGet(), forWhom.toString());
                ps.setInt(i.incrementAndGet(), offset);
                ps.setInt(i.incrementAndGet(), limit);
            };
        }
        query = query + (forWhom == ForEnum.ALL ? "" : SQL_FOR_ADDING) + SQL_LIMIT_ADDING;
    }

    public SQLConsumer<PreparedStatement> getConsumer() {
        return consumer;
    }

    public String getQuery() {
        return query;
    }
}
