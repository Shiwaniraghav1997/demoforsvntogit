<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations component="TADMN" jspName="translationOverview" />

<script type="text/javascript">
<!--
tsheaders = {
          7: {
            sorter: false,
            filter: false
          },
          8: {
	      	sorter: false,
	      	filter: false
	      }
};
//-->
</script>
<script type="text/javascript" src="<%= response.encodeURL(request.getContextPath() + "/resources/js/doc41tablesorter.js") %>"></script>

<html>
  <head>
  	<title>Translations</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>
    <%@include file="../doc41/navigation.jspf" %>
    
    <div class="portlet-body">
		<c:if test="${editable}">
			<div class="portlet-section-header">
		    	<input type="button" class="portlet-form-button" onclick="sendGet('translations/translationAdd', '')" value="<doc41:translate label="buttonCreate"/>" />
			</div>
		</c:if>


		<div class="portlet-section-body">
		
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
		    <th data-placeholder="exact only"><doc41:translate label="Mandant" /></th>
			<th data-placeholder="exact only"><doc41:translate label="Component" /></th>
			<th data-placeholder="exact only"><doc41:translate label="Page" /></th>
			<th><doc41:translate label="TagName" /></th>
			<th data-placeholder="exact only"><doc41:translate label="Language" /></th>
			<th data-placeholder="exact only"><doc41:translate label="Country" /></th>
			<th><doc41:translate label="TagValue" /></th>
			<th>&nbsp;</th>
			<th>&nbsp;</th>
		    </tr>
		    <colgroup>
		    	<col width="5%"/>
		    	<col width="10%"/>
		    	<col width="10%"/>
		    	<col width="20%"/>
		    	<col width="5%"/>
		    	<col width="5%"/>
		    	<col width="41%"/>
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
  </body>
</html>