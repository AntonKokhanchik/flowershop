package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import java.util.List;

public interface FlowerService {
    boolean create(Flower flower);
    boolean update(Flower flower);
    boolean delete(Flower flower);
    Flower find(int id);
    List<Flower> getAll();
    boolean validate(Flower flower);
}
