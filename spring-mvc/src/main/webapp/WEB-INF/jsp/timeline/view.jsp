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
                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addEventModal">Add event</button>
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
                                            <a class="dropdown-item" href="/pa165/event/edit/${event.id}">Edit</a>
                                            <button type="button" class="dropdown-item btn btn-link text-danger" data-toggle="modal" data-target="#removeModal" data-event-id="${event.id}">Remove</button>
                                        </div>
                                    </div>
                                </div>
                                <p><c:out value="${event.description}" /></p>
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
                                                <button type="submit" class="dropdown-item btn btn-link text-danger">Delete</button>
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

            <div class="modal fade" id="addEventModal" tabindex="-1" role="dialog" aria-labelledby="addEventModalLabel" aria-hidden="true">
                <div class="modal-dialog" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="addEventModalLabel">Add event</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body container">
                            <ul class="nav nav-tabs">
                                <li class="active"><a class="nav-link" data-toggle="tab" href="#addEvent-all">Load all</a></li>
                                <li><a class="nav-link" data-toggle="tab" href="#addEvent-between">Between dates</a></li>
                                <li><a class="nav-link" data-toggle="tab" href="#addEvent-create">Create new</a></li>
                            </ul>
                            <div class="tab-content">
                                <div class="tab-pane fade show active" id="addEvent-all">
                                    <div class="input-group mb-3">
                                        <button class="btn btn-outline-secondary" type="button" id="button-search-by-name">Load all</button>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="addEvent-between">
                                    <div class="input-group mb-3">
                                        <input type="text" id="event-by-time-from-input" class="form-control datepicker" placeholder="From date" aria-label="From date" aria-describedby="button-search-by-time">
                                        <input type="text" id="event-by-time-to-input" class="form-control datepicker" placeholder="To date" aria-label="To date" aria-describedby="button-search-by-time">
                                        <button class="btn btn-outline-secondary" type="button" id="button-search-by-time">Search</button>
                                    </div>
                                </div>
                                <div class="tab-pane fade" id="addEvent-create">
                                    <a class="btn btn-primary" href="${pageContext.request.contextPath}/event/new?timelineId=${timeline.id}" role="button">Create new event</a>
                                </div>
                            </div>
                        </div>
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
                                <button type="submit" class="btn btn-danger">Delete</button>
                            </form:form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </jsp:attribute>

    <jsp:attribute name="scripts">
            <script type="application/javascript">
                $(document).on('show.bs.modal', '#removeModal', function (event) {
                    let button = $(event.relatedTarget)
                    let eventId = button.data('event-id')
                    let modal = $(this)
                    modal.find('form.submit').attr('action', '${pageContext.request.contextPath}/event/delete/' + eventId);
                })

                $(document).on('show.bs.modal', '#addEventModal', function (event) {
                    let modal = $(this)
                })

                $(document).on('click', '#button-search-by-name', function (event) {
                    $.ajax({
                        type : "GET",
                        url : "${pageContext.request.contextPath}/rest/events",
                        data : {},
                        success: function(data){
                            console.log(data);
                        }
                    });
                })

                $(document).on('click', '#button-search-by-time', function (event) {
                    $.ajax({
                        type : "GET",
                        url : "${pageContext.request.contextPath}/rest/events",
                        data : {
                            "from" : $('#event-by-time-from-input').val(),
                            "to" : $('#event-by-time-to-input').val()
                        },
                        success: function(data){
                            console.log(data);
                        }
                    });
                })
            </script>
    </jsp:attribute>
</my:maintemplate>