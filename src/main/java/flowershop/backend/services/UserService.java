package flowershop.backend.services;

import flowershop.backend.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    boolean create(User user);
    boolean update(User user);
    boolean delete(User user);
    User find(int id);
    List<User> getAll();
    boolean validate(User user);
    User getAdmin();
    void register(HttpServletRequest req);
    User verify(HttpServletRequest req);
    User findByLogin(String login);
}
