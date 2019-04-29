package flowershop.backend.services;

import flowershop.backend.dto.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.jms.*;
import java.io.IOException;

/**
 * Service for sending and receiving JMS messages
 */
@Service
public class JmsMessageService {
    private static final Logger LOG = LoggerFactory.getLogger(JmsMessageService.class);
    @Autowired
    private UserService userService;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private Queue receiveUserDiscountXmlQueue;

    @Autowired
    private Queue sendNewUsersXmlQueue;

    @Autowired
    private XMLConverter xmlConverter;

    Session session;

    @PostConstruct
    public void init() {
        try {
            Connection connection = connectionFactory.createConnection();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            MessageConsumer consumer = session.createConsumer(receiveUserDiscountXmlQueue);
            connection.start();
            consumer.setMessageListener(
                    new MessageListener() {
                        //<user><login>login2</login><discount>20</discount></user>
                        public void onMessage(Message message) {
                            try {
                                String text = ((TextMessage) message).getText();
                                User m = (User) xmlConverter.convertFromXMLStringToObject(text);

                                User user = userService.find(m.getLogin());
                                user.setDiscount(m.getDiscount());
                                userService.update(user);
                            } catch (IOException | JMSException e) {
                                LOG.error("Error receiving message");
                                e.printStackTrace();
                            }
                        }
                    });
            LOG.info("Jms Message Service on");
        } catch (JMSException e) {
            LOG.error("Jms Message Service error");
            e.printStackTrace();
        }
    }

    public void SendNewUserSql(User user) {
        TextMessage msg;
        try {
            msg = session.createTextMessage(xmlConverter.convertFromObjectToXMLString(user));

            MessageProducer producer = session.createProducer(sendNewUsersXmlQueue);
            producer.send(msg);
            producer.close();
        } catch (JMSException | IOException e) {
            LOG.error("Error sending message");
            e.printStackTrace();
        }
    }
}
