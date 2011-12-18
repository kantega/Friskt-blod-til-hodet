<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="include/js.jsp"%>
    <script type="text/javascript" src="/resources/js/jquery-ui-1.8.16.custom.min.js"></script>
    <script>
        var username = localStorage.getItem('username');
        if(username == undefined){
            window.location = '/login'
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
                <li>
                    <a class="aktivitet" id="${aktivitet.key.id}" href="/aktiviteter/${aktivitet.key.id}" data-icon="aktivitet-score" data-iconpos="right">${aktivitet.key.name}<span class="score" id="${aktivitet.key.id}">${aktivitet.value}</span></a>
                </li>
            </c:forEach>
        </ul>
    </div>
    <script>
        var urlParam = $.urlParam("utfortaktivitet");
        var scores = $("span.score");
        if(urlParam != 0){
            scores.each(function(index, value){
                var jqvalue = $(value);
                if(jqvalue.attr("id") == urlParam){
                    var score = jqvalue.text();
                    setTimeout(function(){
                        jqvalue.text(parseInt(score) + 1);
                        setTimeout(function(){
                            jqvalue.switchClass( "scoreIncrement", "score", 1000 );
                        }, 300);
                    },1000);
                    jqvalue.switchClass( "score", "scoreIncrement", 1000 );

                    return false;
                }
            })
        }
    </script>
    <%@include file="include/footer.jsp"%>
</div>
</body>
</html>