<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML>
<html>
<head>
    <title>Friskt blod til hodet</title>
    <%@include file="../include/js.jsp"%>
</head>
<body>
<div data-role="page">
    <div data-role="header">
        <img class="headerimage" src="${pageContext.request.contextPath}/resources/images/header.png" title="Blod til hodet" alt="Blod til hodet">
        <h2>Highscore</h2>
    </div>
    <div data-role="content">
        <nav id="velgHighscore">
            <fieldset data-role="controlgroup" data-type="horizontal">
                <label for="velggruppe" class="ui-hidden-accessible">Velg gruppe</label>
                <select id="velggruppe" data-role="none">
                    <option value="Totalt">Totalt</option>
                    <option value="Grupper">Grupper</option>
                    <option value="Mingruppe">Innad i min gruppe</option>
                </select>

                <label for="velgaktivitet" class="ui-hidden-accessible">Velg aktivitet</label>
                <select id="velgaktivitet" data-role="none">
                    <option value="Alle">Alle</option>
                    <c:forEach var="aktivitet" items="${aktiviteter}">
                        <option value="${aktivitet.id}">${aktivitet.name}</option>
                    </c:forEach>
                </select>
            </fieldset>
            <script>
                var velgAktivitetSelect = $('#velgaktivitet');
                var velgGruppeSelect = $('#velggruppe');

                var onSelectHandler = function(event){
                    var gruppe = velgGruppeSelect.val();
                    var aktivitet = velgAktivitetSelect.val();

                    var url = "${pageContext.request.contextPath}/highscore/" + gruppe + "/" + aktivitet;
                    $("#highscoreList").load(url);
                };
                velgAktivitetSelect.change(onSelectHandler);
                velgGruppeSelect.change(onSelectHandler);
            </script>
        </nav>
        <div id="highscoreList">
            <%@include file="list.jsp"%>
        </div>
    </div>
    <%@include file="../include/footer.jsp"%>
</div>
</body>
</html>