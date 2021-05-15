<!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html lang="${pageContext.request.locale}">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
    integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
    crossorigin="anonymous">
</head>
<body>
    <div class="container vh-100">
        <div class="row justify-content-center">
            <form:form method="post" action="${pageContext.request.contextPath}/auth/login"
                       modelAttribute="user"
                       cssClass="form-horizontal mt-5 p-5 border col-lg-6 col-md-9 bg-light">
                <h1>Login</h1>
                <div class="form-group">
                    <form:label path="email" cssClass="col-sm-2 control-label">Email</form:label>
                    <div class="col-sm-10">
                        <form:input path="email" cssClass="form-control"/>
                        <form:errors path="email" cssClass="help-block"/>
                    </div>
                </div>
               <div class="form-group">
                   <form:label path="password" cssClass="col-sm-2 control-label">Password</form:label>
                   <div class="col-sm-10">
                       <form:input type="password" path="password" cssClass="form-control"/>
                       <form:errors path="password" cssClass="help-block"/>
                   </div>
               </div>
               <c:if test="${not empty login_failure}">
                   <div class="alert alert-danger" role="alert"><c:out value="${login_failure}"/></div>
               </c:if>
                <button class="btn btn-primary" type="submit">Login</button>
            </form:form>
        </div>
    </div>
</body>
</html>