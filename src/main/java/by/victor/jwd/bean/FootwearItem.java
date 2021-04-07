package by.victor.jwd.bean;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class FootwearItem implements Serializable {

    @Serial
    private static final long serialVersionUID = -5729255221961409256L;

    private Footwear footwear;
    private Float size;

    public FootwearItem () {}

    public FootwearItem(Footwear footwear, Float size) {
        this.size = size;
        this.footwear = footwear;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FootwearItem that = (FootwearItem) o;
        return Objects.equals(size, that.size) && Objects.equals(footwear, that.footwear);
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, footwear);
    }

    @Override
    public String toString() {
        return "FootwearItem{" +
                "size=" + size +
                ", footwear=" + footwear +
                '}';
    }
}
