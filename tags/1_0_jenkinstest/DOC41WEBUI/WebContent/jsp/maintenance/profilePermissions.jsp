<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="maintenance"		activeNav="profilePermissions" 
	jspName="profilePermissions"	component="maintenance"
	title="Profile Permission View">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/doc41tablesorterclient.js"></script>

<div class="portlet-body">
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
  <thead>
  <tr>
  	<th>Permissions</th>
	<th><doc41:translate label="Doc41Carr" /></th>	
	<th><doc41:translate label="Doc41Cusbr" /></th>	
	<th><doc41:translate label="Doc41Laysup" /></th>
	<th><doc41:translate label="Doc41Pmsup" /></th>	
	<th><doc41:translate label="Doc41Badm" /></th>	
	<th><doc41:translate label="Doc41Tadm" /></th>	
	<th><doc41:translate label="Doc41Obsv" /></th>	
  </tr>  
  </thead>
  
  <tbody>
  <c:forEach items="${pps}" var="varpps" varStatus="status">
  <tr>
	<td><span title="${varpps.Permissiondescription}">${varpps.Permissionname}</span></td>
	<td><c:if test="${varpps.Doc41Carr}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
	<td><c:if test="${varpps.Doc41Cusbr}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
	<td><c:if test="${varpps.Doc41Laysup}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>
	<td><c:if test="${varpps.Doc41Pmsup}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
	<td><c:if test="${varpps.Doc41Badm}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
	<td><c:if test="${varpps.Doc41Tadm}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
	<td><c:if test="${varpps.Doc41Obsv}"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></c:if></td>	
  </tr>
  </c:forEach>
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