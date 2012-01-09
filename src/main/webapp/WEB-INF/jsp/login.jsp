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

                $.get('/doesPersonExist', {username:username}, function(person){
                    if (person) {
                        localStorage.setItem('username', username);
                        if(!person.gruppe){
                            document.location = '/velgGruppe';
                        }else{
                            document.location = '/';
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
        <h1>Friskt blod til hodet</h1>
        <h2>Logg inn</h2>
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