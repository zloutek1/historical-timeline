<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Home">
    <jsp:attribute name="body">
        <c:if test="${empty studyGroups}">
            <div class="row">
              <div class="jumbotron text-center col-sm-12">
                <h2>You are not a member of any study groups</h2>
              </div>
            </div>
        </c:if>
        <c:if test="${authUser.role eq 'TEACHER'}">
            <div class="row">
              <div class="offset-md-9 col-col-sm-3">
                  <a href="${pageContext.request.contextPath}/studygroup/new" class="btn btn-primary">New study group</a>
              </div>
            </div>
        </c:if>
        <div class="row mt-3">
        <c:forEach items="${studyGroups}" var="studygroup" varStatus="ic">
            <div class="col-md-6 pb-5">
            <div class="card h-100 mb-3">
                <h3 class="card-header"><c:out value="${studygroup.name}"/></h3>
                <div class="card-body p-3">
                    <h5>Timelines:</h5>
                    <ul class="list-group list-group-flush mt-3 mb-3">
                        <c:choose>
                            <c:when test="${empty studygroup.timelines}">
                                <li class="list-group-item">There are no timelines</li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${studygroup.timelines}" var="timeline">
                                    <li class="list-group-item">
                                        <a href="${pageContext.request.contextPath}/timeline/${timeline.id}"><c:out value="${timeline.name}" /></a>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                            <li class="list-group-item">
                                <span>New timeline</span>
                                <a class="btn btn-outline-success float-right" title="New"
                                 href="${pageContext.request.contextPath}/timeline/new?studyGroupId=${studygroup.id}" >
                                    <i class="fas fa-plus" title="Add"></i>
                                </a>
                            </li>
                        </c:if>
                    </ul>

                    <h5>Members:</h5>
                    <ul class="list-group list-group-flush mt-3 mb-3">
                        <c:choose>
                            <c:when test="${empty studygroup.members}">
                                <li>There are no members</li>
                            </c:when>
                            <c:otherwise>
                                <c:forEach items="${studygroup.members}" var="member" varStatus="ic">
                                    <li class="list-group-item"><span><c:out value="${member.firstName} "> </c:out>  <c:out value="${member.lastName}"> </c:out></span>
                                        <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                                            <form method="POST" class="float-right" action="${pageContext.request.contextPath}/studygroup/unregister/${studygroup.id}/${member.id}">
                                                <button class="btn btn-outline-danger" type="submit" title="Unregister"
                                                        onclick="return confirm('Do you really want to unregister user ${member.firstName} ${member.lastName} from study group ${studygroup.name}?')">
                                                    <i class="fas  fa-user-times" title="Delete"></i>
                                                </button>
                                            </form>
                                        </c:if>
                                    </li>
                                </c:forEach>
                            </c:otherwise>
                        </c:choose>
                        <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                        <li class="list-group-item">
                            <form class="ui-widget form-inline justify-content-between" method="POST" action="${pageContext.request.contextPath}/studygroup/addmember?studyGroupId=${studygroup.id}">
                                <input name="email" class="tags mr-3">
                                <button type="submit" class="btn btn-outline-success"  title="New">
                                    <i class="fas fa-user-plus" title="Add"></i>
                                </button>
                            </form>
                        </li>
                        </c:if>
                    </ul>
                </div>

                <c:if test="${(authUser.id eq studygroup.leader.id) or (authUser.role eq 'ADMINISTRATOR')}">
                    <div class="card-footer">
                        <form method="POST" action="${pageContext.request.contextPath}/studygroup/delete/${studygroup.id}">
                            <button class="btn btn-danger ml-3" type="submit" title="Delete"
                                    onclick="return confirm('Do you really want to delete study group ${studygroup.name}?')">
                                Delete study group
                            </button>
                        </form>
                    </div>
                </c:if>
            </div>
            </div>
        </c:forEach>
        </div>
    </jsp:attribute>
    <jsp:attribute name="scripts">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script>
        $( function() {
            $.ajax({
                url: "${pageContext.request.contextPath}/user/search",
                success: function (data) {
                    let members = data.split(",");
                    $( ".tags" ).autocomplete({
                    source: members
                    });
                }
            });
          });
        </script>
    </jsp:attribute>


</my:maintemplate>
