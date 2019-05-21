package flowershop.backend.services;

import flowershop.backend.repository.FlowerDAO;
import flowershop.backend.repository.OrderRepository;
import flowershop.backend.repository.UserRepository;
import flowershop.frontend.dto.Flower;
import flowershop.frontend.dto.OrderFlowerData;
import flowershop.backend.entity.OrderFlowerDataEntity;
import flowershop.backend.enums.OrderStatus;
import flowershop.frontend.dto.Order;
import flowershop.frontend.dto.User;
import flowershop.backend.entity.OrderEntity;
import flowershop.backend.entity.UserEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.dozer.Mapper;
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
    private OrderRepository orderRepository;
    @Autowired
    private FlowerDAO flowerRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Mapper mapper;

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
            Optional<Boolean> isExceptionNeeded = flowerRepository.findById(item.getKey()).map(flowerEntity -> {
                flowerEntity.setCount(flowerEntity.getCount() - item.getValue());

                if (flowerEntity.getCount() < 0)
                    return true;

                flowerRepository.save(flowerEntity);
                return false;
            });

            if (isExceptionNeeded.isPresent() && isExceptionNeeded.get())
                throw new FlowerValidationException(FlowerValidationException.NOT_ENOUGH_FLOWERS);
        }

        DetailedCart detailedCart = generateDetailedCart(cart);

        // create order
        OrderEntity orderEntity = mapper.map(
                new Order(detailedCart.getResult(user.getDiscount()), user.getDiscount()),
                OrderEntity.class);

        // add flower data
        for (OrderFlowerData f : detailedCart.getItems()) {
            OrderFlowerDataEntity fe = mapper.map(f, OrderFlowerDataEntity.class);
            orderEntity.addFlowerData(fe);
        }

        // set owner
        userRepository.findById(user.getLogin()).ifPresent(userEntity -> {
            orderEntity.setOwner(userEntity);

            // save
            OrderEntity savedOrder = orderRepository.save(orderEntity);
            LOG.info("Order created: {}", savedOrder);
        });
    }

    @Override
    public void pay(Order order) {
        orderRepository.findById(order.getId()).ifPresent(orderEntity -> {
            UserEntity owner = orderEntity.getOwner();

            owner.setBalance(owner.getBalance().subtract(orderEntity.getFullPrice()));
            userRepository.save(owner);

            orderEntity.setStatus(OrderStatus.PAID);
            orderEntity = orderRepository.save(orderEntity);
            LOG.info("Order paid: {}", orderEntity);
        });
    }

    @Override
    public void close(Order order) {
        orderRepository.findById(order.getId()).ifPresent(orderEntity -> {
            orderEntity.setStatus(OrderStatus.CLOSED);
            orderEntity.setDateClosing(LocalDateTime.now());

            orderEntity = orderRepository.save(orderEntity);
            LOG.info("Order closed {}", orderEntity);
        });
    }

    @Override
    public void delete(Long id) {
        orderRepository.deleteById(id);
        LOG.info("Order with id {} deleted", id);
    }

    @Override
    public Order find(Long id) {
        return orderRepository.findById(id).map(entity -> mapper.map(entity, Order.class)).orElse(null);
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new LinkedList<>();

        for (OrderEntity e : orderRepository.findAll())
            orders.add(mapper.map(e, Order.class));

        return orders;
    }

    @Transactional
    @Override
    public List<Order> getByUser(User user) {
        List<Order> orders = new LinkedList<>();

        userRepository.findById(user.getLogin()).ifPresent(orderEntity -> {
            List<OrderEntity> entities = orderEntity.getOrders();

            for (OrderEntity e : entities)
                orders.add(mapper.map(e, Order.class));
        });

        return orders;
    }

    @Transactional
    @Override
    public List<OrderFlowerData> getFlowersData(Order order) {
        List<OrderFlowerData> flowersData = new LinkedList<>();

        orderRepository.findById(order.getId()).ifPresent(orderEntity -> {
            List<OrderFlowerDataEntity> entities = orderEntity.getFlowersData();

            for (OrderFlowerDataEntity e : entities)
                flowersData.add(mapper.map(e, OrderFlowerData.class));
        });

        return flowersData;
    }

    @Override
    public DetailedCart generateDetailedCart(Cart cart) {
        Set<OrderFlowerData> items = new HashSet<>();

        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet())
            flowerRepository.findById(entry.getKey()).ifPresent(entity ->
                    items.add(mapper.map(entity, Flower.class).toOrderData(entry.getValue()))
            );
        return new DetailedCart(items);
    }
}
