<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<html>
    <body>
        <h2>Flowers</h2>
        <c:forEach items="${flowers}" var="f">
            ${f.title}, price: ${f.price}, count: ${f.count}
            <a href="/flower/update/${f.id}"> Update </a>
            <form action="/flower/delete/${f.id}" method="post">
                <input type="submit" value="Delete" />
            </form>
            <br />
        </c:forEach>
        <a href="/flower/new">Add flower</a>
    </body>
</html>