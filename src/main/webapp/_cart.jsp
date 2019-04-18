<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<h2> Cart </h2>
<c:choose>
    <c:when test="${sessionCart != null && !sessionCart.isEmpty()}">
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Flower</th>
                    <th scope="col">Price</th>
                    <th scope="col">Count</th>
                    <th scope="col">Total</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sessionCart.items}" var="item">
                    <tr>
                        <td>${item.key.title}</th>
                        <td>${item.key.price}</td>
                        <td>${item.value}</td>
                        <td>${item.value * item.key.price}</td>
                        <td>
                            <form action="/order/remove_from_cart/${item.key.id}" method="post">
                                <input class="btn btn-light btn-sm" type="submit" value=" - " />
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        total: ${sessionCart.sum}

        <br/>
        <c:choose>
            <c:when test="${sessionUser != null}">
                <form action="/order/new/" method="post">
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
