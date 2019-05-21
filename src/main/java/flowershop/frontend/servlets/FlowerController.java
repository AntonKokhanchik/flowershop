package flowershop.frontend.servlets;

import flowershop.annotations.Secured;
import flowershop.backend.enums.Path;
import flowershop.backend.enums.SessionAttribute;
import flowershop.backend.exception.FlowerValidationException;
import flowershop.backend.services.FlowerService;
import flowershop.backend.services.UserService;
import flowershop.frontend.dto.Flower;
import flowershop.frontend.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Controller
@RequestMapping(value = {"/", "/flower"})
public class FlowerController {
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private UserService userService;

    // не знаю, хорошо так делать или плохо, но работает хорошо
    @Autowired
    private HttpSession httpSession;


    @ModelAttribute
    public void before(ModelMap model) {
        refreshSessionUser();
        model.addAttribute("path", Path.getPathMap());
    }


    //  GET

    @GetMapping
    public String getIndex(@RequestParam(required = false) String sort,
                           @RequestParam(required = false) String order,
                           @RequestParam(value="price_min", required = false) String priceMin,
                           @RequestParam(value="price_max", required = false) String priceMax,
                           @RequestParam(required = false) String name,
                           ModelMap model
    ) {
        BigDecimal min;
        BigDecimal max;
        try {
            min = new BigDecimal(priceMin);
        } catch (NumberFormatException | NullPointerException e) {
            min = null;
        }

        try {
            max = new BigDecimal(priceMax);
        } catch (NumberFormatException | NullPointerException e) {
            max = null;
        }

        model.addAttribute("flowers", flowerService.getAll(name, min, max, sort, order));
        model.addAttribute("sort", sort);
        model.addAttribute("order", order);
        model.addAttribute("price_min", priceMin);
        model.addAttribute("price_max", priceMax);
        model.addAttribute("name", name);

        return Path.FLOWER_INDEX.getPage();
    }

    @GetMapping("new")
    @Secured
    public String getNew(ModelMap model) {
        model.addAttribute("action", Path.FLOWER_NEW.getValue());

        return Path.FLOWER_NEW.getPage();
    }

    @GetMapping("update/{id}")
    @Secured
    public String getUpdate(@PathVariable("id") Long id,  ModelMap model) {
        model.addAttribute("action", Path.FLOWER_UPDATE.getValue());
        model.addAttribute("flower", flowerService.find(id));

        return Path.FLOWER_UPDATE.getPage();
    }


    //  POST

    @PostMapping("new")
    @Secured
    public String postNew(@RequestParam String name, @RequestParam String price, @RequestParam String count, ModelMap model) {
        try {
            flowerService.create(parseFlower(null, name, price, count));
        } catch (FlowerValidationException e) {
            handleValidationError(e, model);

            model.addAttribute("name", name);
            model.addAttribute("price", price);
            model.addAttribute("count", count);
            model.addAttribute("action", Path.FLOWER_NEW.getValue());

            return Path.FLOWER_NEW.getPage();
        }
        return redirect(Path.FLOWER_INDEX.getValue());
    }

    @PostMapping("update")
    @Secured
    public String postUpdate(@RequestParam("id") Long id, @RequestParam String name, @RequestParam String price, @RequestParam String count, ModelMap model) {
        try {
            flowerService.update(parseFlower(id, name, price, count));
        } catch (FlowerValidationException e) {
            handleValidationError(e, model);

            model.addAttribute("id", id);
            model.addAttribute("name", name);
            model.addAttribute("price", price);
            model.addAttribute("count", count);
            model.addAttribute("action", Path.FLOWER_UPDATE.getValue());

            return Path.FLOWER_UPDATE.getPage();
        }
        return redirect(Path.FLOWER_INDEX.getValue());
    }

    @PostMapping("delete/{id}")
    @Secured
    public String postDelete(@PathVariable("id") Long id) {
        flowerService.delete(id);

        return redirect(Path.FLOWER_INDEX.getValue());
    }


    // HELPERS

    private String redirect(String url) {
        return "redirect:" + url;
    }

    private Flower parseFlower(Long id, String name, String priceParam, String countParam) throws FlowerValidationException {
        // parse price
        BigDecimal price;
        try {
            price = new BigDecimal(priceParam);
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_PRICE);
        }

        // parse count
        if (countParam.equals(""))
            countParam = "0";
        Integer count;
        try {
            count = Integer.parseInt(countParam);
        } catch (NumberFormatException e) {
            throw new FlowerValidationException(FlowerValidationException.WRONG_COUNT);
        }

        return new Flower(id, name, price, count);
    }

    private void handleValidationError(FlowerValidationException e, ModelMap model) {
        String attrName;
        switch (e.getMessage()) {
            case FlowerValidationException.NEGATIVE_COUNT:
            case FlowerValidationException.WRONG_COUNT:
                attrName = "countErrorMsg";
                break;
            case FlowerValidationException.NEGATIVE_PRICE:
            case FlowerValidationException.WRONG_PRICE:
                attrName = "priceErrorMsg";
                break;
            default:
                attrName = "anotherErrorMsg";
        }
        model.addAttribute(attrName, e.getMessage());
    }

    private void refreshSessionUser() {
        User sessionUser = (User) httpSession.getAttribute(flowershop.backend.enums.SessionAttribute.USER.getValue());
        sessionUser = sessionUser == null ? null : userService.find(sessionUser.getLogin());
        httpSession.setAttribute(SessionAttribute.USER.getValue(), sessionUser);
    }
}
