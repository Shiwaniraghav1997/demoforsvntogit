<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="documents" 	activeNav="documentType1"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41popup.js"></script>
<script>
function setDeliveryNumber(delNumber,shipunit){
	$allPopups['open_delivery_dialog'].dialog('close');
	$('#deliveryNumber').val(delNumber);
	$('#shippingUnitNumber').val(shipunit);
}
</script>

	<div id="div-body" class="portlet-body">
		<form:form commandName="uploadForm" action="upload"
			method="post" enctype="multipart/form-data">
			<form:hidden path="type"/>
			<div class="portlet-section-header">
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="Upload"/>" />
			</div>
			<div class="portlet-section-body">
				<table class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="2"><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}"/> </th>
						</tr>
					</thead>
					<spring:hasBindErrors name="uploadForm">
						<tr>
							<td colspan="2"><c:forEach items="${errors.globalErrors}"
									var="error">
									<tr style="color: red">
										<doc41:translate label="${error.code}" />
									</tr>
								</c:forEach>
							</td>
						</tr>
					</spring:hasBindErrors>
					
					<tr><td colspan="3">
					<pre>
					TODO Creditor
					TODO Rechnungsnummer je Lieferung
					TODO Version
					TODO predefined values
					</pre>
					</td></tr>
					<tr>
						<td><doc41:translate label="DeliveryNumber" /></td>
						<td><form:input path="deliveryNumber" cssClass="portlet-form-input-field"  maxlength="70"/></td>
						<td><p><a href="opendeliveries?type=${uploadForm.type}" id="openPopupLink" target="open_delivery_dialog">Open Deliveries</a></p></td>
					</tr>
					
					 <c:forEach items="${uploadForm.attributeValues}" var="attributeValues" varStatus="status">
				        <tr>
				            <td><c:out value="${uploadForm.attributeLabels[attributeValues.key]}"/>
				            <input type="hidden" name="attributeLabels['${attributeValues.key}']" value="${uploadForm.attributeLabels[attributeValues.key]}"/>
				            </td>
				            <td><input class="portlet-form-input-field"  maxlength="70" name="attributeValues['${attributeValues.key}']" value="${attributeValues.value}"/></td>
				        </tr>
				    </c:forEach>
					
					
					<tr>
						<th><doc41:translate label="SelectFile" /></th>
						<td>
						<c:choose>
							<c:when test="${empty uploadForm.fileId}">
								<input name="file" type="file" size="40"/><doc41:error path="file" />
							</c:when>
							<c:otherwise>
								<doc41:translate label="FileAlreadyUploaded" />
								<form:hidden path="fileId"/>
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</table>
				<br>

			</div>
			<div class="portlet-section-header">
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="Upload"/>" />
			</div>
		</form:form>
	</div>
</doc41:layout>