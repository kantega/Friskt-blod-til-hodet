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
            username = prompt('Hva er Kantega-brukernavnet ditt?', '');

            $.get('/doesPersonExist', {username:username}, function(person){
                if (person) {
                    localStorage.setItem('username', username);
                    if(!person.avdeling){
                        document.location = '/velgAvdeling';
                    }
                } else {
                    alert('Fant ingen bruker med det brukernavnet')
                }
            }, 'json');
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
                <li><a class="aktivitet" id="${aktivitet.id}" href="#">${aktivitet.name}</a></li>
            </c:forEach>
        </ul>

        <a style="margin-top: 2em;" href="/statistikk" data-role="button" data-theme="a">Statistikk</a>
    </div>
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