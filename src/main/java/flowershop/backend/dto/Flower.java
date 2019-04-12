package flowershop.backend.dto;

import flowershop.backend.entity.FlowerEntity;

import java.math.BigDecimal;
import java.util.Objects;

public class Flower {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer count;

    public Flower(String title, BigDecimal price, Integer count){
        id = null;
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public Flower(Long id, String title, BigDecimal price, Integer count){
        this.id = id;
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public Flower(Flower flower){
        this.id = flower.id;
        this.title = flower.title;
        this.price = flower.price;
        this.count = flower.count;
    }

    public Flower(FlowerEntity entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.count = entity.getCount();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public FlowerEntity toEntity(){
        FlowerEntity entity = new FlowerEntity();
        entity.setId(id);
        entity.setTitle(title);
        entity.setPrice(price);
        entity.setCount(count);

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return flower.price.compareTo(price) == 0 &&
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
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
