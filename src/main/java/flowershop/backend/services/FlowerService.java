package flowershop.backend.services;

import flowershop.backend.entity.Flower;

import java.util.List;

public class FlowerService implements EntityService <Flower> {
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
