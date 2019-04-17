<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Users  </jsp:attribute>
    <jsp:body>


        <h2>Users:</h2>
        <c:forEach items="${users}" var="u">
            ${u.fullName}: ${u.login}, ${u.address}
            <br />
        </c:forEach>


    </jsp:body>
</t:layout>
