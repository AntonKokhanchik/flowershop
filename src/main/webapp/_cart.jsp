<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<h2> Cart </h2>
<c:choose>
    <c:when test="${sessionCart != null && !sessionCart.isEmpty()}">
        <c:forEach items="${sessionCart.items}" var="item">
            <form action="/order/remove_from_cart/${item.key.id}" method="post">
                ${item.key.title} ${item.value} ${item.value * item.key.price}
                <input class="btn btn-light btn-sm" type="submit" value=" - " />
            </form><br/>
        </c:forEach>
        total: ${sessionCart.sum}

        <br/>

        <c:choose>
            <c:when test="${sessionUser != null}">
                <form action="/order/new/" method="post">
                    <input class="btn btn-success" type="submit" value="Create order" />
                </form>
            </c:when>
            <c:otherwise>
                You must log in before create order
            </c:otherwise>
        </c:choose>
    </c:when>

    <c:otherwise>
        Cart is empty
    </c:otherwise>
</c:choose>
