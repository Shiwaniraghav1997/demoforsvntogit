<%@taglib prefix="doc41" uri="doc41-tags"%><doc41:layout
	activePage="${pageContext.request.servletPath}"
	jspName="delcertuplist" component="documents" activeTopNav="upload"
	activeNav="${type}" title="Upload Delivery Certificate Document">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

	<script type="text/javascript">
		tswidgets = [ 'zebra' ];
		
		function chooseBatchObject(type,objectId,materialNumber,materialText,plant,batch,supplier){
			window.location ="delcertupload?type="+type+"&objectId="+objectId
			+ "&materialNumber="+materialNumber
			+ "&materialText="+materialText
			+ "&plant="+plant
			+ "&batch="+batch
			+ "&supplier="+supplier;
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

					<th><doc41:translate label="BatchObjectId" /></th>
					<th><doc41:translate label="MaterialNumber" /></th>
					<th><doc41:translate label="MaterialText" /></th>
					<th><doc41:translate label="Plant" /></th>
					<th><doc41:translate label="Batch" /></th>
					
				</tr>
			<colgroup>
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				<col width="20%" />
				
			</colgroup>
			</thead>

			<tbody >
				<c:forEach items="${QMBatchObjectList}" var="bo"
					varStatus="status">
					<tr style="cursor: pointer;"
						onclick="chooseBatchObject('${type}'
						,'${bo.objectId}'
						,'${bo.materialNumber}'
						,'${bo.materialText}'
						,'${bo.plant}'
						,'${bo.batch}'
						,'${supplier}')">
						
						<td><c:out value="${bo.objectId}" /></td>
						<td><c:out value="${bo.materialNumber}" /></td>
						<td><c:out value="${bo.materialText}" /></td>
						<td><c:out value="${bo.plant}" /></td>
						<td><c:out value="${bo.batch}" /></td>
						
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<doc41:pager />
	</div>
		

	</div>
	
</doc41:layout>
