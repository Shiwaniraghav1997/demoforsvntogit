<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchdelcertcountry" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}" title="Search Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	
	<doc41:searchtemplate action="searchdelcertcountry" showCustomAttributes="false" showObjectId="false" showPartnerNumber="false">
		<jsp:attribute name="fragmentCustomSearchFields">
			<tr>
				<th><doc41:translate label="Country" /></th>
				<td>
					<form:select id="${keyCountry}" path="attributeValues['${keyCountry}']" items="${userCountrySIList}" cssClass="portlet-form-input-field portlet-mandatory" itemValue="value" itemLabel="label"/>*<doc41:error path="attributeValues['${keyCountry}']" />
					
					<%-- <select id="${keyCountry}" class="portlet-form-input-field"  name="attributeValues['${keyCountry}']">
						<c:forEach items="${searchForm.attributePredefValues[keyCountry]}" var="predefValue" varStatus="pdstatus">
							<c:choose>
								<c:when test="${attributeValue.value ==  predefValue}"><option selected="selected">${predefValue}</option></c:when>
								<c:otherwise><option>${predefValue}</option></c:otherwise>
							</c:choose>		            	
		         		</c:forEach>
		         	</select> --%>
				
				</td>
			</tr>
			<tr class="portlet-table-alternate">
				<th><doc41:translate label="MaterialNumber" /></th>
				<td>
					<input id="${keyMaterial}"
						class="portlet-form-input-field" maxlength="70"
						name="attributeValues['${keyMaterial}']"
						value="${searchForm.attributeValues[keyMaterial]}" />
						<doc41:error path="attributeValues['${keyMaterial}']" />
				</td>
			</tr>
			<tr>
				<th><doc41:translate label="Batch" /></th>
				<td>
					<input id="${keyBatch}"
						class="portlet-form-input-field" maxlength="70"
						name="attributeValues['${keyBatch}']"
						value="${searchForm.attributeValues[keyBatch]}" />
						<doc41:error path="attributeValues['${keyBatch}']" />
				</td>
			</tr>
		</jsp:attribute>
	</doc41:searchtemplate>


</doc41:layout>
