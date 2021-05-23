<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Change password">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post" action="${pageContext.request.contextPath}/user/password"
                           modelAttribute="passwords"
                           cssClass="form-horizontal mt-5 p-5 border bg-light">
                    <h1>Change password</h1>
                    <div class="form-group row">
                        <form:label path="oldPassword" cssClass="col-sm-4 control-label">Old password</form:label>
                        <div class="col-sm-8">
                            <form:input path="oldPassword" type="password" cssClass="form-control"/>
                            <form:errors path="oldPassword" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="newPassword" cssClass="col-sm-4 control-label">New password</form:label>
                        <div class="col-sm-8">
                            <form:input path="newPassword" type="password" cssClass="form-control"/>
                            <form:errors path="newPassword" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="newPasswordRepeated" cssClass="col-sm-4 control-label">New password (again)</form:label>
                        <div class="col-sm-8">
                            <form:input path="newPasswordRepeated" type="password" cssClass="form-control"/>
                            <form:errors path="newPasswordRepeated" cssClass="help-block"/>
                        </div>
                    </div>
                  <c:if test="${not empty password_failure}">
                      <div class="alert alert-danger" role="alert"><c:out value="${password_failure}"/></div>
                  </c:if>
                   <div class="row">
                    <button class="btn btn-primary ml-3" type="submit">Update</button>
                   </div>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</my:maintemplate>