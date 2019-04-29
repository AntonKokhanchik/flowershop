package flowershop.backend.dao;

import flowershop.backend.entity.UserEntity;
import flowershop.backend.services.FlowerServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAO {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void create(UserEntity user) {
        em.persist(user);
        LOG.info("New user registered: {}", user);
    }

    @Transactional
    public void update(UserEntity user) {
        em.merge(user);
        LOG.info("User updated: {}", user);
    }

    @Transactional
    public void delete(UserEntity user) {
        em.remove(em.find(UserEntity.class, user.getLogin()));
        LOG.info("User deleted: {}", user);
    }

    public UserEntity find(String login) {
        return em.find(UserEntity.class, login);
    }

    public List<UserEntity> getAll() {
        return em.createNamedQuery("getAllUsers", UserEntity.class).getResultList();
    }
}
