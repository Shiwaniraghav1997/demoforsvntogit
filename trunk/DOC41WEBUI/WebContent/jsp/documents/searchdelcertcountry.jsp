<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchdelcertcountry" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}" title="Search Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	
	<doc41:searchtemplate action="searchdelcertcountry" showCustomAttributes="false" showObjectId="false" showCustomerNumber="false" showVendorNumber="false">
		<jsp:attribute name="fragmentCustomSearchFields">
			<tr>
				<th><label for="${keyCountry}"><doc41:translate label="Country" /></label></th>
				<td>
					<form:select id="${keyCountry}" path="attributeValues['${keyCountry}']" items="${userCountrySIList}" cssClass="portlet-form-input-field portlet-mandatory" itemValue="value" itemLabel="label"/><doc41:error path="attributeValues['${keyCountry}']" />

				
				</td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><label for="${keyMaterial}"><doc41:translate label="MaterialNumber" /></label></th>
				<td>
					<input id="${keyMaterial}"
						class="portlet-form-input-field" maxlength="70"
						name="attributeValues['${keyMaterial}']"
						value="${searchForm.attributeValues[keyMaterial]}" />
						<doc41:error path="attributeValues['${keyMaterial}']" />
				</td>
			</tr>
			<tr>
				<th><label for="${keyBatch}"><doc41:translate label="Batch" /></label></th>
				<td>
					<input id="${keyBatch}"
						class="portlet-form-input-field" maxlength="70"
						name="attributeValues['${keyBatch}']"
						value="${searchForm.attributeValues[keyBatch]}" />
						<doc41:error path="attributeValues['${keyBatch}']" />
				</td>
			</tr>
			
			<%-- TODO use if rfc should be used <tr class="portlet-table-alternate">
				<th><label for="${keyMaterial}"><doc41:translate label="MaterialNumber" /></label></th>
				<td>
					<input id="${keyMaterial}"
						class="portlet-form-input-field" maxlength="70"
						name="viewAttributes['${keyMaterial}']"
						value="${searchForm.viewAttributes[keyMaterial]}" />
						<doc41:error path="viewAttributes['${keyMaterial}']" />
				</td>
			</tr>
			<tr>
				<th><label for="${keyBatch}"><doc41:translate label="Batch" /></label></th>
				<td>
					<input id="${keyBatch}"
						class="portlet-form-input-field" maxlength="70"
						name="viewAttributes['${keyBatch}']"
						value="${searchForm.viewAttributes[keyBatch]}" />
						<doc41:error path="viewAttributes['${keyBatch}']" />
				</td>
			</tr> --%>
		</jsp:attribute>
	</doc41:searchtemplate>


</doc41:layout>
