package flowershop.frontend.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import flowershop.backend.enums.OrderStatus;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class Order implements Serializable {
    private Long id;
    private BigDecimal fullPrice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateClosing;
    private OrderStatus status;
    private Integer appliedDiscount;
    private User owner;

    public Order() { }

    public Order(BigDecimal fullPrice, Integer appliedDiscount) {
        this.fullPrice = fullPrice;
        this.dateCreation = LocalDateTime.now();
        this.dateClosing = null;
        this.status = OrderStatus.CREATED;
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

        return new EqualsBuilder()
                .append(id, order.id)
                .append(fullPrice, order.fullPrice)
                .append(dateCreation, order.dateCreation)
                .append(dateClosing, order.dateClosing)
                .append(status, order.status)
                .append(appliedDiscount, order.appliedDiscount)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(fullPrice)
                .append(dateCreation)
                .append(dateClosing)
                .append(status)
                .append(appliedDiscount)
                .toHashCode();
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
