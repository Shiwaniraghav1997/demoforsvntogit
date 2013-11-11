<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="supcoaupload" 	activeNav="${uploadForm.type}UP"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>


	<doc41:uploadtemplate action="supcoauploadpost" showObjectId="false" showPartnerNumber="false" showCustomAttributes="false">
		<jsp:attribute name="fragmentCustomSearchFields">
						<tr>
							<th><doc41:translate label="InspectionLot" /></th>
							<td><c:out value="${uploadForm.objectId }"/><form:hidden path="objectId"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Vendor" /></th>
							<td><c:out value="${uploadForm.partnerNumber }"/><form:hidden path="partnerNumber"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="VendorBatch" /></th>
							<td><c:out value="${uploadForm.attributeValues[keyVendorBatch]}"/><input id="${keyVendorBatch}" type="hidden" name="attributeValues['${keyVendorBatch}']" value="${uploadForm.attributeValues[keyVendorBatch]}"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Plant" />${uploadForm.attributeValues[keyPlant]}</th>
							<td><c:out value="${uploadForm.attributeValues[keyPlant]}"/><input id="${keyPlant}" type="hidden" name="attributeValues['${keyPlant}']" value="${uploadForm.attributeValues[keyPlant]}"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="Batch" /></th>
							<td><c:out value="${uploadForm.attributeValues[keyBatch]}"/><input id="${keyBatch}" type="hidden" name="attributeValues['${keyBatch}']" value="${uploadForm.attributeValues[keyBatch]}"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="MaterialNumber" /></th>
							<td><c:out value="${uploadForm.attributeValues[keyMaterial]}"/><input id="${keyMaterial}" type="hidden" name="attributeValues['${keyMaterial}']" value="${uploadForm.attributeValues[keyMaterial]}"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="MaterialText" /></th>
							<td><c:out value="${materialText }"/></td>
						</tr>
						
						
		</jsp:attribute>
	</doc41:uploadtemplate>
		
</doc41:layout>