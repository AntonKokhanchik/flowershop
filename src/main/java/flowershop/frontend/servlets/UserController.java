package flowershop.frontend.servlets;

import flowershop.annotations.Secured;
import flowershop.backend.enums.Path;
import flowershop.backend.enums.SessionAttribute;
import flowershop.backend.exception.UserValidationException;
import flowershop.backend.services.UserService;
import flowershop.frontend.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    HttpSession httpSession;


    @ModelAttribute
    public void before(ModelMap model) {
        refreshSessionUser();
        model.addAttribute("path", Path.getPathMap());
    }


    // GET

    @GetMapping
    @Secured
    public String getIndex(ModelMap model) {
        model.addAttribute("users", userService.getAll());

        return Path.USER_INDEX.getPage();
    }

    @GetMapping("login")
    public String getLogin() {
        return Path.LOGIN.getPage();
    }

    @GetMapping("register")
    public String getRegister() {
        return Path.REGISTER.getPage();
    }


    // POST

    @PostMapping("logout")
    public String logout() {
        httpSession.removeAttribute(SessionAttribute.USER.getValue());
        httpSession.removeAttribute(SessionAttribute.CART.getValue());
        httpSession.removeAttribute(SessionAttribute.DETAILED_CART.getValue());

        return redirect(Path.LOGIN.getValue());
    }

    @PostMapping("register")
    public String postRegister(@RequestParam String login,
                               @RequestParam String password,
                               @RequestParam String fullname,
                               @RequestParam String address,
                               @RequestParam String phone) {
        User user = new User(login, password, fullname, address, phone);
        userService.create(user);

        return redirect(Path.LOGIN.getValue());
    }

    @PostMapping("login")
    public String postLogin(@RequestParam String login, @RequestParam String password, ModelMap model) {
        User user;
        try {
            user = userService.verify(new User(login, password, null, null, null));
        } catch (UserValidationException e) {
            handleValidationError(e, model);

            model.addAttribute("login", login);
            model.addAttribute("password", password);

            return Path.LOGIN.getPage();
        }

        httpSession.setAttribute(SessionAttribute.USER.getValue(), user);

        return redirect(Path.FLOWER_INDEX.getValue());
    }


    // HELPERS

    private String redirect(String url) {
        return "redirect:" + url;
    }

    private void handleValidationError(UserValidationException e, ModelMap model) {
        String attrName;
        switch (e.getMessage()) {
            case UserValidationException.WRONG_LOGIN:
                attrName = "loginErrorMsg";
                break;
            case UserValidationException.WRONG_PASSWORD:
                attrName = "passwordErrorMsg";
                break;
            default:
                attrName = "anotherErrorMsg";
        }
        model.addAttribute(attrName, e.getMessage());
    }

    private void refreshSessionUser(){
        User sessionUser = (User) httpSession.getAttribute(SessionAttribute.USER.getValue());
        sessionUser = sessionUser == null ? null : userService.find(sessionUser.getLogin());
        httpSession.setAttribute(SessionAttribute.USER.getValue(), sessionUser);
    }
}
