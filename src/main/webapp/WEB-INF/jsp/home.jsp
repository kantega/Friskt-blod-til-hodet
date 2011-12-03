<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <script type="text/javascript" src="/resources/jquery.min.js"></script>
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <link href="/resources/main.css" rel="stylesheet">
    <script>
        var username = localStorage.getItem('username');
        if(username == undefined){
            username = prompt('Hva er Kantega-brukernavnet ditt?', '');
            var validUsername = true; // TODO check user
            if (validUsername) {
                localStorage.setItem('username', username);
            } else {
                alert('Fant ingen bruker med det brukernavnet')
            }
        }
    </script>
</head>
<body>

<div id="aktiviteter">
    <ul id="aktivitetlist">
        <c:forEach var="aktivitet" items="${aktiviteter}">
           <li id="${aktivitet.id}">${aktivitet.name}</li>
        </c:forEach>
    </ul>
</div>

</body>
</html>