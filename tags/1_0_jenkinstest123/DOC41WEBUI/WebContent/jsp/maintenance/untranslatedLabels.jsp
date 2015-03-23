<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="maintenance"		activeNav="untranslatedLabels" 
	jspName="untranslatedLabels"	component="maintenance"
	title="Untranslated Labels">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<div class="portlet-body">
<div class="portlet-section-header">
	<div class="portlet-section-header-title">Untranslated Labels</div>
</div>
<div class="portlet-section-body">
	<form:form commandName="command" action="untranslatedLabels/reset" method="post">
		<input type="submit" name="_finish" class="portlet-form-button" value="<doc41:translate label="ButtonReset"/>" /></span>
	</form:form>
	<c:forEach items="${command.languages}" var="language" varStatus="languageStatus">
		<div class="portlet-section-body">
		<div class="section-separator"><doc41:translate label="Language"/>:&nbsp;<c:out value="${language}"/></div>
		<ul>
		<c:forEach items="${command.untranslatedLabels[language]}" var="label" varStatus="labelStatus">
			<c:choose>
				<c:when test="${command.editable }">
				<li><a href="../translations/translationAdd?tagName=${label}&language=${language}"><c:out value="${label}"/></a></li>
				</c:when>
				<c:otherwise>
				<li><c:out value="${label}"/></li>
				</c:otherwise>
			</c:choose>
		</c:forEach>
		</ul>
		</div>
	</c:forEach>
</div>
</div>
</doc41:layout>