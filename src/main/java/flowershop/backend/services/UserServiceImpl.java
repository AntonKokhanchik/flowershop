package flowershop.backend.services;

import flowershop.backend.dao.UserDAO;
import flowershop.frontend.dto.User;
import flowershop.backend.entity.UserEntity;
import flowershop.backend.exception.UserValidationException;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @Autowired
    private XMLConverter converter;

    @Autowired
    private JmsMessageService jmsMessageService;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private Mapper mapper;

    @PostConstruct
    public void init() {
        LOG.info("User service on");
    }

    @Override
    public void create(User user) throws UserValidationException {
        user.setBalance(new BigDecimal(2000));
        user.setDiscount(0);

        userDAO.create(mapper.map(user, UserEntity.class));

        createXML(user);
        jmsMessageService.sendNewUserSql(user);
    }

    @Override
    public void update(User user) {
        UserEntity userEntity = userDAO.find(user.getLogin());

        userEntity.setPassword(user.getPassword());
        userEntity.setFullName(user.getFullName());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setDiscount(user.getDiscount());
        userEntity.setBalance(user.getBalance());

        userDAO.update(userEntity);
    }

    @Override
    public void delete(User user) {
        userDAO.delete(mapper.map(user, UserEntity.class));
    }

    @Override
    public User find(String login) {
        return mapper.map(userDAO.find(login), User.class);
    }

    @Override
    public List<User> getAll() {
        List<UserEntity> entities = userDAO.getAll();
        List<User> users = new LinkedList<>();

        for(UserEntity e : entities)
            users.add(mapper.map(e, User.class));

        return users;
    }

    @Override
    public User verify(User user) throws UserValidationException {
        UserEntity userEntity = userDAO.find(user.getLogin());

        if(userEntity == null)
            throw new UserValidationException(UserValidationException.WRONG_LOGIN);

        if (!user.getPassword().equals(userEntity.getPassword()))
            throw new UserValidationException(UserValidationException.WRONG_PASSWORD);

        LOG.info("User logged in: {}", userEntity);

        return mapper.map(userEntity, User.class);
    }

    @Override
    // TODO: брать путь для создания xml из конфига
    public void createXML(User user) {
        try {
            converter.convertFromObjectToXML(user, "userXML/user_"+user.getLogin()+".xml");
            LOG.info("user {}.xml created", user.getLogin());
        } catch (IOException e) {
            LOG.error("Failed to convert to xml", e);
        }
    }
}
