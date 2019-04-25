package flowershop.backend.services;

import flowershop.backend.dto.Flower;
import flowershop.backend.dto.User;
import flowershop.backend.exception.FlowerValidationException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

/**
* Implements HTTPService,
* provides methods for parsing http requests to dto objects (and primal validation)
*/
@Service
public class HTTPServiceImpl implements HTTPService {
    @Override
    public Flower parseFlower(HttpServletRequest req) throws FlowerValidationException {
        // parse id
        String parameter = req.getParameter("id");
        Long id = (parameter == null || parameter.equals("")) ? null : Long.parseLong(parameter);

        // parse name
        String name = req.getParameter("name");
        if (name == null)
            name = "Unnamed";

        // parse price
        BigDecimal price;
        try {
            price = new BigDecimal(req.getParameter("price"));
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_PRICE);
        }
        if (price == null)
            price = BigDecimal.ZERO;
        if(price.compareTo(BigDecimal.ZERO) > 0)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_PRICE);

        // parse count
        parameter = req.getParameter("count");
        if (parameter.equals(""))
            parameter = "0";
        Integer count;
        try {
            count = Integer.parseInt(parameter);
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_COUNT);
        }
        if (count == null)
            count = 0;
        if (count < 0)
            throw new FlowerValidationException(FlowerValidationException.NEGATIVE_COUNT);

        return new Flower(id, name, price, count);
    }

    @Override
    public User parseUser(HttpServletRequest req){
        return new User(
                req.getParameter("login"),
                req.getParameter("password"),
                req.getParameter("fullname"),
                req.getParameter("address"),
                req.getParameter("phone")
        );
    }

    @Override
    public boolean isAccessGranted(HttpServletRequest req) {
        User user = (User) req.getSession().getAttribute("sessionUser");
        return (user != null && user.isAdmin());
    }

    @Override
    public Long getIdParam(HttpServletRequest req){
        return Long.parseLong(req.getPathInfo().split("/")[2]);
    }
}
