package flowershop.frontend.dto;

import flowershop.backend.entity.FlowerEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

public class Flower implements Serializable {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer count;

    public Flower(Long id, String name, BigDecimal price, Integer count) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.count = count;
    }

    public Flower(FlowerEntity entity) {
        id = entity.getId();
        name = entity.getName();
        price = entity.getPrice();
        count = entity.getCount();
    }

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
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public FlowerEntity toEntity() {
        FlowerEntity entity = new FlowerEntity();
        entity.setId(id);
        entity.setName(name);
        entity.setPrice(price);
        entity.setCount(count);

        return entity;
    }

    public OrderFlowerData toOrderData(Integer count) {
        return new OrderFlowerData(name, price, count, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Objects.equals(id, flower.id) &&
                Objects.equals(name, flower.name) &&
                Objects.equals(price, flower.price) &&
                Objects.equals(count, flower.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, count);
    }

    @Override
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
