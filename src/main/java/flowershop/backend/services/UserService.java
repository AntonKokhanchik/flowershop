package flowershop.backend.services;

import flowershop.backend.dto.User;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    void create(User user, EntityManager em);
    void update(User user);
    void delete(User user);
    User find(String login);
    List<User> getAll();
    void validate(User user);
    User getAdmin();
    void register(HttpServletRequest req);
    User verify(HttpServletRequest req);
}
