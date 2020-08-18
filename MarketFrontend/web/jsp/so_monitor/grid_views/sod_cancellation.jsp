
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="taskLevelPriceInd" value="${taskLevelPriceInd}"
	scope="session" />
<c:set var="msg" value="${msg}" scope="request" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/creditCardValidation.js"></script>	

		<script type="text/javascript">
			$(document).ready(function() {
			});
			$('#comment_leftChars').keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			   }			   
			});
			
			$('#providerPayment').keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			   }			   
			});
						
			$("#reasonCode").click(function()
			{
				$("#cancelError").hide();
			});
			
			$("#priceIntegerCancel").click(function()
			{
				$("#cancelError").hide();
			});
			
			$("#priceFractionCancel").click(function()
			{
				$("#cancelError").hide();
			});
	
			$("#comments").click(function()
			{
				$("#cancelError").hide();
			});
			$("#providerPayment").click(function()
			{
				$("#ZeroAmountDiv").hide();
			});			
			function isNumberKeyPressed(evt)
		    {
		        var charCode = (evt.which) ? evt.which : event.keyCode
		        if (charCode > 31 && (charCode < 48 || charCode > 57))
		           return false;
		        return true;
		     }
		     function ismaxlength(obj){
				var mlength= 600;
				if (obj.getAttribute && obj.value.length>mlength)
				obj.value=obj.value.substring(0,mlength)
			}
			function fnNextMangeScope(){
				var soId = jQuery('#soId').val();
				var errorMsg = fnValidateCancellation();
				if(errorMsg!=""){
					return false;
				}
				if(jQuery("#providerPayment").is(':checked')){
					jQuery("#cancellationInfo").css("display","none");
					var htmlVal = jQuery("#manageScopeDiv").html();
					if(htmlVal.indexOf("Manage")!=-1){
						jQuery("#manageScopeDiv").css("display","block");
					}else{
						jQuery("#manageScopeDiv").css("display","block");
						jQuery("#manageScopeDiv").html('<div style="padding: 250px;" align="center"><img src="${staticContextPath}/images/spinner_small.gif\"/></div>');
						jQuery("#manageScopeDiv").load('manageScope.action?fromCancel=true&soId='+soId,function(){
							
						});
					}
				}else{
					fnCancellationSubmit();
				}
			}
			function fnPrevMangeScope(){
				jQuery("#manageScopeDiv").css("display","block");
				jQuery("#releasePaymentDiv").css("display","none");
			}
			function submitReleasePayment(){
				if(!($("#releasePay").is(":checked")==true)){
					return false;
				}else{
					fnCancellationSubmit();
				}
			}
			function trim(myString){
		        return myString.replace( /^\s+/g,'').replace(/\s+$/g,''); 
	        }
			function fnValidateCancellation(){
				var errorMsg = "";
				jQuery("#cancelError").html("");
				jQuery("#cancelError").css("display","none");
				var reasonCode = jQuery("#reasonCode").val();
				if(reasonCode=="-1"){
					errorMsg = "Please select a reason for canceling this order.<br/>";
				}
				var comments = jQuery("#comments").val();
				var commentsTrim=trim(comments);
				jQuery("#comments").val(commentsTrim);
				if(commentsTrim ==""){
					errorMsg = errorMsg + "Please enter comments describing why you are canceling this order.<br/>";
				}else{
					var creditCardValidation=validateCreditCardNumber(commentsTrim);
					if(creditCardValidation){
						errorMsg = errorMsg + "Please don't enter the credit card number in comments.<br/>";
					}
				}
				if(jQuery("#providerPayment")){
					if(jQuery("#providerPayment").is(":checked")){
						if(jQuery("#soPricing").val()=="SO_LEVEL"){
							if(jQuery("#priceIntegerCancel").val()==""){
								errorMsg = errorMsg + "Please enter cancellation amount for canceling this order.<br/>";
							}else{
								var amount = jQuery("#priceIntegerCancel").val();
								var fraction = jQuery("#priceFractionCancel").val();
								if(amount==""){
									amount = "00";
								}
								if(fraction==""){
									fraction = "00";
								}
								var amt = amount+"."+fraction;
								var cancelAmount = parseFloat(amt, 10).toFixed(2);
								//var cancelAmount = amount + fraction * .01;
								if(cancelAmount>parseFloat($("#maxPrice").val())){
									errorMsg = errorMsg + "The cancellation amount should not exceed Maximum price";
								}else{
									document.frmCancelFromSOM.cancelAmt.value = cancelAmount;
								}
							}
						}
					}else{
						document.frmCancelFromSOM.cancelAmt.value ="0.00";
					}
					
				}
				if(errorMsg!=""){
					jQuery("#cancelError").html(errorMsg);
					jQuery("#cancelError").css("display","block");
				}				
				return errorMsg;
			}
			function showZeroCancel(){
				if(jQuery("#providerPayment").is(':checked')){
					jQuery("#cancelZeroAmountDiv").css("display","none");
					if(jQuery("#soPricing").val()=="TASK_LEVEL"){
						jQuery("#cancelButtonDiv> input:button").val("NEXT");
					}
					if(jQuery("#cancelAmountDiv")){
						jQuery("#cancelAmountDiv").css("display","block");
					}
					jQuery("#providerPaymentCheck").val('true');
				}else{
					jQuery("#cancelZeroAmountDiv").css("display","block");
					jQuery("#cancelAmountDiv").css("display","none");
					if(jQuery("#cancelAmountDiv")){
						jQuery("#cancelButtonDiv> input:button").width(150);
						jQuery("#cancelButtonDiv> input:button").val("Cancel Service Order");
					}
					jQuery("#providerPaymentCheck").val('false');
				}
			}
			function fnCancellationSubmit(){
				if(jQuery("#providerPayment")){
					if(jQuery("#providerPayment").is(':checked')){
						jQuery("#providerPaymentCheck").val('true');
					}else{
						jQuery("#providerPaymentCheck").val('false');
					}
				}
				var errorMsg = fnValidateCancellation();
				if(errorMsg!=""){
					return false;
				}
				var action = jQuery("#action").val();
	       	 	var reason = jQuery("#reasonCode :selected").text();
	        	jQuery("#reason").val(reason);
	        	if(jQuery("#wfStateId").val()==150 && jQuery("#within24Hours").val()== "true" && jQuery("#tripChargeOverride").val()!= "true"){
	        		document.frmCancelFromSOM.cancelAmt.value = jQuery("#cancellationFee").val();
	        	}
				jQuery("#frmCancelFromSOM").submit();
				
			}
			
			function countAreaChars(areaName,counter,limit, evnt){
				if (areaName.value.length>limit) {
					areaName.value=areaName.value.substring(0,limit);
					("The field limit is " + limit + " characters.");
		
					//Stop all further events generated (Event Bubble) for the characters user has already typed in .
					//For IE
					if (!evnt) var evnt = window.event;
					evnt.cancelBubble = true;
					//For FireFox
					if (evnt.stopPropagation) evnt.stopPropagation();
				}
				else
					counter.value = limit - areaName.value.length;
			   }
		    function DisableIt(cb) { 
                 if(cb.checked)  { 
                 document.getElementById("releaseAndPaySubmit").disabled=false;
                         } 
                 if(!cb.checked)  { 
                 document.getElementById("releaseAndPaySubmit").disabled=true;
                    } 
     			}		
     </script>
<style type="text/css">
table#releasePaymentTable td {font-size: 9pt;}
.rejectServiceOrderFrameBody {font-size: 9pt;}
</style>
	</head>
	<body>

		<div id="cancellationInfo" style="height: 500px;">
			<div class="modalHomepage" style="height: 15px;vertical-align: middle;">
				<div id="cancelHeading" style="float: left;"></div>
			</div>
			<div style="height: 480px;">
				<table height="90%">
					<tr height="90%">
						<td>
			
				<form id="frmCancelFromSOM" name="frmCancelFromSOM" action="<s:url action="serviceOrderCancel.action" />" method="POST">
						<input type="hidden" id="tripChargeOverride"
							name="tripChargeOverride" value="${TRIP_CHARGE_OVERRIDE }" />
						<input type="hidden" id="within24Hours" name="within24Hours"
							value="${isWithin24Hours }" />
						<input type="hidden" id="providerApproveIndicator"
							name="providerApproveIndicator" value="false" />
						<input type="hidden" id="providerPaymentCheck"
							name="providerPaymentCheck" value="true" />
						<input type="hidden" id="soPricing" name="soPricing"
							value="${currentSO.priceType }" />
						<input type="hidden" id="maxPrice" name="maxPrice" value="${currentSO.totalSpendLimit}" />
						<input type="hidden" id="cancelAmt" name="cancelAmt" value="0.00" />
						<input type="hidden" id="action" name="action" />
						<input type="hidden" id="reason" name="reason" />
						<input type="hidden" id="soId" name="soId" value="${currentSO.id}" />
						<input type="hidden" id="wfStateId" name="wfStateId"
							value="${currentSO.status}" />
						<input type="hidden" id="scopeChangeCancelComments" name="scopeChangeCancelComments" />
						<input type="hidden" id="scopeChangeCancelReason" name="scopeChangeCancelReason" />
						<input type="hidden" id="scopeChangeCancelReasonCode" name="scopeChangeCancelReasonCode" />
						<input type="hidden" id="cancellationFee" name="cancellationFee" value="${cancellationFee}" />
						<input type="hidden" id="nonFunded"
							name="nonFunded" value="${currentSO.nonFundedInd}" />
						<div>
							
							<div>
								<div style="width: 100%; border: 0px" >
	
									<div style="width: 100%; padding-left: 5px;font-size: 9pt;">
										<div id="cancelError" class="errorBox" style="display: none;padding: 5px;width: 90%"></div>
						
										<br/>
										<strong>Service Order: #</strong> ${currentSO.id}
										<br/>
										<c:set var="status" value="${currentSO.primaryStatus}" />
										<c:if test="${currentSO.primaryStatus=='Routed'}">
											<c:set var="status" value="Posted" />
										</c:if>
										<strong>Status:</strong> ${status}
										<br/>
										<strong>Current Maximum Price: $</strong>
										<fmt:formatNumber value="${currentSO.totalSpendLimit}"
											type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
										<br/>
										<c:set var="appointDate" value="" />
										<c:if test="${currentSO.serviceDate2==null}">
											<strong>Appointment Date/Time:</strong>
											<fmt:formatDate value="${currentSO.serviceDate1}"
												pattern="MM/dd/yy" /> ${currentSO.serviceTimeStart}
										</c:if>
										<c:if test="${currentSO.serviceDate2!=null}">
											<strong>Appointment Date/Time:</strong>
											<fmt:formatDate value="${currentSO.serviceDate1}"
												pattern="MM/dd/yy" />  - <fmt:formatDate
												value="${currentSO.serviceDate2}" pattern="MM/dd/yy" /> between ${currentSO.serviceTimeStart} and ${currentSO.serviceTimeEnd}
									   </c:if>
	
										<br />
										<br />
	
										<c:if
											test="${currentSO.nonFundedInd==false &&( (currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170))}">
											<c:choose><c:when test="${TRIP_CHARGE_OVERRIDE==true}">
												<c:set var="disableStatus" value="" />
												<c:choose><c:when test="${currentSO.status==150 && isWithin24Hours==false && currentSO.priceType=='TASK_LEVEL'}">
													<c:set var="checkStatus" value="" />
												</c:when>
												<c:otherwise>
													<c:set var="checkStatus" value="checked" />
												</c:otherwise></c:choose>
											</c:when>
											<c:otherwise>
												<c:set var="checkStatus" value="checked" />
												<c:set var="disableStatus" value="disabled" />
											</c:otherwise></c:choose>
											<input type="checkbox" id="providerPayment" name="providerPayment" onclick="showZeroCancel()"
												${disableStatus} ${checkStatus} ><span style="padding-left:10px">Check this box if the provider needs to be paid an additional amount for this order.</span>
									</c:if>
										<c:if
											test="${currentSO.nonFundedInd==false &&  currentSO.status==150 && isWithin24Hours && TRIP_CHARGE_OVERRIDE!=true}">
											<div class="warningBox" style="width: 90%;font-weight: bold;color: black;padding: 10px">
												Please note: You will be charged a fee of $<fmt:formatNumber value="${cancellationFee}" minFractionDigits="2" maxFractionDigits="2"/> for canceling this order.											
											</div>
										</c:if>
										<br />
										<br />
										<b>Select a reason for canceling</b>
										<span class="req">*</span>
										<br />
										<select name="reasonCode" id="reasonCode" width="300px"
											style="width: 300px;border:2px solid #CCCCCC">
											<option selected="selected" value="-1">
												[Please Select One] 
											</option>
											<c:forEach var="reasonCode" items="${cancelReasonCodes}">
												<option value="${reasonCode.reasonCodeId}">
													${reasonCode.reasonCode}
												</option>
											</c:forEach>
										</select>
										<c:if
											test="${((currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170))&&(currentSO.priceType=='SO_LEVEL') && currentSO.nonFundedInd==false}">
											<div id="cancelAmountDiv">
												<br />
												<br />
												<b>Requested cancellation amount</b> <span class="req">*</span><br>
												<br />
												<b> $ </b>
												<input type="text" size="3" maxlength="7" id="priceIntegerCancel" name="priceIntegerCancel" style="width:30px" value="45" onkeypress="return isNumberKeyPressed(event);"/>
												<b> . </b>
												<input type="text" size="2" maxlength="2" id="priceFractionCancel" name="priceFractionCancel"  style="width:20px" value="00" onkeypress="return isNumberKeyPressed(event);"/>
																						
												<span class="wfmCallOutIcon"><img src="${staticContextPath}/images/s_icons/help.png"/>
												<span class="wfmCallOut" style="margin-left:95px;width: 470px; height: 140px;padding-top:20px; background-image: url(${staticContextPath}/images/tooltip.png);">
												<p style="padding-left:15px;">You may, request the service provider to complete the service order for a lower amount. Specify an amount that you would like the service provider to complete the service order and submit. A communication will go out to the provider with your request.</p> 
												<p style="padding-left:15px;">Important: Sending the cancellation request will not guarantee the provider's agreement of the terms. It is a good idea to speak directly with the provider to explain your intent and get the provider's consent.</p></span>
												</span>
												
											</div>
											
										</c:if>
										
										<c:if
											test="${((currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170))&&(currentSO.priceType=='SO_LEVEL') && currentSO.nonFundedInd==true}">
											<div id="cancelAmountDiv">
											<br />
											<br />
										<b>Requested cancellation amount</b> <span class="req">*</span>
											<br />
											$0.00
										</div> 
										<div style="display: none;">
											<input type="hidden" size="3" maxlength="7" id="priceIntegerCancel" name="priceIntegerCancel" style="width:30px" value="00" />
												<b> . </b>
												<input type="hidden" size="2" maxlength="2" id="priceFractionCancel" name="priceFractionCancel"  style="width:20px" value="00"/>
											</div>
										</c:if>
										<div id="cancelZeroAmountDiv" style="display:none">
											<br />
											<br />
										<b>Requested cancellation amount</b> <span class="req">*</span>
											<br />
											$0.00
										</div>
										<c:if 
											test="${currentSO.status==150 && isWithin24Hours==false && TRIP_CHARGE_OVERRIDE==true && currentSO.priceType=='TASK_LEVEL'}">
										<div id="ZeroAmountDiv">
											<br />
											<br />
										<b>Requested cancellation amount</b> <span class="req">*</span>
											<br />
											$0.00
										</div>
										</c:if>
										<br />
										<br />
										<b>Comments</b>
										<span class="req">*</span>
										<br />
										<textarea style="width: 90%" name="comments" id="comments" 
											onkeyup="countAreaChars(this.form.comments, this.form.comment_leftChars, 600, event);"
											onkeydown="countAreaChars(this.form.comments, this.form.comment_leftChars, 600, event);"></textarea><br>
										<input type="text" id="comment_leftChars" name="comment_leftChars" value="600"
												maxlength="3" size="3" readonly="readonly"/>Characters remaining
												
										<br />
										<br />
										<br />
										<br />
	
									</div>
								</div>
	
							</div>
						</div>
					</form>
				</td>
				</tr>
				</table>

					<div id="cancelBottomDiv">
						<div id="cancelButtonDiv">
							<a href="#" class="simplemodal-close" style="float: left;color: red; padding-left: 5px; margin-top: 10px;"><u>Close Window</u></a>
							<c:choose><c:when 
								test="${currentSO.status==150 && isWithin24Hours==false && TRIP_CHARGE_OVERRIDE==true && currentSO.priceType=='TASK_LEVEL'}">
								<input type="button" id="cancelOrder" value="Cancel Service Order" onclick="fnNextMangeScope()" class="button action" style="float: right;width: 150px; margin-top: 10px; margin-right: 5px;"/>
							</c:when>
							<c:when
								test="${((currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170))&& currentSO.priceType!=null && currentSO.priceType=='TASK_LEVEL'}">
								<input type="button" id="nextScope" value="NEXT"  onclick="fnNextMangeScope()" class="button action" style="float: right;width: 130px"/>
							</c:when>				
							<c:otherwise>
								<input type="button" id="cancelOrder" value="Cancel Service Order" onclick="fnCancellationSubmit()"  class="button action" style="float: right;width: 150px; margin-top: 10px; margin-right: 5px;"/>
							</c:otherwise></c:choose>
						</div>
					</div>
				</div>
			
		</div>	
				<div id="manageScopeDiv">
				</div>
				<div id="releasePaymentDiv" style="display:none">
				<div class="modalHomepage" style="height: 10px;vertical-align: middle;">
				
				<div style="float: left;">Release Payment to Provider</div>
				</div>
				<div  id="releasePaymentTable">
				<br/>
					<div style="height: 30px;padding-left: 10px;">
					<b>Accepted By: </b>
					<c:choose><c:when test="${currentSO.assignmentType == 'FIRM'}">
						Unassigned
					</c:when>
					<c:otherwise>
						${currentSO.acceptedResource.resourceContact.firstName} &nbsp;${currentSO.acceptedResource.resourceContact.lastName}&nbsp;(#${currentSO.acceptedResource.resourceId})
					</c:otherwise></c:choose>
					</div>
					<div style="height: 30px;background: #FFF8BF;padding-left: 10px;padding-top:10px;width:95%;margin-left:10px"><b>
					The amount of $<span id="releaseAmount"></span> will be paid to the provider firm for the services performed.
					</b>
					</div>
					<br/>
					<div style="height: 50px;padding-left: 10px;">
					<input type="checkbox" id="releasePay" name="releasePay"   onClick="DisableIt(this)"  checked />
						Yes, I'm sure I want to release payment to this provider.<span class="req">*</span>
					</div>
			</div>
			<div style="height:310px">
			</div >
					<div style="background-color: #DEDDDD; height: 50px;">
							<a href="javascript:void(0);" style="float: left;color: red;padding-left:5px;padding-top: 15px;" onclick="fnPrevMangeScope()" class="prevScope" id="prevScope"><u>Back</u></a>
							<br>
							<input type="button" id="releaseAndPaySubmit" value="Release Payment" onclick="submitReleasePayment()"  class="button action" style="float: right;width: 130px;"/>
					</div>					
		</div>

	</body>
	<script type="text/javascript">
	</script>


</html>
