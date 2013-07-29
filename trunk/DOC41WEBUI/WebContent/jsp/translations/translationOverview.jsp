<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="translationOverview" component="TADMN"
activeTopNav="maintenance" 	activeNav="translations"
title="Translations">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorter.js"></script>
    <div class="portlet-body">
		<c:if test="${editable}">
			<div class="portlet-section-header">
		    	<a class="portlet-form-button" href='translationAdd'><doc41:translate label="ButtonCreate"/></a>
			</div>
		</c:if>


		<div class="portlet-section-body">
		
		<div class="portlet-section-body">
		
		<doc41:pager/>
		<table class="tablesorter" id="doc41table">
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
		  	</thead>
		
		  <tbody class="portlet-table-body"> <!-- tbody will be loaded via JSON -->
		  </tbody>
		</table>
		<doc41:pager/>
		</div>
	</div>
</div>
</doc41:layout>