<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Add study group">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post" action="${pageContext.request.contextPath}/studygroup/new"
                           modelAttribute="studyGroup"
                           cssClass="form-horizontal mt-5 p-5 border bg-light">
                    <h1>New study group</h1>
                    <div class="form-group row">
                        <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control"/>
                            <form:errors path="name" cssClass="help-block"/>
                        </div>

                  <c:if test="${not empty new_studygroup_failure}">
                      <div class="alert alert-danger" role="alert"><c:out value="${new_studygroup_failure}"/></div>
                  </c:if>
                   <div class="row">
                       <a href="${pageContext.request.contextPath}/home"
                          class="btn btn-secondary ml-3">Back</a>
                       <button class="btn btn-primary ml-3" type="submit">Create</button>
                   </div>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</my:maintemplate>