<%@ page session="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<c:if test="${not empty message}">
	<div class="${message.type.cssClass}">${message.text}</div>
</c:if>

<c:url value="/create" var="createUrl" />
<form:form id="create" action="${createUrl}" method="post" modelAttribute="personForm">
	<div class="formInfo">
  		<h2>Create a new Person</h2>
  		<s:bind path="*">
  		<c:choose>
  		<c:when test="${status.error}">
  			<div class="error">Unable to create person. Please fix the errors below and resubmit.</div>
  		</c:when>
  		</c:choose>			
  		</s:bind>
	</div>
		<fieldset>
			<form:label path="firstname">First Name <form:errors path="firstname" cssClass="error" /></form:label>
			<form:input path="firstname" />
			<form:label path="lastname">Last Name <form:errors path="lastname" cssClass="error" /></form:label>
			<form:input path="lastname" />
		</fieldset>
	<p><button type="submit">Sign Up</button></p>
</form:form>
