package by.victor.jwd.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class FootwearItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -5729255221961409256L;

    private Integer id;
    private Footwear footwear;
    private Float size;
    private Integer maxQuantity;
    private Integer quantity;
    private ItemStatus status;

    public FootwearItem(Footwear footwear, Float size) {
        this.size = size;
        this.footwear = footwear;
        this.maxQuantity = 0;
        this.quantity = 0;
    }

    public FootwearItem (Footwear footwear, Float size, Integer quantity) {
        this.footwear = footwear;
        this.size = size;
        this.quantity = quantity;
        this.maxQuantity = 0;
    }


    public Footwear getFootwear() {
        return footwear;
    }

    public void setFootwear(Footwear footwear) {
        this.footwear = footwear;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Integer maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootwearItem that = (FootwearItem) o;
        return Objects.equals(size, that.size) && Objects.equals(footwear.getArt(), that.footwear.getArt())
                && Objects.equals(quantity, that.quantity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, footwear.getArt(), quantity);
    }

    @Override
    public String toString() {
        return "FootwearItem{" +
                "footwear=" + footwear.getArt() +
                ", size=" + size +
                ", quantity=" + quantity +
                '}';
    }
}
