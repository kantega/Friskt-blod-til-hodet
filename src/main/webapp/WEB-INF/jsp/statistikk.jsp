<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <script type="text/javascript" src="/resources/js/flot/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/flot/jquery.flot.min.js"></script>
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <script>
        $(document).ready(function() {
            var placeholder = $("#placeholder");
            var username = localStorage.getItem('username');
            $.getJSON('statistikk/person/' + username, function(data) {
                $.plot(placeholder, data, options);
            });
        });
    </script>
</head>
<body>

<div id="placeholder" style="width: 100%; height:90%;"></div>
</body>
</html>