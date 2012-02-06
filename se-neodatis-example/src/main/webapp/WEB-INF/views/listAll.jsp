<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>

<c:if test="${not empty message}">
    <div>${message}</div>
</c:if>

<table>
  <tr>
    <th>Firstname</th>
    <th>Lastname</th>
  </tr>
<c:if test="${not empty personList}">
	<c:forEach items="${personList}" var="person">
	  <tr>
	    <td>${person.firstname}</td>
	    <td>${person.lastname}</td>
	  </tr>
	</c:forEach>
</c:if>
</table>

<a href="<c:url value="/create" />">New Person</a>
