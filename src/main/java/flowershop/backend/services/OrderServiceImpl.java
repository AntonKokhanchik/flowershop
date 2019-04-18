package flowershop.backend.services;

import flowershop.backend.OrderStatus;
import flowershop.backend.dto.Flower;
import flowershop.backend.dto.Order;
import flowershop.backend.dto.User;
import flowershop.backend.entity.OrderEntity;
import flowershop.backend.entity.UserEntity;
import flowershop.backend.exception.FlowerValidationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {
    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    public void initialize()
    {
        System.out.println("Order service on");
    }

    @Override
    @Transactional
    public void create(Order order) {
        validate(order);
        order.setDateCreation(LocalDateTime.now());
        order.setDateClosing(null);
        em.persist(order);
    }

    @Override
    @Transactional
    public void create(Cart cart, User user) throws FlowerValidationException {
        // decrease flowers count
        // TODO: здесь надо продумать изменение количества цветов между созданием корзины и созданием заказа
        for (Map.Entry<Flower, Integer> item : cart.items.entrySet()) {
            Flower flower = item.getKey();
            flower.setCount(flower.getCount() - item.getValue());
            if (flower.getCount() < 0)
                throw new FlowerValidationException(FlowerValidationException.NOT_ENOUGH_FLOWERS);

            em.merge(flower.toEntity());
        }

        // create order
        OrderEntity order = new Order(null, cart.getResult(user.getDiscount()),
                LocalDateTime.now(), null, OrderStatus.CREATED).toEntity();

        // set owner
        UserEntity userEntity = em.find(UserEntity.class, user.getLogin());
        order.setOwner(userEntity);

        // save
        em.persist(order);
    }

    @Override
    @Transactional
    public void pay(Order order){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getId());
        UserEntity owner = orderEntity.getOwner();

        owner.setBalance(owner.getBalance().subtract(orderEntity.getFullPrice()));

        orderEntity.setStatus(OrderStatus.PAID);
    }

    @Override
    @Transactional
    public void close(Order order){
        OrderEntity orderEntity = em.find(OrderEntity.class, order.getId());

        orderEntity.setStatus(OrderStatus.CLOSED);
        orderEntity.setDateClosing(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void delete(Order order) {
        em.remove(em.find(OrderEntity.class, order.getId()));
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

    @Override
    public void validate(Order order) {

    }

    @Override
    public Order parse(HttpServletRequest req) {
        String sid = req.getParameter("id");
        Long id = sid == null ? null : Long.parseLong(sid);

        return new Order(
                id,
                new BigDecimal(req.getParameter("fullPrice")),
                LocalDateTime.parse(req.getParameter("dateCreation")),
                LocalDateTime.parse(req.getParameter("dateClosing")),
                OrderStatus.valueOf(req.getParameter("status"))
        );
    }
}
