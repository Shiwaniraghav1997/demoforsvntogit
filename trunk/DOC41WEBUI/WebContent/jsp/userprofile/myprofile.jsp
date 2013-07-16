<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="edit" 				component="useradmin"
activeTopNav="myProfile" 	activeNav="myProfile" 
title="My Profile">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<form:form commandName="user" action="saveprofile" method="post">
	<div>
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<form:hidden path="active"/>		
	</div>

	<div class="portlet-section-header">
		<input type="submit" name="_finish" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
	</div>

	<div class="portlet-section-body">
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
				<tr>
					<th colspan="4"><doc41:translate label="TitelUserData"/></th>
				</tr>
			</thead>
			<tbody class="portlet-table-body">
				<spring:hasBindErrors name="user">
					<tr>
						<td colspan="4">
							<c:forEach items="${errors.fieldErrors}" var="error">
								<tr>
									<td onmouseover="style.cursor='pointer';" onclick="$('#${error.field}').focus();" style="color: blue"> <doc41:translate label="${error.field}"/> </td>
					    			<td style="color: red;"><doc41:translate label="${error.field}.${error.code}" /></td>
								</tr>
							</c:forEach>
						</td>
					</tr>
				</spring:hasBindErrors>
				
				
				<tr>
					<th style="width: 15%"><doc41:translate label="Surname"/></th> 
					<td style="width: 35%">
						<c:if test="${user.type eq 'external' && !user.readOnly}">
							<form:input path="surname" cssClass="portlet-form-input-field"/>
						</c:if>
						<c:if test="${user.type eq 'internal' || user.readOnly}">
							<c:out value="${user.surname}"/>
							<form:hidden path="surname"/>
						</c:if>
					</td>
					<th><doc41:translate label="Firstname"/></th>
					<td>
						<c:if test="${user.type eq 'external' && !user.readOnly}">
							<form:input path="firstname" cssClass="portlet-form-input-field"   maxlength="30"/>
						</c:if>
						<c:if test="${user.type eq 'internal' || user.readOnly}">
							<c:out value="${user.firstname}"/>
							<form:hidden path="firstname"/>
						</c:if>
					</td>
				</tr>
				
				<tr class="portlet-table-alternate">
					<th><doc41:translate label="Cwid"/></th>
					<td>
						<c:out value="${user.cwid}"/> <form:hidden path="cwid"/>
					</td>
					<th><doc41:translate label="Email"/></th>
					<td>
						<c:if test="${user.type eq 'external' && !user.readOnly}">
							<form:input path="email" cssClass="portlet-form-input-field"  maxlength="70"/>
						</c:if>
						<c:if test="${user.type eq 'internal' || user.readOnly}">
							<c:out value="${user.email}"/>
							<form:hidden path="email"/>
						</c:if>
					</td>
				</tr>
				
				<tr>
					<th><doc41:translate label="Language"/></th>
					<td>
					    <form:select path="languageCountry" cssClass="portlet-form-input-field">
							<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
						</form:select>
					</td>
					<th><doc41:translate label="Password"/></th>
					<td>
						<c:if test="${user.type eq 'external' && !user.readOnly}">
							<form:password path="password" cssClass="portlet-form-input-field" />
						</c:if>
					</td>
				</tr>	
				
				<c:if test="${user.type eq 'external' && !user.readOnly}">
				<tr class="portlet-table-alternate">
					<th></th>
					<td>&nbsp;</td>
					<th><doc41:translate label="RepeatPassword"/></th>
					<td>
						
							<form:password path="passwordRepeated" cssClass="portlet-form-input-field" />
						
					</td>
				</tr>		
				</c:if>
						
			</tbody>
		</table>
	</div>
</form:form>
</doc41:layout>