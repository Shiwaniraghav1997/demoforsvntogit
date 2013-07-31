<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="monitoringHistory" component="tAdmin"
activeTopNav="maintenance" 	activeNav="interfaceMonitoring"
title="MonitoringHistory">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script type="text/javascript">
<!--

	tsheaders = {
		0 : {
			filter : false
		},
		1 : {
			filter : false
		},
		2 : {
			filter : false
		},
		3 : {
			filter : false
		},
		4 : {
			filter : false
		},
		5 : {
			filter : false
		},
		6 : {
			filter : false
		}
	};
	addparams='&serviceName=${serviceName}';
//-->
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorter.js"></script>
		<!--Buttons Bar: Start-->
		<div class="portlet-section-header">
			<a class="portlet-form-button" href='monitoringOverview'><doc41:translate label="ButtonBack"/></a>
		</div>
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
		<div class="portlet-section-body">
			<table class="nohover" cellpadding="4" cellspacing="0">
				<thead class="portlet-table-header">
					<tr>
						<th colspan="5"><doc41:translate label="MonitoringHistory" /></th>
					</tr>
				</thead>
			</table>
			<doc41:pager/>
			<table class="tablesorter" id="doc41table">
				<colgroup>
			    	<col width="10%"/>
			    	<col width="15%"/>
			    	<col width="15%"/>
			    	<col width="5%"/>
			    	<col width="25%"/>
			    	<col width="25%"/>
			    	<col width="5%"/>
			    </colgroup>
				<thead>
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
				<tbody> <!-- tbody will be loaded via JSON -->
		  		</tbody>
			</table>
			<doc41:pager/>		
		</div>
</doc41:layout>