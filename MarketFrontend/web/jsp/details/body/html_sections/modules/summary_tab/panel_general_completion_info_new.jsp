<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery.decimalMask.1.0.0.js"></script>
		
 <script type="text/javascript">

  jQuery('#finalPartPrice').decimalMask();
  jQuery('#finalLaborPrice').decimalMask();
  jQuery('#soMaxPart').decimalMask();
  
  

 function setLabourPrice(thisElement){
document.getElementById("finalLaborPrice").value=thisElement.value;
}
  
   function hideToolTip(){

  
  jQuery("#explainer").hide();
  jQuery("#explainers").hide();
   }
   function clearAmount(){
   if(document.getElementById("addonServicesDTO.checkAmount")){
   document.getElementById("addonServicesDTO.checkAmount").value="";
   }
   if(document.getElementById("addonServicesDTO.amtAuthorized")){
   document.getElementById("addonServicesDTO.amtAuthorized").value="";
   }
   }
function expandCompGen(path)
{
jQuery("#generalcompletion p.menugroup_head").css({backgroundImage:"url("+path+"/images/widgets/titleBarBg.gif)"}).next("#generalcompletionId").slideToggle(300);

var ob=document.getElementById('compGenImg').src;
if(ob.indexOf('arrowRight')!=-1){
document.getElementById('compGenImg').src=path+"/images/widgets/arrowDown.gif";
}
if(ob.indexOf('arrowDown')!=-1){
document.getElementById('compGenImg').src=path+"/images/widgets/arrowRight.gif";
}
}
</script>

<script type="text/javascript">
jQuery("#finalLaborPriceComplete").val('');
jQuery("#finalPartPriceComplete").val('');
jQuery("#Price").text('0.00');
jQuery("#incompletesoMaxLabor").val('');

jQuery("#resCommentsincomplete").val('');
function addPrice()
{

var labor= jQuery("#incompletesoMaxLabor").val();

 var parts=jQuery("#incompletefinalPartPrice").val();

 
 var a=1.00*labor+1.00*parts;
 
a=formatMoney(a);
if(a==0)
{

a='0.00';
}
  jQuery("#Price").text(a);
}

jQuery('#incompletesoMaxLabor').decimalMask();
  jQuery('#incompletefinalPartPrice').decimalMask();
  
  jQuery(".toolTipShowIncomplete").click(function(e){
      var x = e.pageX;
    	var y = e.pageY;
    	x=589;
    	jQuery("#explainer").css("top",y-330);
    	jQuery("#explainer").css("left",x+30);

    	jQuery("#explainer").css("position","absolute");
    	var explDefId = "Note: The total amount must be equal to or less than the price shown.";
    	jQuery("#explainer").css("left",x-105);
    	jQuery("#explainer").css("left",x+30);
		jQuery("#explainer").css("z-index",9999999);
    	var arrow = '<div id="pointerArrow" style="position:absolute;font-size:8pt; float:left; left:-13px;width:16px; height:19px; background: url(/ServiceLiveWebUtil/images/icons/explainerArrow.gif) no-repeat 0 0;" />';
    	jQuery("#explainer").html(arrow + explDefId);
    	jQuery("#explainer").show();
   });
  
function clearPrice()
{
jQuery("#Price").text('0.00');
}
function formatMoney(mnt) 
      {
            if (mnt == 0){
                  return mnt;
            }
          mnt -= 0;
          mnt = (Math.round(mnt*100))/100;
          var x = (mnt == Math.floor(mnt)) ? mnt + '.00' 
                    : ( (mnt*10 == Math.floor(mnt*10)) ? 
                             mnt + '0' : mnt);
                             
          if( x > 0 )
            return x;
          return 0;
      }
function countAreaCharsServiceIncomplete(areaNameObj,counterObj,limit, evnt)
{
		if (areaNameObj.value.length>limit) {
      		areaNameObj.value=areaNameObj.value.substring(0,limit);
            alert("The field limit is " + limit + " characters.");
         	//Stop all further events generated (Event Bubble) for the characters user has already typed in .
            //For IE
            if (!evnt) var evnt = window.event;
            evnt.cancelBubble = true;
            //For FireFox
            if (evnt.stopPropagation) evnt.stopPropagation();
      }
      else{
			 counterObj.value = limit - areaNameObj.value.length;            
            }
} 

function officeMaxValidation(areaNameObj){
	var buyerId=document.getElementById("officeMaxBuyerID").value;
	if(buyerId == "1953"){
		
		if (areaNameObj.value.length <= 25) {
			alert("Resolution comments should be greater than 25 characters");
		}
	}	
}
function fnCountAreaCharsServiceIncomplete(areaNameObj,counterObj,limit, evnt)
{
	
	var areaNameObjValue= jQuery('#'+areaNameObj).val();  
	
		if (areaNameObjValue.length>limit) {
      		var value=areaNameObjValue.substring(0,limit);
      		 jQuery('#'+areaNameObj).val(value);
            alert("The field limit is " + limit + " characters.");
         	//Stop all further events generated (Event Bubble) for the characters user has already typed in .
            //For IE
            if (!evnt) var evnt = window.event;
            evnt.cancelBubble = true;
            //For FireFox
            if (evnt.stopPropagation) evnt.stopPropagation();
      }
      else{
    	 var val= jQuery('#'+areaNameObj).val(); 
			var counterObjvalue = limit - val.length;
			
			 jQuery('#'+counterObj).val(counterObjvalue);
            }
}

     function goToSummary(){
					  document.getElementById("frmCompleteForPaymentServiceIncomplete").method = "post";                              
                      document.getElementById("frmCompleteForPaymentServiceIncomplete").action = "${contextPath}/soDetailsController.action?soId=${soId}&defaultTab=Complete%20for%20Payment&displayTab=Today#ui-tabs-3";
                      document.getElementById("frmCompleteForPaymentServiceIncomplete").submit();
                 }
                 
     function serviceIncomplete(){
		var resolution="&nbsp;&nbsp;&nbsp;&nbsp;Please enter resolution comments and submit.<p></p>";
		var maxPrice="&nbsp;&nbsp;&nbsp;&nbsp;Final Labor price cannot be more than Maximum Price for Labor.<p></p>";
		var maxPartsPrice="&nbsp;&nbsp;&nbsp;&nbsp;Final Parts price cannot be more than Maximum Price for Materials.<p></p>";
		var labor="&nbsp;&nbsp;&nbsp;&nbsp;Please enter a final price for labor.<p></p>"; 
		var material="&nbsp;&nbsp;&nbsp;&nbsp;Please enter a final price for materials.<p></p>";
		var assignProvider ="&nbsp;&nbsp;&nbsp;&nbsp;Please select provider.<p></p>";
		var errormsg="";
		var error=0;
		var assignmentType = '${assignmentType}';
		var providerId = jQuery('#acceptedProIdIncomplete option:selected').val();
		if("FIRM" == assignmentType && (-1 == providerId || undefined==providerId)){
			error=1;
			errormsg=errormsg+assignProvider;
		}
		var textId = '#resCommentsincomplete';
		var textValue = jQuery(textId).val();
		textValue=jQuery.trim(textValue);
		var text = escape(textValue);
		if(text=="")
		{
			error=1;
			errormsg=errormsg+resolution;
		}
		var laborPriceId = '#incompletesoInitialMaxLabor';
		var laborPriceValue = jQuery(laborPriceId).val();
		
		var solaborPriceId = '#incompletesoMaxLabor';
		var solaborPriceValue = jQuery(solaborPriceId).val();
		solaborPriceValue=jQuery.trim(solaborPriceValue);
		solaborPriceValue = escape(solaborPriceValue);


		if( solaborPriceValue == "" )
		{
		  	error=1;
			errormsg=errormsg+labor;               
		}               
            
		solaborPriceValue=solaborPriceValue*1.00;

		if(laborPriceValue < solaborPriceValue)
		{
			error=1;
			errormsg=errormsg+maxPrice;
		}
 
		 //
		var partsPriceId = '#partsSl';
		var partsPriceValue = jQuery(partsPriceId).val();
		
		var sopartsPriceId = '#incompletefinalPartPrice';
		var sopartsPriceValue = jQuery(sopartsPriceId).val();
		sopartsPriceValue=jQuery.trim(sopartsPriceValue);
		sopartsPriceValue = escape(sopartsPriceValue);
		
		if( sopartsPriceValue == "" )
		{
		   	error=1;
			errormsg=errormsg+material;               
		}
		sopartsPriceValue=sopartsPriceValue*1.00;
		
		if(partsPriceValue < sopartsPriceValue)
		{
			error=1;
			errormsg=errormsg+maxPartsPrice;
		}
		 //
		if(error==1)
		{
		
			jQuery('#SearchValidationError > .errorText').html(errormsg);
			jQuery('#SearchValidationError').show();
		}
		else
		{
		 	$.modal.impl.close(true);
		 	pop_w9modals();
		}                          
     }
    //	code changes for SLT-2572 
     function validationForMaxPrice(){
    	 jQuery('#MaxPriceError').hide();
		 var maxPrice="&nbsp;&nbsp;&nbsp;&nbsp;Final Labor price cannot be more than Maximum Price for Labor.<p></p>";
		 var  tempLaborPrice = document.getElementById("laborSl").value * 1.0;
		 var  tempMaxPrice = document.getElementById("soMaxLabor").value * 1.0;
		 if(tempLaborPrice < tempMaxPrice ){
			 jQuery('#MaxPriceError > .validErrorText').html(maxPrice);
			 jQuery('#MaxPriceError').show();
		 }
	 }
     
     
       function pop_w9modals()
		{
			jQuery("#autoclose").val('serviceincomplete');

			//alert(jQuery('#fillWithW9'));
			jQuery('#w9modal > #fillWithW9').load("w9registrationAction_isW9exist.action", function() {
				//alert('inside2' + jQuery('#w9modal > #fillWithW9'));
				
				var finalTotalLabel=document.getElementById("Price").innerHTML;
				while(finalTotalLabel.charAt(0) == '$') 
				{
				finalTotalLabel=finalTotalLabel.substr(1);
				}
				
				var completeSOAmount=document.getElementById('completeSOAmount').value ;
				var providerThreshold=document.getElementById('providerThreshold').value ;
				
				if(completeSOAmount=='' || completeSOAmount==null)
				{
				completeSOAmount=0;
				}
				if(finalTotalLabel=='' || finalTotalLabel==null)
				{
				finalTotalLabel=0;
				}
				totalAmount=1.00*finalTotalLabel+1.00*completeSOAmount;
 
			totalAmount=formatMoney(totalAmount);
				
				
			
			var w9isDobNotAvailable= document.getElementById('w9isDobNotAvailable').value ;
			if(w9isDobNotAvailable=='' || w9isDobNotAvailable==null)
			{
			w9isDobNotAvailable=false;
			}
			
			var a=document.getElementById('w9isExist').value;
			var b=document.getElementById('w9isExistWithSSNInd').value;
			
		/*	alert('w9isDobNotAvailable  '+w9isDobNotAvailable);
			alert('w9isExist '+a);
			alert('w9isExistWithSSNInd   '+b);
			alert('completeSOAmount   '+completeSOAmount);
			alert('providerThreshold   '+providerThreshold);
			alert('totalAmount '+totalAmount);*/
			
				if(document.getElementById('w9isExist').value == "false" || document.getElementById('w9isExistWithSSNInd').value == "false"
				
				||(providerThreshold!='' && totalAmount>providerThreshold && w9isDobNotAvailable == "true"))
				{
			
				jQuery("#w9modal").modal({
                		onOpen: modalOpenAddCustomer,
                		onClose: modalOnClose,
                		persist: false,
                		containerCss: ({ width: "800px", height: "650px", marginLeft: "-350px",marginBottom: "200",overflow:"auto"})
            		});
            		window.scrollTo(1,1);
           			jQuery("#customerResponse").fadeOut(5000);
           			
				
				}
				
				else if (document.getElementById('w9isExist').value == "true" && document.getElementById('w9isExistWithSSNInd').value == "true")
				{
				document.getElementById("frmCompleteForPaymentServiceIncomplete").method = "post";                              
                document.getElementById("frmCompleteForPaymentServiceIncomplete").action = "${contextPath}/soDetailsCompleteForPayment_completeSo.action?autoclose=true";
                document.getElementById("frmCompleteForPaymentServiceIncomplete").submit();
				}
				else
				{
				
				}	
			});
		}  
  jQuery("#continueservice").show();
  jQuery("#generalinfo").hide();
  			 
		     jQuery("input[name='complete']:checked").val('1');
		     if (jQuery("#cancellation").val() == '3')
		     {
		     
		     jQuery("#generalinfo").show();
		    jQuery("#continueservice").hide();
        jQuery("#continueincompleteservice").show();
		   }
		     jQuery("input[name='complete']:checked").click(function(){
		  if (jQuery("input[name='complete']:checked").val() == '2')
    	{
        jQuery('#SearchValidationError').hide(); 
        jQuery("#continueservice").hide();
        jQuery("#continueincompleteservice").show();
        jQuery("#generalinfo").show(); 
       	} 
		 if (jQuery("input[name='complete']:checked").val() == '1')
       { 
	jQuery('#SearchValidationError').hide();
       jQuery("#continueservice").show();
        jQuery("#continueincompleteservice").hide();
       jQuery("#generalinfo").hide(); 
        }  
		 });
		    
		    jQuery('#routedProviders_general_info').change(function (){
		    	providerId = jQuery(this).attr('value');
		    	jQuery('#acceptedProviderId').val(providerId);
		    });
		  </script>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	<div id="generalcompletion" class="menugroup_list">
  		<p class="menugroup_head" onclick="expandCompGen('${staticContextPath}')">&nbsp;<img id="compGenImg" src="${staticContextPath}/images/widgets/arrowDown.gif"/>&nbsp;General Completion Information and Pricing</p>
    	<div class="menugroup_body" id="generalcompletionId">
    	<c:if test="${assignmentType == 'FIRM'}">
    		<p>
	   			Service order is currently assigned to Firm ID # ${model.entityId}.Select the service provider who completed the service.<span class="req">*</span>
	    		<select name ="routedProviders_general_info" id= "routedProviders_general_info">
		   			<option selected="selected" value="-1">-Select One-</option>
		    		<c:if test="${!empty model.providerList}">
			    		<c:forEach var="routedResource" items="${model.providerList}" varStatus="rowCounter" >
				    		<option value="${routedResource.resourceId}">
				    			${routedResource.providerLastName} ${routedResource.providerFirstName} ( ${routedResource.distanceFromBuyer} miles) 
				    		</option>
						</c:forEach>
					</c:if>
				</select>
				<input type="hidden" id ="acceptedProviderId" name ="acceptedProviderId"/>
    		</p>
    	</c:if>
		<p>
			In the space provided, enter any comments that you feel are important
			for the resolution of this order. If this service order was based on
			an hourly rate, enter your hours and calculate your fee.
		</p>
		<c:set var="partCnt" value="${partCount}" />
		<s:hidden name="depositionCode" id="depositionCode"
		value="%{model.selectedDepositionCode}"></s:hidden>
		<c:if test="${!empty model.depositionCodes}">
		<table>
  			<tr>
   				<td>Disposition codes : </td>
   				<td>
   				<select name="model.selectedDepositionCode" id="depositionCodeSelect">
   					<option value="-1" selected="selected">
   						--- selecte disposition code if applicable ---
    				</option>
					<c:forEach var="depositionCodeDTO" items="${model.depositionCodes}" varStatus="rowCounter" >
		    		<option value="${depositionCodeDTO.depositionCode}">
		    			${depositionCodeDTO.depositionCode} ${depositionCodeDTO.descr} 
		    		</option>
		    		</c:forEach>
				</select>
				</td>
  			</tr>
		</table>
		</c:if>		
		<s:set name="partsSpendLimit" value="%{partSpLimit}" />
		<s:set name="laborSpendLimit" value="%{laborSpLimit}" />
		<c:set var="partSpLimit" value="${partsSpendLimit}"/>
		<c:set var="laborSpLimit" value="${laborSpendLimit}"/>
		
		
		<s:set name="partsTaxPercentage" value="%{partsTaxPercentage}" />
		<s:set name="laborTaxPercentage" value="%{laborTaxPercentage}" />
		<c:set var="partsTaxPer" value="${partsTaxPercentage}"/>
		<c:set var="laborTaxPer" value="${laborTaxPercentage}"/>
		<input type="hidden" name="partsTaxPer" id="partsTaxPer" value="${partsTaxPercentage}">
		<input type="hidden" name="laborTaxPer" id="laborTaxPer" value="${laborTaxPercentage}"/>
		
		<s:set name="partsSpLimitTax" value="%{partsSpLimitTax}" />
		<s:set name="laborSpLimitTax" value="%{laborSpLimitTax}" />
		<c:set var="partsSpendLimitTax" value="${partsSpLimitTax}"/>
		<c:set var="laborSpendLimitTax" value="${laborSpLimitTax}"/>
		
		<input type="hidden" name="displayTax" id="displayTax" value="${displayTax}"/>
		
		<s:hidden name="hidEndCustomerAddOnSubtotalTotal" id="hidEndCustomerAddOnSubtotalTotal" value="%{addonServicesDTO.endCustomerSubtotalTotal}"/>
		<input type="hidden" name="taskLevelPricing" id="taskLevelPricing" value="${model.taskLevelPricing}"/>
		<input type="hidden" name="permitWarningStatusInd" id="permitWarningStatusInd" value="${model.permitWarningStatusInd}">
		<input type="hidden" name="officeMaxBuyerID" id="officeMaxBuyerID" value="${currentSO.buyerID}"/>
		<table width="600" align="center">
			<tr>
				<td colspan="2">
					Resolution Comments
					<span class="req">*</span>
					<br />
					<%-- Fixing Sears00051299: Resolution Comments - Truncated (Mark J. 5/13/08) --%>
					<s:textarea name="resComments" id="resComments" cssStyle="width: 627px;" value="%{resComments}"
						onkeydown="countAreaCharsServiceIncomplete(this.form.resComments, this.form.resComments_leftChars, 998, event);"
						onkeyup="countAreaCharsServiceIncomplete(this.form.resComments, this.form.resComments_leftChars, 998, event);"
						onblur="officeMaxValidation(this.form.resComments);">
					</s:textarea>					
					<s:textfield name="resComments_leftChars"  id="resComments_leftChars" value="998"
						readonly="true" size="4" maxlength="4" />
					Characters remaining
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<hr />
				</td>
			</tr>
		
		</table>
		
		<%-- %>
		<c:if test="${model.taskLevelPricing}">
		Enter the final labor pricing for any Labor, Materials or Customer Pre-paid Permits. If there were any additional 
		<br/>
		add-on items or permits, check the option below.
		<br/>
		<br/>
		Customer Charge Amount must be collected from the customer.
		<br/>
		
		(<span class="req">*</span> indicates a required field)
		
		</c:if>
		<c:if test="${!model.taskLevelPricing}">
		Enter the final pricing for any Labor or Materials.
		<br/>
		(<span class="req">*</span> indicates a required field)
		</c:if>
		--%>
		
		<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
			<tr>
				<th colspan="5" style="text-align: left;">Service Order Pricing</th>
					</tr>
					<c:if test="${currentSO.buyerID != '3000'}">
							<tr>	
								
								
								<td colspan="3" style="text-align:right;"><b>Maximum Price</b></td>
								<td style="width:18%;text-align:right;"><b>Final Price</b><span class="req">*</span></td>
								
								<c:choose>
								<c:when test="${!empty model.permitTaskList}">
								<td style="width:15%;text-align:right;"><b>Customer<br/>&nbsp;&nbsp;Charge</b></td>
								</c:when>
								<c:otherwise>
								<td style="width:15%;text-align:right;"></td>
								</c:otherwise>
								</c:choose>
							</tr>	
							
							<tr>	
								<td colspan="2" style="text-align:left;">Labor</td>
								<td style="width:12%;text-align:right;">
									$<fmt:formatNumber value="${soInitialMaxLabor}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									<input type="hidden" name="laborSl" id="laborSl"
										value='<fmt:formatNumber value="${laborSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' /></td></td>
								<td style="width:18%;text-align:right;">
									<c:choose>				
										<c:when test="${soCompleteDto.errors[0] != null ||editCompletion=='editCompletion'}">
											<c:choose>
											<c:when test="${nonFunded==true}">
											
											$0.00
											<input type="hidden" name="soMaxLabor" id="soMaxLabor" value="0.00"/>
											</c:when>
											<c:otherwise>
											
											$<input name="soMaxLabor" id="soMaxLabor"
												value='<fmt:formatNumber value="${soCompleteDto.soMaxLabor}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' 
												style="width: 54px;  text-align:right; font-size: 10px; font-family: verdana;"
												class="text toolTipShow"
												onblur="hideToolTip()" onchange="validationForMaxPrice();hideToolTip();addOnObject.findTotals(this);"/>
											
											</c:otherwise>
											</c:choose>	
										</c:when>
										<c:otherwise>
										<c:choose>
										<c:when test="${finalLaborPriceNew !=null}">
										<c:choose>
										<c:when test="${nonFunded==true}">
										
										$0.00
											<input type="hidden" name="soMaxLabor" id="soMaxLabor" value="0.00"/>
										</c:when>
								
										<c:otherwise>
										
										$<s:textfield 
												cssStyle="width: 54px;  text-align:right; font-size: 10px; font-family: verdana;" 
												cssClass="text toolTipShow"  value="%{finalLaborPriceNew}" name="soMaxLabor" id="soMaxLabor"  onblur="hideToolTip()" onchange="validationForMaxPrice();hideToolTip();addOnObject.findTotals(this);"/>
										
										
										</c:otherwise>
										</c:choose>
										</c:when>
										<c:otherwise>
										<c:choose>
										<c:when test="${nonFunded==true}">
										
										$0.00
											<input type="hidden" name="soMaxLabor" id="soMaxLabor" value="0.00"/>
										</c:when>
										<c:otherwise>
										
										$<s:textfield 
												cssStyle="width: 54px;  text-align:right; font-size: 10px; font-family: verdana;" 
												cssClass="text toolTipShow"  value="" name="soMaxLabor" id="soMaxLabor"  onblur="hideToolTip()" onchange="validationForMaxPrice();hideToolTip();addOnObject.findTotals(this);"/>
										</c:otherwise>
										</c:choose>
										</c:otherwise>
										</c:choose>
										</c:otherwise>
									</c:choose>
									<span class="text toolTipShow" style="display:none">Note:Enter the final price.Ensure that any additional amount is collected from the customer.</span>
									<s:hidden name="finalLaborPrice" id="finalLaborPrice" value="%{finalLaborPrice}"></s:hidden>
									<s:hidden name="soInitialMaxLabor" id="soInitialMaxLabor" value="%{soInitialMaxLabor}"></s:hidden>
								</td>
								<td style="width:15%"></td>
							</tr>
							<c:if test="${ displayTax }">
							<tr>	
								<td colspan="2" style="text-align:left;">Labor Tax: (<fmt:formatNumber value="${laborTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td style="width:12%;text-align:right">
									$<fmt:formatNumber value="${soInitialMaxLabor * laborTaxPercentage}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									<input type="hidden" name="laborTaxPercentage" id="laborTaxPercentage"
										value='<fmt:formatNumber value="${laborTaxPercentage}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />
								</td>
								<td style="width:18%;text-align:right">
								$<label id="finalLaborTaxPriceLabel" name="finalLaborTaxPriceLabel"><fmt:formatNumber value="${finalLaborTaxPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/></label>
								<input type="hidden" name="finalLaborTaxPrice" id="finalLaborTaxPrice" value="${finalLaborTaxPrice}" /> 
								</td>
								<td style="width:15%"></td>
							</tr>
							</c:if>
							<tr>	
								<td colspan="2" style="text-align:left;">Parts</td>
								<td style="width:12%;text-align:right">
									$<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									<input type="hidden" name="partsSl" id="partsSl"
										value='<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />
								</td>
								<td style="width:18%;text-align:right">
									<c:set var="finalPartPriceInit" value="${finalPartPrice}" />	
									<c:if test="${partsSpendLimit != 0.00}">
									
										<c:choose>
											<c:when test="${soCompleteDto.errors[0] != null ||editCompletion=='editCompletion'}">
											<c:choose>
											<c:when test="${nonFunded==true}">
											
												$0.00
												<input type="hidden" name="soMaxPart" id="soMaxPart" value="0.00"/>
												<c:set var="finalPartPriceInit" value="0.00" />	
												<!-- <input type="hidden" name="finalPartPrice" id="finalPartPrice" value="0.00"/> -->
											</c:when>
											<c:otherwise>
											
											$<input style="width: 54px;  text-align:right;font-size: 10px; font-family: verdana;"
													class="text toolTipShow"
													name="soMaxPart"
													id="soMaxPart"
													value='<fmt:formatNumber value="${finalPartPrice - (finalPartPrice * partsTaxPercentage)}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>'  onblur="hideToolTip()" onchange="hideToolTip();addOnObject.findTotals(this);"/>
											
											<c:set var="finalPartPriceInit" value="${finalPartPrice}" />	
											
											</c:otherwise>
											</c:choose>
											</c:when>
											<c:otherwise>
											<c:choose>
											<c:when test="${finalPartsPriceNew !=null}">
											<c:choose>
											<c:when test="${nonFunded==true}">
											
											$0.00
											<input type="hidden" name="soMaxPart" id="soMaxPart" value="0.00"/>
											
											<c:set var="finalPartPriceInit" value="0.00" />	
											<!--  <input type="hidden" name="finalPartPrice" id="finalPartPrice" value="0.00"/> -->
											</c:when>
											<c:otherwise>
											
											$<s:textfield 
												cssStyle="width: 54px;  text-align:right;font-size: 10px; font-family: verdana;"
												cssClass="text toolTipShow"
												name="soMaxPart"
												id="soMaxPart"
												value="%{finalPartsPriceNew}"  onblur="hideToolTip()" onchange="hideToolTip();addOnObject.findTotals(this);"/>
											<c:set var="finalPartPriceInit" value="${finalPartsPriceNew}" />	
											</c:otherwise>
											</c:choose>
											</c:when>
											<c:otherwise>
											<c:choose>
											<c:when test="${nonFunded==true}">
											
											$0.00
											<input type="hidden" name="soMaxPart" id="soMaxPart" value="0.00"/>
											<c:set var="finalPartPriceInit" value="0.00" />	
											<!--  <input type="hidden" name="finalPartPrice" id="finalPartPrice" value="0.00"/> -->
											</c:when>
											<c:otherwise>
											
											$<s:textfield 
												cssStyle="width: 54px;  text-align:right;font-size: 10px; font-family: verdana;"
												cssClass="text toolTipShow"
												name="soMaxPart"
												id="soMaxPart"
												value=""  onblur="hideToolTip()" onchange="hideToolTip();addOnObject.findTotals(this);"/>
											
											</c:otherwise>
											</c:choose>	
											</c:otherwise>
											</c:choose>
											</c:otherwise>
										</c:choose>
									</c:if>
									<c:if test="${partsSpendLimit == 0.00}">
										$<fmt:formatNumber value="${partsSpendLimit}" type="NUMBER"
											minFractionDigits="2" maxFractionDigits="2" />
				
										<!--  <input type="hidden" name="finalPartPrice" id="finalPartPrice"
																		value="${partsSpendLimit}" /> -->
										<c:set var="finalPartPriceInit" value="${partsSpendLimit}" />	

									</c:if>
									<input type="hidden" name="finalPartPrice" id="finalPartPrice" value="${finalPartPriceInit + (finalPartPriceInit * partsTaxPercentage)}" />
								</td>
								<td style="width:15%"></td>
							</tr>
							<input type="hidden" name="permitTaskAddonPrice" id="permitTaskAddonPrice" value="${permitTaskAddonPrice }">
							<c:if test="${ displayTax }">
							<tr>	
								<td colspan="2" style="text-align:left;">Parts Tax: (<fmt:formatNumber value="${partsTaxPercentage * 100}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>%)</td>
								<td style="width:12%;text-align:right">
									$<fmt:formatNumber value="${partsSpendLimitTax}" type="NUMBER"
										minFractionDigits="2" maxFractionDigits="2" />
									<input type="hidden" name="partsTaxPercentage" id="partsTaxPercentage"
										value='<fmt:formatNumber value="${partsTaxPercentage}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' />
								</td>
								<td style="width:18%;text-align:right">
								$<label id="finalPartsTaxPriceLabel" name="finalPartsTaxPriceLabel">${finalPartsTaxPrice}</label>
								<input type="hidden" name="finalPartsTaxPrice" id="finalPartsTaxPrice" value="${finalPartsTaxPrice}" /> 
								</td>
								<td style="width:15%"></td>
							</tr>
							</c:if>
							<c:if test="${!empty model.permitTaskList}">
								<input type="hidden" name="permitInd" id="permitInd" value="1">
								<c:forEach var="permitTask" items="${permitTaskList}" varStatus="rowCounter">
								<c:set var="index" value="${rowCounter.count-1}" />	
						
								<tr class="permitTaskRow" >
									<td colspan="2" align="left">
									<c:set value="${permitTask.permitType}" var="typeId"></c:set>
										<b>Permits</b>(Customer Pre-paid):
										<s:select cssClass="addedPermits" cssStyle="width: 138px;" id="permitTaskList[%{#attr['index']}].permitType" headerKey="-1" headerValue="-Select Permit Type-"
																					 name="permitTaskList[%{#attr['index']}].permitType" list="#attr.permitTypeList"
																					listKey="id" listValue="type" multiple="false" value="%{#attr['typeId']}"
																					size="1" theme="simple" /><span class="req">*</span>
									</td>
									
									<td style="width:12%;text-align:right;">$
										<fmt:formatNumber value="${permitTask.sellingPrice}" type="NUMBER"
												minFractionDigits="2" maxFractionDigits="2" />
									</td>
									<td style="width:18%;text-align:right">
										 <%-- <c:choose>	
											<c:when test="${soCompleteDto.errors[0] != null ||editCompletion=='editCompletion'}"> --%>
												$<input name="permitTaskList[${index}].finalPrice" id="permitTaskList[${index}].finalPrice"
													value='<fmt:formatNumber value="${permitTask.finalPrice}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/>' 
													style="width: 54px;  text-align:right; font-size: 10px; font-family: verdana;"
													class="text toolTipShow"
													onblur="hideToolTip()" onchange="hideToolTip();clearAmount();addOnObject.findTotals(this);"/>
											<%-- </c:when>
											<c:otherwise>
												<s:textfield 
												cssStyle="width: 54px;   text-align:right;font-size: 10px; font-family: verdana;"
												cssClass="text toolTipShow"  onblur="hideToolTip()"
												name="permitTaskList[%{#attr['index']}].finalPrice"
												id="permitTaskList[%{#attr['index']}].finalPrice"
												value="" onchange="hideToolTip();clearAmount();addOnObject.findTotals(this);"
												/>
										</c:otherwise>
										</c:choose>  --%>
										<s:hidden name="permitTaskList[%{#attr['index']}].sellingPrice" id="permitTaskList[%{#attr['index']}].sellingPrice" value="%{#attr['permitTask'].sellingPrice}"></s:hidden>
										<s:hidden name="permitTaskList[%{#attr['index']}].retailPrice" id="permitTaskList[%{#attr['index']}].retailPrice" value="%{#attr['permitTask'].retailPrice}"></s:hidden>
										<s:hidden name="permitTaskList[%{#attr['index']}].taskType" id="permitTaskList[%{#attr['index']}].taskType" value="1"></s:hidden>
										<s:hidden name="permitTaskList[%{#attr['index']}].sequenceNumber" id="permitTaskList[%{#attr['index']}].sequenceNumber" value="%{#attr['permitTask'].sequenceNumber}"></s:hidden>
										<s:hidden name="permitTaskList[%{#attr['index']}].permitTaskDesc" id="permitTaskList[%{#attr['index']}].permitTaskDesc" ></s:hidden>
									</td>
									<td style="width:15%;text-align:right"><label name="permitTaskList[${index}].custChargeLabel" id="permitTaskList[${index}].custChargeLabel"><c:if test="${(permitTaskList[index].finalPrice!=null)}"><fmt:formatNumber value="${permitTaskList[index].custCharge}" type="currency" minFractionDigits="2" maxFractionDigits="2" currencySymbol="$"/></c:if></label></td>
									<s:hidden id="permitTaskList[%{#attr['index']}].custCharge" name="permitTaskList[%{#attr['index']}].custCharge"/>
								</tr>
								</c:forEach>
							</c:if>
							<s:hidden id='endCustomerAddOnSubtotalTotal' name='endCustomerAddOnSubtotalTotal'/>
							<s:hidden id='endCustomerFinalTotal' name='endCustomerFinalTotal'/>
							<input type="hidden" id='endCustomerDueFinalTotal' name='endCustomerDueFinalTotal' value="${endCustomerDueFinalTotal}"/>
							
							<tr style="background: #CCCCCC; color: #FFF; border-bottom: 1px solid #CCCCCC;">
							
								<td colspan="2" style="text-align:right;color: #666"><b>Totals</b></td>
								<td style="width:12%;text-align:right;color: #666">$<fmt:formatNumber value="${laborSpendLimit+partsSpendLimit+laborSpendLimitTax+partsSpendLimitTax}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2"/></td>
								<td style="width:18%;color: #666;text-align:right" align="right"><label id="finalWithoutAddonsLabel" name="finalWithoutAddonsLabel">${finalWithoutAddons}</label></td>
								<td style="width:15%;color: #666;text-align:right" align="right"><label id="custChargeWithoutAddonsLabel" name="custChargeWithoutAddonsLabel"><c:if test="${!empty model.permitTaskList}">${custChargeWithoutAddons}</c:if></label></td>
								<s:hidden id='finalWithoutAddons' name='finalWithoutAddons'/>
								<s:hidden id='custChargeWithoutAddons' name='custChargeWithoutAddons'/>
							</tr>
					</c:if>

		</table>
<!-- 	start	code changes for SLT-2572 -->
		<table>
		<tr>
		<td>
			<div id="MaxPriceError" class="error errorMsg errorMessage" style="background-color:#FBC2C4; display: none;width:160%;">
				<p class="validErrorText"></p>
			</div>
		</td>
		</tr>
		</table>
		<!-- 	end	code changes for SLT-2572 -->
		
		<c:if test="${currentSO.buyerID == '3000'}">
			<jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/panel_general_completion_hsr.jsp" />
			<jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/panel_parts_hsr.jsp" />
		</c:if>
	    <jsp:include
			page="/jsp/details/body/html_sections/modules/summary_tab/panel_add_on_services.jsp" />
		<table  class="globalTableLook"  style="margin-bottom: 0px;width:90%" width="85%" cellspacing="0" cellpadding="0">
			<tr style="border-bottom: 0px solid #CCCCCC;">
				<c:choose>
					<c:when test="${currentSO.buyerID == '3000'}">
						 <%-- Final Total for end customer --%>
						 <s:hidden id='endCustomerDueFinalTotal' name='endCustomerDueFinalTotal' />
						 <!-- <td colspan="3" style="text-align:right;color: #666;border-right: 1px solid #CCC;"><b>Combined Total Final</b></td> -->
						 <!--<td style="width:18%;background: #BDEDFF; color: #666;text-align:right;border-right: 1px solid #CCC;" align="right"><label id="finalTotalLabel" name="finalTotalLabel"><c:if test="${soCompleteDto.errors[0] != null ||editCompletion=='editCompletion'}">${finalTotal }</c:if></label></td> -->
						 <!--<td style="width:15%;background: #BDEDFF; color: #666;text-align:right" align="right"><label id="custChargeFinalLabel" name="custChargeFinalLabel"><c:if test="${(soCompleteDto.errors[0] != null ||editCompletion=='editCompletion') &&( model.addonCustomerPrice>0||!empty model.permitTaskList)}">${custChargeFinal }</c:if></label></td> -->
					</c:when>
								
					<c:otherwise>
						<td colspan="3" style="text-align:right;color: #666;border-right: 1px solid #CCC;"><b>Combined Total Final</b></td>
						<td style="width:18%;background: #BDEDFF; color: #666;text-align:right;border-right: 1px solid #CCC;" align="right"><label id="finalTotalLabel" name="finalTotalLabel">${finalTotal}</label>
						<label id="custChargeFinalLabel" name="custChargeFinalLabel" hidden><c:if test="${( model.addonCustomerPrice>0||!empty model.permitTaskList)}">${custChargeFinal}</c:if></label></td>
					</c:otherwise>
				</c:choose>
				<s:hidden id='finalTotal' name='finalTotal'/>
				<s:hidden id='custChargeFinal' name='custChargeFinal'/>
			</tr>	
		</table>
		
		<c:if test="${(addonServicesDTO.addonCheckbox || model.addonCustomerPrice>0)} " >
			** These are buyer-authorized add-on services or permits for this Service Order. Add-on services and permits <br/>
			are subject to approval upon receipt of customer payment.
		</c:if>
		<c:choose>				
			<c:when test="${(addonServicesDTO.addonCheckbox || addonServicesDTO.endCustomerSubtotalTotal>0 || permitTaskAddonPrice>0)}" >
				<div id="togglePayment" style="display:block">
			</c:when>
			<c:otherwise>
				<div id="togglePayment" style="display:none">
			</c:otherwise>
		</c:choose>

	<c:if test="${ null == displayTax || !displayTax }">
		<jsp:include page="/jsp/details/body/html_sections/modules/summary_tab/panel_customer_payment.jsp" />
	</c:if>
					
</div>

</div>
</div>
<div id="explainer"></div>