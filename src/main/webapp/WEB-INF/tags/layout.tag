<%@tag description="Overall Page template" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="header" fragment="true" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Latest compiled and minified CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

        <!-- jQuery library -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

        <!-- Popper JS -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

        <!-- Latest compiled JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>

        <title>
            <jsp:invoke fragment="header"/>
        </title>
    </head>
    <body>
        <nav id="navbar" class="navbar navbar-light bg-light">
            <a class="navbar-brand" href="/flower">Flowershop</a>
            <ul class="nav nav-pills">
                <li class="nav-item">
                    <a class="nav-link" href="/flower">Flowers list</a>
                </li>
                <c:if test="${sessionUser.isAdmin()}">
                    <li class="nav-item">
                        <a class="nav-link" href="/user">Users list</a>
                    </li>
                </c:if>
                <li class="nav-item">
                    <a class="nav-link" href="/order">Orders list</a>
                </li>
                <c:choose>
                    <c:when test="${sessionUser != null}">
                        <li class="nav-item">
                            <form method="post" action="/user/logout">
                                <input type="submit" class="btn btn-warning" value="Logout" />
                            </form>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/login">Log in</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/user/register">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </nav>
        <c:if test="${sessionUser != null}">
            Hello, ${sessionUser.fullName}, your balance: ${sessionUser.balance},
                                        your discount: ${sessionUser.discount}
        </c:if>
        <div class="jumbotron row" style="margin-top:-50px">
            <div class="col-8">
                <jsp:doBody/>
            </div>

            <div class="col-4">
                <c:import url="_cart.jsp"/>
            </div>
        </div>
    </body>
</html>