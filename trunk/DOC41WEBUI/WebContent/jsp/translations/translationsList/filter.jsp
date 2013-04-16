
<form:form commandName="transView" action="${lastRenderedAction}" method="post">
	<form:hidden path="command" />

	<div class="portlet-section-header">
		<input type="button" class="portlet-form-button"
		onClick="submitAction('transView','reset', this)" value="<doc41:translate label="buttonReset" />" />
		
		<input type="button" class="portlet-form-button"
		onClick="submitAction('transView','submit', this)" value="<doc41:translate label="buttonSubmit" />" />
		
		<!-- it needs to be check user role to transfer tags to production
		<c:if test="${transView.isTechAdmin}">
		<input type="button" class="portlet-form-button"
			onclick="confirmTransfer('<doc41:translate label="SendToProduction"/>')"	value="<doc41:translate label="TransferQaToProd" />" />
		</c:if>
		-->
	</div>

	<div class="portlet-section-body">
	<table class="nohover" cellpadding="4" cellspacing="0">
	<thead class="portlet-table-header">
				<tr>
					<th colspan="6"><doc41:translate label="translationOverview"/></th>
				</tr>
	</thead>
	<tbody class="portlet-table-body">
		
		<tr class="portlet-table-alternate">
			
			<th style="width: 15%"><doc41:translate label="TagName" />&nbsp;
			<td colspan="1" style="width: 35%"><form:input cssClass="portlet-form-input-field"  cssStyle="width:140;" path="tagName"/>
			</td>
			
			<th style="width: 15%"><doc41:translate label="TagValue" />&nbsp;</th>
			<td colspan="3" style="width:35%"><form:input cssClass="portlet-form-input-field" cssStyle="width:280;" path="tagValue"/></td>
		
		</tr>
		
		<tr class="portlet-table-alternate">
			<th style="width: 15%"><doc41:translate label="Component" /></th>
			<td style="width: 35%"><form:select path="component" cssClass="portlet-form-input-field"
					cssStyle="width:140;"
				 	onchange="this.form.command.value='refresh';this.form.submit();">
					<option value="">&nbsp;&nbsp;&nbsp;</option>
					<form:options items="${componentList}"/>
					</form:select></td>
					
			<th style="width: 10%"><doc41:translate label="PageName" /></th>
			<td style="width: 15%"><form:select path="jspName" cssClass="portlet-form-input-field" cssStyle="width:140;">
					<option value="">&nbsp;&nbsp;&nbsp;</option>
					<form:options items="${pageList}"/>
					</form:select>
			</td>
			<th style="width: 10%"><doc41:translate label="Language" /></th>
			<td style="width: 15%">
			<form:select cssClass="portlet-form-input-field"
				 	path="language">
					<option value="">&nbsp;&nbsp;&nbsp;</option>
					<form:options items="${languageCodes}"/>
				</form:select>
			</td>
		</tr>   
	</tbody>
	</table>
	</div>
</form:form>