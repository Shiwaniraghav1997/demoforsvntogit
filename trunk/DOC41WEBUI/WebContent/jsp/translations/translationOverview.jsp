<%@include file="../doc41/prolog.jspf"%>
<doc41:loadTranslations component="TADMN" jspName="translationOverview" />


<html>
  <head>
  	<title>Translations</title></head>
  <body>
    <%@include file="../doc41/header.jspf" %>
    <%@include file="../doc41/navigation.jspf" %>
    
    <div class="portlet-body">
		<%@include file="translationsList/filter.jsp"%>
		<%@include file="translationsList/view.jsp"%>
	</div>
  </body>
</html>