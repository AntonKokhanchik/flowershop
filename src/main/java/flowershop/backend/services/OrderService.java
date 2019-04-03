package flowershop.backend.services;

import flowershop.backend.entity.Order;

import java.util.List;

public class OrderService implements EntityService<Order>{
    @Override
    public boolean create(Order entity) {
        return false;
    }

    @Override
    public boolean update(Order entity) {
        return false;
    }

    @Override
    public boolean delete(Order entity) {
        return false;
    }

    @Override
    public Order find(int id) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public boolean validate(Order entity) {
        return false;
    }
}
