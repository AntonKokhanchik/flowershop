package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    public void create(Flower flower) {
        validate(flower);

        em.persist(flower.toEntity());
    }

    @Override
    @Transactional
    public void update(Flower flower) {
        em.merge(flower.toEntity());
    }

    @Override
    @Transactional
    public void updateWithOrder(Cart cart){
        for (Map.Entry<Flower, Integer> item :cart.items.entrySet()) {
            Flower f = item.getKey();
            f.setCount(f.getCount() - item.getValue());
            em.merge(f.toEntity());
        }
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
    public void validate(Flower flower) {
    }

    @Override
    public Flower parse(HttpServletRequest req){
        String sid = req.getParameter("id");
        Long id = sid == null ? null : Long.parseLong(sid);

        return new Flower(
                id,
                req.getParameter("title"),
                new BigDecimal(req.getParameter("price")),
                Integer.parseInt(req.getParameter("count"))
                );
    }
}
