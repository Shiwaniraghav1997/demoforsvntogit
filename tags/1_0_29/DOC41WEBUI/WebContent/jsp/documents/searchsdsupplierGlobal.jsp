<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchsdsupplier" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}" title="Search Document SD Global">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	<doc41:searchtemplate action="searchsdsupplierGlobal">
	</doc41:searchtemplate>


</doc41:layout>
