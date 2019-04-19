package flowershop.backend.entity;

import flowershop.backend.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="ORDERS")
@NamedQuery(name="getAllOrders", query="Select c from OrderEntity c")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal fullPrice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateClosing;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @ManyToOne
    @JoinColumn(name="owner_login", referencedColumnName="login")
    private UserEntity owner;

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

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", fullPrice=" + fullPrice +
                ", dateCreation=" + dateCreation +
                ", dateClosing=" + dateClosing +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}
