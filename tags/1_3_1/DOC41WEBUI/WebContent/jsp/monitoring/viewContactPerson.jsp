<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="viewContcatPerson" component="tAdmin"
activeTopNav="maintenance" 	activeNav="interfaceMonitoring"
title="ViewContactPerson">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>
<!--Buttons Bar: Start-->
<div class="portlet-section-header">
	<a class="portlet-form-button" href='monitoringOverview'><doc41:translate label="ButtonBack"/></a>
</div>
<c:choose>
<c:when test="${!empty service.name }">
<!--Buttons Bar :End-->
<div class="portlet-section-body">
<table cellpadding="4" cellspacing="0" class="nohover">
	<thead class="portlet-table-header">
		<tr>
			<th colspan="5"><doc41:translate label="InterfaceDetails" /></th>
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
		<td colspan="4"><doc41:translate label="EBCContactPerson" /></td>
		<c:choose>
			<c:when test="${ebcUser.dcId==null}">
				<td><a class="portlet-form-button"
					href='addContactPerson?contactType=EBC&amp;serviceName=${service.name}'><doc41:translate label="ButtonAdd"/></a>
				</td>
			</c:when>
			<c:otherwise>
				<td><a class="portlet-form-button"
					href='editContactPerson?objectId=${ebcUser.dcId}&amp;serviceName=${service.name}'>
					<doc41:translate label="ButtonEdit"/></a></td>
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
		<td colspan="4"><doc41:translate label="BackendContactPerson" /></td>
		<c:choose>
			<c:when test="${backendUser.dcId==null}">
				<td><a class="portlet-form-button"
					href='addContactPerson?contactType=BACKEND&amp;serviceName=${service.name}'>
					<doc41:translate label="ButtonAdd"/></a></td>
			</c:when>
			<c:otherwise>
				<td><a class="portlet-form-button"
					href='editContactPerson?objectId=${backendUser.dcId}&amp;serviceName=${service.name}'>
								<doc41:translate label="ButtonEdit"/></a></td>
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
</c:when>
<c:otherwise>
<div class="portlet-section-body" style="color: red">
	<doc41:translate label="AddInterfaceFirst"/>
</div>
</c:otherwise>
</c:choose>
</doc41:layout>