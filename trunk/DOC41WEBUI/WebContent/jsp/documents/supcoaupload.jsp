<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="documentupload" 	component="documents"
activeTopNav="supcoaupload" 	activeNav="${uploadForm.type}"
title="Upload Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script>
	function mapBatchToInspectionLot(batch,inspectionlot){
		if(typeof batch == 'undefined'){
			var batch=$("#batch").val();	
		}
		if(batch==""){
			var inspectionlot = "";
			$("#inspectionlotsingle").show();
			$("#inspectionlotmulti").hide();
			$("#inspectionlotro").html(inspectionlot);
			$("#inspectionlothidden").val(inspectionlot);
			$("#inspectionlothidden").prop('disabled', false);
			$("#inspectionlotselect").prop('disabled', true);
		} else {
				//TODO AJAX call
			if(batch=="123"){
				var inspectionlot = "999";
				$("#inspectionlotsingle").show();
				$("#inspectionlotmulti").hide();
				$("#inspectionlotro").html(inspectionlot);
				$("#inspectionlothidden").val(inspectionlot);
				$("#inspectionlothidden").prop('disabled', false);
				$("#inspectionlotselect").prop('disabled', true);
			} else {
				$("#inspectionlotsingle").hide();
				$("#inspectionlotmulti").show();
				$("#inspectionlothidden").prop('disabled', true);
				$("#inspectionlotselect").prop('disabled', false);
				$('#inspectionlotselect').append('<option value="222" selected="selected">222</option>');
				$('#inspectionlotselect').append('<option value="333">333</option>');
				$('#inspectionlotselect').append('<option value="444">444</option>');
				if(typeof inspectionlot != 'undefined'){
					$('#inspectionlotselect').val(inspectionlot);
				}
				setTimeout(function(){
					$("#inspectionlotselect").focus();
				},1);
			}
		}
	}
	
	$(function() {
		mapBatchToInspectionLot("${uploadForm.batch}","${uploadForm.objectId}");
	});
</script>

	<doc41:uploadtemplate action="supcoauploadpost" showObjectId="false">
		<jsp:attribute name="fragmentCustomSearchFields">
						<tr>
							<th style="border-left-width: 1px; border-top-width: 1px; border-style: solid;"><doc41:translate label="batch" /></th>
							<td style="border-right-width: 1px; border-top-width: 1px; border-style: solid;"><form:input path="batch" cssClass="portlet-form-input-field"  maxlength="70" onblur="mapBatchToInspectionLot();"/><doc41:error path="batch" /></td>
						</tr>
						

						
						<tr id="inspectionlotsingle">
							<th style="border-left-width: 1px; border-bottom-width: 1px; border-style: solid;"><doc41:translate label="inspectionlot" /></th>
							<td style="border-right-width: 1px; border-bottom-width: 1px; border-style: solid;"><span id="inspectionlotro">&nbsp;</span><form:hidden id="inspectionlothidden" path="objectId"/></<input></td>
						</tr>
						
						<tr id="inspectionlotmulti">
							<th style="border-left-width: 1px; border-bottom-width: 1px; border-style: solid;"><doc41:translate label="inspectionlot" /></th>
							<td style="border-right-width: 1px; border-bottom-width: 1px; border-style: solid;"><form:select id="inspectionlotselect" path="objectId" cssClass="portlet-form-input-field" cssStyle="width:240px;"/><doc41:error path="objectId" /></td>
						</tr>
		</jsp:attribute>
	</doc41:uploadtemplate>
		
</doc41:layout>