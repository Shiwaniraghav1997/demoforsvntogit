<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="edit" 		component="useradmin"
activeTopNav="" 	activeNav=""
title="NoPermission">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<div id="div-body" class="portlet-section-body">
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
				<tr>
					<th colspan="4"><doc41:translate label="NoPermission"/></th>
				</tr>
			</thead>
			<tbody class="portlet-table-body">
				<tr>
					<td><doc41:translate label="NoPermission"/></td> 
				</tr>
			</tbody>
		</table>
	</div>
</doc41:layout>