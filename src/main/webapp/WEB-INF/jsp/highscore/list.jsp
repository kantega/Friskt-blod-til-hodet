<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="true">
    <c:forEach var="aktivitet" items="${aktivitetAndCount}">
        <li>
            <a class="aktivitet" id="${aktivitet.key.id}" href="/aktiviteter/${aktivitet.key.id}" data-icon="aktivitet-score" data-iconpos="right">${aktivitet.key.name}<span class="score" id="${aktivitet.key.id}">${aktivitet.value}</span>xp</a>
        </li>
    </c:forEach>
</ul>
<c:if test="${myStanding ne null}">
    <div id="myStanding">
        <span id="my">${myStanding}</span>/<span id="total">${total}</span>
    </div>
</c:if>