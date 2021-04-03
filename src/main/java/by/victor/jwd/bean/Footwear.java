package by.victor.jwd.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Footwear implements Serializable {

    @Serial
    private static final long serialVersionUID = -2715865691919819338L;

    private String art;
    private String name;
    private Float price;
    private String color;
    private String category;
    private String description;
    private String imageLink;
    private String brand;
    private ForEnum forWhom;

    public Footwear(String art) {
        this.art = art;
    }

    public Footwear(String art, String name, Float price, String color, String category,
                    String description, String imageLink, String brand, ForEnum forWhom) {
        this.art = art;
        this.name = name;
        this.price = price;
        this.color = color;
        this.category = category;
        this.description = description;
        this.imageLink = imageLink;
        this.brand = brand;
        this.forWhom = forWhom;
    }

    public String getArt() {
        return art;
    }

    public void setArt(String art) {
        this.art = art;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
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
        Footwear footwear = (Footwear) o;
        return Objects.equals(art, footwear.art);
    }

    @Override
    public int hashCode() {
        return Objects.hash(art);
    }

    @Override
    public String toString() {
        return "Footwear{" +
                "art='" + art + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", color='" + color + '\'' +
                ", category='" + category + '\'' +
                ", description='" + description + '\'' +
                ", imageLink='" + imageLink + '\'' +
                ", brand='" + brand + '\'' +
                ", forWhom=" + forWhom +
                '}';
    }
}
