<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="maintenance"		activeNav="rfcMetadata" 
	jspName="rfcMetadata"			component="maintenance"
	title="RFC Metadata">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<div class="portlet-body">
<div class="portlet-section-body">
<h1>RFC Metadata</h1>
<form:form commandName="command" action="selectRFCName" method="post">
	<span><form:select path="rfcName" cssClass="portlet-form-input-field" style="width: 400px;">
		<form:options items="${command.rfcNames}" itemValue="code" itemLabel="label"/>
	</form:select></span>
	<span><input type="submit" name="_finish" class="portlet-form-button" value="<doc41:translate label="ButtonShow"/>" /></span>
</form:form>
<textarea cols="80" rows="30" id="rfcDumpBox" >${command.rfcDump}</textarea>
</div>
</div>
</doc41:layout>