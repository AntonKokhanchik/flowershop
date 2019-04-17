package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Cart {
    Map<Flower, Integer> items;
    BigDecimal sum;

    public Cart()
    {
        items = new HashMap<>();
        sum = new BigDecimal(0);
    }

    public void addItem(Flower flower){
        if (flower.getCount() < 1)
            return;

        if (items.containsKey(flower))
            if (items.get(flower) < flower.getCount())
                items.put(flower, items.get(flower) + 1);
            else
                return;
        else
            items.put(flower, 1);

        sum = sum.add(flower.getPrice());
    }

    public void removeItem(Flower flower){
        if (!items.containsKey(flower))
            return;

        items.put(flower, items.get(flower) - 1);

        if (items.get(flower) == 0)
            items.remove(flower);

        sum = sum.subtract(flower.getPrice());
    }

    public void clear(){
        items = new HashMap<>();
        sum = new BigDecimal(0);
    }

    public BigDecimal getResult(Integer discount){
        return sum.multiply(new BigDecimal(1-discount/100));
    }

    public Map<Flower, Integer> getItems() {
        return items;
    }

    public void setItems(Map<Flower, Integer> items) {
        this.items = items;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public void setSum(BigDecimal sum) {
        this.sum = sum;
    }

    public boolean isEmpty(){
        return items.isEmpty();
    }
}
