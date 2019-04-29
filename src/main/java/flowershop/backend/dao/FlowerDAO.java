package flowershop.backend.dao;

import flowershop.backend.entity.FlowerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class FlowerDAO {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerDAO.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void create(FlowerEntity flower) {
        em.persist(flower);
        LOG.info("New flower created: {}", flower);
    }

    @Transactional
    public void update(FlowerEntity flower) {
        em.merge(flower);

        LOG.info("Flower updated: {}", flower);
    }

    @Transactional
    public void delete(FlowerEntity flower) {
        em.remove(em.find(FlowerEntity.class, flower.getId()));

        LOG.info("Flower deleted: {}", flower);
    }

    public FlowerEntity find(Long id) {
        return em.find(FlowerEntity.class, id);
    }

    public List<FlowerEntity> getAll() {
        return em.createNamedQuery("getAllFlowers", FlowerEntity.class).getResultList();
    }
}
