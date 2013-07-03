<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="import" 			component="useradmin"
activeTopNav="management" 	activeNav="userManagement" 
title="User Import">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<div class="portlet-body">
		<form:form commandName="userEditForm" action="importuser" method="post">
			<div class="portlet-section-header">
				<form:hidden path="type" />
				<form:hidden path="cwid" />
				
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="UserImport"/></th>
				</table>
				<input type="button" class="portlet-form-button" onclick="sendGet('useradmin/userlist')" value="<doc41:translate label="ButtonCancel"/>"/>
				<input type="button" class="portlet-form-button" onclick="sendGet('useradmin/userlookup');" value="<doc41:translate label="ButtonLookup"/>" />
				<c:if test="${!empty userEditForm.surname}">
					<input type="submit" class="portlet-form-button" value="<doc41:translate label="Save"/>" />
				</c:if>
			</div>
		
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="TitelUserData"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">	
						<spring:hasBindErrors name="userEditForm">
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
								<c:if test="${empty userEditForm.surname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty userEditForm.surname}">
									<c:out value="${userEditForm.surname}"/>
									<form:hidden path="surname"/>
								</c:if>
							</td>
							<th style="width: 15%"><doc41:translate label="Password"/></th>
							<td style="width: 35%"><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Firstname"/></th>
							<td>
								<c:if test="${empty userEditForm.firstname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty userEditForm.firstname}">
									<c:out value="${userEditForm.firstname}"/>
									<form:hidden path="firstname"/>
								</c:if>				
							</td>
							<th><doc41:translate label="RepeatPassword"/></th>
							<td><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="Cwid"/></th>
							<td><c:out value="${userEditForm.cwid}"/></td>
							
							<th><doc41:translate label="Company"/></th>
							<td>
								<c:if test="${empty userEditForm.company}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty userEditForm.company}">
									<c:out value="${userEditForm.company}"/>
									<form:hidden path="company"/>
								</c:if>
							</td>
						</tr>
						
						<c:if test="${!empty userEditForm.surname}">
							<tr class="portlet-table-alternate">
								<th><doc41:translate label="Email"/></th>
								<td>
									<c:if test="${empty userEditForm.email}"><doc41:translate label="AutomaticImport"/></c:if>
									<c:if test="${not empty userEditForm.email}">
										<c:out value="${userEditForm.email}"/>
										<form:hidden path="email"/>
									</c:if>
								</td>
								<th><doc41:translate label="Phone"/></th>
								<td>
									<c:if test="${empty userEditForm.phone}"><doc41:translate label="AutomaticImport"/></c:if>
									<c:if test="${not empty userEditForm.phone}">
										<c:out value="${userEditForm.phone}"/>
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
								<td><c:out value="${userEditForm.type}"/></td>
							</tr>
							
							<tr class="portlet-table-alternate">
								<th><doc41:translate label="Language"/></th>
								<td>
								    <form:select path="languageCountry" cssClass="portlet-form-input-field">
										<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
									</form:select>
								</td>
								<th>&nbsp;</th>
								<td>&nbsp;</td>
							</tr>
							<tr>
							<tr>
								<th><doc41:translate label="Status"/></th>
								<td>
									<form:select path="active" cssClass="portlet-form-input-field">
										<form:option value="false"><doc41:translate label="Inactive"/></form:option>
										<form:option value="true"><doc41:translate label="Active"/></form:option>
								   </form:select>
								</td>

							</tr>				
						</c:if>
					</tbody>
				</table>
			</div>
			<c:if test="${!empty userEditForm.surname}">
				<%@include file="roles.jspf"%>
			</c:if>
		</form:form>
	</div>
</doc41:layout>
