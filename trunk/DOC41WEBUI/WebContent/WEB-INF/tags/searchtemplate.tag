<%@tag description="Search Document Tag" pageEncoding="UTF-8"%>

<%@attribute name="action" required="true"%>
<%@attribute name="fragmentAdditionalButtons" required="false"
	fragment="true"%>
<%@attribute name="fragmentCustomSearchFields" required="false"
	fragment="true"%>
<%@attribute name="showCustomAttributes" required="false"%>
<%@attribute name="showObjectId" required="false"%>
<%@attribute name="showCustomerNumber" required="false"%>
<%@attribute name="showVendorNumber" required="false"%>
<%@attribute name="showTableObjectId" required="false"%>
<%@attribute name="showTableDocumentClass" required="false"%>
<%@attribute name="showMaterialText" required="false"%>
<%@taglib prefix="doc41" uri="doc41-tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>


<script type="text/javascript">
		tswidgets = [ 'zebra' ];
		
		tssorting = [[1,0],[3,1]];
		/* tssorting = [[2,1]]; */
		
		
		function openDocument(key,type){
			window.location ="download?type="+type+"&key="+key;
		}
	</script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>
<!-- <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script> -->
<!-- <link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet"/> -->


<%--  <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/modalLayout.css" /> --%>


<div id="div-body" class="portlet-body">
	<!-- searchform in tag -->
	<form:form commandName="searchForm" action="${action}" method="get">
		<form:hidden path="type" />
		<form:hidden path="subtype" />
		<div class="portlet-section-header">
			<div class="portlet-section-header-title">
				<doc41:translate label="Search Document" />
				&nbsp;
				<doc41:translate label="${searchForm.type}_${searchForm.subtype}" />
			</div>
			<c:if test="${not empty fragmentAdditionalButtons}">
				<jsp:invoke fragment="fragmentAdditionalButtons" />
			</c:if>
			<!-- Search Button -->
			<a class="portlet-form-button" type="button"
				href='${action}?type=${searchForm.type}&subtype=${searchForm.subtype}'><doc41:translate
					label="ButtonReset" /></a> <input type="submit" id="search"
				class="portlet-form-button"
				value="<doc41:translate label="ButtonSearch"/>" name="ButtonSearch" />
			<%-- <input type="submit" id="search1" class="portlet-form-button"
				value="<doc41:translate label="ButtonSearch"/>" name="ButtonSearch" /> --%>
		</div>

		<doc41:errors form="searchForm" />

		<div class="portlet-section-body">
			<div class="section-separator">
				<doc41:translate label="attributes" />
			</div>
			<table cellpadding="4" cellspacing="0" class="nohover">
				<thead class="portlet-table-header">
					<colcolgroup>
					<col width="15%" />
					<col width="85%" />
					</colcolgroup>
				</thead>
				<tbody class="portlet-table-body">

					<tr>
						<c:if
							test="${searchForm.customerNumberUsed && (empty showCustomerNumber or showCustomerNumber)}">
							<th><label for="customerNumber"><doc41:translate
										label="CustomerNumber" /></label></th>
							<td><form:select path="customerNumber"
									items="${searchForm.customers}"
									cssClass="portlet-form-input-field portlet-mandatory portlet-big"
									itemLabel="label" itemValue="number" /> <doc41:error
									path="customerNumber" /></td>
						</c:if>
					</tr>
					<tr>
						<c:if
							test="${searchForm.vendorNumberUsed && (empty showVendorNumber or showVendorNumber)}">
							<th><label for="vendorNumber"><doc41:translate
										label="VendorNumber" />
										<span title="mandatory"> *</span></label></th>
							<td><form:select path="vendorNumber"
									items="${searchForm.vendors}"
									cssClass="portlet-form-input-field portlet-mandatory-custom portlet-big"
									itemLabel="label" itemValue="number" /> <doc41:error
									path="vendorNumber" /></td>
						</c:if>
					</tr>
					<tr>
					<tr id="objectId">
						<c:if test="${empty showObjectId or showObjectId}">
							<th><doc41:translate label="ObjectId${searchForm.type}" /> <span title="mandatory"> *</span></th>
							<td><form:input path="objectId"
									cssClass="portlet-form-input-field portlet-mandatory portlet-big"
									maxlength="70" /> <doc41:error path="objectId" /></td>
						</c:if>
					</tr>

					<c:if test="${not empty fragmentCustomSearchFields}">
						<jsp:invoke fragment="fragmentCustomSearchFields" />
					</c:if>

					<c:if test="${empty showCustomAttributes or showCustomAttributes}">
						<c:forEach items="${searchForm.attributeValues}"
							var="attributeValue" varStatus="status">
							<tr
								<c:if test="${lovStatus.count % 2 != 0}">class="portlet-table-alternate"</c:if>>
								<th><label for="${attributeValue.key}"><c:out
											value="${searchForm.attributeLabels[attributeValue.key]}" />
										<%-- <input type="hidden" name="attributeLabels['${attributeValue.key}']" value="${searchForm.attributeLabels[attributeValue.key]}"/> --%>
								</label></th>
								<td><c:choose>
								
										<c:when
											test="${fn:length(searchForm.attributePredefValues[attributeValue.key])>0}">
											<select id="${attributeValue.key}"
												class="portlet-form-input-field portlet-big"
												name="attributeValues['${attributeValue.key}']">
												<c:if
													test="${not empty searchForm.attributePredefValuesAsString[attributeValue.key]}">
													<option
														value="${searchForm.attributePredefValuesAsString[attributeValue.key]}"></option>
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
										<!-- -----file  value in tag----- -->
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
		<!-- -------testing start -------- -->

		<!-- The Modal -->
		<div id="myModal" class="modal">

			<div class="modal-content modal-lg">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">&times;</button>
					<!-- <h4 class="modal-title">Modal Header</h4> -->
				</div>
				<div class="modal-body">
					<div class="scrollit">
						<table class="table" id="example">
							<thead>
								<tr>

									<th>No</th>
									<th>Material No</th>
									<th>Material Text</th>

								</tr>
							</thead>

							<tbody>

								<c:forEach var="p" items="${searchForm.materialNumberList}"
									varStatus="status">
									<tr>

										<td></td>
										<td>${p}</td>
										<td>${searchForm.materialText[status.index]}<input
											type="hidden" id="pvId" name="pvId"
											value="${searchForm.productionversionList[status.index]}">
										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- <div class="selectRowButtonContainer"><input type="submit"  class="btn btn-primary" value="Submit" id="selectRowButton" /></div> -->
				</div>
				<div class="modal-footer">
					<!--           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
					<!-- <div class="selectRowButtonContainer"><input type="submit" value="Submit" id="selectRowButton" /></div> -->
					<div class="selectRowButtonContainer">
						<input type="submit" class="btn btn-primary" value="Submit"
							id="selectRowButton" />
					</div>
				</div>
			</div>
		</div>

		<!-- testing end  -->

	</form:form>
</div>
<!-- ---------------------------------popup strat------------- -->



<!-- --------------------------New CODE share by LALITA--------- -->
<%-- <form:form commandName="searchpmsupplierGlobal" action="searchpmsupplierGlobal"
			method="get">  --%>

<%--  
	<!-- The Modal -->
	<div id="myModal" class="modal">

<div class="modal-content modal-lg">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <!-- <h4 class="modal-title">Modal Header</h4> -->
        </div>
        <div class="modal-body">
        <div class ="scrollit">
          <table class="table" id="example">
            <thead>
              <tr>
              
                <th>No</th>
                <th>Material No</th>
                <th>Material Text</th>
                
              </tr>
            </thead>
            
            <tbody>

                      <c:forEach var="p" items="${searchForm.materialNumberList}" varStatus="status">
  <tr>
			
 		 <td>  </td>
      <td>${p}</td>
      <td>${searchForm.materialText[status.index]}
      <input type="hidden" id="pvId" name="pvId"
			value="${searchForm.productionversionList[status.index]}"> 
      </td>
      
  </tr>
</c:forEach>
            </tbody>
          </table>
          </div>
           <!-- <div class="selectRowButtonContainer"><input type="submit"  class="btn btn-primary" value="Submit" id="selectRowButton" /></div> -->
        </div>
        <div class="modal-footer">
<!--           <button type="button" class="btn btn-default" data-dismiss="modal">Close</button> -->
<!-- <div class="selectRowButtonContainer"><input type="submit" value="Submit" id="selectRowButton" /></div> -->
 <div class="selectRowButtonContainer"><input type="submit"  class="btn btn-primary" value="Submit" id="selectRowButton" /></div>
      </div>
     </div>
    </div> --%>

<%-- </form:form> --%>
<script>

$(document).ready(function(){
  /* $("#myBtn").click(function(){  	
  	$("#myModal").show();
  }); */
  $(".close").click(function(){  	
  	$("#myModal").hide();
  });
  
  $(".selectRowButtonContainer").click(function(){  	
	  	$("#myModal").hide();
	  });
  
  
  /* $(".table-body-row").click(function(e){
    e.preventDefault();
    e.stopPropagation();
  	$(".resp-table-row , .table-body-row").removeClass("blue");
  	$(this).addClass("blue");
  });
  $(document).click(function() {  
  	$(".resp-table-row , .table-body-row").removeClass("blue");
}); */

$("#selectRowButton").prop('disabled', true);
$("#example td").click(function () {
    $('.selectedRow').removeClass('selectedRow');
    $(this).parents('tr').addClass('selectedRow');
    $("#selectRowButton").prop('disabled', false);
});

var addSerialNumber = function () {
    var i = 0
    $("#example tr").each(function(index) {
        $(this).find('td:nth-child(1)').html(index-1+1);
    });
};

addSerialNumber();

   $("#selectRowButton").click(function(){ 
    var selectedRowData = {};
    selectedRowData["lineNum"] = $('.selectedRow td:nth-child(1)').text();
    
    var materialNumber= $('.selectedRow td:nth-child(2)').text();
     selectedRowData["materialNum"] = $('.selectedRow td:nth-child(3)').text();
    var PvNumber=$('.selectedRow #pvId').val() ;
    var  vendorNumber=$('.selectedRow #vendorNumber').val() ;
    var  targetType=$('#targetType').val() ;
    var allowedDocTypes=$('#allowedDocTypes').val() ;
    var doctype =$('#doctype').val() ;
    var flag="0";
    var subType=1;
    var purchaseOrder=$('#purchaseOrder').val() ;
    var searchType=$('#searchType').val() ;
    /* console.log("seledata:"+selectedRowData); */
  /*   console.log(selectedRowData);
    console.log(materialNumber); */
    console.log(materialNumber);
    console.log("searchType:"+searchType);
    console.log("targetType:"+targetType.toString());
      
       $.ajax({ 
    	type: "get",
    	 url: "/bds/documents/searchpmsupplierGlobal", 
    	 async:false,
         data: {"objectId":materialNumber,"productionVersion":PvNumber,"vendorNumber":vendorNumber,"searchType":searchType, "type":doctype, "flag":flag,"subtype":subType,"purchaseOrder":purchaseOrder}, 
         
        contentType: "application/json", 
     
         success: function(data){
        	 
        	 $("html").html(data);
        	 console.log("res:"+$("html").html(data));
        	 
      	
      		 return data; }, 
    	  error: function( xhr){
             
            console.log("data01"+data); 
             
        }}); 
        
   });  
  
  
}); 
//new code for ajax end

 

</script>
<%-- </form:form> --%>

<%-- <input type="hidden" id="flagId" name="flagId"
			value="${searchForm.flag}">
			<input type="hidden" id="vendorNo" name="vendorNo"
			value="${searchForm.vendorNumber}">
			<input type="hidden" id="targetType" name="targetType"
			value="${searchForm.searchingTargetType}">
			<input type="hidden" id="doctype" name="doctype"
			value="${searchForm.type}"> --%>

<!-- --------------------popup end-------------- -->

<div class="portlet-section-body">
	<doc41:error path="objectId" />
	<!-- result table -->
	<div class="section-separator">
		<doc41:translate label="results" />
	</div>
	<doc41:pager />
	<form:form commandName="downloadMulti" action="downloadMulti"
		method="post">
		<table class="tablesorter" id="doc41table">
			<thead>
				<tr>

					<!-- thead text will be updated from the JSON; make sure the number of columns matches the JSON data -->
					<!-- header update currently disabled to put names in the jsp instead of in java  -->
					<th data-sorter="false">&nbsp;</th>
					<th><doc41:translate label="DocumentType" /></th>
					<c:if test="${empty showTableObjectId or showTableObjectId}">
						<!-- ----------materila no -->
						<th><doc41:translate label="ObjectId${searchForm.type}" /></th>
					</c:if>
					<%-- <th data-sorter="moment" data-date-format="${dateMomentPattern}"><doc41:translate label="StorageDate" /></th> --%>
					<th data-sorter="moment"
						data-date-format="${dateTimeMomentPattern}"><doc41:translate
							label="ArchiveLinkDate" /></th>
					<c:if test="${showTableDocumentClass}">
						<!-- empty showTableDocumentClass or  -->
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
				<c:if test="${showTableDocumentClass}">
					<!-- empty showTableDocumentClass or  -->
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

			<tbody>
				<c:set var="idx" value="-1" scope="page" />
				<c:forEach items="${searchForm.documents}" var="document"
					varStatus="status">
					<c:set var="idx" value="${idx + 1}" scope="page" />
					<tr style="cursor: pointer;">
						<td><input id="docSel" name="docSel" type="checkbox"
							onclick="enableDownloadAll()"
							value="${document.key}|${searchForm.type}" /> <input id="docAll"
							name="docAll" type="hidden"
							value="${document.key}|${searchForm.type}" /></td>
						<td onclick="openDocument('${document.key}','${searchForm.type}')"><doc41:spaceToNbsp>
								<doc41:translate label="${document.type}" />
								<!-- c:out value="${document.type}" / -->
							</doc41:spaceToNbsp></td>
						<c:if test="${empty showTableObjectId or showTableObjectId}">
							<td
								onclick="openDocument('${document.key}','${searchForm.type}')"><doc41:spaceToNbsp>
									<c:out value="${document.objectId}" />\
							</doc41:spaceToNbsp></td>
						</c:if>
						<%-- <td>
							<doc41:formatDate date="${document.storageDate}" zone="${user.timeZone}"></doc41:formatDate>
						</td> --%>
						<td onclick="openDocument('${document.key}','${searchForm.type}')">
							<doc41:formatDate date="${document.archiveLinkDate}"
								zone="${user.timeZone}"></doc41:formatDate>&nbsp;<doc41:formatTime
								date="${document.archiveLinkDate}" zone="${user.timeZone}"></doc41:formatTime>
						</td>
						<c:if test="${showTableDocumentClass}">
							<!-- empty showTableDocumentClass or  -->
							<td
								onclick="openDocument('${document.key}','${searchForm.type}')"><c:out
									value="${document.documentClass}" /></td>
						</c:if>
						<c:forEach items="${searchForm.attributeLabels}" var="attribLabel"
							varStatus="status">
							<td
								onclick="openDocument('${document.key}','${searchForm.type}')"><c:out
									value="${document.customizedValuesByKey[attribLabel.key]}" /></td>
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


		<input id="submitButton" type="submit" class="portlet-form-button"
			value="Download All" name="ButtonDownload"
			oncomplete="disableDownloadAll()"
			style="opacity: <c:out value="<%= opacity %>"/>;"
			<c:out value="<%= downloadeable %>"/> />
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

	<input type="hidden" id="flagId" name="flagId"
		value="${searchForm.flag}"> <input type="hidden" id="vendorNo"
		name="vendorNo" value="${searchForm.vendorNumber}"> <input
		type="hidden" id="targetType" name="targetType"
		value="${searchForm.searchingTargetType}"> <input
		type="hidden" id="doctype" name="doctype" value="${searchForm.type}">
	<input type="hidden" id="purchaseOrder" name="purchaseOrder"
		value="${searchForm.purchaseOrder}"> <input type="hidden"
		id="pvId" name="pvId" value="${searchForm.productionversionList}">
		<input type="hidden" id="searchType" name="searchType"
		value="${searchForm.searchType}">
		

</div>
<script>
	/* var flag00=$("#flagId").val(); */
	var flag=$("#flagId").val();
/* alert(flag+"flag"); */
	var response = "1"
	if (response == flag) {
		$("#myModal").show();

	}
	

	var addSerialNumber = function () {
	    var i = 0
	    $("#example tr").each(function(index) {
	        $(this).find('td:nth-child(1)').html(index-1+1);
	    });
	};

	addSerialNumber();

	
	
	</script>

<%--<div id="div-body" class="portlet-body">
	<doc41:translate label="${stage}.searchSyncComment" />
	</div>--%>