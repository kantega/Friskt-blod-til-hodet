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
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
        <h2>${aktivitet.name}</h2>
    </div>
    <div data-role="content">
        <img src="${pageContext.request.contextPath}/resources/images/aktiviteter/${aktivitet.name}.png" alt="${aktivitet.name}">
        <form id="aktivitetForm">
            <c:if test="${aktivitet.aktivitetsType eq 'MengdeAktivitet'}">
                <div data-role="fieldcontain">
                    <fieldset data-role="controlgroup" data-type="horizontal" >
                        <label for="mengde">Mengde</label>
                        <input name="mengde" id="mengde" type="number" value="1">
                        <c:if test="${not empty aktivitet.mengdeDescription}"><label for="mengde">${aktivitet.mengdeDescription}</label></c:if>
                    </fieldset>
                </div>
            </c:if>
            <c:if test="${aktivitet.aktivitetsType eq 'KonkurranseAktivitet'}">
                <div data-role="fieldcontain">
                    <fieldset data-role="controlgroup" data-type="horizontal" >
                        <legend>Resultat</legend>
                        <input type="radio" name="winner" id="isWinner" value="true"/>
                        <label for="isWinner">Vant</label>
                        <input type="radio" name="winner" id="isNotWinner" value="false"/>
                        <label for="isNotWinner">Tapte</label>
                    </fieldset>
                </div>
            </c:if>
            <input type="hidden" name="aktivitet" value="${aktivitet.id}">
            <input type="hidden" name="person" id="person" value="">
        </form>
        <div data-role="controlgroup" data-type="horizontal">
            <a href="${pageContext.request.contextPath}/aktiviteter" data-ajax="false" data-role="button" id="cancel">Avbryt</a>
            <a href="${pageContext.request.contextPath}/aktiviteter?utfortaktivitet=${aktivitet.id}" data-ajax="false" data-role="button" id="ok">Ok</a>
        </div>
    </div>
    <script>
        $(function(){
            $('#ok').click(function(){
                var personId = localStorage.getItem('username');
                $("#person").val(personId);
                $.post('${pageContext.request.contextPath}/aktiviteter/utfortAktivitet', $("#aktivitetForm").serialize(), function(data, textStatus, jqXHR){
                    if(textStatus != 'success'){
                        alert("Noe feilet :'(")
                    }
                });
                return true;
            });

            $('#aktivitetForm').submit(function(event){
                event.preventDefault();
                return false;
            })
        });
    </script>
    <%@include file="include/footer.jsp"%>
</div>
</body>
</html>