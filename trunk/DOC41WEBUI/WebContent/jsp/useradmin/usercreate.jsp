<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="edit" component="useradmin"/>

<html>
  <head><title>User Management</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>

	<div class="portlet-body">
		<form:form commandName="createuser" action="${lastRenderedAction}" method="post">
			<form:hidden path="command"/>
			<%-- <form:hidden path="user.dcId"/>	 --%>	
		
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="CreateUser"/></th>
				</table>
				<input type="button" class="portlet-form-button" onclick="window.location.href='userlist.htm'" value="<doc41:translate label="ButtonCancel"/>" />
				<input type="button" class="portlet-form-button" onClick="submitAction('createuser', 'submit', this)" value="<doc41:translate label="ButtonSave"/>" />
			</div>
		
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="TitelUserData"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">
						<spring:hasBindErrors name="createuser">
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
								<c:if test="${createuser.type eq 'external'}">
									<form:input path="surname" cssClass="portlet-form-input-field" maxlength="70"/>
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<c:out value="${createuser.surname}"/>
									<form:hidden path="surname"/>
								</c:if>
							</td>
							<th style="width: 15%"><doc41:translate label="Password"/></th>
							<td style="width: 35%">
								<c:if test="${createuser.type eq 'external'}">
									<form:password path="password" cssClass="portlet-form-input-field"/>
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<doc41:translate label="NotChangeable"/>
								</c:if>
							</td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Firstname"/></th>
							<td>
								<c:if test="${createuser.type eq 'external'}">
									<form:input path="firstname" cssClass="portlet-form-input-field"  maxlength="30"/>
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<c:out value="${createuser.firstname}"/>
									<form:hidden path="firstname"/>
								</c:if>
							</td>
							<th><doc41:translate label="RepeatPassword"/></th>
							<td>
								<c:if test="${createuser.type eq 'external'}">
									<form:password path="passwordRepeated" cssClass="portlet-form-input-field" />
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<doc41:translate label="NotChangeable"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<th><doc41:translate label="Cwid"/></th>
							<td>
								<doc41:translate label="generatedAutomatically"/> <form:hidden path="cwid"/>
							</td>
							<th><doc41:translate label="Company"/></th>
							<td>
								<c:if test="${createuser.type eq 'external'}">
									<form:input path="company" cssClass="portlet-form-input-field"   maxlength="70"/>
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<c:out value="${createuser.company}"/>
									<form:hidden path="company"/>
								</c:if>
							</td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Email"/></th>
							<td>
								<c:if test="${createuser.type eq 'external'}">
									<form:input path="email" cssClass="portlet-form-input-field"  maxlength="70"/>
								</c:if>
								<c:if test="${createuser.type eq 'internal'}">
									<c:out value="${createuser.email}"/>
									<form:hidden path="email"/>
								</c:if>
							</td>
							<th><doc41:translate label="Phone"/></th>
							<td>
								<c:if test="${createuser.type eq 'external'}">
									<form:input path="phone" cssClass="portlet-form-input-field"   maxlength="35"/>
								</c:if> 
								<c:if test="${createuser.type eq 'internal'}">
									<c:out value="${createuser.phone}"/>
									<form:hidden path="phone"/>
								</c:if>
							</td>
						</tr>				
						<tr>
							<th><doc41:translate label="TimeZone"/></th>
							<td>
							    <form:select path="timeZone" cssClass="portlet-form-input-field">
									<form:options items="${timeZoneList}" itemValue="code" itemLabel="label"/>
								</form:select>
							</td>
							<th><doc41:translate label="Type"/></th>
							<td><c:out value="${createuser.type}"/></td>
						</tr>
						
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Language"/></th>
							<td>
							    <form:select path="languageCountry" cssClass="portlet-form-input-field">
									<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
								</form:select>
							</td>
							<th>&nbsp;</th><td>&nbsp;</td>
						</tr>
						
						<tr>
							<th><doc41:translate label="Agent"/></th>
							<td>
								<form:select path="agentId" cssClass="portlet-form-input-field">
									<form:options items="${agentList}" itemValue="dcId" itemLabel="name"/>
								</form:select>
							</td>
							<th><doc41:translate label="InventoryAgent"/></th>
							<td>
								<form:select path="invAgentId" cssClass="portlet-form-input-field" onChange="changeUserAgentLocations()">
									<form:option value=""><doc41:translate label="SelectAgent" /></form:option>
									<form:options items="${inventoryAgentList}" itemValue="dcId" itemLabel="name"/>
								</form:select>
							</td>
						</tr> 
						
						<tr>
							<th><doc41:translate label="Status"/></th>
							<td>
								<form:select path="active" cssClass="portlet-form-input-field">
									<form:option value="false"><doc41:translate label="Inactive"/></form:option>
									<form:option value="true"><doc41:translate label="Active"/></form:option>
							   </form:select>
							</td>
							<th><doc41:translate label="location" /></th>
							<td>
								<div id="div-agentlocations">
									<form:select cssClass="portlet-form-input-field" path="locationIds">
										<form:options items="${locationList}"  itemValue="value" itemLabel="label" />
									</form:select>
								</div>
							</td>
						</tr>	
							
										
					</tbody>
				</table>
			</div>
		
			<%@include file="roles.jspf"%>
		</form:form>
	</div>
  </body>
</html>