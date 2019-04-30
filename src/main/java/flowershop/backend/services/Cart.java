package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import java.util.HashMap;
import java.util.Map;

/**
 * Cart for holding flowers for order
 */
public class Cart {
    /**
     * items map {@code <flowerId, count>}
     */
    private Map<Long, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }

    /**
     * Add flower to cart, cannot (do nothing) add more than flowers count in shop
     * @param flower flower to add
     */
    public void addItem(Flower flower, Integer count) {
        Long flowerId = flower.getId();
        Integer stock = 0;

        if (items.containsKey(flowerId))
            stock = items.get(flowerId);

        if (stock + count < flower.getCount())
            items.put(flowerId, stock + count);
        else
            items.put(flowerId, flower.getCount());
    }

    /**
     * Remove flower from cart
     * @param flower flower to remove
     */
    public void removeItem(Flower flower, Integer count) {
        Long flowerId = flower.getId();
        if (count == -1)
            items.remove(flowerId);
        if (!items.containsKey(flowerId))
            return;

        items.put(flowerId, items.get(flowerId) - 1);

        if (items.get(flowerId) <= 0)
            items.remove(flowerId);
    }

    public void clear() {
        items = new HashMap<>();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
