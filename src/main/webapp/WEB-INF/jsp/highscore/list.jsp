<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="false">
    <c:forEach var="person" items="${personAndCount}">
        <li class="highscore">${person.key.name}<span class="score">${person.value}</span>
        </li>
    </c:forEach>
</ul>
<c:if test="${standing ne null}">
    <div id="myStanding">
        <span id="my">${standing}</span>/<span id="total">${total}</span>
    </div>
</c:if>