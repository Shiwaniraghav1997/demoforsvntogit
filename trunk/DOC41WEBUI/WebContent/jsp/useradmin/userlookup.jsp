<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="import" 			component="useradmin"
activeTopNav="management" 	activeNav="userManagement"
title="User Management">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<div class="portlet-body">
		<form:form commandName="userLookupForm" action="lookuppost" method="post">

			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="UserImport"/>
				</div>
				<a class="portlet-form-button" href='userlist'><doc41:translate label="ButtonCancel"/></a>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonLookup"/>" />
			</div>
		
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="TitelUserData"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">	
						<spring:hasBindErrors name="lookupuser">
							<tr>
								<td colspan="4">
									<c:forEach items="${errors.fieldErrors}" var="error">
										<tr>
											<td onmouseover="style.cursor='pointer';" onclick="$('#${error.field}').focus();" style="color: blue"> <doc41:translate label="${error.field}"/> </td>
							    			<td style="color: red;"><doc41:translate label="${error.field}.${error.code}" /></td>
										</tr>
									</c:forEach>
								</td>
							</tr>
						</spring:hasBindErrors>	
						<tr>
							<th style="width: 15%"><doc41:translate label="Surname"/></th> 
							<td style="width: 35%">
								<doc41:translate label="AutomaticImport"/>
							</td>
							<th style="width: 15%"><doc41:translate label="Password"/></th>
							<td style="width: 35%"><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Firstname"/></th>
							<td>
								<doc41:translate label="AutomaticImport"/>
							</td>
							<th><doc41:translate label="RepeatPassword"/></th>
							<td><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="Cwid"/></th>
							<td><form:input id="cwid" path="cwid" cssClass="portlet-form-input-field"/></td>
							
							<th><doc41:translate label="Company"/></th>
							<td>
								<doc41:translate label="AutomaticImport"/>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
		</form:form>
	</div>
</doc41:layout>

