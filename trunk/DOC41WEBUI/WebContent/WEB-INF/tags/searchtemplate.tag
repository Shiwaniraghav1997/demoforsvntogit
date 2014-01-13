<%@tag description="Search Document Tag" pageEncoding="UTF-8"%>

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
<%@taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

	<script type="text/javascript">
		tswidgets = [ 'zebra' ];
		
		tssorting = [[2,1]];
		
		function openDocument(key,type){
			window.location ="download?type="+type+"&key="+key;
		}
	</script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>

	<div id="div-body" class="portlet-body">
		<form:form commandName="searchForm" action="${action}" method="get">
			<form:hidden path="type" />
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="Search Document"/>&nbsp;<doc41:translate label="${searchForm.type}"/>
				</div>
				<c:if test="${not empty fragmentAdditionalButtons}">
					<jsp:invoke fragment="fragmentAdditionalButtons"/>
				</c:if>
				<a class="portlet-form-button" type="button" href='documentsearch?type=${searchForm.type}'><doc41:translate label="ButtonReset" /></a>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSearch"/>" name="ButtonSearch"/>
			</div>
			
			<div class="portlet-section-body">
				<div class="section-separator"><doc41:translate label="attributes" /></div>
				<table cellpadding="4" cellspacing="0" class="nohover">
					<thead class="portlet-table-header">
						<colcolgroup>
						<col width="15%" />
						<col width="85%" />
						</colcolgroup>
					</thead>
					<tbody class="portlet-table-body">
						<spring:hasBindErrors name="searchForm">
							<c:forEach items="${errors.globalErrors}" var="error">
							<tr>
								<td colspan="2" style="color: red">
									<doc41:translate label="${error.code}" />
								</td>
							</tr>
							</c:forEach>
						</spring:hasBindErrors>

						<tr>
							<c:if test="${searchForm.customerNumberUsed && (empty showCustomerNumber or showCustomerNumber)}">
								<th><label for="customerNumber"><doc41:translate label="CustomerNumber" /></label></th>
								<td><form:select path="customerNumber"
										items="${searchForm.customers}" cssClass="portlet-form-input-field portlet-mandatory portlet-big"
										itemLabel="label"
										itemValue="number" />
									<doc41:error path="customerNumber" /></td>
							</c:if>
						</tr>
						<tr>
							<c:if test="${searchForm.vendorNumberUsed && (empty showVendorNumber or showVendorNumber)}">
								<th><label for="vendorNumber"><doc41:translate label="VendorNumber" /></label></th>
								<td><form:select path="vendorNumber"
										items="${searchForm.vendors}" cssClass="portlet-form-input-field portlet-mandatory portlet-big"
										itemLabel="label"
										itemValue="number" />
									<doc41:error path="vendorNumber" /></td>
							</c:if>
						</tr>
						<tr>
							<c:if test="${empty showObjectId or showObjectId}">
								<th><label for="objectId"><doc41:translate label="ObjectId${searchForm.type}" /></label></th>
								<td><form:input path="objectId"
										cssClass="portlet-form-input-field portlet-mandatory portlet-big" maxlength="70" />
									<doc41:error path="objectId" /></td>
							</c:if>
						</tr>
						
						<c:if test="${not empty fragmentCustomSearchFields}">
							<jsp:invoke fragment="fragmentCustomSearchFields"/>
						</c:if>

						<c:if test="${empty showCustomAttributes or showCustomAttributes}">
							<c:forEach items="${searchForm.attributeValues}"
								var="attributeValue" varStatus="status">
								<tr	<c:if test="${lovStatus.count % 2 != 0}">class="portlet-table-alternate"</c:if>>
								<th><label for="${attributeValue.key}"><c:out
										value="${searchForm.attributeLabels[attributeValue.key]}" /> <%-- <input type="hidden" name="attributeLabels['${attributeValue.key}']" value="${searchForm.attributeLabels[attributeValue.key]}"/> --%>
										</label>
								</th>
								<td><c:choose>
										<c:when
											test="${fn:length(searchForm.attributePredefValues[attributeValue.key])>0}">
											<select id="${attributeValue.key}"
												class="portlet-form-input-field portlet-big"
												name="attributeValues['${attributeValue.key}']">
												<c:forEach
													items="${searchForm.attributePredefValues[attributeValue.key]}"
													var="predefValue" varStatus="pdstatus">
													<c:choose>
														<c:when test="${attributeValue.value ==  predefValue}">
															<option selected="selected">${predefValue}</option>
														</c:when>
														<c:otherwise>
															<option>${predefValue}</option>
														</c:otherwise>
													</c:choose>
												</c:forEach>
											</select>
										</c:when>
										<c:otherwise>
											<input id="${attributeValue.key}"
												class="portlet-form-input-field portlet-big" maxlength="70"
												name="attributeValues['${attributeValue.key}']"
												value="${attributeValue.value}" />
										</c:otherwise>
									</c:choose> <doc41:error path="attributeValues['${attributeValue.key}']" />
								</td>
	
	
								</tr>
	
							</c:forEach>
						</c:if>
					</tbody>
				</table>
			</div>
		</form:form>
	</div>
	<div class="portlet-section-body">
		<doc41:error path="objectId" />
		<div class="section-separator"><doc41:translate label="results" /></div>
		<doc41:pager />
		<table class="tablesorter" id="doc41table">
			<thead >
				<tr>

					<!-- thead text will be updated from the JSON; make sure the number of columns matches the JSON data -->
					<!-- header update currently disabled to put names in the jsp instead of in java  -->
					<th><doc41:translate label="ObjectId${searchForm.type}" /></th>
					<%-- <th data-sorter="moment" data-date-format="${dateMomentPattern}"><doc41:translate label="StorageDate" /></th> --%>
					<th data-sorter="moment" data-date-format="${dateTimeMomentPattern}"><doc41:translate label="ArchiveLinkDate" /></th>
					<th><doc41:translate label="DocumentClass" /></th>
					<c:forEach items="${searchForm.customizedValuesLabels}"
							var="custValueLabel" varStatus="status">
					<th><c:out value="${custValueLabel}" /></th>
					</c:forEach>
					
				</tr>
			<colgroup>
				<col width="15%" />
				<col width="20%" />
				<col width="10%" />
				<%-- <col width="10%" />
				<col width="15%" />
				<col width="15%" />
				<col width="10%" /> --%>
				<c:forEach items="${searchForm.customizedValuesLabels}"
							var="custValueLabel" varStatus="status">
				<col width="${searchForm.custColPercent}%" />
				</c:forEach>
			</colgroup>
			</thead>

			<tbody >
				<c:forEach items="${searchForm.documents}" var="document"
					varStatus="status">
					<tr style="cursor: pointer;"
						onclick="openDocument('${document.key}','${searchForm.type}')">
						<td><c:out value="${document.objectId}" /></td>
						<%-- <td>
							<doc41:formatDate date="${document.storageDate}" zone="${user.timeZone}"></doc41:formatDate>
						</td> --%>
						<td>
							<doc41:formatDate date="${document.archiveLinkDate}" zone="${user.timeZone}"></doc41:formatDate>&nbsp;<doc41:formatTime date="${document.archiveLinkDate}" zone="${user.timeZone}"></doc41:formatTime>
						</td>
						<td><c:out value="${document.documentClass}" /></td>
						<c:forEach items="${searchForm.attributeLabels}"
							var="attribLabel" varStatus="status">
						<td><c:out value="${document.customizedValuesByKey[attribLabel.key]}" /></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<doc41:pager />
	</div>