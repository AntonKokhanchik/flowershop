<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Flowers  </jsp:attribute>
    <jsp:body>


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
                <c:forEach items="${items}" var="item">
                    <tr>
                        <td>${item.flowerName}</th>
                        <td>${item.price}</td>
                        <td>${item.count}</td>
                        <td>${item.getFullPrice()}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        discount: ${order.appliedDiscount} <br/>
        total: ${order.fullPrice} <br/>
        created: ${order.dateCreation} <br/>
        closed: ${order.dateClosing} <br/>


    </jsp:body>
</t:layout>
