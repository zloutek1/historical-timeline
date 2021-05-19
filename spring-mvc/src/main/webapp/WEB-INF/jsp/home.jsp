<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Home">
    <jsp:attribute name="body">
        <div class="jumbotron text-center">
            <h1>Home page</h1>
            <p>List of available study groups</p>
        </div>

        <div class="container">
            <div class="row">
            <c:forEach items="${studyGroups}" var="studygroup" varStatus="ic">
                    <div class="col-sm-6">
                        <h3> <c:out value="${ic.count}"/>. <c:out value="${studygroup.name}"/></h3>

                        <h5>Timelines</h5>
                        <c:choose>
                        <c:when test="${empty studygroup.timelines}">
                                <p> There is no timeline yet!</p>
                            </c:when>

                        <c:otherwise>
                        <div class="col-sm-6">
                            <table class="table">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${studygroup.timelines}" var="timeline" varStatus="ic">
                                <tr>
                                    <td><c:out value="${ic.count}"/>. <c:out value="${timeline.name}" /></td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        </c:otherwise>
                        </c:choose>


                        <h5>Members:</h5>
                        <c:choose>
                            <c:when test="${empty studygroup.members}">
                                <p> There is no member yet!</p>
                            </c:when>

                            <c:otherwise>
                                <div class="col-sm-6">
                                    <table class="table">
                                        <thead>
                                        <tr>
                                            <th>Firstname</th>
                                            <th>Lastname</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach items="${studygroup.members}" var="member" varStatus="ic">
                                        <tr>
                                            <td>${member.firstName}</td>
                                            <td>${member.lastName}</td>
                                        </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <c:if test="${authUser.role eq 'STUDENT'}">
                        <div class="col-sm-3">
                            <a href="${pageContext.request.contextPath}/todo/path" class="btn btn-primary">Register to study group</a>
                        </div>
                        </c:if>

                    </div>
    </c:forEach>

        </div>
        <div class="row">
                <c:if test="${authUser.role eq 'TEACHER'}">
                <div class="col-sm-3">
                    <a href="${pageContext.request.contextPath}/studygroup/new" class="btn btn-primary">Create new study group</a>
                </div>
                </c:if>

        </div>

            </div>

    </jsp:attribute>
</my:maintemplate>
