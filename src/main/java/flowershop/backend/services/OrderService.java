package flowershop.backend.services;

import flowershop.backend.dto.Order;
import flowershop.backend.dto.OrderFlowerData;
import flowershop.backend.dto.User;
import flowershop.backend.exception.FlowerValidationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderService {
    void create(Order order);
    void create(Cart cart, User user) throws FlowerValidationException;
    void pay(Order order);
    void close(Order order);
    void delete(Order order);
    Order find(Long id);
    List<Order> getAll();
    List<Order> getByUser(User user);

    @Transactional
    List<OrderFlowerData> getFlowersData(Order order);

    void validate(Order order);

    DetailedCart generateDetailedCart(Cart cart);
}
