<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="edit" 				component="useradmin"
activeTopNav="myProfile" 	activeNav="myProfile" 
title="My Profile">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<form:form commandName="user" action="saveprofile" method="post">
	<div>
		<form:hidden path="id"/>
		<form:hidden path="type"/>
		<form:hidden path="active"/>		
	</div>

	<div class="portlet-section-header">
		<input type="submit" name="_finish" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
	</div>
   
    <doc41:errors form="user"/>

	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitelUserData" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
				<colcolgroup>
					<col width="15%" />
					<col width="35%" />
					<col width="15%" />
					<col width="35%" />
				</colcolgroup>
			</thead>
			<tbody class="portlet-table-body">
				<tr>
					<th style="width: 15%"><doc41:translate label="Surname"/></th> 
					<td style="width: 35%">
						<!-- c:if test="${user.type eq 'external' && !user.readOnly}" -->
							<!-- form:input path="surname" cssClass="portlet-form-input-field"/ -->
						<!-- /c:if -->
						<!-- c:if test="${user.type eq 'internal' || user.readOnly}" -->
							<c:out value="${user.surname}"/>
							<form:hidden path="surname"/>
						<!-- /c:if -->
					</td>
					<th><doc41:translate label="Firstname"/></th>
					<td>
						<!-- c:if test="${user.type eq 'external' && !user.readOnly}" -->
							<!-- form:input path="firstname" cssClass="portlet-form-input-field"   maxlength="30"/ -->
						<!-- /c:if -->
						<!-- c:if test="${user.type eq 'internal' || user.readOnly}" -->
							<c:out value="${user.firstname}"/>
							<form:hidden path="firstname"/>
						<!-- /c:if -->
					</td>
				</tr>
				
				<tr class="portlet-table-alternate">
					<th><doc41:translate label="Cwid"/></th>
					<td>
						<c:out value="${user.cwid}"/> <form:hidden path="cwid"/>
					</td>
					<th><doc41:translate label="Email"/></th>
					<td>
						<!-- c:if test="${user.type eq 'external' && !user.readOnly}" -->
							<!-- form:input path="email" cssClass="portlet-form-input-field"  maxlength="70"/ -->
						<!-- /c:if -->
						<!-- c:if test="${user.type eq 'internal' || user.readOnly}" -->
							<c:out value="${user.email}"/>
							<form:hidden path="email"/>
						<!-- /c:if -->
					</td>
				</tr>
				
				<tr>
					<th><doc41:translate label="Language"/></th>
					<td>
					    <form:select path="languageCountry" cssClass="portlet-form-input-field">
							<form:options items="${languageCountryList}" itemValue="code" itemLabel="label"/>
						</form:select>
					</td>
					<c:if test="${user.type eq 'external' && !user.readOnly}">
					<th><doc41:translate label="Password"/></th>
					<td><form:password path="password" cssClass="portlet-form-input-field" /></td>
					</c:if>
				</tr>	
				
				<c:if test="${user.type eq 'external' && !user.readOnly}">
				<tr class="portlet-table-alternate">
					<th></th>
					<td>&nbsp;</td>
					<th><doc41:translate label="RepeatPassword"/></th>
					<td><form:password path="passwordRepeated" cssClass="portlet-form-input-field" /></td>
				</tr>		
				</c:if>
						
			</tbody>
		</table>
	</div>
	
	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitelUserRoles" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
			</thead>
			<tbody class="portlet-table-body">		
				
				<tr>
					
					<th style="width: 15%"><doc41:translate label="Roles"/></th>
					<td style="width: 35%">
					
						<table id="rolelist">
							<colgroup>
								<col width="100%"/>
							</colgroup>
							<c:forEach items="${user.roles}" var="role" varStatus="roleStatus">
								<tr>
									<td><doc41:translate label="${role}"/></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<th style="width: 15%">
					<td style="width: 35%">
				</tr>
			</tbody>
		</table>
	</div>
	
	<c:if test="${!empty user.customers }">
	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitleCustomers" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
			</thead>
			<tbody class="portlet-table-body">		
				
				<tr>
					
					<th style="width: 15%"><doc41:translate label="CustomerNumbers"/></th>
					<td style="width: 85%">
					
						<table id="customerlist">
							<colgroup>
								<col width="20%"/>
								<col width="40%"/>
								<col width="40%"/>
							</colgroup>
							<c:forEach items="${user.customers}" var="customer" varStatus="customerStatus">
								<tr>
									<td><c:out value="${customer.number}"/></td>
									<td><c:out value="${customer.name1}"/></td>
									<td><c:out value="${customer.name2}"/></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	
	<c:if test="${!empty user.vendors }">
	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitleVendors" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
			</thead>
			<tbody class="portlet-table-body">		
				
				<tr>
					
					<th style="width: 15%"><doc41:translate label="VendorNumbers"/></th>
					<td style="width: 85%">
					
						<table id="vendorlist">
							<colgroup>
								<col width="15%"/>
								<col width="40%"/>
								<col width="40%"/>
								<col width="5%"/>
							</colgroup>
							<c:forEach items="${user.vendors}" var="vendor" varStatus="vendorStatus">
								<tr>
									<td><c:out value="${vendor.number}"/></td>
									<td><c:out value="${vendor.name1}"/></td>
									<td><c:out value="${vendor.name2}"/></td>
								</tr>
							</c:forEach>
						</table>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	
	<c:if test="${!empty user.countries }">
	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitelCountries" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
			</thead>
			<tbody class="portlet-table-body">		
				
				<tr>
					
					<th style="width: 15%"><doc41:translate label="CountryCodes"/></th>
					<td style="width: 35%">
					
						<table id="countrylist">
							<colgroup>
								<col width="100%"/>
							</colgroup>
							<c:forEach items="${user.countries}" var="country" varStatus="countryStatus">
								<tr>
									<td><c:out value="${country}"/></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<th style="width: 15%">
					<td style="width: 35%">
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	
	<c:if test="${!empty user.plants }">
	<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="TitelPlants" /></div>
		<table cellpadding="4" cellspacing="0" class="nohover">
			<thead class="portlet-table-header">
			</thead>
			<tbody class="portlet-table-body">		
				
				<tr>
					
					<th style="width: 15%"><doc41:translate label="Plants"/></th>
					<td style="width: 35%">
					
						<table id="plantlist">
							<colgroup>
								<col width="100%"/>
							</colgroup>
							<c:forEach items="${user.plants}" var="plant" varStatus="plantStatus">
								<tr>
									<td><c:out value="${plant}"/></td>
								</tr>
							</c:forEach>
						</table>
					</td>
					<th style="width: 15%">
					<td style="width: 35%">
				</tr>
			</tbody>
		</table>
	</div>
	</c:if>
	
</form:form>
</doc41:layout>