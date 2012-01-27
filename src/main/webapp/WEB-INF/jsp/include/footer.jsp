<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="footer" data-position="fixed" data-role="footer">
    <nav data-role="navbar">
        <ul>
            <li><a class="navbutton" id="aktiviteterbutton" href="${pageContext.request.contextPath}/" data-role="button" data-theme="a">Aktiviteter</a></li>
            <li><a class="navbutton" id="highscorebutton" href="${pageContext.request.contextPath}/highscore" data-role="button" data-theme="a">Highscore</a></li>
            <li><a class="navbutton" id="infobutton" href="${pageContext.request.contextPath}/information" data-role="button" data-theme="a">Info</a></li>
        </ul>
    </nav>
</div>