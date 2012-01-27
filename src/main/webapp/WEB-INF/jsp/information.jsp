<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
</head>
<body>

<div id="aktiviteter" data-role="page">
    <div data-role="header">
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
    </div>
    <div id="status"></div>
    <div data-role="content">
        <p>${informationtext}</p>
    </div>
    <%@include file="include/footer.jsp"%>
</div>
</body>
</html>