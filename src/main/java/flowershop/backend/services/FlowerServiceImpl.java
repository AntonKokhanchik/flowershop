package flowershop.backend.services;

import flowershop.backend.entity.Flower;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class FlowerServiceImpl implements FlowerService {
    @PostConstruct
    public void initialize()
    {
        System.out.println("Flower service on");
    }

    @Override
    public boolean create(Flower entity) {
        return false;
    }

    @Override
    public boolean update(Flower entity) {
        return false;
    }

    @Override
    public boolean delete(Flower entity) {
        return false;
    }

    @Override
    public Flower find(int id) {
        return null;
    }

    @Override
    public List<Flower> getAll() {
        return null;
    }

    @Override
    public boolean validate(Flower entity) {
        return false;
    }
}
