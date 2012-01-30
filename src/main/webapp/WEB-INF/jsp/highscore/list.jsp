<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul id="aktivitetlist" data-role="listview" data-theme="g" data-filter="false">
    <c:forEach var="person" items="${personAndCount}" varStatus="status">
        <li class="highscore<c:if test="${status.count eq standing}"> onhighscore</c:if>"><span class="position">${status.count}</span>${person.key.name}<span class="score">${person.value}</span></li>
    </c:forEach>
</ul>
<c:if test="${standing ne null}">
    <div id="myStanding">
        <span id="my">${standing}</span>/<span id="total">${total}</span>
    </div>
</c:if>