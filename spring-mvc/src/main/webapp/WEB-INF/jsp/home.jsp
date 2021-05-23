<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Home">
    <jsp:attribute name="body">
        <div class="row">
          <div class="jumbotron text-center col-sm-12">
            <h2>List of available study groups:</h2>
          </div>
        </div>
        <c:if test="${authUser.role eq 'TEACHER'}">
            <div class="row">
              <div class="offset-md-9 col-col-sm-3">
                  <a href="${pageContext.request.contextPath}/studygroup/new" class="btn btn-primary">New study group</a>
              </div>
            </div>
        </c:if>
        <c:forEach items="${studyGroups}" var="studygroup" varStatus="ic">
            <c:if test="${ic.count % 4 == 1}">
                <div class="row mt-3">
            </c:if>
            <div class="card col-sm-6 p-5">
                <div class="card-body">
                    <h3 class="card-title mb-5"><c:out value="${studygroup.name}"/></h3>
                    <h5>Timelines:</h5>

                    <c:choose>
                        <c:when test="${empty studygroup.timelines}">
                            <p>There are no timelines</p>
                        </c:when>
                        <c:otherwise>

                            <ul class="list-group list-group-flush mt-3 mb-3">
                                <c:forEach items="${studygroup.timelines}" var="timeline">
                                    <li class="list-group-item">
                                        <a href="${pageContext.request.contextPath}/timeline/${timeline.id}"><c:out value="${timeline.name}" /></a>
                                    </li>
                                </c:forEach>
                                <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                                <li class="list-group-item">
                                    <span>Add new timeline</span>
                                        <a class="btn btn-outline-success float-right" href="${pageContext.request.contextPath}/timeline/new?studyGroupId=${studygroup.id}" title="New">
                                            <i class="fas fa-plus" title="Add"></i>
                                        </a>
                                </li>
                                </c:if>
                            </ul>
                        </c:otherwise>
                        </c:choose>


                    <h5>Members:</h5>
                    <c:choose>
                        <c:when test="${empty studygroup.members}">
                            <p>There are no members</p>
                        </c:when>
                        <c:otherwise>
                            <ul class="list-group list-group-flush mt-3 mb-3">
                                <c:forEach items="${studygroup.members}" var="member" varStatus="ic">
                                    <li class="list-group-item"><span><c:out value="${member.firstName} "> </c:out>  <c:out value="${member.lastName}"> </c:out></span>
                                        <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR') or (authUser.id == member.id)}">
                                            <form method="POST" action="${pageContext.request.contextPath}/studygroup/unregister/${studygroup.id}/${member.id}">
                                                <button class="btn btn-outline-danger float-right" type="submit" title="Unregister"
                                                        onclick="return confirm('Do you really want to unregister user ${member.firstName} ${member.lastName} from study group ${studygroup.name}?')">
                                                    <i class="fas  fa-user-times" title="Delete"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                    </li>
                                </c:forEach>
                                <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                                <li class="list-group-item">
                                    <span>Add new member</span>
                                    <form method="POST" action="${pageContext.request.contextPath}/timeline/new?studyGroupId=${studygroup.id}">
                                        <button class="btn btn-outline-success float-right" type="submit" title="New">
                                            <i class="fas fa-user-plus" title="Add"></i>
                                        </button>
                                    </form>
                                </li>
                                </c:if>
                            </ul>
                        </c:otherwise>
                    </c:choose>

                  <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                            <form method="POST" action="${pageContext.request.contextPath}/studygroup/delete/${studygroup.id}">
                                <button class="btn btn-danger ml-3" type="submit" title="Delete"
                                        onclick="return confirm('Do you really want to delete study group ${studygroup.name}?')">
                                    Delete study group
                                </button>
                            </form>
                  </c:if>

                </div>
            </div>

            <c:if test="${ic.count % 4 == 1}">
                </div>
            </c:if>
        </c:forEach>
    </jsp:attribute>
</my:maintemplate>
