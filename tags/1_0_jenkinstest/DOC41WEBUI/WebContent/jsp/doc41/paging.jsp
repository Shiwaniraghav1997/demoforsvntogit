
<form:form commandName="${pagingForm.formName}" action="${lastRenderedAction}" method="post">

    <form:hidden path="command"/>
    <form:hidden path="submitCount"/>
    <form:hidden path="totalSize"/>
    
	<table style="float: left; padding-left: 2px; padding-right: 30px;" cellspacing="0" cellpadding="0">
		<tr>
			<%@include file="paging_links.jspf" %>
			
			<c:if test="${pagingForm.totalSize > 0}">
				<td style="whitespace: nowrap; text-align: right; border: 0;">
					<nobr>
						<label for="hitsperpage"><doc41:translate label="recordsPerPage"/></label>
						<form:select path="pageSize" cssClass="portlet-form-input-field" onchange="submitPagingAction('${pagingForm.formName}', 'PageSize')">
							<form:option value="1"></form:option>
							<form:option value="2"></form:option>
							<form:option value="5"></form:option>
							<form:option value="10"></form:option>
							<form:option value="25"></form:option>
							<form:option value="100"></form:option>
							<form:option value="1000"></form:option>
						</form:select>				
					</nobr>
				</td>
			</c:if>
		</tr>
	</table> 
</form:form>
