<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
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
        <h2>Statistikk</h2>
    </div>
    <div data-role="content">

    </div>
</div>
</body>
</html>