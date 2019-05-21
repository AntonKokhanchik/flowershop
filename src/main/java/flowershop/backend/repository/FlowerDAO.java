package flowershop.backend.repository;

import flowershop.backend.entity.FlowerEntity;
import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.jooq.generated.tables.Flowers.FLOWERS;

@Repository
public class FlowerDAO {
    @Autowired
    DSLContext dslContext;

    @Transactional
    public void save(FlowerEntity flower){
        if(flower.getId() == null)
            dslContext.newRecord(FLOWERS, flower).store();
        else
            dslContext.executeUpdate(dslContext.newRecord(FLOWERS, flower));
    }


    public Optional<FlowerEntity> findById(Long id) {
        return Optional.ofNullable(dslContext.selectFrom(FLOWERS).where(FLOWERS.ID.eq(id)).fetchAny().into(FlowerEntity.class));
    }

    public List<FlowerEntity> findAll() {
        return dslContext.selectFrom(FLOWERS).fetchInto(FlowerEntity.class);
    }

    public void deleteById(Long id) {
        dslContext.deleteFrom(FLOWERS).where(FLOWERS.ID.eq(id)).execute();
    }

    public List<FlowerEntity> findAll(String name, BigDecimal priceMin, BigDecimal priceMax, String sort, String order) {
        SelectWhereStep f0 = dslContext.selectFrom(FLOWERS);
        SelectConditionStep f = null;

        if (!StringUtils.isEmpty(name))
            f = f0.where(FLOWERS.NAME.containsIgnoreCase(name));

        if(priceMin != null) {
            Condition tmp = FLOWERS.PRICE.greaterOrEqual(priceMin);
            f = (f == null) ? f0.where(tmp) : f.and(tmp);
        }

        if(priceMax != null) {
            Condition tmp = FLOWERS.PRICE.lessOrEqual(priceMax);
            f = (f == null) ? f0.where(tmp) : f.and(tmp);
        }

        TableField s;
        if(sort == null)
            sort = "name";
        switch(sort) {
            case "price":
                s = FLOWERS.PRICE;
                break;
            case "count":
                s = FLOWERS.FLOWER_COUNT;
                break;
            case "name":
            default:
                s = FLOWERS.NAME;
                break;
        }

        SortField o;
        if (order != null && order.equals("desc"))
            o = s.desc();
        else
            o = s.asc();

        return  (f == null) ? f0.orderBy(o).fetchInto(FlowerEntity.class) : f.orderBy(o).fetchInto(FlowerEntity.class);
    }
}
