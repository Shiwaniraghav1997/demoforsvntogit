<%@include file="../doc41/prolog.jspf"%> 
<doc41:loadTranslations component="TADMN" jspName="translationEdit" />

<html>
  <head><title>Translations</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>
    
    <div class="portlet-body">
		<form:form commandName="translationsForm" action="updatetranslation" method="post" >
			<form:hidden path="objectID" />
			
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="TranslationEdit"/></th>
				</table>
			
				<input type="button" class="portlet-form-button" onclick="submitAction('translationsForm', 'abort', this)" value="<doc41:translate label="Cancel"/>"/>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="Save"/>" />
			</div>
					
			<div class="portlet-section-body">
				<table class="nohover" cellpadding="4" cellspacing="0">
					<thead class="portlet-table-header">
					<spring:hasBindErrors name="translationsForm">
							<tr>
								<td colspan="4">
									<c:forEach items="${errors.fieldErrors}" var="error">
										<tr style="color: red">
											<doc41:translate label="${error.code}" />
										</tr>
									</c:forEach>
								</td>
							</tr>
						</spring:hasBindErrors>
						
						<tr>
						<th><doc41:translate label="Mandant" /></th>
						<th><doc41:translate label="Component" /></th>
						<th><doc41:translate label="PageName" /></th>
					</tr>
					<tr>
						<td><form:input path="mandant" readonly="true" size="30" cssClass="portlet-form-input-field" /></td>
						<td><form:input path="component" readonly="true" size="30" cssClass="portlet-form-input-field" /></td>
						<td><form:input path="jspName" readonly="true" size="30" cssClass="portlet-form-input-field" /></td>
					</tr>
					<tr>
						<th><doc41:translate label="TagName" /></th>
						<th><doc41:translate label="LanguageCode" /></th>
						<th><doc41:translate label="CountryCode" /></th>
		
					</tr>
					<tr>
						<td><form:input path="tagName"  size="30" maxlength ="50" cssClass="portlet-form-input-field" /></td>
						<td><form:select path="language" items="${languageCodes}" cssClass="portlet-form-input-field" cssStyle="width:140;"/></td>
						<td><form:select path="country" items="${countryCodes}" cssClass="portlet-form-input-field" cssStyle="width:140;"/></td>
					</tr>
					<tr>
						<th colspan="4"><doc41:translate label="TagValue" /></th>
					</tr>
					<tr>
						<td colspan="4"><form:textarea path="tagValue" rows="4" cols="100"
							cssClass="portlet-form-input-field"   onchange="javascript:checkLength(this, 4000);" onkeydown="javascript:checkLength(this, 4000);" onkeyup="javascript:checkLength(this, 4000);" /></td>
					</tr>
				</thead>
			</table>
			</div>
		</form:form>
	</div>
  </body>
</html>