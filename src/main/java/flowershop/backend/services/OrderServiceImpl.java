package flowershop.backend.services;

import flowershop.backend.entity.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @PostConstruct
    public void initialize()
    {
        System.out.println("Order service on");
    }

    @Override
    public boolean create(Order order) {
        return false;
    }

    @Override
    public boolean update(Order order) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
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
    public boolean validate(Order order) {
        return false;
    }
}
