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
                     <td></td>
                   </tr>
                </c:forEach>
             </tbody>
           </table>
        </div>
    </jsp:attribute>
</my:maintemplate>