<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Log in  </jsp:attribute>
    <jsp:body>


        <div class="col-5">
            <h1> Log in </h1>
            <form method="post">
                <div class="form-group">
                    <label for="login">Login</label>
                    <input class='form-control ${loginErrorMsg != null ? "is-invalid" : ""}'
                        id="login" name="login" value="${login}" required />
                    <div class="invalid-feedback">
                        ${loginErrorMsg}
                    </div>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class='form-control ${passwordErrorMsg != null ? "is-invalid" : ""}'
                        id="password" name="password" required />
                    <div class="invalid-feedback">
                        ${passwordErrorMsg}
                    </div>
                </div>
                <input type="submit" class="btn btn-primary" value="Log in" />
            </form>
        </div>


    </jsp:body>
</t:layout>