<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
    <script>
        $(function(){
            $("#loginform").submit(function(event){
                event.preventDefault();
                var username = $("#username").val();

                $.get('${pageContext.request.contextPath}/doesPersonExist', {username:username}, function(person){
                    if (person) {
                        localStorage.setItem('username', username);
                        if(!person.gruppe){
                            document.location = '${pageContext.request.contextPath}/velgGruppe';
                        }else{
                            document.location = '${pageContext.request.contextPath}/';
                        }
                    } else {
                        alert('Fant ingen bruker med det brukernavnet')
                    }
                }, 'json');

                return false;
            })
        });
    </script>
</head>
<body>
<div id="login" data-role="page">
    <div data-role="header">
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
    </div>
    <div data-role="content">
        <form id="loginform">
            <label for="username">Kantega-brukernavn</label>
            <input type="text" name="username" id="username" />
            <input type="submit" value="Logg inn">
        </form>
    </div>
</div>

</body>
</html>