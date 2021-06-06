<%@ page contentType="text/html;charset=UTF-8" pageEncoding="utf-8" trimDirectiveWhitespaces="true" session="true" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="my" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<my:maintemplate title="Users">
    <jsp:attribute name="body">
        <div class="row">
           <table class="table table-bordered">
             <thead class="thead-dark">
               <tr>
                 <th>First name</th>
                 <th>Last name</th>
                 <th>Email</th>
                 <th>Role</th>
                 <th>Actions</th>
               </tr>
             </thead>
             <tbody>
                <c:forEach items="${users}" var="user">
                   <tr>
                     <td><c:out value="${user.firstName}"/></td>
                     <td><c:out value="${user.lastName}"/></td>
                     <td><c:out value="${user.email}"/></td>
                     <td><c:out value="${user.role}"/></td>
                     <td>
                     <form:form method="post" action="${pageContext.request.contextPath}/user/delete/${user.id}">
                        <button type="submit" class="btn btn-outline-danger" title="Delete"
                        onclick="return confirm('Do you really want to delete user ${user.firstName}  ${user.lastName}?')">
                            <i class="fas fa-trash" title="Delete"></i>
                        </button>
                        <a class="btn btn-outline-info" title="Edit" href="${pageContext.request.contextPath}/user/edit/${user.id}">
                            <i class="fas fa-edit" title="Edit"></i>
                        </a>
                     </form:form>

                     </td>
                   </tr>
                </c:forEach>
             </tbody>
           </table>
        </div>
        <div class="row">
         <div class="col-sm-3">
           <a href="${pageContext.request.contextPath}/user/new" class="btn btn-primary">Add user</a>
         </div>
        </div>
    </jsp:attribute>
</my:maintemplate>