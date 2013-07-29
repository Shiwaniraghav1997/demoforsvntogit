<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="openDeliveries" 	component="documents"
activeTopNav="documents" 	activeNav="documentType1"
title="Open Deliveries" showTopNav="false"
showHeader="false">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>

    <div class="portlet-body">
		
		<div class="portlet-section-body">
		
		<doc41:pager/>
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
				<tr style="cursor: pointer;" onclick="setDeliveryNumber('${delivery.deliveryNumber}','${delivery.shippingUnitNumber}')">
					<td><c:out value="${delivery.deliveryNumber}"/></td>
					<td><c:out value="${delivery.shippingUnitNumber}"/></td>
					<td><c:out value="${delivery.shipToNumber}"/></td>
					<td><c:out value="${delivery.soldToNumber}"/></td>
					<td><c:out value="${delivery.goodsIssueDate}"/></td>
				</tr>
			</c:forEach>
		  </tbody>
		</table>
		<doc41:pager/>
	</div>
</doc41:layout>