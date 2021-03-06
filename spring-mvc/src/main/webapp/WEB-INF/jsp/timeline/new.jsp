<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="New timeline">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post" action="${pageContext.request.contextPath}/timeline/new?studyGroupId=${studyGroupId}"
                   modelAttribute="timeline"
                   cssClass="form-horizontal mt-5 p-5 border bg-light">
                    <h1>Create timeline</h1>

                    <c:if test="${not empty new_timeline_failure}">
                        <div class="alert alert-danger" role="alert"><c:out value="${new_timeline_failure}"/></div>
                    </c:if>

                   <div class="form-group row">
                       <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                       <div class="col-sm-10">
                           <form:input path="name" cssClass="form-control"/>
                           <form:errors path="name" cssClass="help-block"/>
                       </div>
                   </div>
                    <div class="form-group row">
                        <form:label path="fromDate" cssClass="col-sm-2 control-label">From</form:label>
                        <div class="col-sm-10">
                            <form:input path="fromDate" cssClass="form-control datepicker"/>
                            <form:errors path="fromDate" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="toDate" cssClass="col-sm-2 control-label">To</form:label>
                        <div class="col-sm-10">
                            <form:input path="toDate" cssClass="form-control datepicker"/>
                            <form:errors path="toDate" cssClass="help-block"/>
                        </div>
                    </div>

                    <button class="btn btn-primary ml-3" type="submit">Create</button>
                </form:form>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="<c:url value="/resources/js/datepicker.js" />"></script>
    </jsp:attribute>
</my:maintemplate>