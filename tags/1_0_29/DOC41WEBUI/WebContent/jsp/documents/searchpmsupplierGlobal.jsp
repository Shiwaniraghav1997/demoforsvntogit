<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="searchpmsupplier" component="documents" activeTopNav="download"
	activeNav="${searchForm.type}" title="Search Document PM Global">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
	<doc41:searchtemplate action="searchpmsupplierGlobal">
		<jsp:attribute name="fragmentCustomSearchFields">
          <c:if test="!${searchForm.kgs}">
			<tr class="portlet-table">
				<th><label for="${keyFileName}"><doc41:translate label="FileName" /></label></th>
				<td>
					<input id="${keyFileName}"
						class="portlet-form-input-field portlet-big" maxlength="70"
						name="viewAttributes['${keyFileName}']"
						value="${searchForm.viewAttributes[keyFileName]}" />
						<doc41:error path="viewAttributes['${keyFileName}']" />
				</td>
			</tr>
          </c:if>
        </jsp:attribute>
	</doc41:searchtemplate>


</doc41:layout>
