<%@include file="../doc41/prolog.jspf" %>
<doc41:loadTranslations jspName="list" component="useradmin"/>



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
<script type="text/javascript" src="<%= response.encodeURL(request.getContextPath() + "/resources/js/doc41tablesorter.js") %>"></script>

<html>
  <head><title>User Management</title></head>
  
  <body>
	<%@include file="../doc41/header.jspf" %>
  	<%@include file="../doc41/navigation.jspf" %>
  	
  	<div id="div-body" class="portlet-body">
			<div class="portlet-section-header">
				<table class="portlet-section-subheader" style="float: left; padding-left: 2px; padding-right: 30px;vertical-align:bottom" >
					<th><doc41:translate label="UserOverview"/></th>
				</table>
				
				<input class="portlet-form-button" type="button" onclick="sendGet('userlookup', '')" value="<doc41:translate label="ButtonLookup"/>" />
				<input class="portlet-form-button" type="button" onclick="sendGet('usercreate', '')" value="<doc41:translate label="ButtonCreate"/>" />
			</div>
		
		
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
				<th>&nbsp;</th>
				<th><doc41:translate label="Surname"/></th>
			    <th><doc41:translate label="Firstname"/></th>
			    <th data-placeholder="exact only"><doc41:translate label="Cwid"/></th>
				<th><doc41:translate label="Email"/></th>
				<th><doc41:translate label="Phone"/></th>						
				<th data-placeholder="exact only"><doc41:translate label="Type"/></th>
				<%
					for (int i = 0; i < User.ALL_ROLES.length; i++) {
				%>
				<th><img src="<%= response.encodeURL(request.getContextPath() + "/resources/img/usermanagement/symbol_"+User.ALL_ROLES[i]+".gif") %>" title="<doc41:translate label="<%= User.ALL_ROLES[i] %>"/>"	alt="<doc41:translate label="<%= User.ALL_ROLES[i] %>"/>" style="border: 0px;"></th>
					<%
						}
					%>
				<th>&nbsp;</th>
				<th>&nbsp;</th>
			</tr>
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
		  </thead>
		
		  <tbody class="portlet-table-body"> <!-- tbody will be loaded via JSON -->
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
		</div>
		

    </div>
  </body>
</html>