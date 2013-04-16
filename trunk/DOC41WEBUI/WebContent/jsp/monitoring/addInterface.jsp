<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations component="tAdmin" jspName="addInterface" />
<html>
<head>
<title><doc41:translate label="AddInterface" /></title>
</head>
<body>
<%@include file="../doc41/header.jspf"%>
	<form:form commandName="monitoring" method="post"
		action="interfaceadd.htm">

		<!--Buttons Bar: Start-->
		<div class="portlet-section-header">
			<input type="button" class="portlet-form-button"
				onclick="window.location.href='monitoringoverview.htm'"
				value="<doc41:translate label="ButtonCancel"/>" /> <input
				type="submit" name="save" class="portlet-form-button"
				value="<doc41:translate label="ButtonSave"/>" />
		</div>

		<!--Buttons Bar :End-->

		<div class="portlet-section-body">
			<table cellpadding="4" cellspacing="0" class="nohover">
				<thead class="portlet-table-header">
					<tr>
						<th width="100%" colspan="5"><doc41:translate
								label="Interface Details" />
					</tr>
				</thead>
				<tbody class="portlet-table-body">
					<tr>
						<th style="width: 30%"><doc41:translate label="InterfaceName" /></th>
						<td style="width: 70%"><form:input path="name"
								cssClass="portlet-form-input-field" size="50" />
								<doc41:error path="name" />
								</td>
					</tr>
					<tr>
						<th style="width: 30%"><doc41:translate label="Description" /></th>
						<td style="width: 70%"><form:textarea path="desc"
								cssClass="portlet-form-input-field" rows="10" cols="80"
								onchange="javascript:checkLength(this, 200);"
								onkeydown="javascript:checkLength(this, 200);"
								onkeyup="javascript:checkLength(this, 200);" />
								<doc41:error path="desc" />
								</td>
					</tr>
				</tbody>
			</table>
		</div>
	</form:form>
</body>
</html>
