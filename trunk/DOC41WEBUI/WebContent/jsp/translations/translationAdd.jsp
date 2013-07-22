<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="translationAdd" 	component="TADMN"
activeTopNav="maintenance" 	activeNav="translations"
title="Translations">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

    <div class="portlet-body">
		<form:form commandName="translationsForm" action="inserttranslation" method="post">
			
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<tr><th><doc41:translate label="TranslationAdd"/></th></tr>
				</table>
				<a class="portlet-form-button" href='translationOverview'><doc41:translate label="ButtonCancel"/></a>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSave"/>" />
			
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
						<spring:hasBindErrors name="translationsForm">
							<tr>
								<td colspan="4">
									<c:forEach items="${errors.globalErrors}" var="error">
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
						<th><doc41:translate label="Page" /></th>
					</tr>
					<tr>
						<td><form:input path="mandant" readonly="true" size="30" cssClass="portlet-form-input-field" /></td>
						<td><form:input path="component" size="30" maxlength ="10" cssClass="portlet-form-input-field" /></td>
						<td><form:input path="jspName" size="30" maxlength ="20" cssClass="portlet-form-input-field" /></td>
					</tr>
					<tr>
						<th><doc41:translate label="TagName" /></th>
						<th><doc41:translate label="Language" /></th>
						<th><doc41:translate label="Country" /></th>
		
					</tr>
					<tr>
						<td><form:input path="tagName" size="30" maxlength ="50" cssClass="portlet-form-input-field" /></td>
						<td><form:select path="language" items="${languageCodes}" cssClass="portlet-form-input-field" cssStyle="width:140;"/></td>
						<td><form:select path="country" items="${countryCodes}" cssClass="portlet-form-input-field" cssStyle="width:140;"/></td>
					</tr>
					<tr>
						<th colspan="4"><doc41:translate label="TagValue" /></th>
					</tr>
					<tr>
						<td colspan="4"><form:textarea path="tagValue" rows="4" cols="100"
							cssClass="portlet-form-input-field"   onchange="javascript:checkLength(this, 4000);" onkeydown="javascript:checkLength(this, 4000);" onkeyup="javascript:checkLength(this, 4000);"/></td>
					</tr>
				</thead>
			</table>
			</div>
		</form:form>
	</div>
</doc41:layout>