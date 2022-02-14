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
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonCreateInternalUser"/>" />
			</div>
         
            <doc41:errors form="userLookupForm"/>
		
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="TitelUserData"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">	
						<tr>
							<th style="width: 15%"><label for="surname"><doc41:translate label="Surname"/></label></th> 
							<td style="width: 35%">
								<doc41:translate label="AutomaticImport"/>
							</td>
							<th style="width: 15%"><label for="password"><doc41:translate label="Password"/></label></th>
							<td style="width: 35%"><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><label for="firstname"><doc41:translate label="Firstname"/></label></th>
							<td>
								<doc41:translate label="AutomaticImport"/>
							</td>
							<th><label for="passwordRepeated"><doc41:translate label="RepeatPassword"/></label></th>
							<td><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr>
							<th><label for="cwid"><doc41:translate label="Cwid"/></label></th>
							<td><form:input id="cwid" path="cwid" cssClass="portlet-form-input-field portlet-mandatory"/></td>
							
							<th><label for="company"><doc41:translate label="Company"/></label></th>
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

