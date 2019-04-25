package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.entity.FlowerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.LinkedList;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void initialize() {
        LOG.info("Flower service on");
    }

    @Override
    @Transactional
    public void create(Flower flower) {
        em.persist(flower.toEntity());
        LOG.info("flower {} created", flower);
    }

    @Override
    @Transactional
    public void update(Flower flower) {
        em.merge(flower.toEntity());

        LOG.info("flower {} updated", flower);
    }

    @Override
    @Transactional
    public void delete(Flower flower) {
        em.remove(em.find(FlowerEntity.class, flower.getId()));

        LOG.info("flower {} deleted", flower);
    }

    @Override
    public Flower find(Long id) {
        return new Flower(em.find(FlowerEntity.class, id));
    }

    @Override
    public List<Flower> getAll() {
        List<FlowerEntity> entities = em.createNamedQuery("getAllFlowers", FlowerEntity.class).getResultList();
        List<Flower> flowers = new LinkedList<>();

        for (FlowerEntity e : entities)
            flowers.add(new Flower(e));

        return flowers;
    }
}
