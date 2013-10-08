<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="supcoauplist" component="documents" activeTopNav="upload"
	activeNav="${type}" title="Upload Supplier CoA Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<script type="text/javascript">
		tswidgets = [ 'uitheme','zebra' ];
		
		function chooseILot(type,number,materialNumber,materialText,plant,batch,vendor,vendorBatch){
			window.location ="supcoaupload?type="+type+"&number="+number
			+ "&materialNumber="+materialNumber
			+ "&materialText="+materialText
			+ "&plant="+plant
			+ "&batch="+batch
			+ "&vendor="+vendor
			+ "&vendorBatch="+vendorBatch;
		}
	</script>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>
	
	<div id="div-body" class="portlet-body">
		<div class="portlet-section-header">
			<div class="portlet-section-header-title">
				<doc41:translate label="Upload Document"/>&nbsp;<doc41:translate label="${type}"/>
			</div>
		</div>

		<div class="portlet-section-body">
		<doc41:error path="objectId" />
		<doc41:pager />
		<table class="tablesorter" id="doc41table">
			<thead >
				<tr>

					<th><doc41:translate label="Number" /></th>
					<th><doc41:translate label="MaterialNumber" /></th>
					<th><doc41:translate label="MaterialText" /></th>
					<th><doc41:translate label="Plant" /></th>
					<th><doc41:translate label="Batch" /></th>
					<th><doc41:translate label="Vendor" /></th>
					<th><doc41:translate label="VendorBatch" /></th>
					
				</tr>
			<colgroup>
				<col width="15%" />
				<col width="15%" />
				<col width="20%" />
				<col width="10%" />
				<col width="10%" />
				<col width="15%" />
				<col width="15%" />
				
			</colgroup>
			</thead>

			<tbody >
				<c:forEach items="${inspectionLotList}" var="ilot"
					varStatus="status">
					<tr style="cursor: pointer;"
						onclick="chooseILot('${type}'
						,'${ilot.number}'
						,'${ilot.materialNumber}'
						,'${ilot.materialText}'
						,'${ilot.plant}'
						,'${ilot.batch}'
						,'${ilot.vendor}'
						,'${ilot.vendorBatch}')">
						
						<td><c:out value="${ilot.number}" /></td>
						<td><c:out value="${ilot.materialNumber}" /></td>
						<td><c:out value="${ilot.materialText}" /></td>
						<td><c:out value="${ilot.plant}" /></td>
						<td><c:out value="${ilot.batch}" /></td>
						<td><c:out value="${ilot.vendor}" /></td>
						<td><c:out value="${ilot.vendorBatch}" /></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<doc41:pager />
	</div>
		

	</div>
	
</doc41:layout>
