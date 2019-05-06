package flowershop.frontend.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import flowershop.backend.enums.OrderStatus;

public class Order implements Serializable {
    private Long id;
    private BigDecimal fullPrice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateClosing;
    private OrderStatus status;
    private Integer appliedDiscount;
    private User owner;

    public Order() { }

    public Order(BigDecimal fullPrice, LocalDateTime dateCreation,
                 LocalDateTime dateClosing, OrderStatus status, Integer appliedDiscount) {
        this.fullPrice = fullPrice;
        this.dateCreation = dateCreation;
        this.dateClosing = dateClosing;
        this.status = status;
        this.appliedDiscount = appliedDiscount;
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

    public Integer getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Integer appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
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
                status == order.status &&
                Objects.equals(appliedDiscount, order.appliedDiscount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fullPrice, dateCreation, dateClosing, status, appliedDiscount);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", fullPrice=" + fullPrice +
                ", dateCreation=" + dateCreation +
                ", dateClosing=" + dateClosing +
                ", status=" + status +
                ", appliedDiscount=" + appliedDiscount +
                ", owner=" + owner.getLogin() +
                '}';
    }
}
