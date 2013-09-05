<%@tag description="Upload Document Tag" pageEncoding="UTF-8"%>

<%@attribute name="action"		required="true"%>
<%@attribute name="fragmentAdditionalButtons"		required="false" fragment="true"%>
<%@attribute name="fragmentCustomSearchFields"		required="false" fragment="true"%>
<%@attribute name="showCustomAttributes"		required="false"%>
<%@attribute name="showObjectId"		required="false"%>
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
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<tr><th><doc41:translate label="Upload Document" />&nbsp;<doc41:translate label="${uploadForm.type}"/></th></tr>
				</table>
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
							<col width="35%"/>
							<col width="50%"/>
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<spring:hasBindErrors name="uploadForm">
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
						
						<c:if test="${uploadForm.partnerNumberUsed }">
						<tr>
							<th><doc41:translate label="PartnerNumber" /></th>
							<td><form:select path="partnerNumber" items="${user.partners}" cssClass="portlet-form-input-field" cssStyle="width:240px;" itemLabel="partnerLabel" itemValue="partnerNumber"/><doc41:error path="partnerNumber" /></td>
						</tr>
						</c:if>
						
						<c:if test="${empty showObjectId or showObjectId}">
						 	<tr class="portlet-table-alternate">
								<th><doc41:translate label="ObjectId${uploadForm.type}" /></th>
								<td><form:input path="objectId" cssClass="portlet-form-input-field"  maxlength="70"/><doc41:error path="objectId" /></td>
							</tr>
						</c:if>
						
						<c:if test="${not empty fragmentCustomSearchFields}">
							<jsp:invoke fragment="fragmentCustomSearchFields"/>
						</c:if>
						
						<c:if test="${empty showCustomAttributes or showCustomAttributes}">
						 	<c:forEach items="${uploadForm.attributeValues}" var="attributeValue" varStatus="status">
							    <tr	<c:if test="${lovStatus.count % 2 != 0}">class="portlet-table-alternate"</c:if>>
							        <th><c:out value="${uploadForm.attributeLabels[attributeValue.key]}"/>
							        <input type="hidden" name="attributeLabels['${attributeValue.key}']" value="${uploadForm.attributeLabels[attributeValue.key]}"/>
							        </th>
							        <td>
							        <c:choose>
							         <c:when test="${fn:length(uploadForm.attributePredefValues[attributeValue.key])>0}">
							         	<select id="${attributeValue.key}" class="portlet-form-input-field"  name="attributeValues['${attributeValue.key}']">
									<c:forEach items="${uploadForm.attributePredefValues[attributeValue.key]}" var="predefValue" varStatus="pdstatus">
										<c:choose>
											<c:when test="${attributeValue.value ==  predefValue}"><option selected="selected">${predefValue}</option></c:when>
											<c:otherwise><option>${predefValue}</option></c:otherwise>
										</c:choose>		            	
							         		</c:forEach>
							         	</select>
							         </c:when>
							         <c:otherwise>
							          <input id="${attributeValue.key}" class="portlet-form-input-field"  maxlength="70" name="attributeValues['${attributeValue.key}']" value="${attributeValue.value}"/>
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
							<th><doc41:translate label="SelectFile" /></th>
							<td>
							<c:choose>
								<c:when test="${empty uploadForm.fileId}">
									<input name="file" type="file" size="40"/><doc41:error path="file" />
								</c:when>
								<c:otherwise>
									<doc41:translate label="FileAlreadyUploaded" />
									<form:hidden path="fileId"/>
								</c:otherwise>
							</c:choose>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
		</form:form>
	</div>