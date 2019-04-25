package flowershop.backend.services;

import flowershop.backend.dto.OrderFlowerData;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Set;

/**
 * Cart with detailed information to show
 */
public class DetailedCart {
    Set<OrderFlowerData> items;

    public DetailedCart(Set<OrderFlowerData> items) {
        this.items = items;
    }

    /**
     * Get summary price of all contained items
     *
     * @return sum(item.price)
     */
    public BigDecimal getSum() {
        BigDecimal sum = new BigDecimal(BigInteger.ZERO);
        for (OrderFlowerData f : items)
            sum = sum.add(f.getPrice().multiply(new BigDecimal(f.getCount())));
        return sum;
    }

    /**
     * Get summary price include given discount
     *
     * @param discount discount to apply
     * @return sum with discount
     */
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
