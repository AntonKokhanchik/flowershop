<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Register  </jsp:attribute>
    <jsp:body>


        <div class="col-5">
            <h1> Register </h1>
            <form method="post">
                <div class="form-group">
                    <label for="login">Login</label>
                    <input class="form-control" id="login" name="login" />
                </div>

                <div class="form-group">
                   <label for="password">Password</label>
                   <input type="password" class="form-control" id="password" name="password" />
                </div>

                <div class="form-group">
                    <label for="fullname">Full name</label>
                    <input class="form-control" id="fullname" name="fullname" />
                </div>

                <div class="form-group">
                    <label for="address">Address</label>
                    <input class="form-control" id="address" name="address" />
                </div>

                <div class="form-group">
                    <label for="phone">Phone</label>
                    <input class="form-control" id="phone" name="phone" />
                </div>

                <input class="btn btn-primary" type="submit" value="Register" />
            </form>
        </div>


    </jsp:body>
</t:layout>