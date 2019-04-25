package flowershop.backend.services;

import flowershop.backend.dto.Flower;

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
    void create(Flower flower);

    /**
     * Update corresponding flower object in database
     *
     * @param flower flower to update
     */
    void update(Flower flower);

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
}