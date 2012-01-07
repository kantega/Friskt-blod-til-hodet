<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="../include/js.jsp"%>
    <script type="text/javascript" src="/resources/js/flot/jquery.flot.min.js"></script>
    <script>
        $(document).ready(function() {
            var username = localStorage.getItem('username');
        });
    </script>
</head>
<body>
<div data-role="page">
    <div data-role="header">
        <h1>Friskt blod til hodet</h1>
        <h2>Highscore</h2>
    </div>
    <div data-role="content">
        <ul id="velggruppe">
            <li id="gruppeTotalt"><h3><a href="" class="gruppeButton" data-role="button">Totalt</a></h3></li>
            <c:forEach var="gruppe" items="${grupper}">
                <li id="gruppe${gruppe.navn}"><h3><a href="" class="gruppeButton" data-role="button">${gruppe.navn}</a></h3></li>
            </c:forEach>
        </ul>
        <ul id="velgaktivitet">
            <li id="aktivitetAlle"><h3><a href="" class="aktivitetbutton" data-role="button" data-inline="true">Alle</a></h3></li>
            <c:forEach var="aktivitet" items="${aktiviteter}">
                <li id="aktivitet${gruppe.navn}"><h3><a href="" class="aktivitetbutton" data-role="button" data-inline="true">${gruppe.navn}</a></h3></li>
            </c:forEach>
        </ul>

        <div id="highscoreList"></div>
            <%@include file="list.jsp"%>
    </div>
</div>
</body>
</html>