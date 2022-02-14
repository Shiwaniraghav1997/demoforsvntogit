<%@tag description="Search Document Tag" pageEncoding="UTF-8"%>

<%@attribute name="action"		required="true"%>
<%@attribute name="fragmentAdditionalButtons"	required="false" fragment="true"%>
<%@attribute name="fragmentCustomSearchFields"	required="false" fragment="true"%>
<%@attribute name="showCustomAttributes"		required="false"%>
<%@attribute name="showObjectId"				required="false"%>
<%@attribute name="showCustomerNumber"			required="false"%>
<%@attribute name="showVendorNumber"			required="false"%>
<%@attribute name="showTableObjectId"			required="false"%>
<%@attribute name="showTableDocumentClass"		required="false"%>
<%@taglib prefix="doc41" uri="doc41-tags" %>
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt"		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>


	<script type="text/javascript">
		tswidgets = [ 'zebra' ];
		
		tssorting = [[1,0],[3,1]];
		/* tssorting = [[2,1]]; */
		
		
		function openDocument(key,type){
			window.location ="download?type="+type+"&key="+key;
		}
	</script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>

	<div id="div-body" class="portlet-body">
		<form:form commandName="searchForm" action="${action}" method="get">
			<form:hidden path="type" />
			<form:hidden path="subtype" />
			<div class="portlet-section-header">
				<div class="portlet-section-header-title">
					<doc41:translate label="Search Document"/>&nbsp;<doc41:translate label="${searchForm.type}_${searchForm.subtype}"/>
				</div>
				<c:if test="${not empty fragmentAdditionalButtons}">
					<jsp:invoke fragment="fragmentAdditionalButtons"/>
				</c:if>
				<a class="portlet-form-button" type="button" href='${action}?type=${searchForm.type}&subtype=${searchForm.subtype}'><doc41:translate label="ButtonReset" /></a>
				<input type="submit" class="portlet-form-button" value="<doc41:translate label="ButtonSearch"/>" name="ButtonSearch"/>
			</div>
         
            <doc41:errors form="searchForm"/>
			
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
                                                <c:if test="${not empty searchForm.attributePredefValuesAsString[attributeValue.key]}">
                                                    <option value="${searchForm.attributePredefValuesAsString[attributeValue.key]}"></option>
                                                </c:if>
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
												class="portlet-form-input-field portlet-big" maxlength="255"
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
        <form:form commandName="downloadMulti" action="downloadMulti" method="post">
    	  <table class="tablesorter" id="doc41table">
			<thead >
				<tr>

					<!-- thead text will be updated from the JSON; make sure the number of columns matches the JSON data -->
					<!-- header update currently disabled to put names in the jsp instead of in java  -->
                    <th data-sorter="false">&nbsp;</th>
                    <th><doc41:translate label="DocumentType" /></th>
					<c:if test="${empty showTableObjectId or showTableObjectId}">
						<th><doc41:translate label="ObjectId${searchForm.type}" /></th>
					</c:if>
					<%-- <th data-sorter="moment" data-date-format="${dateMomentPattern}"><doc41:translate label="StorageDate" /></th> --%>
					<th data-sorter="moment" data-date-format="${dateTimeMomentPattern}"><doc41:translate label="ArchiveLinkDate" /></th>
					<c:if test="${showTableDocumentClass}"><!-- empty showTableDocumentClass or  -->
						<th><doc41:translate label="DocumentClass" /></th>
					</c:if>
					<c:forEach items="${searchForm.customizedValuesLabels}"
							var="custValueLabel" varStatus="status">
					<th><c:out value="${custValueLabel}" /></th>
					</c:forEach>
					
				</tr>
			<colgroup>
                <col width="2%" />
                <col width="10%" />
				<c:if test="${empty showTableObjectId or showTableObjectId}">
					<col width="15%" />
				</c:if>
				<col width="15%" />
				<c:if test="${showTableDocumentClass}"><!-- empty showTableDocumentClass or  -->
					<col width="5%" />
				</c:if>
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
                <c:set var="idx" value="-1" scope="page" />
				<c:forEach items="${searchForm.documents}" var="document"
					varStatus="status"><c:set var="idx" value="${idx + 1}" scope="page"/>
					<tr style="cursor: pointer;">
                        <td><input id="docSel" name="docSel" type="checkbox" onclick="enableDownloadAll()" value="${document.key}|${searchForm.type}"/>
                        <input id="docAll" name="docAll" type="hidden" value="${document.key}|${searchForm.type}"/></td>
                        <td onclick="openDocument('${document.key}','${searchForm.type}')"><doc41:spaceToNbsp><doc41:translate label="${document.type}"/>
                        <!-- c:out value="${document.type}" / --></doc41:spaceToNbsp></td>
						<c:if test="${empty showTableObjectId or showTableObjectId}">
							<td onclick="openDocument('${document.key}','${searchForm.type}')"><doc41:spaceToNbsp><c:out value="${document.objectId}" />\
							</doc41:spaceToNbsp></td>
						</c:if>
						<%-- <td>
							<doc41:formatDate date="${document.storageDate}" zone="${user.timeZone}"></doc41:formatDate>
						</td> --%>
						<td onclick="openDocument('${document.key}','${searchForm.type}')">
							<doc41:formatDate date="${document.archiveLinkDate}" zone="${user.timeZone}"></doc41:formatDate>&nbsp;<doc41:formatTime date="${document.archiveLinkDate}" zone="${user.timeZone}"></doc41:formatTime>
						</td>
						<c:if test="${showTableDocumentClass}"><!-- empty showTableDocumentClass or  -->
							<td onclick="openDocument('${document.key}','${searchForm.type}')"><c:out value="${document.documentClass}" /></td>
						</c:if>
						<c:forEach items="${searchForm.attributeLabels}"
							var="attribLabel" varStatus="status">
						<td onclick="openDocument('${document.key}','${searchForm.type}')"><c:out value="${document.customizedValuesByKey[attribLabel.key]}" /></td>
						</c:forEach>
					</tr>
				</c:forEach>
			</tbody>
          </table>
		  <doc41:pager />
	          <!--  input id="download" name="download" type="button" --> 
	          <% String downloadeable = "";%>
              <% String opacity = "1.0";%>
	          <c:choose>
	          <c:when test="${ empty searchForm.documents}">
	          	<% downloadeable = "disabled"; opacity = "0.5"; %>
	          </c:when>
	          <c:otherwise>
	          	<% downloadeable = ""; opacity = "1.0"; %>
	          </c:otherwise>
	          </c:choose>
         
          
          <input id="submitButton" type="submit" class="portlet-form-button" value="Download All" name="ButtonDownload" 
          oncomplete="disableDownloadAll()" style="opacity: <c:out value="<%= opacity %>"/>;" <c:out value="<%= downloadeable %>"/>/>
          <script> <!-- Enable/Disable Downloadbutton when clicking on it -->
                function enableDownloadAll(){
                    document.getElementById("submitButton").disabled = false;
                    document.getElementById("submitButton").style.opacity= "1.0";
                }
				function disableDownloadAll(){
					document.getElementById("submitButton").disabled = true;
                    document.getElementById("submitButton").style.opacity= "0.5";
				}
			</script> 
        </form:form>
	
		
	</div>
	
	
	<%--<div id="div-body" class="portlet-body">
	<doc41:translate label="${stage}.searchSyncComment" />
	</div>--%>