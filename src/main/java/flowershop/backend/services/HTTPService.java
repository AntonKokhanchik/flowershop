package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.dto.User;
import flowershop.backend.exception.FlowerValidationException;

import javax.servlet.http.HttpServletRequest;

/**
 * Provides methods for handling http servlet requests
 */
public interface HTTPService {
    /**
     * Create flower from http request parameters
     * @param req http request
     * @return flower dto
     * @throws FlowerValidationException if parameters validation failed
     * @see Flower
     */
    Flower parseFlower(HttpServletRequest req) throws FlowerValidationException;

    /**
     * Create user from http request parameters
     * @param req http request
     * @return user dto
     * @see User
     */
    User parseUser(HttpServletRequest req);

    /**
     * Check session user for admin privileges
     * @param req http request
     * @return true if session user is admin
     */
    boolean isAccessGranted(HttpServletRequest req);

    /**
     * Get id parameter from path similar to /controller/action/id
     * @param req http request
     * @return Long id path parameter
     */
    Long getIdParam(HttpServletRequest req);
}