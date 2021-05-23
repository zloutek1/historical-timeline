<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Edit user">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post" action="${pageContext.request.contextPath}/user/edit/${id}"
                           modelAttribute="user"
                           cssClass="form-horizontal mt-5 p-5 border bg-light">
                    <h1>Edit user</h1>
                    <div class="form-group row">
                        <form:label path="firstName" cssClass="col-sm-2 control-label">First name</form:label>
                        <div class="col-sm-10">
                            <form:input path="firstName" cssClass="form-control"/>
                            <form:errors path="firstName" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="lastName" cssClass="col-sm-2 control-label">Last name</form:label>
                        <div class="col-sm-10">
                            <form:input path="lastName" cssClass="form-control"/>
                            <form:errors path="lastName" cssClass="help-block"/>
                        </div>
                    </div>
                   <div class="form-group row">
                       <form:label path="role" cssClass="col-sm-2 control-label">Role</form:label>
                       <div class="col-sm-10">
                           <form:select path="role" cssClass="form-control">
                                <form:options/>
                           </form:select>
                           <form:errors path="role" cssClass="help-block"/>
                       </div>
                   </div>
                   <div class="row">
                       <a href="${pageContext.request.contextPath}/user"
                        class="btn btn-secondary ml-3">Back</a>
                    <button class="btn btn-primary ml-3" type="submit">Edit</button>
                   </div>
                </form:form>
            </div>
        </div>
    </jsp:attribute>
</my:maintemplate>
