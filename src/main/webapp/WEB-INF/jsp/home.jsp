<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
    <script>
        var username = localStorage.getItem('username');
        if(username == undefined){
            window.location = '/login'
        }
    </script>
</head>
<body>

<div id="aktiviteter" data-role="page">
    <div data-role="header">
        <h1>Friskt blod til hodet</h1>
        <h2>Velg aktivitet</h2>
    </div>
    <div id="status"></div>
    <div data-role="content">
        <ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="true">
            <c:forEach var="aktivitet" items="${aktiviteter}">
                <li><a class="aktivitet" id="${aktivitet.id}" href="/aktiviteter/${aktivitet.id}">${aktivitet.name}</a></li>
            </c:forEach>
        </ul>
    </div>
    <%@include file="include/footer.jsp"%>
</div>
</body>
</html>