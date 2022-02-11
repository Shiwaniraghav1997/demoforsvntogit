<%@ page import="com.bayer.bhc.doc41webui.common.Doc41Constants" %>
<%@taglib prefix="doc41" uri="doc41-tags"%>
<doc41:layout activePage="${pageContext.request.servletPath}" jspName="searchpmsupplier" component="documents" activeTopNav="download" activeNav="${searchForm.type}" title="Search Document PM Global">
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
	<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
  	<link rel="stylesheet" href="/resources/css/jquery-ui-1.10.3.custom/custom-theme/jquery-ui-1.10.3.custom.css">
  	<script>$( function() { $( "#datepicker" ).datepicker(); } );</script>
	<doc41:searchtemplate action="searchpmsupplierGlobal">
		<jsp:attribute name="fragmentCustomSearchFields">
			<c:if test="!${searchForm.kgs}">
				<tr class="portlet-table">
					<th>
						<label for="${keyFileName}">
							<doc41:translate label="FileName" />
						</label>
					</th>
					<td>
						<input id="${keyFileName}" class="portlet-form-input-field portlet-big" maxlength="70" name="viewAttributes['${keyFileName}']" value="${searchForm.viewAttributes[keyFileName]}" />
						<doc41:error path="viewAttributes['${keyFileName}']" />
					</td>
				</tr>
			</c:if>
			<!-- ---Document Type-------->
			<tr class="portlet-table">
				<th>
					<label for="docType">
						<doc41:translate label="DocumentType" />
					</label>
				</th>
				<td>
					<form:select id="docType" path="docType"  items="${searchForm.allowedDocTypes}" cssClass="portlet-form-input-field portlet-big" itemLabel="label" itemValue="value" />
            		<doc41:error path="docType" />
            	</td>
            </tr>
            <script>
            /* $(document).ready(function(){
    		    $("select").change(function(){
    		        $(this).find("option:selected").each(function(){
    		            var optionValue = $(this).attr("value");
    		            /* alert("optionValue ::"+optionValue);  */
    		             if(optionValue=='YBM'){
    		            	  /* alert("optionValue"+optionValue);  */
    		            
    		                $("#purchaseOrd").show();
    		            } /* else{
    		                $("#purchaseOrd").hide();
    		            }  */
    		        });
    		    }).change();
    		}); */
    		 

    		 			
    		
            </script>
            <!-- -----------------end of document type----------- -->
            
            
            <c:if test="${searchForm.subtype eq Doc41Constants.PM_DOCUMENT_SUBTYPE_BOM_VERSION_ID}">
            	<tr>
            		<th>
            			<label for="versionIdBom">
            				<doc41:translate label="versionIdBom" />
            			</label>
            		</th>
            		<td>
            			<form:input path="versionIdBom" cssClass="portlet-form-input-field portlet-mandatory portlet-big" maxlength="70" />
            			<doc41:error path="versionIdBom" />
            			<doc41:error path="docType" />
            		</td>
            	</tr>
            </c:if>
            <!-- ------------------- purchase order elerj------------------- -->
            <!-- <div style='display:none;'>  -->
          <%--   <c:if test="${searchForm.subtype eq Doc41Constants.PM_DOCUMENT_SUBTYPE_PURCHASE_ORDER}">
                <tr id="purchaseOrd" style='display:none;'>
                    <th>
                        <label for="PurchaseOrderBOM">
                            <doc41:translate label="PurchaseOrderBOM" />
                        </label>
                    </th>
                    <td >
                        <form:input  path="purchaseOrder" cssClass="portlet-form-input-field portlet-big" maxlength="70" />
                        <doc41:error path="purchaseOrder" />
                        <!--------------<doc41:error path="docType" />   ---------------------->
                    </td>
                </tr>
             
           </c:if>
           --%> 
           
            
            <!-- ---------purchase order end ------ -->
            <c:if test="${searchForm.subtype eq Doc41Constants.PM_DOCUMENT_SUBTYPE_BOM_TIME_FRAME}">
            	<tr>
            		<th>
            			<label for="timeFrame">
            				<doc41:translate label="timeFrame" />
            			</label>
            		</th>
            		<td>
            			<form:input type="text" id="datepicker" path="timeFrame" cssClass="portlet-form-input-field portlet-mandatory portlet-big" maxlength="70" />
            			<doc41:error path="timeFrame" />
            			<doc41:error path="docType" />
            		</td>
            	</tr>
            </c:if>
		</jsp:attribute>
	</doc41:searchtemplate>
	<script src="/resources/js/datepicker.js"></script>
</doc41:layout>
