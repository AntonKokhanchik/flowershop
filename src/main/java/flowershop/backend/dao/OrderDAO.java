package flowershop.backend.dao;

import flowershop.backend.entity.OrderEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Repository
public class OrderDAO {
    private static final Logger LOG = LoggerFactory.getLogger(OrderDAO.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void create(OrderEntity order) {
        em.persist(order);
        LOG.info("New order created: {}", order);
    }

    @Transactional
    public void update(OrderEntity order) {
        em.merge(order);
        LOG.info("Order updated: {}", order);
    }

    @Transactional
    public void delete(OrderEntity order) {
        em.remove(em.find(OrderEntity.class, order.getId()));
        LOG.info("Order deleted: {}", order);
    }

    public OrderEntity find(Long id) {
        return em.find(OrderEntity.class, id);
    }

    public List<OrderEntity> getAll() {
        return em.createNamedQuery("getAllOrders", OrderEntity.class).getResultList();
    }
}
