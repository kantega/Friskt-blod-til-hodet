<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet - velg avdeling</title>
    <script type="text/javascript" src="/resources/js/jquery.min.js"></script>
    <script type="text/javascript" src="/resources/js/jquery.mobile-1.0.min.js"></script>
    <link rel="shortcut icon" href="/resources/favicon.ico">
    <link href="/resources/css/jquery.mobile-1.0.min.css" rel="stylesheet">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script>
        $(document).ready(function(){
            $('.avdeling').click(function(){
                var id = $(this).attr('id');
                var username = localStorage.getItem('username');
                $.post('/velgAvdeling', {person: username, avdeling: id}, function(data, status, xhq){
                    if(status === 'success'){
                        document.location = '/';
                    }else{
                        alert('Feil');
                    }
                });
                return false;
            });
        });
    </script>
</head>
<body>
<div data-role="page">
    <div data-role="header">
        <h1>Velg avdeling</h1>
    </div>
    <div data-role="content">
        <ul data-role="listview" data-theme="g" data-filter="true">
            <c:forEach var="avdeling" items="${avdelinger}">
                <li><a href="#" class="avdeling" id="${avdeling.id}">${avdeling.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>