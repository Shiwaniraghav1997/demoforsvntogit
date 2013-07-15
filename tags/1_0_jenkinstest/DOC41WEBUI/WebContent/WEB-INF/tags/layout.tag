<%@tag description="General Layout Tag 2" pageEncoding="UTF-8"%><?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
   "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@attribute name="activeTopNav"	required="true"%>
<%@attribute name="activeNav"		required="true"%>
<%@attribute name="activePage"		required="true"%>
<%@attribute name="jspName"			required="true"%>
<%@attribute name="component"		required="true"%>
<%@attribute name="title"			required="true"%>
<%@attribute name="showTopNav"		required="false"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@include file="/WEB-INF/fragments/layout/head.jspf" %>
	<body>
		<div id="container">
<%@include file="/WEB-INF/fragments/layout/header.jspf" %>
<%@include file="/WEB-INF/fragments/layout/topnavigation.jspf" %>
			<div id="page">
				  <div id="content"><jsp:doBody></jsp:doBody>
				  </div>
			</div>
		</div>
		<!-- Loading Image -->
		<div id="tab-loading" style="display: none;">
			<div style="font-weight: bold; text-align: center; padding-top: 150px">
			<img src="${pageContext.request.contextPath}/resources/img/common/bayer_logo_animated.gif" height="40" title="loading..." alt="loading..." style="border: 0px;"/>
			<p>Loading ...</p>
		</div>
</div>
	</body>
</html>
