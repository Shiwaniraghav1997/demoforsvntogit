<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="myProfile" activeNav="translations" 
	jspName="edit" 		component="useradmin"
	title="Login">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

  <script language="JavaScript">
		function setFocusToCwid() {
			document.getElementById('cwid').focus();
		}
	
		setTimeout(setFocusToCwid, 300);
  </script>
  <div style="margin: 25px auto; width: 500px;">
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
  </div>
</doc41:layout>