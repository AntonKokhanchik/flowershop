package flowershop.backend.services;

import flowershop.backend.enums.OrderStatus;
import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.repository.FlowersDaoCustom;
import flowershop.frontend.dto.Flower;
import flowershop.frontend.dto.Order;
import flowershop.frontend.dto.OrderFlowerData;
import flowershop.frontend.dto.User;
import org.dozer.Mapper;
import org.jooq.generated.tables.daos.OrderFlowersDao;
import org.jooq.generated.tables.daos.OrdersDao;
import org.jooq.generated.tables.daos.UsersDao;
import org.jooq.generated.tables.pojos.Flowers;
import org.jooq.generated.tables.pojos.OrderFlowers;
import org.jooq.generated.tables.pojos.Orders;
import org.jooq.generated.tables.pojos.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private FlowersDaoCustom flowersDao;
    @Autowired
    private UsersDao usersDao;
    @Autowired
    private OrderFlowersDao orderFlowersDao;
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
            Flowers flower = flowersDao.fetchOneById(item.getKey());

            flower.setFlowerCount(flower.getFlowerCount() - item.getValue());

            if (flower.getFlowerCount() < 0)
                throw new FlowerValidationException(FlowerValidationException.NOT_ENOUGH_FLOWERS);

            flowersDao.update(flower);
        }

        DetailedCart detailedCart = generateDetailedCart(cart);

        // create order
        Orders orderEntity = mapper.map(
                new Order(detailedCart.getResult(user.getDiscount()), user.getDiscount()),
                Orders.class);

        // id will appear here
        ordersDao.insert(orderEntity);

        // add flower data
        for (OrderFlowerData f : detailedCart.getItems()) {
            OrderFlowers fe = mapper.map(f, OrderFlowers.class);

            fe.setOrderId(orderEntity.getId());
            orderFlowersDao.insert(fe);
        }

        // set owner
        Users userEntity = usersDao.findById(user.getLogin());
            orderEntity.setOwnerLogin(userEntity.getLogin());

        // save
        ordersDao.update(orderEntity);
        LOG.info("Order created: {}", orderEntity);
    }

    @Override
    public void pay(Order order) {
        Orders orderEntity = ordersDao.findById(order.getId());
        Users owner = usersDao.findById(orderEntity.getOwnerLogin());

        owner.setBalance(owner.getBalance().subtract(orderEntity.getFullPrice()));
        usersDao.update(owner);

        orderEntity.setStatus(OrderStatus.PAID.name());
        ordersDao.update(orderEntity);

        LOG.info("Order paid: {}", orderEntity);
    }

    @Override
    public void close(Order order) {
        Orders orderEntity = ordersDao.findById(order.getId());
        orderEntity.setStatus(OrderStatus.CLOSED.name());
        orderEntity.setDateClosing(Timestamp.valueOf(LocalDateTime.now()));

        ordersDao.update(orderEntity);
        LOG.info("Order closed {}", orderEntity);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        orderFlowersDao.delete(orderFlowersDao.fetchByOrderId(id));
        ordersDao.deleteById(id);
        LOG.info("Order with id {} deleted", id);
    }

    @Override
    public Order find(Long id) {
        Orders entity = ordersDao.findById(id);
        Order order = mapper.map(entity, Order.class);

        order.setOwner(mapper.map(usersDao.findById(entity.getOwnerLogin()), User.class));

        return order;
    }

    @Override
    public List<Order> getAll() {
        List<Order> orders = new LinkedList<>();

        for (Orders e : ordersDao.findAll()) {
            Order order = mapper.map(e, Order.class);
            order.setOwner(mapper.map(usersDao.findById(e.getOwnerLogin()), User.class));

            orders.add(order);
        }

        return orders;
    }

    @Override
    public List<Order> getByUser(User user) {
        List<Order> orders = new LinkedList<>();

        user = mapper.map(usersDao.findById(user.getLogin()), User.class);

        List<Orders> entities = ordersDao.fetchByOwnerLogin(user.getLogin());

        for (Orders e : entities) {
            Order order = mapper.map(e, Order.class);
            order.setOwner(user);

            orders.add(order);
        }

        return orders;
    }

    @Override
    public List<OrderFlowerData> getFlowersData(Order order) {
        List<OrderFlowerData> flowersData = new LinkedList<>();
        order = find(order.getId());

        List<OrderFlowers> entities = orderFlowersDao.fetchByOrderId(order.getId());

        for (OrderFlowers e : entities) {
            OrderFlowerData f = mapper.map(e, OrderFlowerData.class);
            f.setOrder(order);
            flowersData.add(f);
        }

        return flowersData;
    }

    @Override
    public DetailedCart generateDetailedCart(Cart cart) {
        Set<OrderFlowerData> items = new HashSet<>();

        Object entity;
        for (Map.Entry<Long, Integer> entry : cart.getItems().entrySet())
            items.add(
                    mapper.map(flowersDao.findById(entry.getKey()), Flower.class).toOrderData(entry.getValue())
            );
        return new DetailedCart(items);
    }
}
