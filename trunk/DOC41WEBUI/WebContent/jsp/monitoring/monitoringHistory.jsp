<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations component="tAdmin" jspName="monitoringHistory" />

<html>
	<head>
	<title><doc41:translate label="MonitoringHistory" /></title>
	</head>
	<script type="text/javascript"
		src="<%=response.encodeURL(request.getContextPath() + "/resources/js/sorttable.js")%>">
	</script>
	
	<body>
	<%@include file="../doc41/header.jspf"%>
		<!--Buttons Bar: Start-->
		<div class="portlet-section-header">
			<input type="button" class="portlet-form-button" onclick="window.location.href='monitoringoverview.htm'" value="<doc41:translate label="Back"/>" />
		</div>
		<!--Buttons Bar :End-->
	
		<div class="portlet-section-body">
			<table cellpadding="4" cellspacing="0" class="nohover">
				<thead class="portlet-table-header">
					<tr>
						<th width="100%" colspan="5"><doc41:translate label="InterfaceDetails" />
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
		<div class="pagingBlock">
			<%@include file="../doc41/paging.jsp"%>
		</div>
		<div class="portlet-section-body">
			<table class="nohover" cellpadding="4" cellspacing="0">
				<thead class="portlet-table-header">
					<tr>
						<th colspan="5"><doc41:translate label="MonitoringHistrory" /></th>
					</tr>
				</thead>
			</table>
			<table class="sortable" cellpadding="4" cellspacing="0">
				<thead class="portlet-table-header">
					<tr>
						<th><doc41:translate label="EntryId" /></th>
						<th><doc41:translate label="Action" /></th>
						<th><doc41:translate label="LastRequest" /></th>
						<th><doc41:translate label="Status" /></th>
						<th><doc41:translate label="Details" /></th>
						<th><doc41:translate label="Remarks" /></th>
						<th><doc41:translate label="ResponseTime(ms)" /></th>
					</tr>
				</thead>
				<tbody class="portlet-table-body">
					<c:forEach items="${monitoringHistoryByInterface}" var="view"
						varStatus="lineInfo">
						<tr
							<c:if test="${lineInfo.count % 2 == 0}"> class="portlet-table-alternate" </c:if>>
							<td><c:out value="${view.id}"/></td>
							<td><c:out value="${view.action}"/></td>
							<td><c:out value="${view.created}"/></td>
							<td><c:if test="${view.status}">
									<img
										src="<%=response.encodeURL(request.getContextPath()
								+ "/resources/img/common/ball_green.gif")%>"
										alt="<doc41:translate label="Active"/>" 
										title="<doc41:translate label="Active"/>" style="border: 0px;">
								</c:if> <c:if test="${!view.status}">
									<img
										src="<%=response.encodeURL(request.getContextPath()
								+ "/resources/img/common/ball_red.gif")%>"
										alt="<doc41:translate label="Inactive"/>" 
										title="<doc41:translate label="Inactive"/>" style="border: 0px;">
								</c:if></td>
							<td><c:out value="${view.details}"/></td>
							<td><c:out value="${view.remarks}"/></td>
							<td><c:out value="${view.responseTime}"/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>