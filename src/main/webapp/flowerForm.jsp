<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  New flower  </jsp:attribute>
    <jsp:body>


        <div class="col-5">
            <h1> Flower </h1>
            <form action="${action}" method="post">
                <input type="hidden" id="id" name="id" value="${id == null ? flower.id : id}" />

                <div class="form-group">
                    <label for="name">Name</label>
                    <input class="form-control" id="name" name="name" required
                        value="${name == null ? flower.name : name}" />
                </div>

                <div class="form-group">
                    <label for="price">Price</label>
                    <input class='form-control ${priceErrorMsg != null ? "is-invalid" : ""}' id="price"
                        name="price" value="${price == null ? flower.price : price}" required />
                    <div class="invalid-feedback">
                        ${priceErrorMsg}
                    </div>
                </div>

                <div class="form-group">
                    <label for="count">Count</label>
                    <input class='form-control ${countErrorMsg != null ? "is-invalid" : ""}' id="count" name="count"
                        value="${count == null ? flower.count : count}" />
                    <div class="invalid-feedback">
                        ${countErrorMsg}
                    </div>
                </div>

                <input class="btn btn-primary" type="submit" value="Save" />
            </form>
        </div>


    </jsp:body>
</t:layout>