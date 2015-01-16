<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="editContactPerson" component="useradmin"
activeTopNav="maintenance" 	activeNav="interfaceMonitoring"
title="EditContactPerson">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<form:form commandName="contactPerson" method="post" action="editContactPersonPost">
<form:hidden path="dcId"/>
<form:hidden path="type"/>
<form:hidden path="company"/>
	<div class="portlet-section-header">
		<a class="portlet-form-button" href='viewContactPerson?serviceName=${contactPerson.company}'><doc41:translate label="ButtonCancel"/></a>
		<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
	</div>
   
    <doc41:errors form="contactPerson"/>

	<div class="portlet-section-body">
	<table cellpadding="4" cellspacing="0" class="nohover">
		<thead class="portlet-table-header">
			<tr>
				<th colspan="4"><doc41:translate label="EditContactPerson" /></th>
			</tr>
		</thead>
		<tbody class="portlet-table-body">
			<tr>
				<th style="width: 15%"><doc41:translate label="Surname" /></th>
				<td style="width: 35%"><form:input path="surname" cssClass="portlet-form-input-field" maxlength="40"/>
				<doc41:error path="surname" /></td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><doc41:translate label="Firstname" /></th>
				<td><form:input path="firstname" cssClass="portlet-form-input-field" maxlength="12"/>
				<doc41:error path="firstname" /></td>
			</tr>
			<tr>
				<th><doc41:translate label="Cwid" /></th>
				<td><form:input path="cwid" cssClass="portlet-form-input-field" maxlength="10"/>
				<doc41:error path="cwid" /></td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><doc41:translate label="Email" /></th>
				<td><form:input path="email" cssClass="portlet-form-input-field" maxlength="40"/>
				<doc41:error path="email" /></td>
			</tr>
			<tr>
				<th><doc41:translate label="Phone" /></th>
				<td><form:input path="phone" cssClass="portlet-form-input-field" maxlength="20"/>
				<doc41:error path="phone" /></td>
			</tr>
	</table>
	</div>
</form:form>
</doc41:layout>