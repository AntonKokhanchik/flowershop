package flowershop.annotations;

import flowershop.backend.enums.SessionAttribute;
import flowershop.frontend.dto.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpSession;

@Aspect
@Component
public class SecuredAnnotationProcessor {
    @Autowired
    HttpSession httpSession;

    @Around("@annotation(Secured)")
    public Object Secure (ProceedingJoinPoint joinPoint) throws Throwable {
        User user = (User) httpSession.getAttribute(SessionAttribute.USER.getValue());
        if (user != null && user.isAdmin())
            return joinPoint.proceed();

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}
