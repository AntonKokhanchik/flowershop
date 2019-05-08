<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page isELIgnored="false" %>

<t:layout>
    <jsp:attribute name="header">  Flowers  </jsp:attribute>
    <jsp:body>


        <h2>Flowers</h2>

        <form action="${path.FLOWER_INDEX}">
            <div class="form-row">
                <div class="input-group col">
                    <label class="m-2"> Sort: </label>
                    <select class="form-control" name="sort">
                        <option value="name" ${sort == null || sort == "name" ? "selected" : ""}> Name </option>
                        <option value="price" ${sort == "price" ? "selected" : ""}> Price </option>
                        <option value="count" ${sort == "count" ? "selected" : ""}> Count </option>
                    </select>
                </div>
                <div class="input-group col">
                    <label  class="m-2"> Order: </label>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" name="order" id="order_asc" type="radio" value="asc" ${order == null || order == "asc" ? "checked" : ""} />
                        <label class="form-check-label" for="order_asc"> Asc </label>
                    </div>
                    <div class="form-check form-check-inline">
                        <input class="form-check-input" name="order" id="order_desc" type="radio" value="desc" ${order == "desc" ? "checked" : ""} />
                        <label class="form-check-label" for="order_desc"> Desc </label>
                    </div>
                </div>
            </div>
            <div class="form-row">
                <div class="input-group col">
                    <label class="m-2" for="name"> Search: </label>
                    <input class="form-control" name="name" placeholder="Search name" value="${name}"/>
                </div>
                <div class="input-group col">
                    <input class="form-control" name="price_min" placeholder="Min price" value="${price_min}"/>
                    <input class="form-control" name="price_max" placeholder="Max price" value="${price_max}"/>
                </div>
            </div>
            <input class="btn btn-success" type="submit" value="Apply" />
            <input class="btn btn-danger" type="reset" value="Reset" />
        </form>

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
                        <td>${f.name}</td>
                        <td>${f.price}</td>
                        <td>${f.count}</td>
                        <td>
                            <form action="${path.ADD_TO_CART}/${f.id}" method="post">
                                <div class="input-group">
                                    <input name="count" type="number" class="form-control" placeholder="Count" max="${f.count - sessionCart.items.get(f.id)}" min=1>
                                    <div class="input-group-append">
                                        <input class="btn btn-success" type="submit" value="Add to cart" />
                                    </div>
                                </div>
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
