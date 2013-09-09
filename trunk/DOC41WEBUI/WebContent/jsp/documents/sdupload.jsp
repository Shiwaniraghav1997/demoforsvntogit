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
function setDeliveryNumber(refNumber,shipunit){
	$allPopups['open_delivery_dialog'].dialog('close');
	$('#objectId').val(refNumber);
}

function popupAppendFunction(){
	var pn = $("#partnerNumber").val();
	return '&partnerNumber='+pn;
}
</script>

	<doc41:uploadtemplate action="sduploadpost">
		<jsp:attribute name="fragmentAdditionalButtons">
			<a  class="portlet-form-button" href="opendeliveries?type=${uploadForm.type}" id="openPopupLink" target="open_delivery_dialog"><doc41:translate label="DeliveriesWithout" />&nbsp;<doc41:translate label="${uploadForm.type}"/></a>
		</jsp:attribute>
	</doc41:uploadtemplate>

	
</doc41:layout>