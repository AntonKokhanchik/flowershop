package flowershop.backend.services;

import flowershop.backend.dto.User;
import flowershop.backend.entity.UserEntity;
import flowershop.backend.exception.UserValidationException;
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
public class UserServiceImpl implements UserService{
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void init(){
        System.out.println("User service on");
    }

    @Override
    @Transactional
    public void create(User user) throws UserValidationException {
        validate(user);

        if (em.find(UserEntity.class, user.getLogin()) != null)
            throw new UserValidationException(UserValidationException.LOGIN_IS_TAKEN);

        user.setBalance(new BigDecimal(2000));
        user.setDiscount(0);

        em.persist(user.toEntity());
    }

    @Override
    @Transactional
    public void update(User user) {
        em.merge(user.toEntity());
    }

    @Override
    @Transactional
    public void delete(User user) {
        em.remove(em.merge(user.toEntity()));
    }

    @Override
    public User find(String login) {
        return new User(em.find(UserEntity.class, login));
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> entities = em.createNamedQuery("getAllUsers", UserEntity.class).getResultList();
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
    public User parse(HttpServletRequest req){
        return new User(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("fullname"),
                req.getParameter("address"),
                req.getParameter("phone")
        );
    }

    @Override
    public User verify(HttpServletRequest req) throws UserValidationException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        UserEntity user = em.find(UserEntity.class, login);

        if(user == null)
            throw new UserValidationException(UserValidationException.WRONG_LOGIN);

        if (password.equals(user.getPassword()))
            return new User(user);
        throw new UserValidationException(UserValidationException.WRONG_PASSWORD);
    }
}
