<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="addInterface" 		component="tAdmin"
activeTopNav="maintenance" 	activeNav="interfaceMonitoring"
title="AddInterface">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<form:form commandName="monitor" method="post"
		action="addInterfacePost">

		<!--Buttons Bar: Start-->
		<div class="portlet-section-header">
				<input type="button" class="portlet-form-button" onclick="sendGet('monitoring/monitoringOverview')" value="<doc41:translate label="ButtonCancel"/>"/>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
		</div>

		<!--Buttons Bar :End-->

		<div class="portlet-section-body">
			<table cellpadding="4" cellspacing="0" class="nohover">
				<thead class="portlet-table-header">
					<tr>
						<th colspan="5"><doc41:translate label="Interface Details" /></th>
					</tr>
				</thead>
				<tbody class="portlet-table-body">
					<tr>
						<th style="width: 30%"><doc41:translate label="InterfaceName" /></th>
						<td style="width: 70%"><form:input path="name"
								cssClass="portlet-form-input-field" size="50" />
								<doc41:error path="name" />
								</td>
					</tr>
					<tr>
						<th style="width: 30%"><doc41:translate label="Description" /></th>
						<td style="width: 70%"><form:textarea path="desc"
								cssClass="portlet-form-input-field" rows="10" cols="80"
								onchange="javascript:checkLength(this, 200);"
								onkeydown="javascript:checkLength(this, 200);"
								onkeyup="javascript:checkLength(this, 200);" />
								<doc41:error path="desc" />
								</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</doc41:layout>