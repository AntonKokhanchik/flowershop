package flowershop.backend.services;

import flowershop.frontend.dto.Flower;
import flowershop.backend.exception.FlowerValidationException;

import java.math.BigDecimal;
import java.util.List;

/**
 * Provides access to Flower objects in database
 * @see Flower
 */
public interface FlowerService {
    /**
     * Create new flower object and put it to database
     *
     * @param flower flower to add
     */
    void create(Flower flower) throws FlowerValidationException;

    /**
     * Update corresponding flower object in database
     *
     * @param flower flower to update
     */
    void update(Flower flower) throws FlowerValidationException;

    /**
     * Delete corresponding flower object from database
     *
     * @param flower flower to delete
     */
    void delete(Flower flower);

    /**
     * Find flower in database with given id
     *
     * @param id flower id
     * @return  Flower dto
     */
    Flower find(Long id);

    /**
     * Get all flowers from database
     *
     * @return flowers list
     */
    List<Flower> getAll();

    List<Flower> getAll(String sort, String order, String name, BigDecimal price_min, BigDecimal price_max);
}