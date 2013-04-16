<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations jspName="monitoringOverview" component="tAdmin" />

<html>
<head>
<title><doc41:translate label="MonitoringOverview" /></title>
</head>

<body>
	<%@include file="../doc41/header.jspf"%>
	<%@include file="../doc41/navigation.jspf"%>

	<div class="portlet-section-body">
				<table class="nohover" cellpadding="3">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="7"><doc41:translate
									label="MonitoringOverview" /></th>
						</tr>
					</thead>
	<tr align="right">
	<td><input type="button" class="portlet-form-button" onclick="window.location.href='monitoringoverview.htm'" value="<doc41:translate label="Refresh" />" />
		<input type="button" class="portlet-form-button" onclick="window.location.href='interfaceadd.htm'" value="<doc41:translate label="AddInterface"/>" /></td>
	</tr>
	</table>
	</div>
	<div class="portlet-section-body">
	<table class="nohover" cellpadding="4" cellspacing="0">
		<thead class="portlet-table-header">
					<c:if test="${empty monitoringEntries}">
						<tr>
							<td colspan="7" style="color:red">
							<doc41:translate label="Monitoring Status NotAvailable" />
							</td>
						</tr>
					</c:if>
					<tr>
						<th><doc41:translate label="InterfaceName" /></th>
						<th><doc41:translate label="Action" /></th>
						<th><doc41:translate label="LastRequest" /></th>
						<th><doc41:translate label="Status" /></th>
						<th><doc41:translate label="Details" /></th>
						<th><doc41:translate label="Remarks" /></th>
						<th><doc41:translate label="ResponseTime(ms)" /></th>
						<th colspan="2">&nbsp;</th>
					</tr>
				</thead>

				<tbody class="portlet-table-body">
					<c:forEach items="${monitoringEntries}" var="view"
						varStatus="lineInfo">
						<tr	<c:if test="${lineInfo.count % 2 == 0}"> class="portlet-table-alternate" </c:if>>
							<td><c:out value="${view.name}"/></td>
							<td><c:out value="${view.action}"/></td>
							<td><doc41:formatDate date="${view.created}" zone="${user.timeZone}"></doc41:formatDate>&nbsp;<doc41:formatTime date="${view.created}" zone="${user.timeZone}"></doc41:formatTime></td>
							<td><c:if test="${view.status}">
								<img
									src="<%=response.encodeURL(request.getContextPath()
							+ "/resources/img/common/ball_green.gif")%>" title="<doc41:translate label="Success"/>"
									alt="<doc41:translate label="Success"/>" style="border: 0px;">
							</c:if> <c:if test="${!view.status}">
								<img
									src="<%=response.encodeURL(request.getContextPath()
							+ "/resources/img/common/ball_red.gif")%>" title="<doc41:translate label="Failure"/>"
									alt="<doc41:translate label="Failure"/>" style="border: 0px;">
							</c:if></td>
							<td><c:out value="${view.details}"/></td>
							<td><c:out value="${view.remarks}"/></td>
							<td><c:out value="${view.responseTime}"/></td>
							<td style="background-color: #FFFFFF;">
							 <a href="#" onclick="sendGet('monitoringhistory.htm', 'serviceName=${view.name}')"><img
								src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/clock.png") %>" title="<doc41:translate label="History"/>"
								alt="<doc41:translate label="History"/>" style="border: 0px;"></a></td>
							<td style="background-color: #FFFFFF;">
							 <a href="#" onclick="sendGet('contactpersonview.htm', 'serviceName=${view.name}')"><img
								src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/report_user.png") %>" title="<doc41:translate label="ContactPerson"/>"
								alt="<doc41:translate label="ContactPerson"/>" style="border: 0px;"></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
	<table class="nohover" cellpadding="3">

	<tr align="right">
	<td><input type="button" class="portlet-form-button" onclick="window.location.href='monitoringoverview.htm'" value="<doc41:translate label="Refresh" />" />
		<input type="button" class="portlet-form-button" onclick="window.location.href='interfaceadd.htm'" value="<doc41:translate label="AddInterface"/>" /></td>
	</tr>
	</table>
			</div>
	  </body>
</html>	