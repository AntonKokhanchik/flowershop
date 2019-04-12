package flowershop.backend.services;

import flowershop.backend.dto.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {
    void create(User user);
    void update(User user);
    void delete(User user);
    User find(String login);
    List<User> getAll();
    void validate(User user);
    User getAdmin();
    User parse(HttpServletRequest req);
    User verify(HttpServletRequest req);
}
