<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Event">
    <jsp:attribute name="body">
        <div class="row justify-content-center">
            <div class="col-sm-6">
                <form:form method="post"
                           modelAttribute="event"
                           cssClass="form-horizontal mt-5 p-5 border bg-light"
                           enctype="multipart/form-data">
                    <h1>Event</h1>
                    <div class="form-group row">
                        <form:label path="name" cssClass="col-sm-2 control-label">Name</form:label>
                        <div class="col-sm-10">
                            <form:input path="name" cssClass="form-control"/>
                            <form:errors path="name" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="date" cssClass="col-sm-2 control-label">Date</form:label>
                        <div class="col-sm-10">
                            <form:input path="date" cssClass="form-control datepicker"/>
                            <form:errors path="date" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="location" cssClass="col-sm-2 control-label">Location</form:label>
                        <div class="col-sm-10">
                            <form:input path="location" cssClass="form-control"/>
                            <form:errors path="location" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="description" cssClass="col-sm-2 control-label">Description</form:label>
                        <div class="col-sm-10">
                            <form:input path="description" cssClass="form-control"/>
                            <form:errors path="description" cssClass="help-block"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <form:label path="image" cssClass="col-sm-2 control-label">Image</form:label>
                        <div class="col-sm-10">
                            <input type="file" name="image" accept="image/png, image/jpeg" />
                            <form:errors path="image" cssClass="help-block"/>
                        </div>
                    </div>
                   <div class="row">
                       <button class="btn btn-primary ml-3" type="submit">Submit</button>
                   </div>
                </form:form>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="<c:url value="/resources/js/datepicker.js"/>"></script>>
    </jsp:attribute>
</my:maintemplate>
