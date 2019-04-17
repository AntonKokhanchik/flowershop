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
                <table class="table">
                    <thead>
                        <tr>
                            <th scope="col">Price</th>
                            <th scope="col">Status</th>
                            <th scope="col">Created</th>
                            <th scope="col">Closed</th>
                            <c:if test="${sessionUser.isAdmin()}">
                                <th scope="col">Owner</th>
                            </c:if>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="o">
                            <tr>
                                <td>${o.fullPrice}</th>
                                <td>${o.status}</td>
                                <td>${o.dateCreation}</td>
                                <td>${o.dateClosing == null ? "never" : o.dateClosing}</td>
                                <c:if test="${sessionUser.isAdmin()}">
                                    <td>${o.owner.login}</td>
                                </c:if>
                                <td>
                                    <form action="/order/pay/${o.id}" method="post">
                                        <input class="btn btn-success" type="submit" value="Pay" />
                                    </form>
                                </td>
                                <c:if test="${sessionUser.isAdmin()}">
                                    <td>
                                        <form action="/order/close/${o.id}" method="post">
                                            <input class="btn btn-warning" type="submit" value="Close" />
                                        </form>
                                    </td>
                                    <td>
                                        <form action="/order/delete/${o.id}" method="post">
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