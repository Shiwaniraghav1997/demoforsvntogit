<c:if test="${editable}">
	<div class="portlet-section-header">
  		<%@include file="../../doc41/paging.jsp"%>
    	<input type="button" class="portlet-form-button" onclick="sendGet('translationadd.htm', '')" value="<doc41:translate label="buttonCreate"/>" />
	</div>
</c:if>
<c:if test="${!editable}">
	<div class="portlet-section-header">
  		<%@include file="../../doc41/paging.jsp"%>
	</div>
</c:if>

<div class="portlet-section-body">
<table cellpadding="4" cellspacing="0">
	<thead class="portlet-table-header" >
		<c:if test="${empty translationList}">
			<tr>
				<td colspan="9" style="color:red">
				<doc41:translate label="NoTranslationsFound" />
				</td>
			</tr>
		</c:if>
		<tr>
			<th><doc41:translate label="Mandant" /></th>
			<th><doc41:translate label="Component" /></th>
			<th><doc41:translate label="Page" /></th>
			<th><doc41:translate label="TagName" /></th>
			<th><doc41:translate label="Language" /></th>
			<th><doc41:translate label="Country" /></th>
			<th><doc41:translate label="TagValue" /></th>
			<th colspan="2">&nbsp;</th>
		</tr>
	</thead>

	<tbody class="portlet-table-body">
		<c:forEach items="${translationList}" var="view" varStatus="lineInfo">
			<tr<c:if test="${lineInfo.count % 2 == 0}"> class="portlet-table-alternate" </c:if>>
				<td><c:out value="${view.mandant}"/></td>
				<td><c:out value="${view.component}"/></td>
				<td><c:out value="${view.jspName}"/></td>
				<td><c:out value="${view.tagName}"/></td>
				<td><c:out value="${view.language}"/></td>
				<td><c:out value="${view.country}"/></td>
				<td><c:out value="${view.tagValue}"/></td>
				<c:if test="${editable}">
					<td style="background-color: #FFFFFF;">
					<a href="#" onclick="sendGet('translationedit.htm', 'objectID=${view.id}')">
						<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/page_edit.gif") %>" alt="<doc41:translate label="Edit"/>" title="<doc41:translate label="Edit"/>" style="border: 0px;"></a>
					</td>
					<td style="background-color: #FFFFFF;">
					<a href="#"	onclick="sendPostAfterCheck('<doc41:translate label="DeletionWanted"/>', 'translationdelete.htm', 'objectID=${view.id}')"> 
						<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/trash.png") %>" alt="<doc41:translate label="Delete"/>" title="<doc41:translate label="Delete"/>" style="border: 0px;" /></a>
					</td>
				</c:if>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div> 

<div class="portlet-section-header">
	<%@include file="../../doc41/paging_footer.jsp" %>
</div>