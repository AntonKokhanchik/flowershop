package flowershop.backend.services;

import flowershop.backend.entity.User;

import java.util.List;

public class UserService implements EntityService <User>{

    @Override
    public boolean create(User entity) {
        return false;
    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }

    @Override
    public User find(int id) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public boolean validate(User entity) {
        return false;
    }
}
