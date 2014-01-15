<%@tag description="Upload Document Tag" pageEncoding="UTF-8"%>

<%@attribute name="action"		required="true"%>
<%@attribute name="fragmentAdditionalButtons"		required="false" fragment="true"%>
<%@attribute name="fragmentCustomSearchFields"		required="false" fragment="true"%>
<%@attribute name="showCustomAttributes"		required="false"%>
<%@attribute name="showObjectId"		required="false"%>
<%@attribute name="showCustomerNumber"		required="false"%>
<%@attribute name="showVendorNumber"		required="false"%>
<%@taglib prefix="doc41" uri="doc41-tags" %>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<div id="div-body" class="portlet-body">
		<form:form commandName="uploadForm" action="${action}"
			method="post" enctype="multipart/form-data">
			<form:hidden path="type"/>
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}"/>
				</div>
				<c:if test="${not empty fragmentAdditionalButtons}">
					<jsp:invoke fragment="fragmentAdditionalButtons"/>
				</c:if>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonUpload"/>" />
			</div>

			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="4"><doc41:translate label="attributes"/></th>
						</tr>
						<colcolgroup>
							<col width="15%"/>
							<col width="85%"/>
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<spring:hasBindErrors name="uploadForm">
							<c:forEach items="${errors.globalErrors}" var="error">
							<tr>
								<td colspan="2" style="color: red">
									<doc41:translate label="${error.code}" />
								</td>
							</tr>
							</c:forEach>
						</spring:hasBindErrors>
						
						<c:if test="${uploadForm.customerNumberUsed && (empty showCustomerNumber or showCustomerNumber)}">
						<tr>
							<th><label for="customerNumber"><doc41:translate label="CustomerNumber" /></label></th>
							<td><form:select path="customerNumber" items="${uploadForm.customers}" cssClass="portlet-form-input-field portlet-mandatory portlet-big" itemLabel="label" itemValue="number"/><doc41:error path="customerNumber" /></td>
						</tr>
						</c:if>
						
						<c:if test="${uploadForm.vendorNumberUsed && (empty showVendorNumber or showVendorNumber)}">
						<tr>
							<th><label for="vendorNumber"><doc41:translate label="VendorNumber" /></label></th>
							<td><form:select path="vendorNumber" items="${uploadForm.vendors}" cssClass="portlet-form-input-field portlet-mandatory portlet-big" itemLabel="label" itemValue="number"/><doc41:error path="vendorNumber" /></td>
						</tr>
						</c:if>
						
						<c:if test="${empty showObjectId or showObjectId}">
						 	<tr class="portlet-table-alternate">
								<th><label for="objectId"><doc41:translate label="ObjectId${uploadForm.type}" /></label></th>
								<td><form:input path="objectId" cssClass="portlet-form-input-field portlet-mandatory portlet-big"  maxlength="70"/><doc41:error path="objectId" /></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty fragmentCustomSearchFields}">
							<jsp:invoke fragment="fragmentCustomSearchFields"/>
						</c:if>
						
						<c:if test="${empty showCustomAttributes or showCustomAttributes}">
						 	<c:forEach items="${uploadForm.attributeValues}" var="attributeValue" varStatus="status">
							    <tr	<c:if test="${lovStatus.count % 2 != 0}">class="portlet-table-alternate"</c:if>>
							        <th><label for="${attributeValue.key}"><c:out value="${uploadForm.attributeLabels[attributeValue.key]}"/></label>
							        <input type="hidden" name="attributeLabels['${attributeValue.key}']" value="${uploadForm.attributeLabels[attributeValue.key]}"/>
							        </th>
							        <td>
							        <c:choose>
							         <c:when test="${fn:length(uploadForm.attributePredefValues[attributeValue.key])>0}">
							         	<select id="${attributeValue.key}" class="portlet-form-input-field ${searchForm.attributeMandatory[attributeValue.key]?'portlet-mandatory':''} portlet-big"  name="attributeValues['${attributeValue.key}']">
									<c:forEach items="${uploadForm.attributePredefValues[attributeValue.key]}" var="predefValue" varStatus="pdstatus">
										<c:choose>
											<c:when test="${attributeValue.value ==  predefValue}"><option selected="selected">${predefValue}</option></c:when>
											<c:otherwise><option>${predefValue}</option></c:otherwise>
										</c:choose>		            	
							         		</c:forEach>
							         	</select>
							         </c:when>
							         <c:otherwise>
							          <input id="${attributeValue.key}" class="portlet-form-input-field ${searchForm.attributeMandatory[attributeValue.key]?'portlet-mandatory':''} portlet-big"  maxlength="70" name="attributeValues['${attributeValue.key}']" value="${attributeValue.value}"/>
							         </c:otherwise>
							        </c:choose>
							        <doc41:error path="attributeValues['${attributeValue.key}']" />
							        </td>
							    </tr>
							</c:forEach></html>
						</c:if>
						 
					</tbody>
				</table>
			</div>
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="2"><doc41:translate label="Document File" /></th>
						</tr>
						<colcolgroup>
							<col width="15%"/>
							<col width="85%"/>
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<tr>
							<th><label for="file"><doc41:translate label="SelectFile" /></label></th>
							<td>
							<c:choose>
								<c:when test="${empty uploadForm.fileId}">
									<input name="file" type="file" class="portlet-form-input-field portlet-mandatory portlet-big"/><doc41:error path="file" />
								</c:when>
								<c:otherwise>
									<doc41:translate label="FileAlreadyUploaded" />
									<form:hidden path="fileId"/>
									<form:hidden path="fileName"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="portlet-section-body">
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<tr>
							<th colspan="2"><doc41:translate label="EMail Notification" /></th>
						</tr>
						<colcolgroup>
							<col width="15%"/>
							<col width="85%"/>
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<tr>
							<th><label for="notificationEMail"><doc41:translate label="NotificationEMailAddress" /></label></th>
							<td>
								<form:input path="notificationEMail" cssClass="portlet-form-input-field portlet-big"  maxlength="200"/><doc41:error path="notificationEMail" />	
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form:form>
	</div>