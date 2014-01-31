<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="edit" 				component="useradmin"
activeTopNav="management" 	activeNav="userManagement" 
title="User Management">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<div class="portlet-body">
		<form:form commandName="userEditForm" action="createuser" method="post">
			<form:hidden path="type" />
		
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="CreateExternalUser"/>
				</div>
				<a class="portlet-form-button" href='userlist'><doc41:translate label="ButtonCancel"/></a>
				<a class="portlet-form-button" href='#' onclick="userEditForm.submit();"><doc41:translate label="ButtonSave"/></a>
<%-- 				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" /> --%>
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
							<th style="width: 15%"><label for="surname"><doc41:translate label="Surname"/></label></th> 
							<td style="width: 35%">
								<c:if test="${userEditForm.type eq 'external'}">
									<form:input tabindex="1" path="surname" cssClass="portlet-form-input-field portlet-mandatory" maxlength="70"/>
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<c:out value="${userEditForm.surname}"/>
									<form:hidden path="surname"/>
								</c:if>
							</td>
							<th style="width: 15%"><label for="password"><doc41:translate label="Password"/></label></th>
							<td style="width: 35%">
								<c:if test="${userEditForm.type eq 'external'}">
									<form:password tabindex="6" path="password" cssClass="portlet-form-input-field portlet-mandatory"/>
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<doc41:translate label="NotChangeable"/>
								</c:if>
							</td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><label for="firstname"><doc41:translate label="Firstname"/></label></th>
							<td>
								<c:if test="${userEditForm.type eq 'external'}">
									<form:input tabindex="2" path="firstname" cssClass="portlet-form-input-field portlet-mandatory"  maxlength="30"/>
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<c:out value="${userEditForm.firstname}"/>
									<form:hidden path="firstname"/>
								</c:if>
							</td>
							<th><label for="passwordRepeated"><doc41:translate label="RepeatPassword"/></label></th>
							<td>
								<c:if test="${userEditForm.type eq 'external'}">
									<form:password tabindex="7" path="passwordRepeated" cssClass="portlet-form-input-field portlet-mandatory" />
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<doc41:translate label="NotChangeable"/>
								</c:if>
							</td>
						</tr>
						<tr>
							<th><label for="cwid"><doc41:translate label="Cwid"/></label></th>
							<td>
								<doc41:translate label="generatedAutomatically"/> <form:hidden path="cwid"/>
							</td>
							<th><label for="company"><doc41:translate label="Company"/></label></th>
							<td>
								<c:if test="${userEditForm.type eq 'external'}">
									<form:input tabindex="8" path="company" cssClass="portlet-form-input-field"   maxlength="70"/>
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<c:out value="${userEditForm.company}"/>
									<form:hidden path="company"/>
								</c:if>
							</td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><label for="email"><doc41:translate label="Email"/></label></th>
							<td>
								<c:if test="${userEditForm.type eq 'external'}">
									<form:input tabindex="3" path="email" cssClass="portlet-form-input-field portlet-mandatory"  maxlength="70"/>
								</c:if>
								<c:if test="${userEditForm.type eq 'internal'}">
									<c:out value="${userEditForm.email}"/>
									<form:hidden path="email"/>
								</c:if>
							</td>
							<th><label for="phone"><doc41:translate label="Phone"/></label></th>
							<td>
								<c:if test="${userEditForm.type eq 'external'}">
									<form:input tabindex="" path="phone" cssClass="portlet-form-input-field"   maxlength="35"/>
								</c:if> 
								<c:if test="${userEditForm.type eq 'internal'}">
									<c:out value="${userEditForm.phone}"/>
									<form:hidden path="phone"/>
								</c:if>
							</td>
						</tr>				
						<tr>
							<th><label for="timeZone"><doc41:translate label="TimeZone"/></label></th>
							<td>
							    <form:select tabindex="4" path="timeZone" cssClass="portlet-form-input-field portlet-mandatory">
									<form:options items="${timeZoneList}" itemValue="code" itemLabel="label"/>
								</form:select>
							</td>
							<th><label for="type"><doc41:translate label="Type"/></label></th>
							<td><c:out value="${userEditForm.type}"/></td>
						</tr>
						
						<tr class="portlet-table-alternate">
							<th><label for="languageCountry"><doc41:translate label="Language"/></label></th>
							<td>
							    <form:select tabindex="5" path="languageCountry" cssClass="portlet-form-input-field portlet-mandatory">
									<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
								</form:select>
							</td>
							<th><label for="active"><doc41:translate label="Status"/></label></th>
							<td>
								<form:select tabindex="9" path="active" cssClass="portlet-form-input-field portlet-mandatory">
									<form:option value="false"><doc41:translate label="Inactive"/></form:option>
									<form:option value="true"><doc41:translate label="Active"/></form:option>
							   </form:select>
							</td>
						</tr>
						
					</tbody>
				</table>
			</div>
		
			<%@include file="roles.jspf"%>
			<%@include file="customers.jspf"%>
			<%@include file="vendors.jspf"%>
			<%@include file="countries.jspf"%>
			<%@include file="plants.jspf"%>
		</form:form>
	</div>
</doc41:layout>