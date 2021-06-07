<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Timeline">
    <jsp:attribute name="head">
        <style>
            .timeline .date {
                padding-top: .75rem;
                color: gray;
            }

            .timeline .event {
                border-left: 3px solid royalblue;
                background-color: aliceblue;
            }

            .timeline .event::after {
                content: '';
                position: absolute;
                top: 1rem;
                left: -.5rem;
                width: 1rem;
                height: 1rem;
                border: 3px solid royalblue;
                border-radius: 100%;
                background-color: white;
            }

            .comment .date {
                color: gray;
            }

            .image {
                max-width: 10em;
            }
        </style>
    </jsp:attribute>
    <jsp:attribute name="body">
        <div class="timeline container">
            <div class="text-center">
                <h1><c:out value="${timeline.name}" /></h1>
                <p><my:localdate value = "${timeline.fromDate}" /> - <my:localdate value="${timeline.toDate}" /></p>
            </div>

            <div class="events container">
                <div class="row justify-content-end mb-2">
                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/timeline/${timeline.id}/add/event">Add event</a>
                </div>

                <c:choose>
                <c:when test="${empty timeline.events}">
                    <p class="text-center"> There are no events yet!</p>
                </c:when>

                <c:otherwise>
                    <c:forEach items="${timeline.events}" var="event">
                        <div class="row">
                            <div class="date pr-3 w-25 text-right">
                                <my:localdate value = "${event.date}" />
                            </div>
                            <div class="event py-2 col container">
                                <div class="row">
                                    <h3 class="col d-inline"><c:out value="${event.name}" /></h3>
                                    <div class="dropdown text-right col-1">
                                        <a type="button" id="eventDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-ellipsis-v"></i></a>
                                        <div class="dropdown-menu dropdown-primary" aria-labelledby="eventDropdownMenuButton">
                                            <a class="dropdown-item" href="/pa165/event/update/${event.id}?timelineId=${timeline.id}">Edit</a>
                                            <button type="button" class="dropdown-item btn btn-link text-danger" data-toggle="modal" data-target="#removeModal" data-event-id="${event.id}">Remove</button>
                                        </div>
                                    </div>
                                </div>
                                <c:if test="${event.image ne null}">
                                    <div class="image float-right">
                                        <img src="${pageContext.request.contextPath}/event/${event.id}/image" class="img-thumbnail" />
                                    </div>
                                </c:if>
                                <p><c:out value="${event.description}" /></p>
                                <p><i class="fas fa-map-pin"></i> <c:out value="${event.location}" /></p>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
                </c:choose>
            </div>

            <div class="comments container mt-4">
                <div class="row">
                    <div class="w-25">
                        <!-- padding -->
                    </div>
                    <div class="col container">
                        <h2 class="mt-5">Comments</h2>
                        <form:form method="post" action="${pageContext.request.contextPath}/comment/new"
                                   modelAttribute="comment"
                                   cssClass="form-horizontal mb-4">
                            <form:hidden path="timelineId"/>
                            <form:hidden path="userId" value="${authUser.id}"/>
                            <div class="input-group">
                                <form:input path="text" cssClass="form-control"/>
                                <form:errors path="text" cssClass="help-block"/>
                                <div class="input-group-append">
                                    <div class="align-self-end">
                                        <button class="btn btn-primary" type="submit"><i class="fas fa-paper-plane"></i></button>
                                    </div>
                                </div>
                            </div>
                        </form:form>

                        <c:forEach items="${timeline.comments}" var="comment">
                            <div class="comment container">
                                <div class="row">
                                    <div class="row col align-items-end">
                                        <h4 class="m-0"><c:out value="${comment.author.firstName} ${comment.author.lastName}" /></h4>
                                        <p class="date m-0 ml-2"><my:localdatetime value = "${comment.time}" /></p>
                                    </div>
                                    <div class="dropdown text-right col-1">
                                        <a type="button" id="commentDropdown" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-ellipsis-v"></i></a>
                                        <div class="dropdown-menu dropdown-primary" aria-labelledby="commentDropdownMenuButton">
                                            <a class="dropdown-item" href="${pageContext.request.contextPath}/comment/update/${comment.id}?timelineId=${timeline.id}">Edit</a>
                                            <form:form action="${pageContext.request.contextPath}/comment/delete/${comment.id}?timelineId=${timeline.id}" method="delete" cssClass="submit">
                                                <button type="submit" class="dropdown-item btn btn-link text-danger">Remove</button>
                                            </form:form>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <p class="mb-1"><c:out value="${comment.text}" /></p>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>

            <div class="modal fade" id="removeModal" tabindex="-1" role="dialog" aria-labelledby="removeModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="removeModalLabel">Remove event</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            Are you sure you want to remove this event?
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <form:form action="#" method="post" cssClass="submit">
                                <button type="submit" class="btn btn-danger">Remove</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="scripts">
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script src="<c:url value="/resources/js/datepicker.js" />"></script>
        <script type="application/javascript">
            $(document).on('show.bs.modal', '#removeModal', function (event) {
                let button = $(event.relatedTarget)
                let eventId = button.data('event-id')
                let modal = $(this)
                let url = '${pageContext.request.contextPath}/timeline/${timeline.id}/remove/event/' + eventId;
                modal.find('form.submit').attr('action', url);
            })
        </script>
    </jsp:attribute>
</my:maintemplate>