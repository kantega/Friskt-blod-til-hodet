<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet - velg gruppe</title>
    <%@include file="include/js.jsp"%>
    <script>
        $(document).ready(function(){
            $('.gruppe').click(function(){
                var id = $(this).attr('id');
                var username = localStorage.getItem('username');
                $.post('${pageContext.request.contextPath}/velgGruppe', {person: username, gruppe: id}, function(data, status, xhq){
                    if(status === 'success'){
                        document.location = '${pageContext.request.contextPath}/';
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
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
        <h2>Velg gruppe</h2>
    </div>
    <div data-role="content">
        <ul data-role="listview" data-theme="g" data-filter="true">
            <c:forEach var="gruppe" items="${grupper}">
                <li><a href="#" class="gruppe" id="${gruppe.id}">${gruppe.name}</a></li>
            </c:forEach>
        </ul>
    </div>
</div>
</body>
</html>