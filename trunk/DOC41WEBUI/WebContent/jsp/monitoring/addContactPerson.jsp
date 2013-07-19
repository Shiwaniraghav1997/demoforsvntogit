<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="addContactPerson" 	component="tAdmin"
activeTopNav="maintenance" 	activeNav="interfaceMonitoring"
title="AddContactPerson">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<form:form commandName="contactPerson" method="post" action="addContactPersonPost">
	<form:hidden path="type"/>
	<form:hidden path="company"/>
	
	<div class="portlet-section-header">
		<input type="button" class="portlet-form-button" onclick="sendGet('monitoring/viewContactPerson','serviceName=${contactPerson.company}')" value="<doc41:translate label="ButtonCancel"/>"/>
		<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
	</div>

	<div class="portlet-section-body">
	<table cellpadding="4" cellspacing="0" class="nohover">
		<thead class="portlet-table-header">
			<tr>
				<th colspan="4"><doc41:translate label="AddContactPerson" /></th>
			</tr>
		</thead>
		<tbody class="portlet-table-body">
			<spring:hasBindErrors name="contactPerson">
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
				<th style="width: 15%"><doc41:translate label="Surname" /></th>
				<td style="width: 35%"><form:input path="surname" cssClass="portlet-form-input-field" />
				<doc41:error path="surname" /></td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><doc41:translate label="Firstname" /></th>
				<td><form:input path="firstname" cssClass="portlet-form-input-field" />
				<doc41:error path="firstname"/></td>
			</tr>
			<tr>
				<th><doc41:translate label="Cwid" /></th>
				<td><form:input path="cwid" cssClass="portlet-form-input-field" />
				<doc41:error path="cwid" /></td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><doc41:translate label="Email" /></th>
				<td><form:input path="email" cssClass="portlet-form-input-field" />
				<doc41:error path="email" /></td>
			</tr>
			<tr>
				<th><doc41:translate label="Phone" /></th>
				<td><form:input path="phone" cssClass="portlet-form-input-field" />
				<doc41:error path="phone" /></td>
			</tr>
		</tbody>
	</table>
	</div>
</form:form>
</doc41:layout>