package flowershop.backend.services;

import flowershop.backend.dto.Order;

import java.util.List;

public interface OrderService {
    boolean create(Order order);
    boolean update(Order order);
    boolean delete(Order order);
    Order find(int id);
    List<Order> getAll();
    boolean validate(Order order);
}
