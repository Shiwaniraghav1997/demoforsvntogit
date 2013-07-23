<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="nothing" activeNav="translations" 
	jspName="exception" 		component="error"
	title="Exception">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<div class="portlet-section-body" style="width: 98%">
	<table cellpadding="4" cellspacing="0" class="nohover">
		<tbody class="portlet-table-body">
			<tr>
				<td>
					<table cellpadding="0" cellspacing="0" style="border-width: 0px;" class="nohover">
						<tr style="color: red"> 
							<td style="border-width: 0px;" valign="baseline"><img alt="Exception" src="${pageContext.request.contextPath}/resources/img/common/exclamation.png" align="top" />&nbsp;</td>
							<td style="border-width: 0px;" align="top"><doc41:exception exception="${doc41exception}"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</tbody>
	</table>
</div>
</doc41:layout>