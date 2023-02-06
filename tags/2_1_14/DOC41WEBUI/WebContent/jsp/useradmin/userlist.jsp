<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="${pageContext.request.servletPath}"
jspName="list" 				component="useradmin"
activeTopNav="management" 	activeNav="userManagement"
title="User Management">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
<!--

// https://mottie.github.io/tablesorter/docs/example-widget-filter-custom.html
// https://github.com/Mottie/tablesorter/issues/692

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

	tsFilterSource = {
			7: [
				<c:forEach items="${allRoles}" var="role"
					varStatus="status">
					'<c:out value="${role}|"/><doc41:translate label="${role}" />',
	        	</c:forEach>
			   ]
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
			<div class="portlet-section-header-title"><doc41:translate label="UserOverview"/></div>
			<a class="portlet-form-button" type="button" href='userlookup'><doc41:translate label="ButtonCreateInternalUser"/></a>
			<a class="portlet-form-button" type="button" href='usercreate'><doc41:translate label="ButtonCreateExternalUser"/></a>
		</div>


		<div class="portlet-section-body">
		<doc41:pager/>
		<table class="tablesorter" id="doc41table">
			<colgroup>
		    	<col width="5%"/>
		    	<col width="17%"/>
		    	<col width="17%"/>
		    	<col width="5%"/>
		    	<col width="15%"/>
		    	<col width="10%"/>
		    	<col width="5%"/>
		    	<col width="22%"/>
		    	<col width="2%"/>
		    	<col width="2%"/>
		    </colgroup>
			<thead>
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

			<tbody> <!-- tbody will be loaded via JSON -->
			</tbody>
		</table>
		<doc41:pager/>
		</div>
    </div>
</doc41:layout>