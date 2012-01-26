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
            <form class="adminForm" id="aktivitetForm" method="post" action="${pageContext.request.contextPath}/admin/aktivitet">
                <fieldset>
                    <legend>Aktivitet</legend>
                    <label for="aktivitetname">Aktivitetsnavn</label>
                    <input name="name" id="aktivitetname" type="text">
                    <label for="aktivitettype">Aktivitetstype</label>
                    <select name="aktivitetsType" id="aktivitettype">
                        <c:forEach var="aktivitettype" items="${aktivitetsTyper}">
                            <option value="${aktivitettype}">${aktivitettype}</option>
                        </c:forEach>
                    </select>
                    <label for="mendgedescription" class="mengdeDescription">Beskrivelse av mengdeenhet(feks etasjer)</label>
                    <input name="mengdeDescription" id="mendgedescription" class="mengdeDescription" type="text">
                    <input type="submit" value="Lagre">
                </fieldset>
            </form>

        </div>

        <div class="admin" id="person">
            <form class="adminForm" id="personForm" method="post" action="${pageContext.request.contextPath}/admin/person">
                <fieldset>
                    <legend>Person</legend>

                    <label for="personname">Personnavn</label>
                    <input name="name" id="personname" type="text">
                    <label for="username">Brukernavn</label>
                    <input name="username" id="username" type="text">
                    <label for="gruppeInput">Gruppe</label>
                    <select id="gruppeInput" name="gruppe" multiple="false">
                        <c:forEach var="gruppe" items="${grupper}">
                            <option value="${gruppe.id}">${gruppe.name}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Lagre">
                </fieldset>
            </form>

        </div>

        <div class="admin" id="gruppe">
            <form class="adminForm" id="gruppeForm" method="post" action="${pageContext.request.contextPath}/admin/gruppe">
                <fieldset>
                    <legend>Gruppe</legend>

                    <label for="gruppename">Gruppesnavn</label>
                    <input name="name" id="gruppename" type="text">
                    <label for="foreldreGruppe">Overgruppe</label>
                    <select id="foreldreGruppe" name="foreldreGruppe" multiple="false">
                        <c:forEach var="gruppe" items="${grupper}">
                            <option value="${gruppe.id}">${gruppe.name}</option>
                        </c:forEach>
                    </select>
                    <input type="submit" value="Lagre">
                </fieldset>
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
    $('#aktivitettype').change(function(event){
        if($('#aktivitettype').val() == "MengdeAktivitet"){
            $('.mengdeDescription').show();
        }
    })
</script>
</body>
</html>