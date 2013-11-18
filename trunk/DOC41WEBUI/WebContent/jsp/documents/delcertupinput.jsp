<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="delcertupinput" component="documents" activeTopNav="upload"
	activeNav="${batchObjectForm.type}UP" title="Upload Delivery Certificate Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	
	<div id="div-body" class="portlet-body">
		<form:form commandName="batchObjectForm" action="delcertuplist"
			method="get">
			<form:hidden path="type" />
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="Upload Document"/>&nbsp;<doc41:translate label="${batchObjectForm.type}"/>
				</div>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonContinue"/>" name="ButtonContinue"/>
			</div>
			
			<div class="portlet-section-body">
				<spring:hasBindErrors name="batchObjectForm">
					<tr>
						<td colspan="2"><c:forEach items="${errors.globalErrors}"
								var="error">
								<tr style="color: red">
									<doc41:translate label="${error.code}" />
								</tr>
							</c:forEach>
						</td>
					</tr>
				</spring:hasBindErrors>
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="attributes" /></th>
						</tr>
						<colcolgroup>
						<col width="15%" />
						<col width="35%" />
						<col width="15%" />
						<col width="35%" />
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<spring:hasBindErrors name="searchForm">
							<tr>
								<td colspan="2"><c:forEach items="${errors.globalErrors}"
										var="error">
										<tr style="color: red">
											<doc41:translate label="${error.code}" />
										</tr>
									</c:forEach></td>
							</tr>
						</spring:hasBindErrors>

						<tr>
							<th><doc41:translate label="PartnerNumber" /></th>
							<td><form:select path="partnerNumber" items="${batchObjectForm.partners}" cssClass="portlet-form-input-field portlet-mandatory" itemLabel="partnerLabel" itemValue="partnerNumber"/>*<doc41:error path="partnerNumber" /></td>
						</tr>
						<tr>
							<th><doc41:translate label="Plant" /></th>
							<td><form:select path="plant" items="${user.plants}" cssClass="portlet-form-input-field portlet-mandatory" />*<doc41:error path="plant" /></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Material" /></th>
							<td><form:input path="material" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="material" /></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Batch" /></th>
							<td><form:input path="batch" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="batch" /></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="Order" /></th>
							<td><form:input path="order" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="order" /></td>
						</tr>
						
						
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
	
</doc41:layout>
