package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void initialize()
    {
        System.out.println("Flower service on");
    }

    @Override
    @Transactional
    public void create(Flower flower) throws FlowerValidationException {
        validate(flower);
        em.persist(flower.toEntity());
    }

    @Override
    @Transactional
    public void update(Flower flower) throws FlowerValidationException {
        validate(flower);
        em.merge(flower.toEntity());
    }

    @Override
    @Transactional
    public void delete(Flower flower) {
        em.remove(em.merge(flower.toEntity()));
    }

    @Override
    public Flower find(Long id) {
        return new Flower(em.find(FlowerEntity.class, id));
    }

    @Override
    public List<Flower> getAll() {
        List<FlowerEntity> entities = em.createNamedQuery("getAllFlowers", FlowerEntity.class).getResultList();
        List<Flower> flowers = new LinkedList<>();

        for(FlowerEntity e : entities)
            flowers.add(new Flower(e));

        return flowers;
    }

    @Override
    public void validate(Flower flower) throws FlowerValidationException {
        if (flower.getTitle() == null)
            flower.setTitle("Unnamed");

        if (flower.getPrice() == null)
            flower.setPrice(new BigDecimal(0));

        if (flower.getCount() == null)
            flower.setCount(0);

        if (flower.getCount() < 0)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_COUNT);

        if (flower.getPrice().compareTo(BigDecimal.ZERO) == -1)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_PRICE);
    }

    @Override
    public Flower parse(HttpServletRequest req) throws FlowerValidationException {
        String parameter = req.getParameter("id");
        Long id = (parameter == null || parameter == "") ? null : Long.parseLong(parameter);

        BigDecimal price;
        try {
            price = new BigDecimal(req.getParameter("price"));
        } catch (NumberFormatException e){
            throw new FlowerValidationException(FlowerValidationException.WRONG_PRICE);
        }

        parameter = req.getParameter("count");
        if (parameter == "")
            parameter = "0";

        Integer count;
        try {
            count = Integer.parseInt(req.getParameter("count"));
        } catch (NumberFormatException e){
            throw new FlowerValidationException(FlowerValidationException.WRONG_COUNT);
        }

        return new Flower(id, req.getParameter("title"), price, count);
    }
}
