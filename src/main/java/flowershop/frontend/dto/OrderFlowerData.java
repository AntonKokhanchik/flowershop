package flowershop.frontend.dto;

import flowershop.backend.entity.OrderFlowerDataEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * DTO for OrderFlowerDataEntity
 * @see OrderFlowerDataEntity
 */
public class OrderFlowerData implements Serializable {
    private Long id;
    private String flowerName;
    private BigDecimal price;
    private Integer count;
    private Order order;

    private Long flowerId;

    public OrderFlowerData() { }

    public OrderFlowerData(String flowerName, BigDecimal price, Integer count, Long flowerId) {
        this.flowerName = flowerName;
        this.price = price;
        this.count = count;
        this.flowerId = flowerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
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

    public Long getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Long flowerId) {
        this.flowerId = flowerId;
    }

    /**
     * @return flower price multiplied by number
     */
    public BigDecimal getFullPrice() {
        return price.multiply(BigDecimal.valueOf(count));
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderFlowerData that = (OrderFlowerData) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(flowerName, that.flowerName) &&
                Objects.equals(price, that.price) &&
                Objects.equals(count, that.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, flowerName, price, count);
    }

    @Override
    public String toString() {
        return "OrderFlowerData{" +
                "id=" + id +
                ", flowerName='" + flowerName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", order=" + order +
                '}';
    }
}
