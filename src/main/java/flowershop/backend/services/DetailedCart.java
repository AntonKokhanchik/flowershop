package flowershop.backend.services;

import flowershop.backend.dto.OrderFlowerData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.Set;

public class DetailedCart {
    Set<OrderFlowerData> items;

    public DetailedCart(Set<OrderFlowerData> items) {
        this.items = items;
    }

    public void clear() {
        items = new HashSet<>();
    }

    public BigDecimal getSum() {
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (OrderFlowerData f : items)
            sum = sum.add(f.getPrice().multiply(new BigDecimal(f.getCount())));
        return sum;
    }

    public BigDecimal getResult(Integer discount) {
        return getSum().multiply(new BigDecimal(1 - discount / 100f)).setScale(2, RoundingMode.DOWN);
    }

    public Set<OrderFlowerData> getItems() {
        return items;
    }

    public void setItems(Set<OrderFlowerData> items) {
        this.items = items;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        return "DetailedCart{" +
                "items=" + items +
                '}';
    }
}
