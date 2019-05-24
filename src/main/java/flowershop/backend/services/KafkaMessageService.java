package flowershop.backend.services;

import flowershop.frontend.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Service
public class KafkaMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(KafkaMessageService.class);

    private static final String NEW_USERS_TOPIC = "newUsers";
    private static final String DISCOUNT_TOPIC = "discount";

    @Autowired
    KafkaListenerEndpointRegistry registry;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private XMLConverter converter;

    @Autowired
    private UserService userService;


    @PostConstruct
    public void init() {
        LOG.info("KafkaMessage service on");
    }

    public void init2() {
        LOG.info("starting listener");

    }

    public void sendNewUserXml(User user) {
        try {
            String message = converter.convertFromObjectToXMLString(user);
            ListenableFuture a = kafkaTemplate.send(NEW_USERS_TOPIC, message);
            LOG.info("Message response {}", a);
        } catch (IOException e) {
            LOG.error("Error sending message", e);
        }
    }

    @KafkaListener(id ="first", topics = DISCOUNT_TOPIC, groupId = "discount-1", autoStartup = "false")
    public void resieveDiscountInfromation (String message) {
        //<user><login>login2</login><discount>20</discount></user>
        try {
            User m = (User) converter.convertFromXMLStringToObject(message);

            User user = userService.find(m.getLogin());
            user.setDiscount(m.getDiscount());
            userService.update(user);
        } catch (IOException e) {
            LOG.error("Error receiving message", e);
        }
    }

    // запускаем consumer(listener) вручную, чтобы поймать ошибку подключения
    // если нет kafka broker-a, прогорамма продолжит работу (правда придётся ждать миуту пока consumer не выбрсит ошибку)
    // лучше закомментировать этот метод, если kafka broker выключен
    @EventListener
    public void handleContextRefresh(ContextRefreshedEvent event) {
        LOG.info("starting listener");
        try {
            registry.getListenerContainer("first").start();
        } catch (Exception e) {
            LOG.error("Error connecting to broker", e);
        }
    }
}
