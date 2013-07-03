<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations component="documents" jspName="openDeliveries" />

<script type="text/javascript" src="<%= response.encodeURL(request.getContextPath() + "/resources/js/doc41tablesorterclient.js") %>"></script>

<html>
  <head>
  	<title>Open Deliveries</title></head>
  <body>
    
    <div class="portlet-body">
		
		<div class="portlet-section-body">
		
		      <div class="pager">
		        <img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/first.png") %>" class="first" alt="First" title="First page" />
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/prev.png") %>" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/next.png") %>" class="next" alt="Next" title="Next page" />
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/last.png") %>" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		      </div>
		<table class="tablesorter" id="doc41table">
		  <thead class="portlet-table-header" >
		    <tr>
		      
		       <!-- thead text will be updated from the JSON; make sure the number of columns matches the JSON data -->
		       <!-- header update currently disabled to put names in the jsp instead of in java  -->
		    <th><doc41:translate label="DeliveryNumber" /></th>
			<th><doc41:translate label="ShippingUnitNumber" /></th>
			<th><doc41:translate label="ShipTo" /></th>
			<th><doc41:translate label="SoldTo" /></th>
			<th><doc41:translate label="GoodsIssuedDate" /></th>
		    </tr>
		    <colgroup>
		    	<col width="20%"/>
		    	<col width="20%"/>
		    	<col width="20%"/>
		    	<col width="20%"/>
		    	<col width="20%"/>
		    </colgroup>
		  </thead>
		
		  <tbody class="portlet-table-body">
		  	<c:forEach items="${deliveryList}" var="delivery"
				varStatus="status">
				<tr>
					<td><a onclick="test('${delivery.deliveryNumber}')" href="#">${delivery.deliveryNumber}</a></td>
					<td><c:out value="${delivery.shippingUnitNumber}"/></td>
					<td><c:out value="${delivery.shipToNumber}"/></td>
					<td><c:out value="${delivery.soldToNumber}"/></td>
					<td><c:out value="${delivery.goodsIssueDate}"/></td>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
		<div class="pager">
		        <img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/first.png") %>" class="first" alt="First" title="First page" />
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/prev.png") %>" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/next.png") %>" class="next" alt="Next" title="Next page" />
				<img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/tablesorter/last.png") %>" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		      </div>
		</div>
  </body>
</html>