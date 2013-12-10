<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="uploadsuccess" component="documents" activeTopNav="upload"
	activeNav="${type}UP" title="Upload Successful">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


	<div id="div-body" class="portlet-body">
		<div class="portlet-section-header">
			<div class="portlet-section-header-title">
				<doc41:translate label="Upload Successful"/><doc41:translate label="${type}"/>
			</div>
			<a class="portlet-form-button" href='${uploadurl}?type=${type}&partnerNumber=${partnerNumber}'><doc41:translate label="NewUpload"/></a>
		</div>
		
		<div class="portlet-section-body">
			<doc41:translate label="UploadSuccessfulText" />
		</div>
	</div>
</doc41:layout>
