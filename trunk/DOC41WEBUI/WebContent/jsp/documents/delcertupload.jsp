<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="delcertupload" 	activeNav="${uploadForm.type}"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<doc41:uploadtemplate action="delcertuploadpost" showObjectId="false" showPartnerNumber="false" showCustomAttributes="false">
		<jsp:attribute name="fragmentCustomSearchFields">
						<tr>
							<th><doc41:translate label="BatchObjectId" /></th>
							<td><c:out value="${uploadForm.objectId }"/><form:hidden path="objectId"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Supplier" /></th>
							<td><c:out value="${uploadForm.partnerNumber }"/><form:hidden path="partnerNumber"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="Plant" />${uploadForm.attributeValues[keyPlant]}</th>
							<td><c:out value="${uploadForm.attributeValues[keyPlant]}"/><input id="${keyPlant}" type="hidden" name="attributeValues['${keyPlant}']" value="${uploadForm.attributeValues[keyPlant]}"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Batch" /></th>
							<td><c:out value="${uploadForm.attributeValues[keyBatch]}"/><input id="${keyBatch}" type="hidden" name="attributeValues['${keyBatch}']" value="${uploadForm.attributeValues[keyBatch]}"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="MaterialNumber" /></th>
							<td><c:out value="${uploadForm.attributeValues[keyMaterial]}"/><input id="${keyMaterial}" type="hidden" name="attributeValues['${keyMaterial}']" value="${uploadForm.attributeValues[keyMaterial]}"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="MaterialText" /></th>
							<td><c:out value="${materialText }"/></td>
						</tr>
	
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Country" /></th>
							<td>
								<form:select id="${keyCountry}" path="attributeValues['${keyCountry}']" items="${userCountrySIList}" cssClass="portlet-form-input-field" cssStyle="width:240px;" itemValue="value" itemLabel="label"/><doc41:error path="attributeValues['${keyCountry}']" />
								
								<%-- <select id="${keyCountry}" class="portlet-form-input-field"  name="attributeValues['${keyCountry}']">
									<c:forEach items="${uploadForm.attributePredefValues[keyCountry]}" var="predefValue" varStatus="pdstatus">
										<c:choose>
											<c:when test="${attributeValue.value ==  predefValue}"><option selected="selected">${predefValue}</option></c:when>
											<c:otherwise><option>${predefValue}</option></c:otherwise>
										</c:choose>		            	
					         		</c:forEach>
					         	</select> --%>
							
							</td>
						</tr>
						
						
		</jsp:attribute>
	</doc41:uploadtemplate>
		
</doc41:layout>