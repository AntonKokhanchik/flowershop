package flowershop.backend.services;

import flowershop.backend.exception.UserValidationException;
import flowershop.frontend.dto.User;
import org.dozer.Mapper;
import org.jooq.generated.tables.daos.UsersDao;
import org.jooq.generated.tables.pojos.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @Value("${flowershop.backend.services.UserService.xmlPath}")
    private String XMLPath;

    @Autowired
    private XMLConverter converter;

    @Autowired
    private KafkaMessageService messageService;

    @Autowired
    private UsersDao usersDao;

    @Autowired
    private Mapper mapper;

    @PostConstruct
    public void init() {
        LOG.info("User service on");
    }

    @Override
    public void create(User user) {
        user.setBalance(new BigDecimal(2000));
        user.setDiscount(0);

        usersDao.insert(mapper.map(user, Users.class));
        LOG.info("User created: {}", user);

        createXML(user);
        messageService.sendNewUserXml(user);
    }

    @Override
    public void update(User user) {
        Users userEntity = usersDao.findById(user.getLogin());
        userEntity.setPassword(user.getPassword());
        userEntity.setFullName(user.getFullName());
        userEntity.setAddress(user.getAddress());
        userEntity.setPhone(user.getPhone());
        userEntity.setDiscount(user.getDiscount());
        userEntity.setBalance(user.getBalance());

        usersDao.update(userEntity);
        LOG.info("User updated to {}", userEntity);
    }

    @Override
    public void delete(String login) {
        usersDao.deleteById(login);
        LOG.info("User with login {} deleted", login);
    }

    @Override
    public User find(String login) {
        Users entity = usersDao.findById(login);

        return entity == null ? null : mapper.map(entity, User.class);
    }

    @Override
    public List<User> getAll() {
        List<Users> entities = usersDao.findAll();
        List<User> users = new LinkedList<>();

        for (Users e : entities)
            users.add(mapper.map(e, User.class));

        return users;
    }

    @Override
    public User verify(User user) throws UserValidationException {
        Users userEntity = usersDao.findById(user.getLogin());

        if (userEntity == null)
            throw new UserValidationException(UserValidationException.WRONG_LOGIN);

        if (!user.getPassword().equals(userEntity.getPassword()))
            throw new UserValidationException(UserValidationException.WRONG_PASSWORD);

        LOG.info("User logged in: {}", userEntity);
        return mapper.map(userEntity, User.class);
    }

    @Override
    public void createXML(User user) {
        try {
            new File(XMLPath).mkdir();
            String filepath = XMLPath + "/user_" + user.getLogin() + ".xml";
            converter.convertFromObjectToXML(user, filepath);
            LOG.info("file {} created", filepath);
        } catch (IOException e) {
            LOG.error("Failed to convert to xml", e);
        }
    }
}
