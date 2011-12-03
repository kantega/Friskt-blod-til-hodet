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
            var validUsername = true; // TODO check user, velg avdeling dersom ikke allerede satt
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
    <div id="status"></div>
    <ul id="aktivitetlist">
        <c:forEach var="aktivitet" items="${aktiviteter}">
           <li><a class="aktivitet" id="${aktivitet.id}" href="#">${aktivitet.name}</a></li>
        </c:forEach>
    </ul>
</div>
<script>
    $('.aktivitet').click(function(){
        var aktivitetId = $(this).attr('id');
        var personId = localStorage.getItem('username');
        $.post('/utfortaktivitet', {aktivitet: aktivitetId, person: personId}, function(data, textStatus, jqXHR){
            $('#status').text(':)');
        });
        return false;
    })
</script>
</body>
</html>