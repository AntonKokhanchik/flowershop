package flowershop.backend.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import flowershop.backend.OrderStatus;
import flowershop.backend.entity.OrderEntity;

public class Order {
    private Long id;
    private BigDecimal fullPrice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateClosing;
    private OrderStatus status;
    private User owner;

    public Order(Long id, BigDecimal fullPrice, LocalDateTime dateCreation, LocalDateTime dateClosing, OrderStatus status){
        this.id = id;
        this.fullPrice = fullPrice;
        this.dateCreation = dateCreation;
        this.dateClosing = dateClosing;
        this.status = status;
    }

    public Order(OrderEntity entity){
        id = entity.getId();
        fullPrice = entity.getFullPrice();
        dateCreation = entity.getDateCreation();
        dateClosing = entity.getDateClosing();
        status = entity.getStatus();
        owner = new User(entity.getOwner());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(BigDecimal fullPrice) {
        this.fullPrice = fullPrice;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDateTime getDateClosing() {
        return dateClosing;
    }

    public void setDateClosing(LocalDateTime dateClosing) {
        this.dateClosing = dateClosing;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public OrderEntity toEntity(){
        OrderEntity entity = new OrderEntity();
        entity.setId(id);
        entity.setFullPrice(fullPrice);
        entity.setDateCreation(dateCreation);
        entity.setDateClosing(dateClosing);
        entity.setStatus(status);

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(fullPrice, order.fullPrice) &&
                Objects.equals(dateCreation, order.dateCreation) &&
                Objects.equals(dateClosing, order.dateClosing) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullPrice, dateCreation, dateClosing, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id="+ id +
                ", fullPrice=" + fullPrice +
                ", dateCreation=" + dateCreation +
                ", dateClosing=" + dateClosing +
                ", status=" + status +
                '}';
    }
}
