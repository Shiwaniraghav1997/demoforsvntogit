<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="list" component="useradmin"/>

<%@page import="java.util.List"%>
<%@page import="com.bayer.bhc.doc41webui.domain.User"%>
<%@page import="com.bayer.bhc.doc41webui.container.UserListFilter"%>


<html>
  <head><title>User Management</title></head>
  
  <body>
	<%@include file="../doc41/header.jspf" %>
  	<%@include file="../doc41/navigation.jspf" %>
  	
  	<div id="div-body" class="portlet-body">
		<form:form commandName="userFilter" action="${lastRenderedAction}" method="post">
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="UserOverview"/></th>
				</table>
				<form:hidden path="command"/>
		
				<input type="button" class="portlet-form-button" onClick="callAction('userlist.htm', 'userFilter', 'reset', this)" value="<doc41:translate label="ButtonReset"/>">
				<input type="button" class="portlet-form-button" onClick="callAction('userlist.htm', 'userFilter', 'submit', this)" value="<doc41:translate label="ButtonSubmit"/>">
			</div>
		
			<div class="portlet-section-body">
				<table class="nohover" cellpadding="4" cellspacing="0">
					<thead class="portlet-table-header">								
						<tr>
							<th><doc41:translate label="Status"/></th>
							<th><doc41:translate label="Surname"/></th>
							<th><doc41:translate label="Cwid"/></th>							
							<th><doc41:translate label="Type"/></th>
							<th><doc41:translate label="Role"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">		
						<tr>
							<td>
								<form:select cssClass="portlet-form-input-field" path="status">
									<form:option value="<%=UserListFilter.FILTER_ALL_OPTIONS%>"><doc41:translate label="AllOptions"/></form:option>
									<form:option value="active"><doc41:translate label="Active"/></form:option>
									<form:option value="inactive"><doc41:translate label="Inactive"/></form:option>
							   </form:select>
							</td>
							
							<td><form:input cssClass="portlet-form-input-field" path="surname" /></td>
							<td><form:input cssClass="portlet-form-input-field" path="cwid"  /></td>							
							<td>
								<form:select cssClass="portlet-form-input-field" path="type">
									<form:option value="<%=UserListFilter.FILTER_ALL_OPTIONS%>"><doc41:translate label="AllOptions"/></form:option>
									<form:option value="internal"><doc41:translate label="Internal"/></form:option>
									<form:option value="external"><doc41:translate label="External"/></form:option>
							   </form:select>
							</td>
							<td>
								<form:select cssClass="portlet-form-input-field" path="role" >									
									<form:option value="<%=UserListFilter.FILTER_ALL_OPTIONS%>"><doc41:translate label="AllOptions"/></form:option>
									<c:forEach items="<%=User.ALL_ROLES%>" var="role">
										<form:option value="${role}"><doc41:translate label="${role}"/></form:option>
									</c:forEach>
							   </form:select>
							</td>
						</tr>	
					</tbody>
				</table>
			</div>
		</form:form>
	
		<div class="portlet-section-header">
			<%@include file="../doc41/paging.jsp" %>
			<input class="portlet-form-button" type="button" onclick="sendGet('userlookup.htm', '')" value="<doc41:translate label="ButtonLookup"/>" />
			<input class="portlet-form-button" type="button" onclick="sendGet('usercreate.htm', '')" value="<doc41:translate label="ButtonCreate"/>" />
		</div>
		
		<div class="portlet-section-body">
			<table cellpadding="4" cellspacing="0">
				<thead class="portlet-table-header">
					<tr>
						<th>&nbsp;</th>
						<th><doc41:translate label="Surname"/></th>
					    <th><doc41:translate label="Firstname"/></th>
					    <th><doc41:translate label="Cwid"/></th>
						<th><doc41:translate label="Email"/></th>
						<th><doc41:translate label="Phone"/></th>						
						<th><doc41:translate label="Type"/></th>
						<%
							for (int i = 0; i < User.ALL_ROLES.length; i++) {
						%>
						<th><img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/usermanagement/symbol_"+User.ALL_ROLES[i]+".gif") %>" title="<doc41:translate label="<%= User.ALL_ROLES[i] %>"/>"	alt="<doc41:translate label="<%= User.ALL_ROLES[i] %>"/>" style="border: 0px;"></th>
							<%
								}
							%>
						<th colspan="2">&nbsp;</th>
					</tr>
				</thead>
				<tbody class="portlet-table-body">
				
				<c:forEach items="${userList}" var="user" varStatus="userStatus">
	
					<tr <c:if test="${userStatus.count % 2 == 0}">class="portlet-table-alternate"</c:if>>
						<td>
							<c:if test="${user.active}">
								<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/ball_green.gif") %>" alt="<doc41:translate label="Active"/>" style="border: 0px;">
							</c:if>
								<c:if test="${!user.active}">
								<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/ball_red.gif") %>" alt="<doc41:translate label="Inactive"/>" style="border: 0px;">
							</c:if>
						</td>				
						<td><c:out value="${user.surname}"/></td>
						<td><c:out value="${user.firstname}"/></td>
						<td><c:out value="${user.cwid}"/></td>
						<td><c:out value="${user.email}"/></td>
						<td><c:out value="${user.phone}"/></td>						
						<td><c:out value="${user.type}"/></td>
						
						<%
							List roles = (pageContext.getAttribute("user") != null && ((User)pageContext.getAttribute("user")).getRoles() != null) ? ((User)pageContext.getAttribute("user")).getRoles() : null;
							for (int i = 0; i < User.ALL_ROLES.length; i++) {
					            if (roles != null && roles.contains(User.ALL_ROLES[i])) {
						%>
								<td>
									<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/check_green.gif") %>" alt="<doc41:translate label="CheckGreen"/>" style="border: 0px;">
								</td>
								<%
								} else {
								%>
								<td>&nbsp;</td>
							<%
								}
							}
							%>
						<td>
							<a href='#' onclick="sendGet('useredit.htm', 'editcwid=${user.cwid}')">
								<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/common/page_edit.gif") %>" alt="<doc41:translate label="ButtonEdit"/>" title="<doc41:translate label="ButtonEdit"/>" style="border: 0px;">
							</a>
						</td>
						<td>
							<c:if test="${user.active}">
								<a href='#' onclick="sendPostAfterCheck('<doc41:translate label="MessageDeactivate"/>', 'useractivation.htm', 'togglecwid=${user.cwid}')">			
						      		<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/usermanagement/user_deactivate.gif") %>" alt="<doc41:translate label="ButtonDeactivate"/>" title="<doc41:translate label="ButtonDeactivate"/>" style="border: 0px;">
						      	</a>
							</c:if>
							<c:if test="${!user.active}">						
								<a href='#' onclick="sendPostAfterCheck('<doc41:translate label="MessageActivate"/>', 'useractivation.htm', 'togglecwid=${user.cwid}')">			
						      		<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/usermanagement/user_activate.gif") %>" alt="<doc41:translate label="ButtonActivate"/>" title="<doc41:translate label="ButtonActivate"/>" style="border: 0px;">
						      	</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
				
				</tbody>
			</table>
		</div>
		
		<div class="portlet-section-header">
			<%@include file="../doc41/paging_footer.jsp" %>
		</div>
    </div>
  </body>
</html>