package by.victor.jwd.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class FootwearCriteria implements Serializable {
    @Serial
    private static final long serialVersionUID = 7607507216858003589L;

    public final static String ALL = "all";

    private String category;
    private String brand;
    private ForEnum forWhom;

    public FootwearCriteria (){
        category = ALL;
        brand = ALL;
        forWhom = ForEnum.ALL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public ForEnum getForWhom() {
        return forWhom;
    }

    public void setForWhom(ForEnum forWhom) {
        this.forWhom = forWhom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootwearCriteria that = (FootwearCriteria) o;
        return Objects.equals(category, that.category) && Objects.equals(brand, that.brand) && forWhom == that.forWhom;
    }

    @Override
    public int hashCode() {
        return Objects.hash(category, brand, forWhom);
    }

    @Override
    public String toString() {
        return "FootwearCriteria{" +
                "category='" + category + '\'' +
                ", brand='" + brand + '\'' +
                ", forWhom=" + forWhom +
                '}';
    }
}
