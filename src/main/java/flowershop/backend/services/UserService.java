package flowershop.backend.services;

import flowershop.frontend.dto.User;
import flowershop.backend.exception.UserValidationException;

import java.util.List;

/**
 * Provides access to User objects
 * @see User
 */
public interface UserService {
    /**
     * Create new user entity and put it to database
     *
     * @param user user to add
     */
    void create(User user);

    /**
     * Update corresponding user object in database
     *
     * @param user user to update
     */
    void update(User user);

    /**
     * Delete corresponding user object from database
     *
     * @param login user login to delete
     */
    void delete(String login);

    /**
     * Find user in database with given login
     *
     * @param login user login
     * @return User dto
     */
    User find(String login);

    /**
     * Get all users from database
     *
     * @return users list
     */
    List<User> getAll();

    /**
     * Check user login and password to authenticate him in app
     *
     * @param user User dto (can have only login and password fields filled)
     * @return User from database with given corresponding login
     * @throws UserValidationException if user login is not exists or password is incorrect
     */
    User verify(User user) throws UserValidationException;

    /**
     * Marshal given user to XML in location "userXML/"
     *
     * @param user user to marshal
     * @see XMLConverter
     */
    void createXML(User user);
}
