package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Long, Integer> items;

    public Cart() {
        items = new HashMap<>();
    }

    public Map<Long, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Long, Integer> items) {
        this.items = items;
    }

    public void addItem(Flower flower) {
        if (flower.getCount() < 1)
            return;

        Long flowerId = flower.getId();
        if (items.containsKey(flowerId)) {
            if (items.get(flowerId) < flower.getCount())
                items.put(flowerId, items.get(flowerId) + 1);
        } else
            items.put(flowerId, 1);
        System.out.println(items);
    }

    public void removeItem(Flower flower) {
        Long flowerId = flower.getId();
        if (!items.containsKey(flowerId))
            return;

        items.put(flowerId, items.get(flowerId) - 1);

        if (items.get(flowerId) == 0)
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
