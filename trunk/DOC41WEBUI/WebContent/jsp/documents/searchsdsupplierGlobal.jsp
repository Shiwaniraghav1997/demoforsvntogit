<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchsdsupplier" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}" title="Search Document SD Global">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	<doc41:searchtemplate action="searchsdsupplierGlobal">
        <jsp:attribute name="fragmentCustomSearchFields">
          <tr class="portlet-table">
            <th><label for="docType"><doc41:translate label="DocumentType" /></label></th>
            <td><form:select path="docType"
                items="${searchForm.allowedDocTypes}" cssClass="portlet-form-input-field portlet-big"
                itemLabel="label"
                itemValue="value" />
            <doc41:error path="docType" /></td>
          </tr>
        </jsp:attribute>
	</doc41:searchtemplate>


</doc41:layout>
