package flowershop.backend.services;

import flowershop.backend.dto.Flower;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FlowerService {
    void create(Flower flower);
    void update(Flower flower);
    void updateWithOrder(Cart cart);
    void delete(Flower flower);
    Flower find(Long id);
    List<Flower> getAll();
    void validate(Flower flower);
    Flower parse(HttpServletRequest req);
}
