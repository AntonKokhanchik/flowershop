package flowershop.backend.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="FLOWERS")
@NamedQuery(name="getAllFlowers", query="Select c from FlowerEntity c")
public class FlowerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private BigDecimal price;
    private Integer count;


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


    @Override
    public String toString() {
        return "Flower{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", count=" + count +
                '}';
    }
}
