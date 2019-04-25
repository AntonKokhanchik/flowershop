<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<h2> Cart </h2>
<c:choose>
    <c:when test="${sessionDetailedCart != null && !sessionDetailedCart.isEmpty()}">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Flower</th>
                    <th scope="col">Price</th>
                    <th scope="col">Count</th>
                    <th scope="col">Total</th>
                    <th scope="col"></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sessionDetailedCart.items}" var="item">
                    <tr>
                        <td>${item.flowerName}</th>
                        <td>${item.price}</td>
                        <td>${item.count}</td>
                        <td>${item.getFullPrice()}</td>
                        <td>
                            <form action="${path.REMOVE_FROM_CART}/${item.flowerId}" method="post">
                                <input class="btn btn-light btn-sm" type="submit" value=" - " />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        total: ${sessionDetailedCart.sum} <br/>
        <c:if test="${sessionUser != null && sessionUser.discount > 0}">
            with discount: ${sessionDetailedCart.getResult(sessionUser.discount)}
        </c:if>

        <br/>
        <c:choose>
            <c:when test="${sessionUser != null}">
                <form action="${path.ORDER_NEW}" method="post">
                    <input class="btn btn-success" type="submit" value="Create order" />
                </form>
                <c:if test="${cartErrorMsg != null}">
                    <div class="alert alert-danger" role="alert">
                        ${cartErrorMsg}
                    </div>
                </c:if>
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
