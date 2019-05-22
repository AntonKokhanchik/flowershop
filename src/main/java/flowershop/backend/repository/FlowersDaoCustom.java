package flowershop.backend.repository;

import org.apache.commons.lang3.StringUtils;
import org.jooq.*;
import org.jooq.generated.tables.daos.FlowersDao;
import org.jooq.generated.tables.pojos.Flowers;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

import static org.jooq.generated.tables.Flowers.FLOWERS;

public class FlowersDaoCustom extends FlowersDao {
    public FlowersDaoCustom(Configuration configuration) {
        super(configuration);
    }

    @Autowired
    DSLContext dslContext;

    public List<Flowers> findAll(String name, BigDecimal priceMin, BigDecimal priceMax, String sort, String order) {
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

        return  (f == null) ? f0.orderBy(o).fetchInto(Flowers.class) : f.orderBy(o).fetchInto(Flowers.class);
    }
}
