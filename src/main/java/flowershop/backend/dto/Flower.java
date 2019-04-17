package flowershop.backend.dto;

import flowershop.backend.entity.FlowerEntity;

import java.math.BigDecimal;

public class Flower {
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer count;

    public Flower(Long id, String title, BigDecimal price, Integer count){
        this.id = id;
        this.title = title;
        this.price = price;
        this.count = count;
    }

    public Flower(FlowerEntity entity){
        id = entity.getId();
        title = entity.getTitle();
        price = entity.getPrice();
        count = entity.getCount();
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
    public String toString() {
        return "Flower{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
