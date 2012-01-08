<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="false">
    <c:forEach var="person" items="${personAndCount}">
        <li>${person.key.name}<span class="score">${person.value} xp</span>
        </li>
    </c:forEach>
</ul>
<c:if test="${myStanding ne null}">
    <div id="myStanding">
        <span id="my">${myStanding}</span>/<span id="total">${total}</span>
    </div>
</c:if>