package flowershop.backend.entity;

import flowershop.backend.enums.OrderStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "ORDERS")
public class OrderEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal fullPrice;
    private LocalDateTime dateCreation;
    private LocalDateTime dateClosing;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @Column(name = "discount")
    private Integer appliedDiscount;
    @ManyToOne
    @JoinColumn(name = "owner_login", referencedColumnName = "login")
    private UserEntity owner;
    @OneToMany(mappedBy = "order", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<OrderFlowerDataEntity> flowersData = new LinkedList<>();

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

    public Integer getAppliedDiscount() {
        return appliedDiscount;
    }

    public void setAppliedDiscount(Integer appliedDiscount) {
        this.appliedDiscount = appliedDiscount;
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

    public List<OrderFlowerDataEntity> getFlowersData() {
        return flowersData;
    }

    public void setFlowersData(List<OrderFlowerDataEntity> flowersData) {
        this.flowersData = flowersData;
    }

    public void addFlowerData(OrderFlowerDataEntity d) {
        flowersData.add(d);
        d.setOrder(this);
    }

    public void removeFlowerData(OrderFlowerDataEntity d) {
        d.setOrder(null);
        flowersData.remove(d);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "id=" + id +
                ", fullPrice=" + fullPrice +
                ", dateCreation=" + dateCreation +
                ", dateClosing=" + dateClosing +
                ", status=" + status +
                ", appliedDiscount=" + appliedDiscount +
                ", owner=" + owner +
                '}';
    }
}
