<%@tag description="Search Document Tag" pageEncoding="UTF-8"%>

<%@attribute name="form"      required="true"%>
<%@taglib prefix="doc41" uri="doc41-tags" %>
<%@taglib prefix="c"        uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn"       uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="fmt"      uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="form"     uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring"   uri="http://www.springframework.org/tags" %>


<spring:hasBindErrors name="${form}">
    <div class="portlet-section-body">
        <table cellpadding="4" cellspacing="0" class="nohover">
            <tbody>
               <c:forEach items="${errors.globalErrors}" var="error">
                    <tr>
                       <td style="width:15%"></td>
                       <td style="color: red"><doc41:translate label="${error.code}" /></td>
                    </tr>
                </c:forEach>
               <c:forEach items="${errors.fieldErrors}" var="error">
                   <tr>
                       <td onmouseover="style.cursor='pointer';" onclick="$('#${error.field}').focus();" style="color: blue;width:15%;"> <doc41:translate label="${error.field}"/> </td>
                       <td style="color: red;"><doc41:translate label="${error.field}.${error.code}" /></td>
                   </tr>
               </c:forEach>
            </tbody>
        </table>
    </div>
</spring:hasBindErrors> 