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
     * @param id flower id to delete
     */
    void delete(Long id);

    /**
     * Find flower in database with given id
     *
     * @param id flower id
     * @return Flower dto
     */
    Flower find(Long id);

    /**
     * Get all flowers from database
     *
     * @return flowers list
     */
    List<Flower> getAll();

    /**
     * Get all flowers with searched name substring, price between min and max, sorted
     * @param name searched substring
     * @param priceMin min price
     * @param priceMax max price
     * @param sort sort field
     * @param order order (direction) sorting (asc or desc)
     * @return founded flowers result list
     */
    List<Flower> getAll(String name, BigDecimal priceMin, BigDecimal priceMax, String sort, String order);
}