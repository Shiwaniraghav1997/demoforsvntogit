<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="list" 				component="useradmin"
activeTopNav="management" 	activeNav="userManagement"
title="User Management">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
<!--

	tsheaders = {
		0 : {
			sorter : true,
			filter : false
		},
		5 : {
			sorter : true,
			filter : false
		},
		8 : {
			sorter : false,
			filter : false
		},
		9 : {
			sorter : false,
			filter : false
		}
	};
	
	tsfilters = {
			6: {
				internal:{},
				external:{}
			},
			7: {
				<c:forEach items="${allRoles}" var="role"
					varStatus="status">
				 <c:out value="${role} : {},"/>
		          </c:forEach>
			}
	};
//-->
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorter.js"></script>
  	
  	<div id="div-body" class="portlet-body">
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<tr><th><doc41:translate label="UserOverview"/></th></tr>
				</table>
				
				<a class="portlet-form-button" type="button" href='userlookup'><doc41:translate label="ButtonLookup"/></a>
				<a class="portlet-form-button" type="button" href='usercreate'><doc41:translate label="ButtonCreate"/></a>
			</div>
		
		
		<div class="portlet-section-body">
		<div class="pager">
		        <img src="${pageContext.request.contextPath}/resources/img/tablesorter/first.png" class="first" alt="First" title="First page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/prev.png" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/next.png" class="next" alt="Next" title="Next page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/last.png" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		      </div>
		<table class="tablesorter" id="doc41table">
			<colgroup>
		    	<col width="5%"/>
		    	<col width="20%"/>
		    	<col width="20%"/>
		    	<col width="7%"/>
		    	<col width="15%"/>
		    	<col width="10%"/>
		    	<col width="5%"/>
		    	<col width="14%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    </colgroup>		
			<thead class="portlet-table-header" >
		    <tr>
				<th>&nbsp;</th>
				<th><doc41:translate label="Surname"/></th>
			    <th><doc41:translate label="Firstname"/></th>
			    <th data-placeholder="exact only"><doc41:translate label="Cwid"/></th>
				<th><doc41:translate label="Email"/></th>
				<th><doc41:translate label="Phone"/></th>						
				<th><doc41:translate label="Type"/></th>
				<th><doc41:translate label="Roles"/></th>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
		  	</thead>
		
			<tbody class="portlet-table-body"> <!-- tbody will be loaded via JSON -->
			</tbody>
		</table>
		<div class="pager">
		        <img src="${pageContext.request.contextPath}/resources/img/tablesorter/first.png" class="first" alt="First" title="First page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/prev.png" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/next.png" class="next" alt="Next" title="Next page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/last.png" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		      </div>
		</div>
    </div>
</doc41:layout>