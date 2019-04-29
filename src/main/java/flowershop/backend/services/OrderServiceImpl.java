package flowershop.backend.services;

import flowershop.backend.dao.FlowerDAO;
import flowershop.backend.dao.OrderDAO;
import flowershop.backend.dao.UserDAO;
import flowershop.backend.dto.Flower;
import flowershop.backend.dto.OrderFlowerData;
import flowershop.backend.entity.FlowerEntity;
import flowershop.backend.entity.OrderFlowerDataEntity;
import flowershop.backend.enums.OrderStatus;
import flowershop.backend.dto.Order;
import flowershop.backend.dto.User;
import flowershop.backend.entity.OrderEntity;
import flowershop.backend.entity.UserEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private FlowerDAO flowerDAO;
    @Autowired
    private UserDAO userDAO;

    @PostConstruct
    public void initialize() {
        LOG.info("Order service on");
    }

    @Override
    @Transactional
    public void create(Cart cart, User user) throws FlowerValidationException {
        // decrease flowers count
        // TODO: здесь надо продумать изменение количества цветов между созданием корзины и созданием заказа
        for (Map.Entry<Long, Integer> item : cart.getItems().entrySet()) {
            FlowerEntity flower = flowerDAO.find(item.getKey());
            flower.setCount(flower.getCount() - item.getValue());

            if (flower.getCount() < 0)
                throw new FlowerValidationException(FlowerValidationException.NOT_ENOUGH_FLOWERS);
            flowerDAO.update(flower);
        }

        DetailedCart detailedCart = generateDetailedCart(cart);

        // create order
        OrderEntity order = new Order(detailedCart.getResult(user.getDiscount()),
                LocalDateTime.now(), null, OrderStatus.CREATED, user.getDiscount()).toEntity();

        // add flower data
        for (OrderFlowerData f : detailedCart.getItems()) {
            OrderFlowerDataEntity fe = f.toEntity();
            order.addFlowerData(fe);
        }

        // set owner
        UserEntity userEntity = userDAO.find(user.getLogin());
        order.setOwner(userEntity);

        // save
        orderDAO.create(order);
    }

    @Override
    public void pay(Order order) {
        OrderEntity orderEntity = orderDAO.find(order.getId());
        UserEntity owner = orderEntity.getOwner();

        owner.setBalance(owner.getBalance().subtract(orderEntity.getFullPrice()));
        userDAO.update(owner);

        orderEntity.setStatus(OrderStatus.PAID);
        orderDAO.update(orderEntity);

        LOG.info("Order {} paid", order);
    }

    @Override
    public void close(Order order) {
        OrderEntity orderEntity = orderDAO.find(order.getId());

        orderEntity.setStatus(OrderStatus.CLOSED);
        orderEntity.setDateClosing(LocalDateTime.now());
        orderDAO.update(orderEntity);
    }

    @Override
    public void delete(Order order) {
        orderDAO.delete(order.toEntity());
    }

    @Override
    public Order find(Long id) {
        return new Order(orderDAO.find(id));
    }

    @Override
    public List<Order> getAll() {
        List<OrderEntity> entities = orderDAO.getAll();
        List<Order> orders = new LinkedList<>();

        for (OrderEntity e : entities)
            orders.add(new Order(e));

        return orders;
    }

    @Transactional
    @Override
    public List<Order> getByUser(User user) {
        UserEntity userEntity = userDAO.find(user.getLogin());
        List<OrderEntity> entities = userEntity.getOrders();

        List<Order> orders = new LinkedList<>();

        for (OrderEntity e : entities)
            orders.add(new Order(e));

        return orders;
    }

    @Transactional
    @Override
    public List<OrderFlowerData> getFlowersData(Order order) {
        OrderEntity entity = orderDAO.find(order.getId());
        List<OrderFlowerDataEntity> entities = entity.getFlowersData();

        List<OrderFlowerData> flowersData = new LinkedList<>();

        for (OrderFlowerDataEntity e : entities)
            flowersData.add(new OrderFlowerData(e));

        return flowersData;
    }

    @Override
    public DetailedCart generateDetailedCart(Cart cart) {
        Set<OrderFlowerData> items = new HashSet<>();

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet())
            items.add(new Flower(flowerDAO.find(entry.getKey())).toOrderData(entry.getValue()));

        return new DetailedCart(items);
    }
}
