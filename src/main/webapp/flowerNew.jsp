<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  New flower  </jsp:attribute>
    <jsp:body>


        <div class="col-5">
            <h1> Flower </h1>
            <form action="/flower/new" method="post">
                <div class="form-group">
                    <label for="title">Title</label>
                    <input class="form-control" id="title" name="title" />
                </div>

                <div class="form-group">
                   <label for="price">Price</label>
                   <input class="form-control" id="price" name="price" />
                </div>

                <div class="form-group">
                    <label for="count">Count</label>
                    <input class="form-control" id="count" name="count" />
                </div>

                <input class="btn btn-primary" type="submit" value="Save" />
            </form>
        </div>


    </jsp:body>
</t:layout>