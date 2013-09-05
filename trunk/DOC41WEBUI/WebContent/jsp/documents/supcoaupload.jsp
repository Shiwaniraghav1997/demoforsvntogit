<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="supcoaupload" 	activeNav="${uploadForm.type}"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script>
	function mapBatchToTestLot(batch,testlot){
		if(typeof batch == 'undefined'){
			var batch=$("#batch").val();	
		}
		if(batch==""){
			var testlot = "";
			$("#testlotsingle").show();
			$("#testlotmulti").hide();
			$("#testlotro").html(testlot);
			$("#testlothidden").val(testlot);
			$("#testlothidden").prop('disabled', false);
			$("#testlotselect").prop('disabled', true);
		} else {
				//TODO AJAX call
			if(batch=="123"){
				var testlot = "999";
				$("#testlotsingle").show();
				$("#testlotmulti").hide();
				$("#testlotro").html(testlot);
				$("#testlothidden").val(testlot);
				$("#testlothidden").prop('disabled', false);
				$("#testlotselect").prop('disabled', true);
			} else {
				$("#testlotsingle").hide();
				$("#testlotmulti").show();
				$("#testlothidden").prop('disabled', true);
				$("#testlotselect").prop('disabled', false);
				$('#testlotselect').append('<option value="222" selected="selected">222</option>');
				$('#testlotselect').append('<option value="333">333</option>');
				$('#testlotselect').append('<option value="444">444</option>');
				if(typeof testlot != 'undefined'){
					$('#testlotselect').val(testlot);
				}
				setTimeout(function(){
					$("#testlotselect").focus();
				},1);
			}
		}
	}
	
	$(function() {
		mapBatchToTestLot("${uploadForm.batch}","${uploadForm.objectId}");
	});
</script>

	<doc41:uploadtemplate action="supcoauploadpost" showObjectId="false">
		<jsp:attribute name="fragmentCustomSearchFields">
						<tr>
							<th><doc41:translate label="batch" /></th>
							<td><form:input path="batch" cssClass="portlet-form-input-field"  maxlength="70" onblur="mapBatchToTestLot();"/><doc41:error path="batch" /></td>
						</tr>
						

						
						<tr id="testlotsingle">
							<th><doc41:translate label="testlot" /></th>
							<td><span id="testlotro">&nbsp;</span><form:hidden id="testlothidden" path="objectId"/></<input></td>
						</tr>
						
						<tr id="testlotmulti">
							<th><doc41:translate label="testlot" /></th>
							<td><form:select id="testlotselect" path="objectId" cssClass="portlet-form-input-field" cssStyle="width:240px;"/><doc41:error path="objectId" /></td>
						</tr>
		</jsp:attribute>
	</doc41:uploadtemplate>
		
</doc41:layout>