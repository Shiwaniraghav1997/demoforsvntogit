<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="upload" 	activeNav="${uploadForm.type}"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<c:if test="${uploadForm.showOpenDeliveries }">
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
</c:if>



	<div id="div-body" class="portlet-body">
		<form:form commandName="uploadForm" action="upload"
			method="post" enctype="multipart/form-data">
			<form:hidden path="type"/>
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<tr><th><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}"/></th></tr>
				</table>
				<c:if test="${uploadForm.partnerNumberUsed && uploadForm.showOpenDeliveries }">
				<a  class="portlet-form-button" href="opendeliveries?type=${uploadForm.type}" id="openPopupLink" target="open_delivery_dialog"><doc41:translate label="DeliveriesWithout" />&nbsp;<doc41:translate label="${uploadForm.type}"/></a>
				</c:if>
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
						
						
						
						<script>
	function checktest1(test1,test2){
		if(typeof test1 == 'undefined'){
			var test1=$("#test1").val();	
		}
		if(test1==""){
			var test2 = "";
			$("#test2single").show();
			$("#test2multi").hide();
			$("#test2ro").html(test2);
			$("#test2hidden").val(test2);
			$("#test2hidden").prop('disabled', false);
			$("#test2select").prop('disabled', true);
		} else {
 			//TODO AJAX call
			if(test1=="123"){
				var test2 = "999";
				$("#test2single").show();
				$("#test2multi").hide();
				$("#test2ro").html(test2);
				$("#test2hidden").val(test2);
				$("#test2hidden").prop('disabled', false);
				$("#test2select").prop('disabled', true);
			} else {
				$("#test2single").hide();
				$("#test2multi").show();
				$("#test2hidden").prop('disabled', true);
				$("#test2select").prop('disabled', false);
				$('#test2select').append('<option value="222" selected="selected">222</option>');
				$('#test2select').append('<option value="333">333</option>');
				$('#test2select').append('<option value="444">444</option>');
				if(typeof test2 != 'undefined'){
					$('#test2select').val(test2);
				}
				setTimeout(function(){
					$("#test2select").focus();
				},1);
			}
		}
	}
	
	$(function() {
		checktest1("${uploadForm.test1}","${uploadForm.test2}");
	});
</script>
						
						<tr id="test2single">
							<th><doc41:translate label="test2" /></th>
							<td><span id="test2ro">&nbsp;</span><form:hidden id="test2hidden" path="test2"/></<input></td>
						</tr>
						
						<tr id="test2multi">
							<th><doc41:translate label="test2" /></th>
							<td><form:select id="test2select" path="test2" cssClass="portlet-form-input-field" cssStyle="width:240px;"/><doc41:error path="test2" /></td>
						</tr>						
						
						
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="ObjectId${uploadForm.type}" /></th>
							<td><form:input path="objectId" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="objectId" /></td>
						</tr>
						
						 <%@include file="uploadcustattrib.jspf" %>
						 
					</tbody>
				</table>
			</div>
			<%@include file="uploadfile.jspf" %>
		</form:form>
	</div>
</doc41:layout>