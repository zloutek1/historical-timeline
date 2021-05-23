<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Edit comment">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <c:choose>
                <c:when test="${not empty comment_not_found}">
                    <div class="alert alert-danger" role="alert"><c:out value="${comment_not_found}"/></div>
                    <a href="${pageContext.request.contextPath}/timeline/${timelineId}" class="btn btn-secondary ml-3">Back</a>
                </c:when>
                <c:otherwise>
                    <form:form method="put" action="${pageContext.request.contextPath}/comment/update?timelineId=${timelineId}"
                               modelAttribute="comment"
                               cssClass="form-horizontal mt-5 p-5 border bg-light">
                        <h1>Edit comment</h1>

                        <form:hidden path="id"/>

                        <div class="form-group row">
                           <form:label path="text" cssClass="col-sm-2 control-label">Text</form:label>
                           <div class="col-sm-10">
                               <form:input path="text" cssClass="form-control"/>
                               <form:errors path="text" cssClass="help-block"/>
                           </div>
                        </div>

                        <button class="btn btn-primary ml-3" type="submit">Edit</button>
                    </form:form>
                </c:otherwise>
                </c:choose>
            </div>
        </div>
    </jsp:attribute>
</my:maintemplate>