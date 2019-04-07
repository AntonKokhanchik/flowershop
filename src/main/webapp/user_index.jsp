<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
    <body>
        <c:if test="${sessionUser != null}">
            Hello, ${sessionUser.fullName}, your balance: ${sessionUser.balance}, your discount: ${sessionUser.discount}
        </c:if>
        <h2>Users:</h2>
        <c:forEach items="${users}" var="u">
            ${u.fullName}: ${u.login}
            <br />
        </c:forEach>
        <c:if test="${sessionUser != null}">
            <form method="post">
                <input type="submit" class="btn btn-warning" value="Logout" />
            </form>
        </c:if>
    </body>
</html>
