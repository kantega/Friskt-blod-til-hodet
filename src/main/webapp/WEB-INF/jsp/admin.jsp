<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet – admin</title>
    <%@include file="include/js.jsp"%>
</head>
<body>
<div data-role="page">
    <div data-role="header">
        <h1>Friskt blod til hodet</h1>
        <h2>Admin</h2>
    </div>
    <div data-role="content">
        <div class="status" id="adminstatus"></div>

        <div class="admin" id="aktivitet">
            <form class="adminForm" id="aktivitetForm" method="post" action="/admin/aktivitet">
                <label for="aktivitetname">Aktivitetsnavn</label>
                <input name="name" id="aktivitetname" type="text">
                <input type="submit" value="Lagre">
            </form>

        </div>

        <div class="admin" id="person">
            <form class="adminForm" id="personForm" method="post" action="/admin/person">
                <label for="personname">Personnavn</label>
                <input name="name" id="personname" type="text">
                <label for="username">Brukernavn</label>
                <input name="username" id="username" type="text">
                <label for="avdeling">Avdeling</label>
                <select id="avdeling" name="avdeling" multiple="false">
                    <c:forEach var="avdeling" items="${avdelinger}">
                        <option value="${avdeling.id}">${avdeling.name}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Lagre">
            </form>

        </div>

        <div class="admin" id="avdeling">
            <form class="adminForm" id="avdelingForm" method="post" action="/admin/avdeling">
                <label for="avdelingname">Avdelingsnavn</label>
                <input name="name" id="avdelingname" type="text">
                <label for="foreldreAvdeling">Overavdeling</label>
                <select id="foreldreAvdeling" name="foreldreAvdeling" multiple="false">
                    <c:forEach var="avdeling" items="${avdelinger}">
                        <option value="${avdeling.id}">${avdeling.name}</option>
                    </c:forEach>
                </select>
                <input type="submit" value="Lagre">
            </form>

        </div>
    </div>
</div>
<script type="text/javascript">
    $('.adminForm').submit(function(event){
        event.preventDefault();
        $.post($(this).attr('action'), $(this).serializeArray(), function(data){
            $("#adminstatus").html(data);
        }, 'json');
        return false;
    });
</script>
</body>
</html>