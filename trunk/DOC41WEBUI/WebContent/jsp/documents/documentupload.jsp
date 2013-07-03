<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations jspName="documentupload" component="documents" />
<script type="text/javascript" src="<%= response.encodeURL(request.getContextPath() + "/resources/js/doc41popup.js") %>"></script>
<script>
function test(text){
	alert(text);
}
</script>
<html>
<head>
<title><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}" /></title>
</head>

<body>
	  <%@include file="../doc41/header.jspf" %>
	<div id="div-body" class="portlet-body">
		<form:form commandName="uploadForm" action="upload"
			method="post" enctype="multipart/form-data">
			<div class="portlet-section-header">
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="Upload"/>" />
			</div>
			<div class="portlet-section-body">
				<table class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="2"><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}" /></th>
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
					TODO Delivery Nummer
					TODO Button "Available Delivery Numbers"
					TODO Creditor
					TODO Rechnungsnummer je Lieferung
					TODO Version
					TODO dynamische Attribute
					TODO predefined values
					</pre>
					</td></tr>
					<tr>
						<td><doc41:translate label="DeliveryNumber" /></td>
						<td><form:input path="deliveryNumber" cssClass="portlet-form-input-field"  maxlength="70"/></td>
						<td><p><a href="opendeliveries?type=${uploadForm.type}" id="fireIframe">Open Deliveries</a></p></td>
					</tr>
					<tr>
						<td><doc41:translate label="ShippingUnitNumber" /></td>
						<td><form:input path="shippingUnitNumber" cssClass="portlet-form-input-field"  maxlength="70"/></td>
					</tr>
					
					 <c:forEach items="${uploadForm.attributeValues}" var="attributeValues" varStatus="status">
				        <tr>
				            <td><c:out value="${uploadForm.attributeLabels[attributeValues.key]}"/>
				            <input type="hidden" name="attributeLabels['${attributeValues.key}']" value="${uploadForm.attributeLabels[attributeValues.key]}"/>
				            </td>
				            <td><input name="attributeValues['${attributeValues.key}']" value="${attributeValues.value}"/></td>
				        </tr>
				    </c:forEach>
					
					
					<tbody class="portlet-table-body">
						<tr>
							<th><doc41:translate label="SelectFile" /></th>
							<td><input name="file" type="file" size="40"/>
							 <doc41:error path="file" /></td>
						</tr>
					</tbody>
				</table>
				<br>

			</div>
			<div class="portlet-section-header">
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="Upload"/>" />
			</div>
		</form:form>
	</div>
</body>
</html>