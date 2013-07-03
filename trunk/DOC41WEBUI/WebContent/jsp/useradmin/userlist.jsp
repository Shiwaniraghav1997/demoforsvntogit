<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="list" 				component="useradmin"
activeTopNav="management" 	activeNav="userManagement"
title="">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
<!--

	tsheaders = {
		5 : {
			sorter : true,
			filter : false
		},
		7 : {
			sorter : false,
			filter : false
		},
		8 : {
			sorter : false,
			filter : false
		},
		9 : {
			sorter : false,
			filter : false
		},
		10 : {
			sorter : false,
			filter : false
		},
		11 : {
			sorter : false,
			filter : false
		},
		12 : {
			sorter : false,
			filter : false
		},
		13 : {
			sorter : false,
			filter : false
		},
		14 : {
			sorter : false,
			filter : false
		},
		15 : {
			sorter : false,
			filter : false
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
				
				<input class="portlet-form-button" type="button" onclick="sendGet('useradmin/userlookup', '')" value="<doc41:translate label="ButtonLookup"/>" />
				<input class="portlet-form-button" type="button" onclick="sendGet('useradmin/usercreate', '')" value="<doc41:translate label="ButtonCreate"/>" />
			</div>
		
		
		<div class="portlet-section-body">
		<div class="pager">
		        <img src="${pageContext.request.contextPath}/resources/img/tablesorter/first.png" class="first" alt="First" title="First page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/prev.png" class="prev" alt="Prev" title="Previous page" />
				<span class="pagedisplay"></span> <!-- this can be any element, including an input -->
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/next.png" class="next" alt="Next" title="Next page" />
				<img src="${pageContext.request.contextPath}/resources/img/tablesorter/last.png" class="last" alt="Last" title= "Last page" />
		        <select class="pagesize">
		        	<option value="1">1</option>
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
		    	<col width="2%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
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
				<th data-placeholder="exact only"><doc41:translate label="Type"/></th>
				<c:forEach items="${user.ALL_ROLES}" var="role">
				<th><img src="${pageContext.request.contextPath}/resources/img/usermanagement/symbol_${role}.gif" title="<doc41:translate label="${role}"/>" alt="<doc41:translate label="${role}"/>" style="border: 0px;"/></th>
				</c:forEach>
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
		        	<option value="1">1</option>
		         	<option selected="selected" value="10">10</option>
					<option value="20">20</option>
					<option value="30">30</option>
					<option value="40">40</option>
		        </select>
		      </div>
		</div>
    </div>
</doc41:layout>