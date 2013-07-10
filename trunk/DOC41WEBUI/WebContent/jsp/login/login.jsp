<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="edit" component="useradmin"/>

<html>
  <script language="JavaScript">
		function setFocusToCwid() {
			document.getElementById('cwid').focus();
		}
	
		setTimeout(setFocusToCwid, 300);
  </script>
  
  <head><title>Login</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>
    
    <div style="border-bottom: 1px solid #009899">
    </div>
    
	<form:form action="post" method="post">
		<table align="center" width="610px" cellpadding=0 cellspacing=0 border=0 class="nohover" style="margin-top: 10px; background-color: #FFFFFF">
			<tr>
				<td colspan="2" style="padding: 20px; BACKGROUND-COLOR: #eaf8f8; color: #696866; TEXT-ALIGN: center;"><doc41:translate label="LoginDoc41"/></td>
			</tr>
			<tr>
				<td align="center">
				  <table border="0" width="300px" align="center" style="margin: 20px;">
					<tr>
						<td ><doc41:translate label="Cwid"/></td> 
						<td >
							<input id="cwid" name="cwid" type="text" value="" class="portlet-form-input-field" style="width: 110px" SIZE="15"> 
						</td>
					</tr>
					<tr>
						<td ><doc41:translate label="Password"/></td>
						<td >
							<input id="password" name="password" type="password" value="" class="portlet-form-input-field" style="width: 110px" SIZE="15"> 
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="submit" name="_finish"  value="<doc41:translate label="LoginSubmit"/>" class="portlet-form-input-field" style="width: 110px" />
						</td>
					</tr>
				</table>
			  </td>
			</tr>
			<tr height="20px">
				<td colspan="2" style="padding: 20px; BACKGROUND-COLOR: #eaf8f8; color: #696866; TEXT-ALIGN: center;"></td>
			</tr>
		</table>
	</form:form>

  </body>
</html>
