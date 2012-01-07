<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="../include/js.jsp"%>
</head>
<body>
<div data-role="page">
    <div data-role="header">
        <h1>Friskt blod til hodet</h1>
        <h2>Highscore</h2>
    </div>
    <div data-role="content">
        <nav id="velgHighscore">
            <ul id="velggruppe">
                <li data-gruppe="Totalt" class="gruppeButton"><a href="" data-role="button">Totalt</a></li>
                <c:forEach var="gruppe" items="${grupper}">
                    <li data-gruppe="${gruppe.name}" class="gruppeButton"><a href="" data-role="button">${gruppe.name}</a></li>
                </c:forEach>
            </ul>
            <ul id="velgaktivitet">
                <li data-aktivitet="Alle" class="aktivitetbutton"><a href="" data-role="button">Alle</a></li>
                <c:forEach var="aktivitet" items="${aktiviteter}">
                    <li data-aktivitet="${aktivitet.name}" class="aktivitetbutton"><a href="" data-role="button" >${aktivitet.name}</a></li>
                </c:forEach>
            </ul>
        </nav>
        <div id="highscoreList"></div>
        <%@include file="list.jsp"%>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>
</body>
</html>