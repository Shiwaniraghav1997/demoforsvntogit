<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="import" 			component="useradmin"
activeTopNav="management" 	activeNav="userManagement" 
title="User Management">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<div class="portlet-body">
		<form:form commandName="userEditForm" action="importuser" method="post">
			<form:hidden path="type" />
			<form:hidden path="cwid" />
				
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="UserImport"/>
				</div>
				<a class="portlet-form-button" href='userlist'><doc41:translate label="ButtonCancel"/></a>
				<a class="portlet-form-button" href='userlookup'><doc41:translate label="ButtonBack"/></a>
				<c:if test="${!empty userEditForm.surname}">
					<a class="portlet-form-button" href='#' onclick="userEditForm.submit();"><doc41:translate label="ButtonSave"/></a>
<%-- 					<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" /> --%>
				</c:if>
			</div>
         
            <doc41:errors form="userEditForm"/>
		
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
								<c:if test="${empty userEditForm.surname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty userEditForm.surname}">
									<c:out value="${userEditForm.surname}"/>
									<form:hidden path="surname"/>
								</c:if>
							</td>
							<th style="width: 15%"><label for="password"><doc41:translate label="Password"/></label></th>
							<td style="width: 35%"><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><label for="firstname"><doc41:translate label="Firstname"/></label></th>
							<td>
								<c:if test="${empty userEditForm.firstname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty userEditForm.firstname}">
									<c:out value="${userEditForm.firstname}"/>
									<form:hidden path="firstname"/>
								</c:if>				
							</td>
							<th><label for="passwordRepeated"><doc41:translate label="RepeatPassword"/></label></th>
							<td><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr>
							<th><label for="cwid"><doc41:translate label="Cwid"/></label></th>
							<td><c:out value="${userEditForm.cwid}"/></td>
							
							<th><label for="company"><doc41:translate label="Company"/></label></th>
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
								<th><label for="email"><doc41:translate label="Email"/></label></th>
								<td>
									<c:if test="${empty userEditForm.email}"><doc41:translate label="AutomaticImport"/></c:if>
									<c:if test="${not empty userEditForm.email}">
										<c:out value="${userEditForm.email}"/>
										<form:hidden path="email"/>
									</c:if>
								</td>
								<th><label for="phone"><doc41:translate label="Phone"/></label></th>
								<td>
									<c:if test="${empty userEditForm.phone}"><doc41:translate label="AutomaticImport"/></c:if>
									<c:if test="${not empty userEditForm.phone}">
										<c:out value="${userEditForm.phone}"/>
										<form:hidden path="phone"/>
									</c:if>
								</td>
							</tr>	
							<tr>
								<th><label for="timeZone"><doc41:translate label="TimeZone"/></label></th>
								<td>
								    <form:select path="timeZone" cssClass="portlet-form-input-field portlet-mandatory">
										<form:options items="${timeZoneList}" itemValue="code" itemLabel="label"/>
									</form:select>
								</td>
								<th><label for="type"><doc41:translate label="Type"/></label></th>
								<td><c:out value="${userEditForm.type}"/></td>
							</tr>
							
							<tr class="portlet-table-alternate">
								<th><label for="languageCountry"><doc41:translate label="Language"/></label></th>
								<td>
								    <form:select path="languageCountry" cssClass="portlet-form-input-field portlet-mandatory">
										<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
									</form:select>
								</td>
								<th><label for="active"><doc41:translate label="Status"/></label></th>
								<td>
									<form:select path="active" cssClass="portlet-form-input-field portlet-mandatory">
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
