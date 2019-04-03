package flowershop.backend.services;

import flowershop.backend.entity.Entity;

import java.util.List;

public interface EntityService <E extends Entity> {
    boolean create(E entity);
    boolean update(E entity);
    boolean delete(E entity);
    E find(int id);
    List<E> getAll();
    boolean validate(E entity);
}
