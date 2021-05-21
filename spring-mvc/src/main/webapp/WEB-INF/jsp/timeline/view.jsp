<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Timeline">
    <jsp:attribute name="head">
        <style type="text/css">
            .timeline .date {
                padding-top: .75rem;
                color: gray;
            }

            .timeline .event {
                border-left: 3px solid royalblue;
                background-color: aliceblue;
            }

            .timeline .event::after {
                content: '';
                position: absolute;
                top: 1rem;
                left: -.5rem;
                width: 1rem;
                height: 1rem;
                border: 3px solid royalblue;
                border-radius: 100%;
                background-color: white;
            }

            .comment .date {
                color: gray;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="timeline container">
            <div class="text-center">
                <h1><c:out value="${timeline.name}" /></h1>
                <p><c:out value="${timeline.fromDate} - ${timeline.toDate}" /></p>
            </div>

            <c:choose>
            <c:when test="${empty timeline.events}">
                <p class="text-center"> There are no events yet!</p>
            </c:when>

            <c:otherwise>
                <c:forEach items="${timeline.events}" var="event">
                    <div class="row">
                        <div class="date pr-3 w-25 text-right">
                            <c:out value="${event.date}" />
                        </div>
                        <div class="event py-2 col">
                            <h3><c:out value="${event.name}" /></h3>
                            <p><c:out value="${event.description}" /></p>
                        </div>
                    </div>
                </c:forEach>
            </c:otherwise>
            </c:choose>

            <h2 class="mt-5">Comments</h2>
            <form:form method="post" action="${pageContext.request.contextPath}/comment/new"
                       modelAttribute="comment"
                       cssClass="form-horizontal">
                <div class="form-group">
                    <form:textarea path="text" cssClass="form-control p-1 border bg-light"/>
                    <form:errors path="text" cssClass="help-block"/>
                </div>
                <button class="btn btn-primary" type="submit"><i class="fas fa-paper-plane"></i></button>
            </form:form>

            <div class="comments container mt-2">
                <c:forEach items="${timeline.comments}" var="comment">
                    <div class="comment container mr-1">
                        <div class="row align-items-end">
                            <h4 class="m-0"><c:out value="${comment.author.firstName} ${comment.author.lastName}" /></h4>
                            <p class="date m-0 ml-2"><c:out value="${comment.time}" /></p>
                        </div>
                        <div class="row">
                            <p><c:out value="${comment.text}" /></p>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>
    </jsp:attribute>
</my:maintemplate>