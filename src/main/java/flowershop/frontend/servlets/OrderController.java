package flowershop.frontend.servlets;

import flowershop.backend.enums.Path;
import flowershop.backend.enums.SessionAttribute;
import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.services.Cart;
import flowershop.backend.services.FlowerService;
import flowershop.backend.services.OrderService;
import flowershop.backend.services.UserService;
import flowershop.frontend.dto.Order;
import flowershop.frontend.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
@RequestMapping(value = "/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private UserService userService;

    @Autowired
    private HttpSession httpSession;


    @ModelAttribute
    public void before(ModelMap model) {
        refreshSessionUser();
        model.addAttribute("path", Path.getPathMap());
    }


    // GET

    @GetMapping
    public String getIndex(ModelMap model) {
        User user = (User)httpSession.getAttribute(SessionAttribute.USER.getValue());

        if (user != null)
            if (user.isAdmin())
                model.addAttribute("orders", orderService.getAll());
            else
                model.addAttribute("orders", orderService.getByUser(user));


        return Path.ORDER_INDEX.getPage();
    }

    @GetMapping("detail/{id}")
    public String getShow(@PathVariable Long id, ModelMap model) {
        Order order = orderService.find(id);
        model.addAttribute("order", order);
        model.addAttribute("items", orderService.getFlowersData(order));

        return Path.ORDER_SHOW.getPage();
    }


    // POST

    @PostMapping("new")
    public String postNew(@PathVariable Long id, ModelMap model) {
        Cart cart = (Cart) httpSession.getAttribute(SessionAttribute.CART.getValue());

        try {
            orderService.create(cart, (User) httpSession.getAttribute(SessionAttribute.USER.getValue()));
        } catch (FlowerValidationException e) {
            httpSession.setAttribute("cartErrorMsg", e.getMessage());
        }
        httpSession.removeAttribute(SessionAttribute.CART.getValue());
        httpSession.removeAttribute(SessionAttribute.DETAILED_CART.getValue());

        return redirect(Path.ORDER_INDEX.getValue());
    }

    @PostMapping("add_to_cart/{id}")
    public String addToCart(@PathVariable Long id, @RequestParam String count) {
        Cart cart = (Cart) httpSession.getAttribute(SessionAttribute.CART.getValue());
        if (cart == null)
            cart = new Cart();

        if (count.equals(""))
            count = "1";

        Integer countI = Integer.parseInt(count);

        cart.addItem(flowerService.find(id), countI);

        httpSession.setAttribute(SessionAttribute.CART.getValue(), cart);
        httpSession.setAttribute(SessionAttribute.DETAILED_CART.getValue(), orderService.generateDetailedCart(cart));

        return redirect(Path.FLOWER_INDEX.getValue());
    }

    @PostMapping("remove_from_cart/{id}")
    public String removeFromCart(@PathVariable Long id) {
        Cart cart = (Cart) httpSession.getAttribute(SessionAttribute.CART.getValue());

        cart.removeItem(flowerService.find(id), 1);

        httpSession.setAttribute(SessionAttribute.CART.getValue(), cart);
        httpSession.setAttribute(SessionAttribute.DETAILED_CART.getValue(), orderService.generateDetailedCart(cart));

        return redirect(Path.FLOWER_INDEX.getValue());
    }

    @PostMapping("remove_cart_item/{id}")
    public String removeCartItem(@PathVariable Long id) {
        Cart cart = (Cart) httpSession.getAttribute(SessionAttribute.CART.getValue());

        cart.removeItem(flowerService.find(id), -1);

        httpSession.setAttribute(SessionAttribute.CART.getValue(), cart);
        httpSession.setAttribute(SessionAttribute.DETAILED_CART.getValue(), orderService.generateDetailedCart(cart));

        return redirect(Path.FLOWER_INDEX.getValue());
    }

    @PostMapping("pay/{id}")
    public String pay(@PathVariable Long id) {
        User user = (User) httpSession.getAttribute(SessionAttribute.USER.getValue());
        Order order = orderService.find(id);

        if (user.getLogin().equals(order.getOwner().getLogin()) && user.getBalance().compareTo(order.getFullPrice()) >= 0) {
            orderService.pay(order);
            httpSession.setAttribute(SessionAttribute.USER.getValue(), userService.find(user.getLogin()));
        }

        return redirect(Path.ORDER_INDEX.getValue());
    }

    @PostMapping("close/{id}")
    public String close(@PathVariable Long id) {
        if (!isAccessGranted())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        orderService.close(orderService.find(id));

        return "redirect:" + Path.ORDER_INDEX.getValue();
    }

    @PostMapping("delete/{id}")
    public String postDelete(@PathVariable Long id) {
        if (!isAccessGranted())
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        orderService.delete(id);

        return redirect(Path.ORDER_INDEX.getValue());
    }


    // HELPERS

    private String redirect(String url) {
        return "redirect:" + url;
    }

    private boolean isAccessGranted() {
        User user = (User) httpSession.getAttribute(SessionAttribute.USER.getValue());
        return (user != null && user.isAdmin());
    }

    private void refreshSessionUser() {
        User sessionUser = (User) httpSession.getAttribute(SessionAttribute.USER.getValue());
        sessionUser = sessionUser == null ? null : userService.find(sessionUser.getLogin());
        httpSession.setAttribute(SessionAttribute.USER.getValue(), sessionUser);
    }
}
