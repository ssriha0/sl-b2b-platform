<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html>
<head>
<style type="text/css">

#explainers {display: none; width:220px;height:auto;border: 3px solid #adaaaa; background:#fcfae6; border-radius:10px;
-moz-border-radius:10px; -webkit-border-radius:10px; padding:10px;}

/*Priority 5B changes*/
#modelHeading {background:#000000;color:#FFFFFF;height:25px;padding: 10px;font-family:Verdana;}
#modelClose {float:right;padding:5px;cursor:pointer;}
#modelCancel {height:30px; width:80px;cursor: pointer;font-family:Verdana;}
#modelContinue {height:30px;width:80px;border:1px;margin-left:100px;margin-top:-30px;
				text-transform:none;font-size:12px;font-weight:normal;}
.customRefError {background-color:#FFF8BF;border:1px solid #FFD324;width:670px;padding:5px;display:none;}

</style>

<jsp:directive.page import="com.newco.marketplace.interfaces.OrderConstants"/>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
					
<c:set var="currentSO" scope="request" value="${THE_SERVICE_ORDER}" />	
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
	<jsp:param name="PageName"
		value="ServiceOrderDetails.providerCompletionDocs" />
</jsp:include><!-- START RIGHT SIDE MODULES -->
<link href="${staticContextPath}/javascript/style.css" rel="stylesheet"
	type="text/css" />
<style>   
	
	.simplemodal-container {
	    top: 150px;
	}
</style> 
<script type="text/javascript"
	src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>

<script
	src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"
	type="text/javascript"></script>
<!-- Priority 5B change -->
<script	type="text/javascript" src="${staticContextPath}/javascript/model-serial-validations.js" ></script>
<script type="text/javascript">
   $("#provcomp").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}"); 

</script>
   
   
   	<script>
   	jQuery.noConflict();	
   	// The summing for the Invoice parts will be done by the below function.
	
function calculatInvoicePricingSummary(){
	
	
	var addOnPrice=jQuery(".addOnPricing").text().replace("$", "");;
	var laborNetPrice = jQuery(".laborPricing").text().replace("$", "");;
	var materialNetPrice=jQuery(".partsPricing").text().replace("$", "");;
	var partsNetPrice=jQuery(".partsInPricing").text().replace("$", "");;
	if(laborNetPrice ==''){
		laborNetPrice=0.00; 	
	}
	if(materialNetPrice==''){
		materialNetPrice=0.00;
	}
	if(partsNetPrice==''){
		partsNetPrice=0.00;
	}
	if(addOnPrice==''){
		addOnPrice=0.00;
	}

	var netTotal = parseFloat(laborNetPrice) + parseFloat(materialNetPrice)+parseFloat(partsNetPrice)+parseFloat(addOnPrice);
	if(netTotal == 0.00){
		jQuery('.pricewidget').hide();
	}else{ 
		var net=addOnObject.fmtMoney(netTotal);
		jQuery(".totalServiceOrderPrice").html("$"+addOnObject.fmtMoney(net));
		jQuery(".serviceLiveFee").html("- $"+addOnObject.fmtMoney(net*0.1));
		jQuery(".netPaymentFee").html("$"+addOnObject.fmtMoney(net-net*0.1));
	}
}
   	
 
	function formatPrice(obj)
	{
	if(!isNaN(obj.value) & obj.value != ''){
	          var amt = parseFloat(obj.value);
	             jQuery(obj).val(amt.toFixed(2));
	         }
	    }     
	
	<c:if test="${modelerrors[0] == null}">
	<c:if test="${isAutocloseOn==true}">
	jQuery(document).ready(function ($) {
		setTimeout(function(){},4000);
		   jQuery("#servicecompletion").modal({
                onOpen: modalOpen,
                onClose: modalOnClose,
                persist: true,
                containerCss: ({ width: "600px", height: "auto", marginLeft: "0px"})
            });
		   jQuery.modal.defaults = {
				overlay: 50,
				overlayId: 'modalOverlay',
				overlayCss: {},
				containerId: 'modalContainer',
				containerCss: {top: "150px"},
				close: false,
				closeTitle: 'Close',
				closeClass: 'modalClose',
				persist: false,
				onOpen: null,
				onShow: null,
				onClose: null
			};

			function modalOpen(dialog) {
		            dialog.overlay.fadeIn('fast', function() {
		            	dialog.container.fadeIn('fast', function() {
		            		dialog.data.hide().slideDown('slow');
		            	});
		        	});
		 		}
		  
		   		function modalOnClose(dialog) {
		       		dialog.data.fadeOut('fast', function() {
		            	dialog.container.slideUp('fast', function() {
		            		dialog.overlay.fadeOut('fast', function() {
		            			jQuery.modal.close(); 
		            		});
		          		});
		       		});
		    	}
		
	 });   
    </c:if> 
    </c:if>

      function toggleGeneralinfo()
	{
		var checkboxValue = jQuery("input[name='complete']:checked").val();
		console.log('selected value::'+checkboxValue);
		if (checkboxValue == '2')
		{
		jQuery("#simplemodal-container").css("top","150px");
		jQuery('#SearchValidationError').hide(); 
		jQuery("#continueservice").hide();
		jQuery("#continueincompleteservice").show();
		jQuery("#generalinfo").show(); 
		} 

		if (checkboxValue == '1')
		{ 
		jQuery('#SearchValidationError').hide();
		jQuery("#continueservice").show();
		jQuery("#continueincompleteservice").hide();
		jQuery("#generalinfo").hide(); 
		}  
		
	}
      

      <%request.removeAttribute("modelerrors"); %>
            jQuery(".toolTipShow").click(function(e){
            
           
	
      	var x = e.pageX;
      	var y = e.pageY;
      
      	jQuery("#explainer").css("top",y-340);
			x=616;

      	jQuery("#explainer").css("left",x-70);

      	jQuery("#explainer").css("position","absolute");
      	var explDefId = "Note:Enter the final price that you paid for this permit. Ensure that any additional amount is collected from the customer.";

			x=249;

		if(jQuery(this).attr("id")=="addonServicesDTO.amtAuthorized")
		{

		x=605;

		}
      	if(jQuery(this).attr("id")=="addonServicesDTO.checkAmount"||($(this).attr("id")=="addonServicesDTO.amtAuthorized")){
      		explDefId = "Note:Amount must be the total amount due from the customer.";


      		jQuery("#explainer").css("left",x-50);
      		
      	}if((jQuery(this).attr("id")=="addonServicesDTO.checkNumber")){
      		explDefId = "Note:When processing a check over $200 will need pre - authorization.Your Service Order will close for payment when we recieve a check in the mail.";
      		jQuery("#explainer").css("left",x-50);
      	}if(jQuery(this).attr("id")=="soMaxLabor"){
      		explDefId = "Note:The total amount must be equal to or less than the price shown .Ensure all additional services are added in the form below and money collected from the customer.";
      	}if(jQuery(this).attr("id")=="finalPartPrice"){
      		explDefId = "Note:The total amount must be equal to or less than the price shown .Ensure all additional services are added in the form below and money collected from the customer.";
      	}if(jQuery(this).attr("id")=="soFinalLabourPrice"){
      	     
      		jQuery("#explainer").css("left",570);
      		jQuery("#explainer").css("z-index",10);
      		jQuery("#explainer").css("margin-top",4);
      		explDefId = "Note: The total amount must be equal to or less than the price shown. Ensure all additional services are added in the form below and money collected from the customer.";
      	}
      	if(jQuery(this).attr("id")=="soFinalPartPrice"){
      	     
      		jQuery("#explainer").css("left",595);
      		jQuery("#explainer").css("z-index",10);
      		jQuery("#explainer").css("margin-top",4);
      		explDefId = "Note: The total amount must be equal to or less than the price shown. Ensure all additional services are added in the form below and money collected from the customer.";
      	}
  		var arrow = '<div id="pointerArrow" style="position:absolute;font-size:8pt; float:left; left:-13px; width:16px; height:19px; background: url(/ServiceLiveWebUtil/images/icons/explainerArrow.gif) no-repeat 0 0;" />';
      	jQuery("#explainer").html(arrow + explDefId);
    	jQuery("#explainer").show();          
     
 		}); 
               		 
</script>
  </head>
   <body>
<script>
// var myInvoiceParts;
// myInvoiceParts=setTimeout(function(){invoicePartsShow()},5000);

// Set a time interval
var intervalId = setInterval(function () {
var partsLength=jQuery('table#addtable tr:last').index() + 1; 
console.log("partsLength"+partsLength);
var orderExpressMenuLength = jQuery("#ui-tabs-9 .orderExpressMenu").length;
  		if (partsLength>0 && orderExpressMenuLength>0) {
    			invoicePartsShow(intervalId);
  		}
		}, 50);
function invoicePartsShow(intervalId)
{	
	
	// Showing the table on Page Load
	jQuery("#addtable").show();
	jQuery("#partsforhsr").show();
	jQuery("#installedparts").attr('checked', true);
	//Setting values from the table to the pricing summary widget
	
	 var netTtlPayment=jQuery("#totalPayment").html();
	 var netTotalPayment=netTtlPayment.split(",");
	 netTotalPayment=netTotalPayment[0]+netTotalPayment[1];
	 netTotalPayment=parseFloat(netTotalPayment);
	 
	 var finalTtlPaymentRate=jQuery("#finalTotalPartPrice").html();
	 var finalTotalPaymentRate=finalTtlPaymentRate.split(",");
     finalTotalPaymentRate=finalTotalPaymentRate[0]+finalTotalPaymentRate[1];
	 
	 if(netTotalPayment==''){
	 	netTotalPayment = 0.00;	
	 }
	 if(finalTotalPaymentRate=='' || finalTotalPaymentRate=='undefined'){
	 	finalTotalPaymentRate = 0.00;	 	
	 }
	 finalTotalPaymentRate=parseFloat(finalTotalPaymentRate);	
	 jQuery(".partsInEstPricing").html("$"+formatAsMoney(netTotalPayment));
	 jQuery(".partsInPricing").html("$"+formatAsMoney(finalTotalPaymentRate));
	 calculatInvoicePricingSummary();
     jQuery('.pricewidget').show();
     jQuery('.pVoice').show();
     
     // Clear the time interval
	if(intervalId != null){
	  clearInterval(intervalId);
	}
}


</script>
<div class="soNote">
	  <div id="rightsidemodules" class="colRight255 clearfix">
		<p id="provcomp"
			style="color: #000; font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px;">
			<span> </span>
		</p>
	</div>
	<!-- END RIGHT SIDE MODULES -->
	
	<s:form action="soDetailsCompleteForPayment_completeSo"
		id="frmCompleteForPayment" theme="simple"
		enctype="multipart/form-data" method="POST">
		<div class="contentPane">
		
		<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}" />
		<s:hidden name="w9isExist" id="w9isExist" value="%{#request.w9isExist}" />
		<s:hidden name="w9isDobNotAvailable" id="w9isDobNotAvailable" value="%{#request.w9isDobNotAvailable}" />
		<s:hidden name="completeSOAmount" id="completeSOAmount" value="%{#request.completeSOAmount}" />
		<s:hidden name="providerThreshold" id="providerThreshold" value="%{#request.providerThreshold}" />
		<s:hidden name="w9isExistWithSSNInd" id="w9isExistWithSSNInd" value="%{#request.w9isExistWithSSNInd}" />
		<%--  SL 3768 --%>
		<s:hidden name="addonServicesDTO.responseXML" id="addonServicesDTO.responseXML" value="%{addonServicesDTO.responseXML}" />
		<input type="hidden" name="addonServicesDTO.authToken" id="authToken" value="${authToken}" />
		<input type="hidden" name="addonServicesDTO.tokenLife" id="tokenLife" value="${tokenLife}" />
		<input type="hidden" name="addonServicesDTO.maskedCardNumber" id="maskedCardNumber" value="${addonServicesDTO.maskedCardNumber}" />
		<input type="hidden" name="addonServicesDTO.tokenizeCardNumber" id="tokenizeCardNumber" value="${addonServicesDTO.tokenizeCardNumber}" />
		<input type="hidden" name="addonServicesDTO.responseCode" id="responseCode" value="${responseCode}" />
		<input type="hidden" name="addonServicesDTO.responseMessage" id="responseMessage" value="${responseMessage}" />
		<s:hidden name="creditCardAuthTokenizeUrl" id="addonServicesDTO.CreditCardAuthTokenizeUrl" value = "%{#session.CreditCardAuthTokenizeUrl}" />
		<s:hidden name="creditCardTokenUrl" id="addonServicesDTO.CreditCardTokenUrl" value = "%{#session.CreditCardTokenUrl}" />
		<s:hidden name="creditCardTokenAPICrndl" id="addonServicesDTO.CreditCardTokenAPICrndl" value = "%{#session.CreditCardTokenAPICrndl}" />
		<s:hidden name="userName" id="addonServicesDTO.userName" value = "%{#session.userName}" />
		<s:hidden name="creditCardAuthTokenizeXapikey" id="addonServicesDTO.CreditCardAuthTokenizeXapikey" value = "%{#session.CreditCardAuthTokenizeXapikey}" />
		<s:hidden name="addonServicesDTO.sowErrFieldId" id="addonServicesDTO.sowErrFieldId" value="%{addonServicesDTO.sowErrFieldId}" />
		<s:hidden name="addonServicesDTO.sowErrMsg" id="addonServicesDTO.sowErrMsg" value="%{addonServicesDTO.sowErrMsg}" />
		
		<%--  Display Validation error messages here --%>
			<%-- 
	<jsp:include page="/jsp/details/body/html_sections/modules/detailsValidationMessages.jsp" />
	--%>
			<c:set var="addonError" value="<%=OrderConstants.ADDON_COMPLETE_ERROR%>" />
			<%-- TODO - HACK!!! get the errors from the model and use the validation jsp commented out above or something similar --%>
			<c:if test="${soCompleteDto.errors[0] != null}">
				<div class="errorBox">
					<c:forEach items="${soCompleteDto.errors}" var="error">
						<p class="errorMsg">
							<c:choose>
								<c:when test="${error.fieldId == ''}">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${error.msg}</c:when>
								<c:otherwise>&nbsp;&nbsp;&nbsp;&nbsp;${error.fieldId} - ${error.msg}</c:otherwise>
							</c:choose>
						</p>
					</c:forEach>
				</div>
			</c:if>
			<c:choose>
			  <c:when test="${msg=='ERROR IN TOKENIZATION'}"></c:when>
			  <c:when test="${msg == addonError}"></c:when>
			  <c:otherwise>
			     <div style="color: blue">
				   <p style="padding: 10px;">${msg}</p>
				     <%request.setAttribute("msg", "");%>
			     </div>
			  </c:otherwise> 
			</c:choose>
		<p>
				An asterisk (
				<span class="req">*</span> ) indicates a required field
			</p>
			<p>
				Your service is complete and you're ready to get paid. Upload
				photographs and other relevant documents, confirm your final billing
				amount and submit the order for payment. After the buyer has
				reviewed and approved this information the order will be closed and
				payment will be transferred to your account.
			</p>

			<jsp:include
				page="/jsp/details/body/html_sections/modules/summary_tab/panel_general_completion_info_new.jsp" />
			
			<!-- SLM 119 : Display parts if so parts exists for the order -->
			<c:if test="${partCount > 0 }">
			   <jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/panel_parts.jsp" />
			</c:if>
			<jsp:include
				page="/jsp/details/body/html_sections/modules/summary_tab/panel_custom_references.jsp" />
			<c:set var="docSource" scope="request" value="<%=(OrderConstants.COMPLETE_FOR_PAYMENT) %>" />	
			<jsp:include
				page="../../../../wizard/body/sections/modules/panels_scope_of_work/panel_documentsAndPhotos.jsp" />
		</div>
	</s:form>
<div>
</div>
<div id="servicecompletion" class="menugroup_list" style="display: none;border: 0px;">



	<div class="menugroup_body" id="generalcompletionId">
		<div class="modalHomepage" style="background-color: black;" >
			<div style="float: left;">
				Service Completion Confirmation 
			</br>
			</div>
			<a id="closeLink" class="btnBevel simplemodal-close" href="javascript:void(0)" style="color:white;"
				float:left>Close</a>
		</div>
		
		<form action="soDetailsCompleteForPayment_completeSo"  name="frmCompleteForPaymentServiceIncomplete"
			id="frmCompleteForPaymentServiceIncomplete" theme="simple" method="POST">
			
			<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}" />
			<div style="color: blue">
				<p style="padding: 10px;">
					${msg}
				</p>
				<%
					request.setAttribute("msg", "");
				%>
			</div>
			<div id="SearchValidationError" class="error errorMsg errorMessage" style="background-color:#FBC2C4; display: none;">
				<p class="errorText"></p>
			</div>
			
			<c:if test="${currentSO.subStatusString != 'Cancellation Request'}">
			<p>
			<b>&nbsp;Select the appropriate option below. </b> <br><br/>
			&nbsp;&nbsp;&nbsp;	<td colspan="1"><input type="radio" name="complete" value="1" checked="true" onchange="toggleGeneralinfo();" />Yes, service was completed. </td><p></p>
			&nbsp;&nbsp;&nbsp;	<td colspan="1"><input type="radio" name="complete" value="2" onchange="toggleGeneralinfo()"/>No, service was not completed or was cancelled.</td><p></p>
			
			</c:if>
			<c:if test="${currentSO.subStatusString == 'Cancellation Request'}">
				<p>
				&nbsp;&nbsp;&nbsp;	Buyer has requested cancellation. Please complete the information
					below and continue.
					<br />
					<input type="hidden" name="cancellation" id="cancellation"
						value="3" />
				</p>
			</c:if>
			<div id="generalinfo" style="display: none;">

				<c:set var="partCnt" value="${partCount}" />
				<s:set name="partsSpendLimit" value="%{partSpLimit}" />
				<s:set name="laborSpendLimit" value="%{laborSpLimit}" />
				<c:set var="partSpLimit" value="${partsSpendLimit}" />
				<c:set var="laborSpLimit" value="${laborSpendLimit}" />

				<p>
					
				&nbsp;&nbsp;&nbsp;	An asterisk(
					<span class="req">*</span>)indicates a required field.
					<br/>
					<c:if test="${assignmentType == 'FIRM'}">
					    <p>
						  &nbsp;&nbsp;&nbsp;&nbsp;  Service order is currently assigned to Firm ID # ${model.entityId}.Select the service provider who completed the <span style="padding-left:15px;">service.</span><span class="req">*</span>
						  <p></p>
						   &nbsp;&nbsp;&nbsp;&nbsp;  <select name ="acceptedProviderId" id="acceptedProIdIncomplete">
						    <option selected="selected" value="-1">-Select One-</option>
						    <c:if test="${!empty model.providerList}">
							    <c:forEach var="routedResource" items="${model.providerList}" varStatus="rowCounter" >
								    <option value="${routedResource.resourceId}">
								    	${routedResource.providerLastName} ${routedResource.providerFirstName} ( ${routedResource.distanceFromBuyer} miles)
								    </option>
								</c:forEach>
							</c:if>
							</select>
	   					 </p>
   					 </c:if>
				&nbsp;&nbsp;&nbsp;	In the space provided, enter any comments that you feel are
					important for the resolution of this order.
				<p></p>
				
					<tr>
	<input type="hidden" name="taskLevelPricing" id="taskLevelPricing" value="${model.taskLevelPricing}"/>

						<td colspan="2">
					&nbsp;&nbsp;&nbsp;		<b>Resolution Comments</b>
							<span class="req">*</span>
							<br />
							<%-- Fixing Sears00051299: Resolution Comments - Truncated (Mark J. 5/13/08) --%>

<p></p>
						&nbsp;&nbsp;&nbsp;	<s:textarea name="incompleteresComments" id="resCommentsincomplete"
								cssStyle="width: 525px;" value="%{resComments}"
								onkeydown="fnCountAreaCharsServiceIncomplete('resCommentsincomplete','resCommentsincomplete_leftChars',998,event);"
								onkeyup="fnCountAreaCharsServiceIncomplete('resCommentsincomplete','resCommentsincomplete_leftChars',998,event);"
								onfocus="clearPrice();" >
							</s:textarea>
							
							</td>
							<p></p>
							<td colspan="2">
						&nbsp;&nbsp;&nbsp;	<s:textfield name="resComments_leftChars"
								id="resCommentsincomplete_leftChars" readonly="true" size="4"
								maxlength="4" />
							Characters remaining<p></p>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<hr style="margin-left:13px;width:95%;"/>
						</td>
					</tr>

					<p></p>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Enter Final Price </b>
							<span class="req">*</span><p></p>
					<tr>
					
						<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>	Maximum Labor:</b> $ 
							&nbsp;<fmt:formatNumber value="${soInitialMaxLabor}" type="NUMBER"
					minFractionDigits="2" maxFractionDigits="2" />
						</td>
						<input type="hidden" name="laborSl" id="laborSl"
							value='<fmt:formatNumber value="${laborSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />

						<td align="right">
					<s:hidden name="incompleteService" id="incompleteService" value="1"></s:hidden>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$&nbsp;<s:textfield
								cssStyle="width: 50px; float: center; margin-right: 50px; font-size: 10px; height:10px;"
								cssClass="text toolTipShowIncomplete" name="incompletesoMaxLabor" id="incompletesoMaxLabor"
								value="%{finalLaborPrice}" onfocus="clearPrice();"  onchange="hideToolTip();" onblur="formatPrice(this);hideToolTip();addPrice();"/>
								<div id="explainers"></div>
					<s:hidden name="finalLaborPrice" id="finalLaborPrice" value="%{finalLaborPrice}"></s:hidden>
					<s:hidden name="incompletesoInitialMaxLabor" id="incompletesoInitialMaxLabor" value="%{soInitialMaxLabor}"></s:hidden>
						
									<s:hidden name="autoclose" id="autoclose" value="2"></s:hidden>
									</td>
							</tr>
						<tr>
						<p></p>
						<td align="left">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>	Maximum Materials:</b>  $ 
							&nbsp;<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
						</td>
						
					<c:set var="partsPrice" value="${partsSpendLimit}"/>
						<input type="hidden" name="partsSl" id="partsSl"
							value='<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />

					<td align="left">
<c:if test="${partsSpendLimit != 0.00}">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$&nbsp;<s:textfield
								cssStyle="width: 50px; float: center; margin-right: 50px; font-size: 10px; height:10px;"
								cssClass="text toolTipShowIncomplete" name="incompletefinalPartPrice" id="incompletefinalPartPrice"
								value="" onchange="hideToolTip();" onfocus="clearPrice();"onblur="formatPrice(this);hideToolTip();addPrice();"/>

</c:if>

<c:if test="${partsSpendLimit == 0.00}">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0.00
<input type="hidden"					 name="incompletefinalPartPrice" id="incompletefinalPartPrice"
								value="${partsSpendLimit}" /> 


</c:if>

						</td>
					</tr>
					<c:if test="${prepaidPermitPrice != 0.00}">
					<tr>
					<p></p>
<td align="left">
						<b>	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Permits </b>(Customer Pre-paid):  $ 
							&nbsp;<fmt:formatNumber value="${prepaidPermitPrice}" type="NUMBER"
								minFractionDigits="2" maxFractionDigits="2" />
						</td> 
</tr>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;$ &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;0.00
</c:if>
<p></p>	
<hr style="width:347px;align:left;float:left;margin-left:13px;">
				<p></p>	
					<tr>
						<td align="center">
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Total Final Price:
							
							 $
							  <s:label 
								 name="Price" id="Price"
								 />
								 
								 
						</td>

					</tr><p></p>
					<!-- end -->
				
			</div>
			<div id="continueservice" style="display:none">
			<input type="image"
				src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="22"
				style="background-image: url(${staticContextPath}/images/btn/continue.gif); float: right;"
				class="btnBevel simplemodal-close" onclick="return false;" />
			</div>


<div id="continueincompleteservice" style="display:none">
			<!--  <input type="image"
				src="${staticContextPath}/images/common/spacer.gif" width="72"
				height="22"
				style="background-image: url(${staticContextPath}/images/btn/continue.gif); float: right;"
				class="btnBevel" onclick="serviceIncomplete(this); $.modal.impl.close(true);" /> -->
				
				<img src="${staticContextPath}/images/common/spacer.gif" width="72"
					height="22"
					style=" background-image: url(${staticContextPath}/images/btn/continue.gif); float: right;"
					class="btnBevel" onclick="serviceIncomplete(this);" />
</div>
			<a id="cancelLink" class="btnBevel simplemodal-close" href="javascript:void(0)" onclick="goToSummary(this);"
				float:left>Cancel</a>

		</form>
		
	</div>
</div>
	<table>
		<tr>
		  <div id="submitDiv" class="clearfix buttnNav" style="display: block;">
				
				<c:choose>
					<c:when test="${'3000' == currentSO.buyerID}">
						<img src="${staticContextPath}/images/common/spacer.gif" width="125"
							height="30"
							style="cursor: pointer; height: 30px; background-image: url(${staticContextPath}/images/btn/submitForPayment.gif);"
							class="inlineBtn" onclick="showModelSerialConfirm();" />
					</c:when>
					<c:otherwise>
						<img src="${staticContextPath}/images/common/spacer.gif" width="125"
							height="30"
							style="cursor: pointer; height: 30px; background-image: url(${staticContextPath}/images/btn/submitForPayment.gif);"
							class="inlineBtn" onclick="setPermitInd();pop_w9modal();" />
					</c:otherwise>
				</c:choose>

		  </div>
		  <div id="disabledSubmitDiv" style="display: none;width: 250px;">
			Submitting for payment ....
		  </div>

		</tr>
	</table>

		<div id="fillWithW9_ceg">
		</div>


	<input type="hidden" name="formW9" id="formW9" value="complete" />
	<div class="jqmWindow2" id="w9modal">
		<div id="fillWithW9"></div>
	</div>
</div>

<!-- Priority 5B changes -->
<!-- Show the model & serial no confirmation pop up on Submitting -->
	<div id="modelSerialConfirm" style="display:none;">
	
		<div id="modelHeading">
			<b>Invalid Model or Serial Number</b>
			
			<img id="modelClose" onclick="closeConfirmation();" 
				src="${staticContextPath}/images/widgets/tabClose.png">
			
		</div>
		
		<div id="continueError" class="errorBox" style="padding:5px;display:none;">
			<span class="errorMsg">Please check 'Yes' to Continue</span>
		</div>
		
		<div style="padding:10px">	
			<p></p>
			<p>There are issues with your model or serial number that you entered. 
			Please cancel and re-enter the model and serial number or continue with this data.</p>		
			<p></p>		
			<p>To complete this order with these values, click "Continue". This may delay closure of this order</p>		
			<p></p>			
			<input id="continueYes" type="checkbox" style="cursor: pointer;" onclick="hideError();">
			<span style="margin-left:5px;">Yes. Complete this order with the Model and Serial Numbers as entered.</span> <br>
			<p></p><br>
		</div>
		
		<div style="background-color:#eeeeee;height:45px;">
			<div style="margin-left:300px;padding:10px;">
				<input id="modelCancel" type="button" value="Cancel" onclick="closeConfirmation();">
				<input id="modelContinue" type="button" class="button action" value="Continue" onclick="validateYes();">
			</div>
		</div>
		
	</div>
	
</body>
</html>