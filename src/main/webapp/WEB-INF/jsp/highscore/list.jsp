<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<ul id="aktivitetlist" class="ui-listview" data-role="listview" data-theme="g" data-filter="false">
    <c:forEach var="person" items="${personAndCount}" varStatus="status">
        <li class="highscore<c:if test="${status.count eq standing}"> onhighscore</c:if> ui-li ui-li-static ui-body-g"><span class="position">${status.count}</span>${person.key.name}<span class="score"><span class="scorevalue"><fmt:formatNumber type="number" maxFractionDigits="1" value="${person.value}" /></span></span></li>
    </c:forEach>
</ul>
<c:if test="${standing ne null and standing ge 10}">
    <section id="myStanding">
        <h2>Din plassering:</h2>
        <span id="my">${standing}</span>/<span id="total">${total}</span>
    </section>
</c:if>