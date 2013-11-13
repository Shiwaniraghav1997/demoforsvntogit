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
	<textarea cols="80" rows="30" id="rfcDumpBox" >${command.untranslatedLabels}</textarea>
</div>
</div>
</doc41:layout>