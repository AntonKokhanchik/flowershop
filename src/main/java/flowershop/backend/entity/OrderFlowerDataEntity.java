package flowershop.backend.entity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Stores a copy of the information about the flower in the order (name and price),
 * and the number of ordered flowers of this species
 *
 * Хранит копию информцаии о цветке в заказе (название и цену),
 * а также количество заказанных цветков данного вида
 */
@Entity
@Table(name="ORDER_FLOWERS")
public class OrderFlowerDataEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** название цветка на момент заказа */
    private String flowerName;

    /** цена цветка на момент заказа */
    private BigDecimal price;

    /** количество цветков в заказе */
    private Integer count;

    @ManyToOne
    @JoinColumn(name="order_id", referencedColumnName="id")
    private OrderEntity order;

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

    public OrderEntity getOrder() {
        return order;
    }

    public void setOrder(OrderEntity order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "OrderFlowerDataEntity{" +
                "id=" + id +
                ", flowerName='" + flowerName + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", order=" + order +
                '}';
    }
}
