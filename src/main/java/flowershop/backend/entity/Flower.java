package flowershop.backend.entity;

import java.util.Objects;

public class Flower extends Entity{
    private String title;
    private float price;
    private int count;

    public Flower(String title, float price, int count){
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public Flower(Flower flower){
        this.title = flower.title;
        this.price = flower.price;
        this.count = flower.count;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return Float.compare(flower.price, price) == 0 &&
                count == flower.count &&
                title.equals(flower.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, count);
    }

    @Override
    public String toString() {
        return "Flower{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
