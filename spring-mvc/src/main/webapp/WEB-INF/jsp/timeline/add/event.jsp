<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Add event to timeline">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post" action="${pageContext.request.contextPath}/timeline/add/event"
                           modelAttribute="addEvent"
                           cssClass="form-horizontal mt-5 p-5 border bg-light">
                    <h1>Add event to timeline</h1>

                    <form:hidden path="timelineId"/>

                    <div class="form-group row">
                       <form:label path="eventId" cssClass="col-sm-2 control-label">Event</form:label>
                       <div class="col-sm-10">
                           <form:select path="eventId" cssClass="form-control">
                                <c:forEach items="${events}" var="event">
                                    <form:option value="${event.id}" label="${event.name}"/>
                                </c:forEach>
                           </form:select>
                           <form:errors path="eventId" cssClass="help-block"/>
                       </div>
                    </div>

                    <button class="btn btn-primary" type="submit">Add</button>

                    <div class="my-2">-- or --</div>

                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/event/new?timelineId=${timeline.id}" role="button">Create new event</a>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</my:maintemplate>