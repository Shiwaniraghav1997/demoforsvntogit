<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="sdupload" 	component="documents"
activeTopNav="upload" 	activeNav="${uploadForm.type}"
title="Upload SD Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<!-- open delivery popup -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41popup.js"></script>
<script>
function setDeliveryNumber(delNumber,shipunit){
	$allPopups['open_delivery_dialog'].dialog('close');
	$('#deliveryNumber').val(delNumber);
	$('#SHIPPINGUNIT').val(shipunit);
}

function popupAppendFunction(){
	var pn = $("#partnerNumber").val();
	return '&partnerNumber='+pn;
}
</script>



	<div id="div-body" class="portlet-body">
		<form:form commandName="uploadForm" action="sduploadpost"
			method="post" enctype="multipart/form-data">
			<form:hidden path="type"/>
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<tr><th><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}"/></th></tr>
				</table>
				<a  class="portlet-form-button" href="opendeliveries?type=${uploadForm.type}" id="openPopupLink" target="open_delivery_dialog"><doc41:translate label="DeliveriesWithout" />&nbsp;<doc41:translate label="${uploadForm.type}"/></a>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonUpload"/>" />
			</div>
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="attributes"/></th>
						</tr>
						<colcolgroup>
							<col width="15%"/>
							<col width="35%"/>
							<col width="50%"/>
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
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
						
						<%@include file="uploadstdattrib.jspf" %>
						
						 <%@include file="uploadcustattrib.jspf" %>
						 
					</tbody>
				</table>
			</div>
			<%@include file="uploadfile.jspf" %>
		</form:form>
	</div>
</doc41:layout>