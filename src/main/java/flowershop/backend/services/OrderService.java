package flowershop.backend.services;

import flowershop.backend.dto.Order;
import flowershop.backend.dto.OrderFlowerData;
import flowershop.backend.dto.User;
import flowershop.backend.exception.FlowerValidationException;

import java.util.List;

/**
 * Provides access to Order objects in database, some work with Cart and DetailedCart
 * @see Order
 * @see Cart
 * @see DetailedCart
 */
public interface OrderService {
    /**
     * Create new order object and put it to database
     *
     * @param cart cart with data about new order
     * @param user session user - order owner
     * @throws FlowerValidationException if flowers count in cart more then count in shop
     * @see Cart
     */
    void create(Cart cart, User user) throws FlowerValidationException;

    /**
     * Change order status in database to PAID
     *
     * @param order order to update
     * @see flowershop.backend.enums.OrderStatus
     */
    void pay(Order order);

    /**
     * Change order status in database to CLOSED
     *
     * @param order order to update
     * @see flowershop.backend.enums.OrderStatus
     */
    void close(Order order);

    /**
     * Delete corresponding order object from database
     *
     * @param order order to delete
     */
    void delete(Order order);

    /**
     * Find order in database with given id
     *
     * @param id order id
     * @return Order dto
     */
    Order find(Long id);

    /**
     * Get all orders from database
     *
     * @return orders list
     */
    List<Order> getAll();

    /**
     * Get all orders of given user from database
     *
     * @param user orders owner
     * @return orders list
     */
    List<Order> getByUser(User user);

    /**
     * Get items list (bought flowers) of given order
     *
     * @param order order to get details
     * @return items list (bought flowers)
     * @see OrderFlowerData
     */
    List<OrderFlowerData> getFlowersData(Order order);

    /**
     * Generates detailed cart from regular cart to represent data
     *
     * @param cart Cart to be detailed
     * @return DetailedCart
     * @see Cart
     * @see DetailedCart
     */
    DetailedCart generateDetailedCart(Cart cart);
}
