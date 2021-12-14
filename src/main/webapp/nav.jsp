<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html lang="en">
<ul class="nav">
    <li class="nav-item">
        <a class="nav-link" href="<%=request.getContextPath()%>/logout">
            <c:out value="${user.name}"/>
            | Log out
        </a>
    </li>
</ul>