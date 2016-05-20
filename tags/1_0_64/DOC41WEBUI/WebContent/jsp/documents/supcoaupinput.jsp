<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="supcoaupinput" component="documents" activeTopNav="upload"
	activeNav="${vendorBatchForm.type}UP" title="Upload Supplier CoA Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	
	<div id="div-body" class="portlet-body">
		<form:form commandName="vendorBatchForm" action="supcoauplist"
			method="get">
			<form:hidden path="type" />
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${vendorBatchForm.type}"/>
				</div>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonContinue"/>" name="ButtonContinue"/>
			</div>
         
            <doc41:errors form="vendorBatchForm"/>
			
			<div class="portlet-section-body">
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
						<tr>
							<th><label for="vendorNumber"><doc41:translate label="VendorNumber" /></label></th>
							<td><form:select path="vendorNumber" items="${vendorBatchForm.vendors}" cssClass="portlet-form-input-field portlet-mandatory portlet-big" itemLabel="label" itemValue="number"/><doc41:error path="vendorNumber" /></td>
						</tr>
						<tr class="portlet-table-alternate">
							<th><label for="vendorBatch"><doc41:translate label="VendorBatch" /></label></th>
							<td><form:input path="vendorBatch" cssClass="portlet-form-input-field portlet-mandatory portlet-big"  maxlength="70"/><doc41:error path="vendorBatch" /></td>
						</tr>
						<tr>
							<th><label for="plant"><doc41:translate label="Plant" /></label></th>
							<td><form:select path="plant" items="${user.plants}" cssClass="portlet-form-input-field portlet-mandatory portlet-big" /><doc41:error path="plant" /></td>
						</tr>
						
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
	
</doc41:layout>
