package flowershop.frontend.dto;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

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

    public Flower() { }

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

    public OrderFlowerData toOrderData(Integer count) {
        return new OrderFlowerData(name, price, count, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Flower flower = (Flower) o;

        return new EqualsBuilder()
                .append(id, flower.id)
                .append(name, flower.name)
                .append(price, flower.price)
                .append(count, flower.count)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(price)
                .append(count)
                .toHashCode();
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
