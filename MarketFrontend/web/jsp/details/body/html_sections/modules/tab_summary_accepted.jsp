<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="Message" scope="request" value="<%=request.getAttribute("msg")%>" />
  <style type="text/css">
    blockquote {padding-left:15px;}
  </style>
   <script type="text/javascript">
     

 	//For priority 5B
  	 jQuery("#modelSerialCloseDiv").html("");


     // Only show Bid Form for zero_price_bid and routed/posted status
     if(${(summaryDTO.priceModel == 'ZERO_PRICE_BID' || summaryDTO.priceModel == 'BULLETIN') && summaryDTO.status == 110} && ${userRole == 'Provider'})
     	jQuery('#checkSummary').load('soDetailsBid_execute.action?bidderResourceId=${bidderResourceId}&soId=${SERVICE_ORDER_ID}&resId=${routedResourceId}&tab=Summary');     
     else
      	jQuery('#checkSummary').load('soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}&resId=${routedResourceId}&tab=Summary');
    
     function expandSO(id,path){

	var divId="order"+id;
	var bodyId="order_menu_body"+id;
	jQuery("#"+divId+" p.menugroupso_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#"+bodyId).slideToggle(300);
	var ob=document.getElementById('soImage'+id).src;
	if(ob.indexOf('arrowRight')!=-1){
	document.getElementById('soImage'+id).src=path+"/images/widgets/arrowDown.gif";
	}
	if(ob.indexOf('arrowDown')!=-1){
	document.getElementById('soImage'+id).src=path+"/images/widgets/arrowRight.gif";
	}
	
	
} 
    // var nAgt = navigator.userAgent;
   	 
    // window.onload=goToDocumentsAndPhotos();
     function expandInvoiceParts(imgId,divId,path){
            $("#"+divId).toggle();
            var ob=document.getElementById(imgId).src;
            if(ob.indexOf('arrowRight')!=-1){
                 document.getElementById(imgId).src=path+"/images/widgets/arrowDown.gif";
             }
            if(ob.indexOf('arrowDown')!=-1){
                  document.getElementById(imgId).src = path+"/images/widgets/arrowRight.gif";
               }
            }
    
      </script>

<div class="soNote">	
<c:set var="SOStatusCode" value="<%=request.getAttribute(
					"THE_SERVICE_ORDER_STATUS_CODE")%>"/>
<c:set var="statusReceived" scope="page" value="<%=String.valueOf(OrderConstants.ROUTED_STATUS)%>" />
<c:choose>
<c:when test="${THE_SERVICE_ORDER_STATUS_CODE == 165 }">
   	<c:if test="${msg != null&& not empty msg}">
   		<c:choose>
   		<c:when test="${cancellation_request_failure == 'true' }">
   				<div class="errorBox errorMsg" style="background:#FAAFBA; color: #FE0000; border: 1px solid #FF9600;width:669px;">  ${msg}</div>
   		</c:when>
   		<c:otherwise>
   					<div class="successBox" style="background-color:#F0FFF0; color: #006400; border:solid 2px #B4EEB4;
padding:5px 5px 5px 20px; margin-bottom: 3px; font-weight:normal; position:relative;width:669px;">  ${msg}</div>
   		</c:otherwise>
   		</c:choose>
   					
   					
   	</c:if>
   					<%
   						request.removeAttribute("msg");
   					%>
</c:when>
<c:otherwise>
     	<div style="color: blue">
   			<p>${msg}</p>
    <%
    	request.removeAttribute("msg");
    %>
		</div>
</c:otherwise>
</c:choose>       

   
    <div id="rightsidemodules" class="colRight255 clearfix" >
           
         
         <p id="checkSummary" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span> </span></p>
         
      
       
    </div>


<div class="contentPane">
<%-- Display Server Side Validation Messages when we are coming from other pages: Display from Session --%>
<c:set var="errList" value="${errList}" scope="request" />
<c:if test="${fn:length(errList) > 0}">
<div class="errorBox clearfix" style="width: 665px; overflow-y:hidden;visibility:visible">
	<c:forEach items="${errList}"  var="error" varStatus="errIndex">
		<p class="errorMsg">
		&nbsp;&nbsp;&nbsp;&nbsp;${error.fieldId} -  ${error.msg}
		</p>
	</c:forEach>
</div>

<%
	session.removeAttribute("errList");
%>
<br>
</c:if>

<c:if test="${fn:length(message) > 0}">
<div class="errorBox clearfix" style="width: 665px; overflow-y:hidden;visibility:visible">
		<p class="errorMsg">
		<s:property value="errMessage" />
		&nbsp;&nbsp;&nbsp;&nbsp;${message}
		</p>
</div>
<%
	session.removeAttribute("message");
%>
<br>
</c:if>	

<c:set var="checkGroup" value="" scope="request" />
<c:choose>
	<c:when test="${not empty groupOrderId}">
	<c:set var="checkGroup" value="group" scope="request" />

		<jsp:include page="panel_general_information_order_group.jsp" />
		
		<c:forEach items="${serviceOrders}" var="summaryDTO" varStatus="soCounter">
		
		 	<c:set var="summaryDTO" value="${summaryDTO}" scope="request" />
		 	<c:set var="divName" value="order"/>
			 <c:set var="divName" value="${divName}${summaryDTO.id}"/>
			<div id="${divName}" class="menugroupso_list">
			<c:set var="bodyName" value="order_menu_body"/>
			<c:set var="bodyName" value="${bodyName}${summaryDTO.id}"/>
			<c:set var="soImage" value="soImage"/>
    		<c:set var="soImage" value="${soImage}${summaryDTO.id}"/>
			<p class="menugroupso_head" onclick="expandSO('${summaryDTO.id}','${staticContextPath}')">&nbsp;<img id="${soImage}" src="${staticContextPath}/images/widgets/arrowRight.gif"/>&nbsp;Service Order: ${summaryDTO.id}</p>

    		<div id="${bodyName}" class="menugroupso_body">
		 	<c:set var="soCounter" value="${soCounter.index}" scope="request" />
							
				<c:if test="${not empty showGeneralInfo}">
				<c:set var="counting" value="${counting+1}"/>
					<jsp:include page="panel_general_information.jsp" />
				</c:if>
				
				<c:if test="${not empty showScopeOfWork}">
					<c:choose>
					<c:when test="${summaryDTO.priceModel == 'ZERO_PRICE_BID'}">
						<jsp:include page="panel_scope_of_work_zero_price.jsp" />
					</c:when>
					<c:when test="${summaryDTO.priceModel == 'NAME_PRICE'}">
						<jsp:include page="panel_scope_of_work.jsp" />
					</c:when>
					<c:otherwise>
						<jsp:include page="panel_scope_of_work.jsp" />
					</c:otherwise>
					</c:choose>
				</c:if>
                <!-- Moves parts section to next of scope of work as part of R 12_0 Sprint 5 -->
		        <c:if test="${not empty showParts && showParts =='true'}">
			         <jsp:include page="panel_parts.jsp" />
		        </c:if>
		        <c:choose>
					<c:when test="${showInvoicePartsEdit}">
					     <p onClick="expandInvoiceParts('invoicePartsEdit','invoicePartssummaryEdit','${staticContextPath}')" class="submenu_head" style="width:680px;">&nbsp;<img id="invoicePartsEdit" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
					     <div id="invoicePartssummaryEdit" style="margin-top:5px;margin-left:1px;width:99%;"> 
		                     <jsp:include page="panel_parts_hsr_summary_edit.jsp" />
					     </div>
					 </c:when>
					 <c:when test="${showInvoicePartsView}">
					      <p onClick="expandInvoiceParts('invoicePartsView','invoicePartssummaryView','${staticContextPath}')" style="width:680px;" class="submenu_head">&nbsp;<img id="invoicePartsView" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
					      <div id="invoicePartssummaryView" style="margin-top:5px;margin-left:1px;width:99%;"> 
					            <jsp:include page="panel_parts_hsr_summary_view.jsp" />
					      </div>
					 </c:when>
					  <c:otherwise></c:otherwise>
			    </c:choose>
				<c:if test="${not empty showServiceOrderBids}">
					<jsp:include page="panel_service_order_bids.jsp" />
				</c:if>

				<c:if test="${showBidNotes}">
					<jsp:include page="panel_bid_notes.jsp" />
				</c:if>

				
				<c:if test="${not empty showContactInfo}">
					<jsp:include page="panel_contact_information.jsp" />
				</c:if>
			
				<c:if test="${not empty showDocumentsAndPhotos}">
					 <jsp:include page="${contextPath}/jsp/wizard/body/sections/modules/panels_scope_of_work/panel_documentsAndPhotos.jsp" />
				</c:if>
			    <c:if test="${not empty showPricingBuyer}">
					<jsp:include page="panel_pricing_buyer.jsp" />
				</c:if>
				
				<c:if test="${not empty showPricingProvider && viewOrderPricing=='true'}">
					<jsp:include page="panel_pricing_provider.jsp" />
				</c:if>
				
				<div align="right">
					<a href="#details_top">Back to Top</a>
				</div>

			</div>
			</div>
		
		</c:forEach>
		
			 <!-- so appontment -->
		<c:if test="${not empty summaryDTO.serviceDatetimeSlots && SOStatusCode == statusReceived}">
		  <input type="hidden" id="slotsAvailable" value="yes">
			<jsp:include page="panel_so_appointment.jsp"></jsp:include>
		</c:if>
		
		<c:if test="${not empty showProviderPanel  && SOStatusCode == statusReceived}">
			<jsp:include page="panel_so_assignment.jsp"></jsp:include>
		</c:if>

		<c:if test="${showProviderPanel == null  && SOStatusCode == statusReceived && showAcceptBlock}">
							<jsp:include page="panel_accept_for_dispatcher.jsp"></jsp:include>	
		</c:if>

				<a name="terms_and_condition"></a>
		<div id="showTermsAndConditions" style=<c:if test="${showProviderPanel == true}">"display: none;"</c:if> >	
			<c:if test="${not empty  showTermsAndConditions}">
				<jsp:include page="panel_terms_and_conditions.jsp" />
			</c:if>
		</div>
		
	</c:when>
	<c:otherwise>
		<c:set var="soCounter" value="0" scope="request" />
		<c:if test="${not empty showGeneralInfo}">
			<jsp:include page="panel_general_information.jsp" />
		</c:if>
	
		<c:if test="${not empty showScopeOfWork}">					
			<c:if test="${summaryDTO.priceModel == 'ZERO_PRICE_BID'}">
				<jsp:include page="panel_scope_of_work_zero_price.jsp" />
			</c:if>
			<c:if test="${summaryDTO.priceModel == 'NAME_PRICE'}">
				<jsp:include page="panel_scope_of_work.jsp" />
			</c:if>
		</c:if>
		<!-- Moves parts section to next of scope of work as part of R 12_0 Sprint 5 -->
		<c:if test="${not empty showParts && showParts == 'true'}">
			<jsp:include page="panel_parts.jsp" />
		</c:if>
		
		<div class="menugroup_list">
		  <c:choose>
			<c:when test="${showInvoicePartsEdit}">
			     <p onClick="expandInvoiceParts('invoicePartsEdit','invoicePartssummaryEdit','${staticContextPath}')" class="menugroup_head"">&nbsp;<img id="invoicePartsEdit" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
			     <div class="menugroup_body" id="invoicePartssummaryEdit"> 
                     <jsp:include page="panel_parts_hsr_summary_edit.jsp" />
			     </div>
			 </c:when>
			 <c:when test="${showInvoicePartsView}">
			      <p onClick="expandInvoiceParts('invoicePartsView','invoicePartssummaryView','${staticContextPath}')" class="menugroup_head">&nbsp;<img id="invoicePartsView" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;Invoice Parts</p>
			      <div  class="menugroup_body" id="invoicePartssummaryView"> 
			            <jsp:include page="panel_parts_hsr_summary_view.jsp" />
			      </div>
			 </c:when>
			  <c:otherwise></c:otherwise>
	    </c:choose>
	  </div>
		<c:if test="${not empty showServiceOrderBids}">
			<jsp:include page="panel_service_order_bids.jsp" />
		</c:if>

		<c:if test="${showBidNotes}">
			<jsp:include page="panel_bid_notes.jsp" />
		</c:if>
		
	
		<c:if test="${not empty showContactInfo}">
			<jsp:include page="panel_contact_information.jsp" />
		</c:if>
	
		<c:if test="${not empty showDocumentsAndPhotos}">
   
			 <jsp:include page="../../../../wizard/body/sections/modules/panels_scope_of_work/panel_documentsAndPhotos.jsp" />
		</c:if>
	    
	    <c:if test="${not empty showPricingBuyer}">
			<jsp:include page="panel_pricing_buyer.jsp" />
		</c:if>
		
		<c:if test="${not empty showPricingProvider && viewOrderPricing=='true'}">
			<jsp:include page="panel_pricing_provider.jsp" />
		</c:if>
		
		<c:if test="${not empty summaryDTO.serviceDatetimeSlots  && SOStatusCode == statusReceived}">
		   <input type="hidden" id="slotsAvailable" value="yes">
			<jsp:include page="panel_so_appointment.jsp"></jsp:include>
		</c:if>		

		<c:if test="${not empty showProviderPanel && SOStatusCode == statusReceived}">
			<jsp:include page="panel_so_assignment.jsp"></jsp:include>
		</c:if>
		<c:if test="${empty showProviderPanel  && SOStatusCode == statusReceived && showAcceptBlock}">
							<jsp:include page="panel_accept_for_dispatcher.jsp"></jsp:include>	
		</c:if>
		<a name="terms_and_condition"></a>
		
		<div id="showTermsAndConditions" style=<c:if test="${showProviderPanel == true}">"display: none;"</c:if> >	
			<c:if test='${not empty showPricingProvider && SOStatusCode == statusReceived}'>
				<jsp:include page="panel_terms_and_conditions.jsp" />
			</c:if>
		</div>
		
		<div align="right">
			<a href="javascript:void(0)" onclick="toTop(0,0)">Back to Top</a>
		</div>

	</c:otherwise>
</c:choose>

</div>
</div>

   <script type="text/javascript"> 


var delay=1000;//1 seconds 
    setTimeout(function(){

   goToDocumentsAndPhotos();  
   
   var soState='${THE_SERVICE_ORDER_STATUS_CODE}';

   if('110'==soState){
	   
	   checkProviderPanel();  
   }
 
   goToParts();
    },delay); 
    
    
  

    


</script>
   