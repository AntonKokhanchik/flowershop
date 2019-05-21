package flowershop.backend.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class FlowerEntity implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer flowerCount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return flowerCount;
    }

    public void setCount(Integer count) {
        this.flowerCount = count;
    }

    @Override
    public String toString() {
        return "FlowerEntity{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", count=" + flowerCount +
                '}';
    }
}
