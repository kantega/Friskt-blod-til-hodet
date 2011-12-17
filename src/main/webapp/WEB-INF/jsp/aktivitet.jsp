<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
</head>
<body>

<div id="aktivitet" data-role="page">
    <div data-role="header">
        <h1>Friskt blod til hodet</h1>
        <h2>${aktivitet.name}</h2>
    </div>
    <div data-role="content">
        <img src="/resources/images/aktiviteter/${aktivitet.name}.png" alt="${aktivitet.name}">
        <div data-role="controlgroup" data-type="horizontal">
            <a href="/" data-role="button" id="cancel">Avbryt</a>
            <a href="/?utfortaktivitet=${aktivitet.id}" data-role="button" id="ok">Ok</a>
        </div>
    </div>
    <%@include file="include/footer.jsp"%>
</div>
<script>
    $('#ok').click(function(){
        var aktivitetId = ${aktivitet.id};
        var personId = localStorage.getItem('username');
        $.post('/aktiviteter/utfortaktivitet', {aktivitet: aktivitetId, person: personId}, function(data, textStatus, jqXHR){
            if(textStatus != 'success'){
                alert("Noe feilet :'(")
            }
        });
        return true;
    })
</script>
</body>
</html>