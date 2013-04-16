<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="import" component="useradmin"/> 


<script >
	function lookupCwid() {
		var cwid = $('#lookupcwid').val();
		sendPost('userlookup.htm', 'lookupcwid='+cwid);
	}
</script>
	
<html>
  <head><title>User Import</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>

	<div class="portlet-body">
		<form:form commandName="edituser" action="${lastRenderedAction}" method="post">
			
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="UserImport"/></th>
				</table>
				<input type="button" class="portlet-form-button" onclick="window.location.href='userlist.htm'" value="<doc41:translate label="ButtonCancel"/>" />
				<input type="button" class="portlet-form-button" onclick="lookupCwid()" value="<doc41:translate label="ButtonLookup"/>" name="ButtonLookup" />
			</div>
		
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="TitelUserData"/></th>
						</tr>
					</thead>
					<tbody class="portlet-table-body">	
						<spring:hasBindErrors name="edituser">
							<tr>
								<td colspan="4">
									<c:forEach items="${errors.fieldErrors}" var="error">
										<tr>
											<td onmouseover="style.cursor='pointer';" onclick="$('#${error.field}').focus();" style="color: blue"> <doc41:translate label="${error.field}"/> </td>
							    			<td style="color: red;"><doc41:translate label="${error.field}.${error.code}" /></td>
										</tr>
									</c:forEach>
								</td>
							</tr>
						</spring:hasBindErrors>	
						<tr>
							<th style="width: 15%"><doc41:translate label="Surname"/></th> 
							<td style="width: 35%">
								<c:if test="${empty edituser.surname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty edituser.surname}">
									<c:out value="${edituser.surname}"/>
									<form:hidden path="surname"/>
								</c:if>
							</td>
							<th style="width: 15%"><doc41:translate label="Password"/></th>
							<td style="width: 35%"><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Firstname"/></th>
							<td>
								<c:if test="${empty edituser.firstname}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty edituser.firstname}">
									<c:out value="${edituser.firstname}"/>
									<form:hidden path="firstname"/>
								</c:if>				
							</td>
							<th><doc41:translate label="RepeatPassword"/></th>
							<td><doc41:translate label="NotChangeable"/></td>
						</tr>
						<tr>
							<th><doc41:translate label="Cwid"/></th>
							<td><form:input id="lookupcwid" path="cwid" cssClass="portlet-form-input-field"/></td>
							
							<th><doc41:translate label="Company"/></th>
							<td>
								<c:if test="${empty edituser.company}"><doc41:translate label="AutomaticImport"/></c:if>
								<c:if test="${not empty edituser.company}">
									<c:out value="${edituser.company}"/>
									<form:hidden path="company"/>
								</c:if>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
		</form:form>
	</div>
  </body>
</html>

