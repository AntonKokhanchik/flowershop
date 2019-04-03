package flowershop.backend.entity;

import java.util.Calendar;
import java.util.Objects;

import flowershop.backend.OrderStatus;
import org.jetbrains.annotations.NotNull;

public class Order extends Entity{
    private int fullPrice;
    private Calendar dateCreation;
    private Calendar dateClosing;
    private OrderStatus status;

    public Order(int fullPrice, Calendar dateCreation, Calendar dateClosing, OrderStatus status){
        this.fullPrice = fullPrice;
        this.dateCreation = dateCreation;
        this.dateClosing = dateClosing;
        this.status = status;
    }

    public Order(@NotNull Order order){
        this.fullPrice = order.fullPrice;
        this.dateCreation = order.dateCreation;
        this.dateClosing = order.dateClosing;
        this.status = order.status;
    }

    public int getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(int fullPrice) {
        this.fullPrice = fullPrice;
    }

    public Calendar getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Calendar dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Calendar getDateClosing() {
        return dateClosing;
    }

    public void setDateClosing(Calendar dateClosing) {
        this.dateClosing = dateClosing;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return fullPrice == order.fullPrice &&
                Objects.equals(dateCreation, order.dateCreation) &&
                Objects.equals(dateClosing, order.dateClosing) &&
                status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fullPrice, dateCreation, dateClosing, status);
    }

    @Override
    public String toString() {
        return "Order{" +
                "fullPrice=" + fullPrice +
                ", dateCreation=" + dateCreation +
                ", dateClosing=" + dateClosing +
                ", status=" + status +
                '}';
    }
}
