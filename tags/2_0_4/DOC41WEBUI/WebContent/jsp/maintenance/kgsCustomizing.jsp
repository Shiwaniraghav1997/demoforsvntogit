<%@taglib prefix="doc41" uri="doc41-tags" %><doc41:layout activePage="<%=this.getClass().getSimpleName()%>"
	activeTopNav="maintenance"		activeNav="kgsCustomizing" 
	jspName="kgsCustomizing"			component="maintenance"
	title="KGS Customizing">
<%@taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" 		uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form" 	uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"	uri="http://www.springframework.org/tags" %>

<div class="portlet-body">
<div class="portlet-section-body">
<h1>KGS Customizing</h1>
<form:form commandName="command" action="selectDocType" method="post">
	<span><form:select path="sapDocType" cssClass="portlet-form-input-field portlet-big">
		<form:options items="${command.sapDocTypes}" itemValue="code" itemLabel="label"/>
	</form:select></span>
	<span><input type="submit" name="_finish" class="portlet-form-button" value="<doc41:translate label="ButtonShow"/>" /></span>
</form:form>
<textarea cols="80" rows="30" id="dumpBox" >${command.dump}</textarea>
</div>
</div>
</doc41:layout>