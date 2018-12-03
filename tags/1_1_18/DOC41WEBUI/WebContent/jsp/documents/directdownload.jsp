<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="directdownload" component="documents" activeTopNav="download"
	activeNav="${type}" title="Direct Download">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


	<div id="div-body" class="portlet-body">
		<div class="portlet-section-header">
			<div class="portlet-section-header-title">
				<doc41:translate label="DirectDownload"/><doc41:translate label="${type}"/>
			</div>
		</div>
		
		<div class="portlet-section-body">
			<doc41:translate label="DirectDownloadText" /><p>
			<a href="${autoRedirectUrl}"><doc41:translate label="ManualDownload" /></a>
		</div>
	</div>
</doc41:layout>
