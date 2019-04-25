package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import java.util.List;

public interface FlowerService {
    void create(Flower flower);
    void update(Flower flower);
    void delete(Flower flower);
    Flower find(Long id);
    List<Flower> getAll();
}