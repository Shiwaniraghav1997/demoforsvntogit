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
			<div class="pager">
		        <img src="${pageContext.request.contextPath}/resources/img/tablesorter/first.png" class="first" alt="First" title="First page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/prev.png" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/next.png" class="next" alt="Next" title="Next page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/last.png" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		    </div>
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
				<tbody class="portlet-table-body"> <!-- tbody will be loaded via JSON -->
		  		</tbody>
			</table>
			<div class="pager">
		        <img src="${pageContext.request.contextPath}/resources/img/tablesorter/first.png" class="first" alt="First" title="First page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/prev.png" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/next.png" class="next" alt="Next" title="Next page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/last.png" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		    </div>		
		</div>
</doc41:layout>