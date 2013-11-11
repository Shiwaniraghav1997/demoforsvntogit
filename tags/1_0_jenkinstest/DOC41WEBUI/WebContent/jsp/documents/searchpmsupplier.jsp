<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchpmsupplier" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}DOWN" title="Search Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	<doc41:searchtemplate action="searchpmsupplier">
		<jsp:attribute name="fragmentCustomSearchFields">
			<tr class="portlet-table">
				<th><doc41:translate label="PONumber" /></th>
				<td>
					<input id="${keyPONumber}"
						class="portlet-form-input-field-mandatory" maxlength="70"
						name="viewAttributes['${keyPONumber}']"
						value="${searchForm.viewAttributes[keyPONumber]}" />*
						<doc41:error path="viewAttributes['${keyPONumber}']" />
				</td>
			</tr>
		</</jsp:attribute>
	</doc41:searchtemplate>


</doc41:layout>
