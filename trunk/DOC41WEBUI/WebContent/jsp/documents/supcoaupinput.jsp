<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="supcoaupinput" component="documents" activeTopNav="upload"
	activeNav="${vendorBatchForm.type}" title="Upload Supplier CoA Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	
	<div id="div-body" class="portlet-body">
		<form:form commandName="vendorBatchForm" action="supcoauplist"
			method="get">
			<form:hidden path="type" />
			<div class="portlet-section-header">
				<table class="portlet-section-subheader"
					style="float: left; padding-left: 2px; padding-right: 30px; vertical-align: bottom">
					<tr>
						<tr><th><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${vendorBatchForm.type}"/></th></tr>
					</tr>
				</table>
				<input type="submit" class="portlet-form-button"
					value="<doc41:translate label="ButtonContinue"/>" name="ButtonContinue"/>
			</div>
			<div class="portlet-section-body">
				<spring:hasBindErrors name="vendorBatchForm">
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
							<td><form:select path="partnerNumber" items="${user.partners}" cssClass="portlet-form-input-field" cssStyle="width:240px;" itemLabel="partnerLabel" itemValue="partnerNumber"/><doc41:error path="partnerNumber" /></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><doc41:translate label="VendorBatch" /></th>
							<td><form:input path="vendorBatch" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="vendorBatch" /></td>
						</tr>
						<tr>
							<th><doc41:translate label="Plant" /></th>
							<td><form:select path="plant" items="${user.plants}" cssClass="portlet-form-input-field" cssStyle="width:240px;" /><doc41:error path="plant" /></td>
						</tr>
						
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
	
</doc41:layout>
