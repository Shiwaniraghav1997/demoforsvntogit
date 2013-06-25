<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations jspName="viewContcatPerson" component="tAdmin" />
<html>
  <head><title><doc41:translate	label="ViewContactPerson" /></title></head>
  <body>
  <%@include file="../doc41/header.jspf" %>
<!--Buttons Bar: Start-->
<div class="portlet-section-header">
	<input type="button" class="portlet-form-button" onclick="sendGet('monitoring/monitoringOverview')" value="<doc41:translate label="Back"/>"/>
</div>
<!--Buttons Bar :End-->
<div class="portlet-section-body">
<table cellpadding="4" cellspacing="0" class="nohover">
	<thead class="portlet-table-header">
		<tr>
			<th width="100%" colspan="5"><doc41:translate	label="InterfaceDetails" /></th>
		</tr>
	</thead>
	<tbody class="portlet-table-body">
		<tr>
			<th style="width: 30%"><doc41:translate label="InterfaceName" /></th>
			<td style="width: 70%"><c:out value="${service.name}" /></td>
		</tr>
		<tr class="portlet-table-alternate">
			<th style="width: 30%"><doc41:translate label="Description" /></th>
			<td style="width: 70%"><c:out value="${service.desc}" /></td>
		</tr>
	</tbody>
</table>
</div>
<div>
<table class="nohover">
	<tr class="portlet-table-header">
		<td width="100%" colspan="4"><doc41:translate
			label="EBCContactPerson" /></td>
		<c:choose>
			<c:when test="${ebcUser.dcId==null}">
				<td><input type="button" class="portlet-form-button"
					onclick="sendGet('monitoring/addContactPerson', 'contactType=EBC&serviceName=${service.name}')"
					value="<doc41:translate label="Add"/>" /></td>
			</c:when>
			<c:otherwise>
				<td><input type="button" class="portlet-form-button"
					onclick="sendGet('monitoring/editContactPerson', 'objectId=${ebcUser.dcId}')"
					value="<doc41:translate label="Edit"/>" /></td>
			</c:otherwise>
		</c:choose>
	</tr>
</table>
</div>
<div class="portlet-section-body">
<table cellpadding="4" cellspacing="0" class="nohover">
	<tbody class="portlet-table-body">
		<tr>
			<th style="width: 15%"><doc41:translate label="Surname" /></th>
			<td style="width: 35%"><c:out value="${ebcUser.surname}" /></td>
		</tr>
		<tr class="portlet-table-alternate">
			<th><doc41:translate label="Firstname" /></th>
			<td><c:out value="${ebcUser.firstname}" /></td>
		</tr>
		<tr>
			<th><doc41:translate label="Cwid" /></th>
			<td><c:out value="${ebcUser.cwid}" /></td>
		</tr>
		<tr class="portlet-table-alternate">
			<th><doc41:translate label="Email" /></th>
			<td><c:out value="${ebcUser.email}" /></td>
		</tr>
		<tr>
			<th><doc41:translate label="Phone" /></th>
			<td><c:out value="${ebcUser.phone}" /></td>
		</tr>
	</tbody>
</table>
</div>
<div>
<table class="nohover">
	<tr class="portlet-table-header">
		<td width="100%" colspan="4"><doc41:translate
			label="BackendContactPerson" /></td>
		<c:choose>
			<c:when test="${backendUser.dcId==null}">
				<td><input type="button" class="portlet-form-button"
					onclick="sendGet('monitoring/addContactPerson', 'contactType=BACKEND&serviceName=${service.name}')"
					value="<doc41:translate label="Add"/>" /></td>
			</c:when>
			<c:otherwise>
				<td><input type="button" class="portlet-form-button"
					onclick="sendGet('monitoring/editContactPerson', 'objectId=${backendUser.dcId}')"
								value="<doc41:translate label="Edit"/>" /></td>
			</c:otherwise>
		</c:choose>
	</tr>
</table>
</div>
<div class="portlet-section-body">
<table cellpadding="4" cellspacing="0" class="nohover">
	<tbody class="portlet-table-body">
		<tr>
			<th style="width: 15%"><doc41:translate label="Surname" /></th>
			<td style="width: 35%"><c:out value="${backendUser.surname}" /></td>
		</tr>
		<tr class="portlet-table-alternate">
			<th><doc41:translate label="Firstname" /></th>
			<td><c:out value="${backendUser.firstname}" /></td>
		</tr>
		<tr>
			<th><doc41:translate label="Cwid" /></th>
			<td><c:out value="${backendUser.cwid}" /></td>
		</tr>
		<tr class="portlet-table-alternate">
			<th><doc41:translate label="Email" /></th>
			<td><c:out value="${backendUser.email}" /></td>
		</tr>
		<tr>
			<th><doc41:translate label="Phone" /></th>
			<td><c:out value="${backendUser.phone}" /></td>
		</tr>
	</tbody>
</table>
</div>
  </body>
</html>