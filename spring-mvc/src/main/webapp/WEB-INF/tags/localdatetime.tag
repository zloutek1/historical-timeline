<%@ tag body-content="empty" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="value" required="true" type="java.time.LocalDateTime" %>
<%@ attribute name="pattern" required="false" type="java.lang.String" %>

<c:if test="${empty pattern}">
    <c:set var="pattern" value="dd.MM.yyyy hh:mm"/>
</c:if>

<fmt:parseDate value="${value}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both"/>
<fmt:formatDate value="${parsedDateTime}" pattern="${pattern}"/>
