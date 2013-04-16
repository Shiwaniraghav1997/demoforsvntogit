<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="usergroups" component="useradmin"/>

<%@page import="java.util.List"%>
<%@page import="com.bayer.bhc.doc41webui.domain.User"%>
<%@page import="com.bayer.bhc.doc41webui.container.UserGroupsForm"%>


<html>
  <head><title>User Management</title></head>
  
  <body>
	<%@include file="../doc41/header.jspf" %>
  	<%@include file="../doc41/navigation.jspf" %>
  	
  	<div id="div-body" class="portlet-body">
		<form:form commandName="userGroupsForm" action="${lastRenderedAction}" method="post">
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="UserGroups"/></th>
				</table>
				<form:hidden path="command"/>
				<input type="button" class="portlet-form-button" onclick="sendGet('useredit.htm', 'editcwid=${userGroupsForm.user.cwid}')" value="<doc41:translate label="Back"/>" />
			</div>
		
			<div class="portlet-section-body">
				<table class="nohover" cellpadding="4" cellspacing="0">
					<thead class="portlet-table-header">								
						<tr>
							<th colspan="2"><doc41:translate label="SalesOrgDivisionDistChan"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">		
						<tr>
							<c:choose>
								<c:when test="${isAddAllowed}">
									<td>
										<form:select cssClass="portlet-form-input-field" path="groupId" >									
											<doc41:options items="${availableGroupsList}" itemCode="${userGroupsForm.groupId}"/>
									   </form:select>
									</td>
									<td>
										
										<input type="button" class="portlet-form-button"
											onClick="submitAction('userGroupsForm','submit', this)"
											value="<doc41:translate label="ButtonAddGroup" />" />
										
									</td>
									
								</c:when>
								<c:otherwise>
									<td><doc41:translate label="OnlyOneGroupForExternalUsers"/></td>
								</c:otherwise>
							</c:choose>
						</tr>	
					</tbody>
				</table>
			</div>
		</form:form>
	
		
		
		<div class="portlet-section-body">
			<table cellpadding="4" cellspacing="0">
				<thead class="portlet-table-header">
					<tr>
						<th><doc41:translate label="SalesOrg"/></th>
					    <th><doc41:translate label="Division"/></th>
					    <th><doc41:translate label="DistributionChannel"/></th>
						<th>&nbsp;</th>
					</tr>
				</thead>
				<tbody class="portlet-table-body">
				
				<c:forEach items="${groupList}" var="group" varStatus="groupStatus">
	
						<td><c:out value="${group.salesOrg.keyAndValue}"/></td>
						<td><c:out value="${group.division.keyAndValue}"/></td>
						<td><c:out value="${group.distChan.keyAndValue}"/></td>

						<td>
					      	<input class="portlet-form-button" type="button" onclick="sendPost('userremovegroup.htm', 'cwid=${userGroupsForm.user.cwid}&groupId=${group.dcId}')" value="<doc41:translate label="ButtonRemoveGroup"/>" />
						</td>
					</tr>
				</c:forEach>
				
				</tbody>
			</table>
		</div>
    </div>
  </body>
</html>