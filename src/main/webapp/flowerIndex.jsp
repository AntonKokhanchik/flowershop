<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Flowers  </jsp:attribute>
    <jsp:body>


        <h2>Flowers</h2>
        <table class="table">
            <thead>
                <tr>
                    <th scope="col">Title</th>
                    <th scope="col">Price</th>
                    <th scope="col">Count</th>
                    <th scope="col"></th>
                    <c:if test="${sessionUser.isAdmin()}">
                        <th scope="col"></th>
                        <th scope="col"></th>
                    </c:if>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${flowers}" var="f">
                    <tr>
                        <td>${f.name}</th>
                        <td>${f.price}</td>
                        <td>${f.count}</td>
                        <td>
                            <form action="${path.ADD_TO_CART}/${f.id}" method="post">
                                <input class="btn btn-success" type="submit" value="Add to cart" />
                            </form>
                        </td>
                        <c:if test="${sessionUser.isAdmin()}">
                            <td>
                                <a class="btn btn-warning" href="${path.FLOWER_UPDATE}/${f.id}"> Update </a>
                            </td>
                            <td>
                                <form action="${path.FLOWER_DELETE}/${f.id}" method="post">
                                    <input class="btn btn-danger" type="submit" value="Delete" />
                                </form>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <c:if test="${sessionUser.isAdmin()}">
            <a class="btn btn-primary" href="${path.FLOWER_NEW}">Add flower</a>
        </c:if>


    </jsp:body>
</t:layout>
