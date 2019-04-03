package flowershop.backend.entity;

import java.util.Calendar;
import flowershop.backend.OrderStatus;

public class Order {
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
}
