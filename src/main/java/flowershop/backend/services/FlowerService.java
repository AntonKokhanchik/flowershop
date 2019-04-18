package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.exception.FlowerValidationException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface FlowerService {
    void create(Flower flower) throws FlowerValidationException;
    void update(Flower flower) throws FlowerValidationException;
    void delete(Flower flower);
    Flower find(Long id);
    List<Flower> getAll();
    void validate(Flower flower) throws FlowerValidationException;
    Flower parse(HttpServletRequest req) throws FlowerValidationException;
}
