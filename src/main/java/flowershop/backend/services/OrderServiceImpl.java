package flowershop.backend.services;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void initialize()
    {
        LOG.info("Order service on");
    }

    @Override
    @Transactional
    public void create(Order order) {
        validate(order);

        order.setDateCreation(LocalDateTime.now());
        order.setDateClosing(null);

        em.persist(order);

        LOG.info("new order {} created", order);
    }

    @Override
    @Transactional
    public void create(Cart cart, User user) throws FlowerValidationException {
        // decrease flowers count
        // TODO: здесь надо продумать изменение количества цветов между созданием корзины и созданием заказа
        for (Map.Entry<Long, Integer> item : cart.items.entrySet()) {
            FlowerEntity flower = em.find(FlowerEntity.class, item.getKey());
            flower.setCount(flower.getCount() - item.getValue());

            if (flower.getCount() < 0)
                throw new FlowerValidationException(FlowerValidationException.NOT_ENOUGH_FLOWERS);
        }

        DetailedCart detailedCart = generateDetailedCart(cart);

        // create order
        OrderEntity order = new Order(detailedCart.getResult(user.getDiscount()),
                LocalDateTime.now(), null, OrderStatus.CREATED, user.getDiscount()).toEntity();

        // add flower data
        for (OrderFlowerData f : detailedCart.items) {
            OrderFlowerDataEntity fe = f.toEntity();
            em.persist(fe);
            order.addFlowerData(fe);
        }

        // set owner
        UserEntity userEntity = em.find(UserEntity.class, user.getLogin());
        order.setOwner(userEntity);

        // save
        em.persist(order);

        LOG.info("new order {} created", order);
    }

    @Override
    @Transactional
    public void pay(Order order){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getId());
        UserEntity owner = orderEntity.getOwner();

        owner.setBalance(owner.getBalance().subtract(orderEntity.getFullPrice()));

        orderEntity.setStatus(OrderStatus.PAID);

        LOG.info("Order {} paid", order);
    }

    @Override
    @Transactional
    public void close(Order order){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getId());

        orderEntity.setStatus(OrderStatus.CLOSED);
        orderEntity.setDateClosing(LocalDateTime.now());

        LOG.info("new order {} closed", order);
    }

    @Override
    @Transactional
    public void delete(Order order) {
        em.remove(em.find(OrderEntity.class, order.getId()));
        LOG.info("new order {} deleted", order);
    }

    @Override
    public Order find(Long id) {
        return new Order(em.find(OrderEntity.class, id));
    }

    @Override
    public List<Order> getAll() {
        List<OrderEntity> entities = em.createNamedQuery("getAllOrders", OrderEntity.class).getResultList();
        List<Order> orders = new LinkedList<>();

        for(OrderEntity e : entities)
            orders.add(new Order(e));

        return orders;
    }

    @Transactional
    @Override
    public List<Order> getByUser(User user){
        UserEntity entity = em.find(UserEntity.class, user.getLogin());
        List<OrderEntity> entities = entity.getOrders();

        List<Order> orders = new LinkedList<>();

        for(OrderEntity e : entities)
            orders.add(new Order(e));

        return orders;
    }

    @Transactional
    @Override
    public List<OrderFlowerData> getFlowersData(Order order){
        OrderEntity entity = em.find(OrderEntity.class, order.getId());
        List<OrderFlowerDataEntity> entities = entity.getFlowersData();

        List<OrderFlowerData> flowersData = new LinkedList<>();

        for(OrderFlowerDataEntity e : entities)
            flowersData.add(new OrderFlowerData(e));

        return flowersData;
    }

    @Override
    public void validate(Order order) {

    }

    @Override
    public DetailedCart generateDetailedCart(Cart cart){
        Set<OrderFlowerData> items = new HashSet<>();

        for (Map.Entry<Long, Integer> entry : cart.items.entrySet())
            items.add(new Flower(em.find(FlowerEntity.class, entry.getKey())).toOrderData(entry.getValue()));

        return new DetailedCart(items);
    }
}
