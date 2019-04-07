package flowershop.backend.services;

import flowershop.backend.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.LinkedList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private List<User> users = new LinkedList<>();

    @PostConstruct
    public void init(){
        System.out.println("User service on");

        create(new User("admin", "admin123", "admin", "unknown", "+0(000)000-00-00"));
        create(new User("login1", "pass1", "Alex1", "anywhere1", "+0(000)000-00-01"));
        create(new User("login2", "pass2", "Alex2", "anywhere2", "+0(000)000-00-02"));
        create(new User("login3", "pass3", "Alex3", "anywhere3", "+0(000)000-00-03"));
        create(new User("login4", "pass4", "Alex4", "anywhere4", "+0(000)000-00-04"));
        create(new User("login5", "pass5", "Alex5", "anywhere5", "+0(000)000-00-05"));
        create(new User("login6", "pass6", "Alex6", "anywhere6", "+0(000)000-00-06"));
        create(new User("login7", "pass7", "Alex7", "anywhere7", "+0(000)000-00-07"));
    }

    @Override
    public boolean create(User user) {
        user.setId(users.size());
        user.setBalance(2000);
        user.setDiscount(0);
        if (validate(user)) {
            users.add(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public boolean delete(User user) {
        return false;
    }

    @Override
    public User find(int id) {
        for(User user : users)
            if (user.getId() == id)
                return user;
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }

    @Override
    public boolean validate(User user) {
        return true;
    }

    @Override
    public User getAdmin(){ return null; }

    @Override
    public void register(HttpServletRequest req){
        User newUser = new User(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("fullname"),
                req.getParameter("address"),
                req.getParameter("phone")
        );

        create(newUser);
    }

    @Override
    public User verify(HttpServletRequest req) {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        User user = findByLogin(login);

        if (password.equals(user.getPassword()))
            return user;
        return null;
    }

    @Override
    public User findByLogin(String login) {
        for (User u : users)
            if (login.equals(u.getLogin()))
                return u;
        return null;
    }
}
