<%@include file="../doc41/prolog.jspf" %>
<%-- no translations needed --%>
<html>
<body>
	<div id="div-body">
		<select id="locationIds" class="portlet-form-input-field" multiple="multiple" name="locationIds">
			<c:forEach items="${locationList}" var="location">
				<option value="${location.value}"><c:out value="${location.label}"/></option>>
			</c:forEach>
		</select>
	</div>
</body>
</html>