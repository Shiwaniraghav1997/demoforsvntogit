<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="ptupload" 	component="documents"
activeTopNav="upload" 	activeNav="${uploadForm.type}UP"
title="Upload PT Document">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<!-- open delivery popup -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41popup.js"></script>

	<doc41:uploadtemplate action="ptuploadpost">
	</doc41:uploadtemplate>

	
</doc41:layout>