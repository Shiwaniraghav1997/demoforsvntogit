<%@taglib prefix="doc41" uri="doc41-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<doc41:layout activePage="${pageContext.request.servletPath}"
	jspName="qmcoaupload" component="documents" activeTopNav="upload"
	activeNav="${uploadForm.type}UP" title="Upload QMCoA Document">
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/doc41popup.js"></script>
	<script>
		function setDeliveryNumber(refNumber, shipunit) {
			$allPopups['open_delivery_dialog'].dialog('close');
			$('#objectId').val(refNumber);
		}
		function popupAppendFunction() {
			var pn = $("#vendorNumber").val();
			return '&vendorNumber=' + pn;
		}
	</script>
	<doc41:uploadtemplate action="qmcoauploadpost">
		<jsp:attribute name="fragmentAdditionalButtons">
		</jsp:attribute>
	</doc41:uploadtemplate>
</doc41:layout>