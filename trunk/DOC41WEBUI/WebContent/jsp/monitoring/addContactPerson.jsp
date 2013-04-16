<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations jspName="addContactPerson" component="tAdmin" />

<html>
  <head><title><doc41:translate label="AddContactPerson" /></title></head>
  <body>
  <%@include file="../doc41/header.jspf" %>
	<form:form commandName="user" method="post" action="contactpersonadd.htm">
	<form:hidden path="type"/>
	<form:hidden path="company"/>
	
	<div class="portlet-section-header">
	<input type="button" class="portlet-form-button" onclick="window.location.href='contactpersonview.htm'" value="<doc41:translate label="ButtonCancel"/>" />
		<input type="submit" name="save" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
	</div>

	<div class="portlet-section-body">
	<table cellpadding="4" cellspacing="0" class="nohover">
		<thead class="portlet-table-header">
			<tr>
				<th colspan="4"><doc41:translate label="AddContactPerson" /></th>
			</tr>
		</thead>
		<tbody class="portlet-table-body">
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
  </body>
</html>