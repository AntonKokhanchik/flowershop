<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Orders  </jsp:attribute>
    <jsp:body>


        <h2>Orders</h2>
        <c:choose>
            <c:when test="${orders != null}">
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th scope="col"></th>
                            <th scope="col">Price</th>
                            <th scope="col">Status</th>
                            <th scope="col">Created</th>
                            <th scope="col">Closed</th>
                            <c:choose>
                                <c:when test="${sessionUser.isAdmin()}">
                                    <th scope="col">Owner</th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                    <th scope="col"></th>
                                </c:when>
                                <c:otherwise>
                                    <th scope="col"></th>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="o">
                            <tr>
                                <td><a class="btn btn-link" href="${path.ORDER_SHOW}/${o.id}">more</a></td>
                                <td>${o.fullPrice}</th>
                                <td>${o.status}</td>
                                <td>${o.dateCreation}</td>
                                <td>${o.dateClosing == null ? "never" : o.dateClosing}</td>
                                <c:if test="${sessionUser.isAdmin()}">
                                    <td>${o.owner.login}</td>
                                </c:if>
                                <td>
                                    <c:if test="${o.status == 'CREATED' && sessionUser.login == o.owner.login}">
                                        <form action="${path.ORDER_PAY}/${o.id}" method="post">
                                            <input class="btn btn-success"
                                                ${sessionUser.balance < o.fullPrice ? "disabled title='Not enough money'" : "" }
                                                type="submit" value="Pay" />
                                        </form>
                                    </c:if>
                                </td>
                                <c:if test="${sessionUser.isAdmin()}">
                                    <td>
                                        <c:if test="${o.status == 'PAID'}">
                                            <form action="${path.ORDER_CLOSE}/${o.id}" method="post">
                                                <input class="btn btn-warning" type="submit" value="Close" />
                                            </form>
                                        </c:if>
                                    </td>
                                    <td>
                                        <form action="${path.ORDER_DELETE}/${o.id}" method="post">
                                            <input class="btn btn-danger" type="submit" value="Delete" />
                                        </form>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                No orders
            </c:otherwise>
        </c:choose>


    </jsp:body>
</t:layout>