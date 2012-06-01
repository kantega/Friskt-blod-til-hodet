<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
    <script>
        var username = localStorage.getItem('username');
        if(username == undefined || username == null){
            window.location = '${pageContext.request.contextPath}/login'
        }

        var usernameCookie = getCookie('username');
        if(usernameCookie  == undefined){
            document.cookie='USERNAME' + "=" + username;
        }

        function getCookie(c_name){
            var i,x,y,ARRcookies=document.cookie.split(";");
            for (i=0;i<ARRcookies.length;i++){
                x=ARRcookies[i].substr(0,ARRcookies[i].indexOf("="));
                y=ARRcookies[i].substr(ARRcookies[i].indexOf("=")+1);
                x=x.replace(/^\s+|\s+$/g,"");
                if (x==c_name){
                    return unescape(y);
                }
            }
        }
    </script>
    <style type="text/css">
        @-moz-document url-prefix(){
            .score {
                top: -40px;
            }
        }
    </style>
</head>
<body>

<div id="aktiviteter" data-role="page">
    <div data-role="header">
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
        <div class="loggetinnsom">
            Logget inn som: ${person.name} (<a href="" id="persongruppe">${person.gruppe.name}</a>)
        </div>
        <script>
            $("#persongruppe").click(function(){
                document.location = '${pageContext.request.contextPath}/velgGruppe';
                return false;
            })
        </script>
    </div>
    <div id="status"></div>
    <div data-role="content">
        <ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="false">
            <c:forEach var="aktivitet" items="${aktivitetAndCount}">
                <li>
                    <a name="${aktivitet.key.id}" class="aktivitet" id="${aktivitet.key.id}" href="${pageContext.request.contextPath}/aktiviteter/${aktivitet.key.id}" data-icon="aktivitet-score" data-iconpos="right">${aktivitet.key.name}<span class="score" id="${aktivitet.key.id}"><span class="scorevalue">${aktivitet.value}</span></span></a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <%@include file="include/footer.jsp"%>
</div>
</body>
</html>