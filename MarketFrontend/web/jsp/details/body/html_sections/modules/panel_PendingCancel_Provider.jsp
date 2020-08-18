<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="qstaticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()%>" />

<script type="text/javascript">


		jQuery(".cancellationAmount").val('');
		jQuery(".cancelAmt").val('');
		jQuery(".cancelComment").val('');
		
		function isNumberKey(evt)
   		 {
        var charCode = (evt.which) ? evt.which : event.keyCode
        if (charCode > 31 && (charCode < 48 || charCode > 57))
           return false;
        return true;
    	 }
    	 
var panel1Def = "Use this reason when you have already performed the work and the Pending Cancellation request is not valid. <br>Click 'REPORT A PROBLEM' button to continue. This order will be placed in PROBLEM status and buyer will review your request.";

$(".glossaryItem").mouseover(function(e){
    	$(".toolTipReport").css("position","absolute");
   	    if($(this).attr("id") == "completeWork") { 
   	    // var position = $(".glossaryItem").position();
    	 $(".toolTipReport").html(panel1Def);
    	// $(".toolTipReport").css("top",position.top-150);
    	// $(".toolTipReport").css("right",100);
    	}
    	
    	if($(this).attr("id") == "completeWork1") {
   	    // var position = $("#completeWork").position();
   	  	 $(".toolTipReport").html(panel1Def);
    	 //$("#explainer").css("top",position.top-150);
    	 //$("#explainer").css("right",100);
    	}  	
    	
    	$(".toolTipReport").show();   
	});  
	$(".glossaryItem").mouseout(function(e){
	  $(".toolTipReport").hide();
	}); 
	
</script>
<div id="providerwidget" title="Pending Cancel" class="pendingCancelWidget">

<div id="history${theTab}" class="pendingCancelHistory" onmouseover="showHistoryDiv();" onmouseout="hideHistory();"  style="
			position:absolute;
			width:240px;
			background-color:white;
			border: 4px outset grey;
			z-index: 999999;display:none;height:170px;overflow: auto;">

</div>
<form id="frmPendingCancel" action="${qstaticContextPath}/MarketFrontend/serviceOrderPendingCancel_pendingCancelSO.action" method="POST" theme="simple">
	<input type="hidden" id="soId" name="soId" value="${SERVICE_ORDER_ID}"/>
					<c:if test="${pendingCancelSubstatus == 'pendingReview' }">

<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">

<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
							<input type="hidden" id="serviceOrderId" class="serviceOrderId" value="${soID}">
			
					<tr width="549px">
						<td width="180px">
							<b>On
							<span id="buyerEntryDate">${providerEntryDate}</span>
							you
							<span id="notice">
							requested
							</span>
							the amount of:</b>
						</td>
<td align = "right" width="169px">
						<b style="font-size: 20px;">$<span id="buyerPrice"></span><fmt:formatNumber value="${providerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></b>
</td>
					</tr>
					</table>
<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9;table-layout: fixed;">
<tr>
<td width="245px" style="word-wrap: break-word;">
					<div id="comment" style="display: block; word-wrap: break-word; width:245px;">
						<br>
						<b> Comments: </b>

						<span id="buyerComments">${providerComments}</span>
						<br>
					</div>
					</td>
					</tr>
				</table>

				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">
<tr>
	<td>
					<div id="firstView"  class="firstView" >

						<hr>
						<br>
						<br>
						<b>Waiting for buyer response.</b>
						<br>
						The buyer will either accept or decline the amount you requested.
						<br>

					</div>

</td>
</tr>

					<!-- TODO: change to 'withdraw this request' button  -->
					<tr>
	<td>
					<div id="withdrawRequest" align="center" class="withdrawRequest" >
					<br>
						<img onclick="providerWithdraw();" src="${staticContextPath}/images/common/spacer.gif"
							width="175" height="38"
							style="background-image: url(${staticContextPath}/images/btn/withdrawThisRequest.png);cursor: pointer;" />

					</div>
</td>
</tr>
				</table>
				</c:if>
				<c:if test="${pendingCancelSubstatus == 'pendingResponse' }">
				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">

<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
					<tr width="549px">
						<td width="180px">
						<b>	On
							<span id="buyerEntryDate${theTab}">${buyerEntryDate}</span>
							you
							<span id="notice">
							received a request in
							</span>
							the amount of:</b>
						</td>
<td align = "right" width="169px">
						<b style="font-size: 20px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
</td>
					</tr>
					</table>
<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">
<tr style="word-wrap: break-word; width:245px;">
	<td>
					<div id="comment" style="display: block;word-wrap: break-word; width:245px;">
						<br>
						<b> Comments: </b>

						<span id="buyerComments${theTab}">${buyerComments}</span>
						<br>
					</div>
					</td>
					</tr>
				</table>

				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">

<tr>
	<td>
					<div id="firstView"  class="firstView" style="display: none;">

						<hr>
						<br>
						<br>
						<b>Waiting for buyer response.</b>
						<br>
						The buyer will either accept or decline the amount you requested.
						<br>

					</div>
</td>
</tr>

<tr>
	<td>

					<div id="providerDecision_pend_resp" class="providerDecision_pend_resp"
						style="display: block;">
						<hr>
						<br>
						Do you agree to the amount requested by the buyer to cancel this
						service order in exchange for any work performed, late notice of
						cancellation, or visits to the job site?
						<br>
						<input type="radio" name="complete" value="1"
							onclick="hideDisagreeDiv();" checked="true" />
						<b>I agree with the cancellation. </b>
						<br>
						<br>
						<input type="radio" name="complete" value="2"
							onclick="showProviderRequestDiv();" />
						<b>I would like to request a new amount.</b>
						<br>
						<b style="padding-left: 17px;"></b>						<br>
					</div>
</td>
</tr>
<tr>
	<td>

					<div id="providerDisagree" class="providerDisagree" style="display: none;">
						<hr>

						<br>
						<b>Select a reason</b>&nbsp;<SPAN style="color: red">*</SPAN>
						<br>
						<div>
							<input type="radio" name="disagree" value="1" onclick="hideProviderRequestDiv();"/>
							<b>I have completed the work</b>
							<img id="completeWork" class="glossaryItem" src="${staticContextPath}/images/s_icons/help.png"  />
						</div>						
						<br>
						<input type="radio" name="disagree" value="2"
							onclick="showProviderRequestDiv();" />
						<b>I would like to request a new amount</b>
						<br>
						<b style="padding-left: 17px;"></b><br>
					</div>
					</td>
					</tr>
					<tr>
	<td>
                     <div style="background:#FAAFBA; display: none;" id="errorMessage"  class="errorBox errorMsg">
				</div>
</td>
</tr>
<tr>
	<td>

					<div id="newRequest" class="newRequest" style="display: none;">

						<hr>
						<br>
						Complete the form below to request a different amount of money for
						this cancelled order, and add some comments about why you
						disagreed with the amount previously requested.

						<br>

						<b>Requested Amount: <sup style="color: red">
												*
											</sup></b>

						$<input type="text" onkeypress="return isNumberKey(event);" id="cancellationAmount" onblur="setCancellationAmount(this);" class="cancellationAmount" maxlength="7" size="1"></input>
						.<input type="text" onkeypress="return isNumberKey(event);" id="cancelAmt" onblur="setCancelAmt(this);" class="cancelAmt" name="cancelAmt" maxlength="2" size="1"></input>

						<br>
						<b>Comments: <sup style="color: red">
												*
											</sup></b>
						<textarea rows="" name="cancelComment" onblur="setCancelComment(this);" id="cancelComment" class="cancelComment" cols="25" onkeyup="textCounter(this,'cancelComntCtr',600);" onkeydown="textCounter(this,'cancelComntCtr',600);"></textarea>
						<div style="width:90%;">
										<div style="float: right"><span id="cancelComntCtr" class="cancelComntCtr">600</span>&nbsp characters remaining</div>
						</div>

					</div>
</td>
</tr>

<tr>
	<td>
					<div id="agreeSubmit" class="agreeSubmit">
					<br>
						<img onclick="providerAgree();" src="${staticContextPath}/images/common/spacer.gif"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
                    </td>
                    </tr> 

<tr>
	<td>
					<div id="reportAproblem" class="reportAproblem" style="display: none; ">
					<br>
						<img onclick="providerReportProblem();"
							src="${staticContextPath}/images/common/spacer.gif"
							width="122" height="20"
							style="   background-image: url(${staticContextPath}/images/btn/reportAProblem_gold.gif); float: right;cursor: pointer; background-repeat: no-repeat;" />

					</div>
</td>
</tr>
<tr>
	<td>
					<div id="disagreeSubmit" class="disagreeSubmit" style="display: none;">
					<br>
						<img onclick="providerDisagree();" src="${staticContextPath}/images/common/spacer.gif"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
</td>
</tr>
					<!-- TODO: change to 'withdraw this request' button  -->
					<tr>
	<td>
					<div id="withdrawRequest" align="center" class="withdrawRequest" style="display: none;">
					<br>
						<img onclick="providerWithdraw();" src="${staticContextPath}/images/common/spacer.gif"
							width="175" height="38"
							style="background-image: url(${staticContextPath}/images/btn/withdrawThisRequest.png);cursor: pointer;" />

					</div>
</td>
</tr>
				</table>
				</c:if>
				
				<c:if test="${pendingCancelSubstatus == null }">
				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">

<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
					<tr width="549px">
						<td width="180px">
						<b>	On
							<span id="buyerEntryDate${theTab}">${buyerEntryDate}</span>
							you
							<span id="notice">
							received a request in
							</span>
							the amount of:</b>
						</td>
<td align = "right" width="169px">
						<b style="font-size: 20px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
</td>
					</tr>
					</table>
					<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">
<tr style="word-wrap: break-word; width:245px;">
	<td>

					<div id="comment" style="display: none;word-wrap: break-word; width:245px;">
						<br>
						<b> Comments: </b>

						<span id="buyerComments${theTab}">${buyerComments}</span>
						<br>
					</div>
					</td>
					</tr>
				</table>

				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">

<tr>
	<td>
					<div id="firstView" class="firstView" style="display: none;">

						<hr>
						<br>
						<br>
						<b>Waiting for buyer response.</b>
						<br>
						The buyer will either accept or decline the amount you requested.
						<br>

					</div>

</td>
</tr>
<tr>
	<td>

					<div id="providerDecision" class="providerDecision" style="display: block;">
						<hr>
						<br>
						Do you agree to the amount requested by the buyer to cancel this
						service order in exchange for any work performed, late notice of
						cancellation, or visits to the job site?
						<br>
						<input type="radio" name="complete" value="1"
							onclick="hideDisagreeDiv();" checked="true" />
						<b>I agree with the cancellation. </b>
						<br>
						<br>
						<input type="radio" name="complete" value="2"
							onclick="showDisagreeDiv();" />
						<b>I disagree with the cancellation. </b>
						<br>
					</div>
</td>
</tr>
<tr>
	<td>
					
					<div id="providerDisagree" class="providerDisagree" style="display: none;">
						<hr>

						<br>
						<b>Select a reason</b>&nbsp;<SPAN style="color: red">*</SPAN>
						<br>
						<div >
							<input type="radio" id="disagreeComplete" name="disagree" value="1"	onclick="hideProviderRequestDiv();"/>
							<b>I have completed the work</b>
							<img id="completeWork" class="glossaryItem" src="${staticContextPath}/images/s_icons/help.png"  />							
							<div id="explainer" class="toolTipReport" style="z-index: 9999999999999;"></div>
						</div>
						<br>
						<input type="radio" id="disagreeRequest" name="disagree" value="2"
							onclick="showProviderRequestDiv();" />
						<b>I would like to request a new amount</b>
						<br>
						<b style="padding-left: 17px;"></b>						<br>
					</div>
					</td>
					</tr>
					
					<tr>
	<td>
<div style="background:#FAAFBA; display: none;" id="errorMessage"  class="errorBox errorMsg">
	
	</div>
</td>
</tr>

<tr>
	<td>
					<div id="newRequest" class="newRequest" style="display: none;">

						<hr>
						<br>
						Complete the form below to request a different amount of money for
						this cancelled order, and add some comments about why you
						disagreed with the amount previously requested.

						<br>

						<b>Requested Amount: <sup style="color: red">
												*
											</sup></b>

						$<input type="text" onkeypress="return isNumberKey(event);" onblur="setCancellationAmount(this);" id="cancellationAmount" class="cancellationAmount" maxlength="7" size="1"></input>
						.<input type="text" onkeypress="return isNumberKey(event);" onblur="setCancelAmt(this);" id="cancelAmt" class="cancelAmt" name="cancelAmt" maxlength="2" size="1"></input>

						<br>
						<b>Comments: <sup style="color: red">
												*
											</sup></b>
						<textarea rows="" onblur="setCancelComment(this);" id="cancelComment" name="cancelComment" class="cancelComment"  cols="25" onkeyup="textCounter(this,'cancelComntCtr',600);" onkeydown="textCounter(this,'cancelComntCtr',600);"></textarea>
						<div style="width:90%;">
										<div style="float: right"><span id="cancelComntCtr" class="cancelComntCtr">600</span>&nbsp characters remaining</div>
						</div>

					</div>
</td>
</tr>


<tr>
	<td>
					<div id="agreeSubmit" class="agreeSubmit">
					<br>
						<img onclick="providerAgree();" src="${staticContextPath}/images/common/spacer.gif"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
                    </td>
                    </tr> 

<tr>
	<td>
					<div id="reportAproblem" class="reportAproblem" style="display: none;">
					<br>
						<img onclick="providerReportProblem();"
							src="${staticContextPath}/images/common/spacer.gif"
							width="122" height="20"
							style="background-image: url(${staticContextPath}/images/btn/reportAProblem_gold.gif); float: right;cursor: pointer; background-repeat: no-repeat;" />

					</div>
</td>
</tr>
<tr>
	<td>
					<div id="disagreeSubmit" class="disagreeSubmit"  style="display: none;">
					<br>
						<img onclick="providerDisagree();" src="${staticContextPath}/images/common/spacer.gif"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
</td>
</tr>
					<!-- TODO: change to 'withdraw this request' button  -->
					<tr>
	<td>
					<div id="withdrawRequest" align="center" class="withdrawRequest" style="display: none;">
					<br>
						<img onclick="providerWithdraw();" src="${staticContextPath}/images/common/spacer.gif"
							width="175" height="38"
							style="background-image: url(${staticContextPath}/images/btn/withdrawThisRequest.png);cursor: pointer;" />

					</div>
</td>
</tr>
				</table>
				</c:if>
				
				
</form>

</div>