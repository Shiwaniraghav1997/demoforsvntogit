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

<doc41:pager/>

<table class="tablesorter" id="doc41table">
  <thead>
  <tr>
  	<th>Permissions</th>
    <c:forEach items="${pro}" var="varpro">
    	<th>
            <doc41:translate label="${varpro.profilename}" /><BR>
            [${varpro.type}, ${varpro.extType}]
        </th>
      </c:forEach>
  </tr>  
  </thead>
  
  <tbody>
  <c:forEach items="${pps}" var="varpps">
  <tr>
	<td style="text-align: left; white-space: pre;"><span title="[${varpps.permissionCodeHTML}&nbsp;/&nbsp;${varpps.typeHTML}] ${varpps.permissionNameHTML}">${varpps.permissionDescriptionHTML}<BR>[${varpps.permissionStatusHTML}]</span></td>
    <c:forEach items="${pro}" var="varpro">
        <td class="centered"><c:if test="${varpps.profiles[varpro.profilename] eq '1'}"><span title="${varpro.profilename} [${varpro.type}, ${varpro.extType}]"><img src="${pageContext.request.contextPath}/resources/img/common/check_green.gif" alt="<doc41:translate label="CheckGreen" />" style="border: 0px;"/></span></c:if></td>
    </c:forEach>
  </tr>
  </c:forEach>
  </tbody>

  <thead>
  <tr>
    <th>Permissions</th>
    <c:forEach items="${pro}" var="varpro">
        <th>
            <doc41:translate label="${varpro.profilename}" /><BR>
            [${varpro.type}, ${varpro.extType}]
        </th>
      </c:forEach>
  </tr>  
  </thead>

</table>

<doc41:pager/>
		
	</div>
</div>
</doc41:layout>