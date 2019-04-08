package flowershop.backend.services;

import flowershop.backend.dto.User;
import flowershop.backend.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init(){
        System.out.println("User service on");

        create(new User("admin", "admin123", "admin", "unknown", "+0(000)000-00-00"));
        create(new User("login1", "pass1", "Alex1", "anywhere1", "+0(000)000-00-01"));
        create(new User("login2", "pass2", "Alex2", "anywhere2", "+0(000)000-00-02"));
        create(new User("login3", "pass3", "Alex3", "anywhere3", "+0(000)000-00-03"));
        create(new User("login4", "pass4", "Alex4", "anywhere4", "+0(000)000-00-04"));
        create(new User("login5", "pass5", "Alex5", "anywhere5", "+0(000)000-00-05"));
        create(new User("login6", "pass6", "Alex6", "anywhere6", "+0(000)000-00-06"));
        create(new User("login7", "pass7", "Alex7", "anywhere7", "+0(000)000-00-07"));
    }

    @Override
    public void create(User user) {
        validate(user);

        if (em.find(UserService.class, user.getLogin()) != null)
            throw new IllegalArgumentException("login is already in use");

        user.setBalance(new BigDecimal(2000));
        user.setDiscount(0);

        em.getTransaction().begin();
        em.persist(user.toEntity());
        em.getTransaction().commit();
    }

    @Override
    public void update(User user) {
        em.getTransaction().begin();
        em.merge(user.toEntity());
        em.getTransaction().commit();
    }

    @Override
    public void delete(User user) {
        em.getTransaction().begin();
        em.merge(user.toEntity());
        em.getTransaction().commit();
    }

    @Override
    public User find(String login) {
        return new User(em.find(UserEntity.class, login));
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> entities = em.createNamedQuery("getAll", UserEntity.class).getResultList();
        List<User> users = new LinkedList<>();

        for(UserEntity e : entities)
            users.add(new User(e));

        return users;
    }

    @Override
    public void validate(User user) {
    }

    @Override
    public User getAdmin(){
        return new User(em.find(UserEntity.class, "admin"));
    }

    @Override
    public void register(HttpServletRequest req){
        User newUser = new User(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("fullname"),
                req.getParameter("address"),
                req.getParameter("phone")
        );

        create(newUser);
    }

    @Override
    public User verify(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = find(login);

        if(user == null)
            throw new IllegalArgumentException("Wrong login");

        if (password.equals(user.getPassword()))
            return user;
        throw new IllegalArgumentException("Wrong password");
    }
}
