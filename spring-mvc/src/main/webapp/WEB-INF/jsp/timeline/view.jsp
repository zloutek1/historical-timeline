<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Timeline">
    <jsp:attribute name="body">
        <div class="container">
            <h1><c:out value="${timeline.name}" /></h1>
            <p><c:out value="${timeline.fromDate} - ${timeline.toDate}" /></p>
            <c:choose>
            <c:when test="${empty timeline.events}">
                <p> There are no events yet!</p>
            </c:when>

            <c:otherwise>
                <c:forEach items="${timeline.events}" var="event">
                    <div class="row">
                        <div class="col">
                            <c:out value="${event.date}" />
                        </div>
                        <div class="col">
                            <c:out value="${event.name}" />
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
            </c:choose>
        </div>
    </jsp:attribute>
</my:maintemplate>