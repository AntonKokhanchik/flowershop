package flowershop.backend.services;

import flowershop.backend.dto.Order;
import flowershop.backend.dto.User;
import flowershop.backend.exception.FlowerValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrderService {
    void create(Order order);
    void create(Cart cart, User user) throws FlowerValidationException;
    void update(Order order);
    void delete(Order order);
    Order find(Long id);
    List<Order> getAll();
    List<Order> getByUser(User user);
    void validate(Order order);
    Order parse(HttpServletRequest req);
}
