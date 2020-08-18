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
		</script>

<div id="buyerwidget" title="Pending Cancel" class="pendingCancelWidget">
<div id="history${theTab}" class="pendingCancelHistory" onmouseover="showHistoryDiv();" onmouseout="hideHistory();"  style="
			position:absolute;
			width:240px;
			background-color:white;
			border: 4px outset grey;
			z-index: 999999;display:none;height:170px;overflow: auto;">

</div>
<form id="frmPendingCancel" method="POST" theme="simple"
	action="${qstaticContextPath}/MarketFrontend/serviceOrderPendingCancel_pendingCancelSO.action?soId=${SERVICE_ORDER_ID}">

<c:if test="${pendingCancelSubstatus == 'pendingResponse' }">

	<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
					<input type="hidden" id="serviceOrderId" name="soId" class="serviceOrderId" value="${soID}">
			         <input type="hidden" id="balance" class="text" name="balance" value="${balance}">
				     <input type="hidden" id="buyerPvsAmount" class="text" name="buyerPvsAmount" value="${buyerPvsAmount}">
				     <input type="hidden" id="soLevelAutoACH"  name="isSoLevelAutoACH" value="${isSoLevelAutoACH}">
	
						
							<tr width="549px">
								<td width="180px">
								<b>	On
									<span id="buyerEntryDate${theTab}">${buyerEntryDate}</span>
									you
									<span id="notice">
									sent a request in
									</span>
									the amount of:</b>
								</td>
								<td width="169px" align="right">
								
								<c:choose>
								<c:when test="${buyerPrice >999999.99}">
									<b style="font-size: 15px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
								</c:when>	
								<c:otherwise>
									<b style="font-size: 20px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</table>
							<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9;table-layout: fixed;">
	<tr>
	<td style="word-wrap: break-word;" width="245px">
							<div style="background:#FAAFBA; display: none;" id="errorMsge"  class="errorBoxes errorMsg">				
							</div>
							<div id="comment" style="display: block;word-wrap: break-word; width:245px;">
							<br>
								<b> Comments: </b>
	
								<span id="buyerComments${theTab}">${buyerComments}</span>
							</div>
							</td>
							</tr>
							
					</table>
					<table border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	
	<tr>
	<td>				<br>
	<div style="background:#FAAFBA; display: none;" id="errorMessageW"  class="errorBox errorMsg">	
	</div></td>
		</tr>		
	<tr>	
	<td><div id="firstView${theTab}" style="display: block;">
							<hr>
							<br>
							<b>Waiting for provider response.</b> 
							<br>
							The provider will either
							accept or decline the amount you requested.
							<br><br>
						</div>
	
	
	</td>
	</tr>
						
	
	
	
					<tr>
	<td>	
	
						<div id="disagreeSubmit" style="display: none;">
							<img onclick="buyerDisagree();" src="${staticContextPath}/images/common/spacer.gif"
								width="72" height="22"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />
	
						</div>
						</td>
						</tr>
						<!-- TODO: change to 'withdraw this request' button  -->
						<tr>
	<td>
						<div id="withdrawRequest" align="center">
	                     
							<img  onclick="buyerWithdraw();" src="${staticContextPath}/images/common/spacer.gif"
								width="175" height="38"
								style="background-image: url(${staticContextPath}/images/btn/withdrawThisRequest.png);cursor: pointer;" />
	
						</div>
	</td>
	</tr>
					</table>
					</c:if>
					<c:if test="${pendingCancelSubstatus == 'pendingReview' }">
					<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
		         <input type="hidden" id="balance" class="text" name="balance" value="${balance}">
					     <input type="hidden" id="soLevelAutoACH"  name="isSoLevelAutoACH" value="${isSoLevelAutoACH}">
	
						
							<tr width="549px">
								<td width="180px">
								<b>	On
									<span id="buyerEntryDate${theTab}">${providerEntryDate}</span>
									you
									<span id="notice"> 
									received a request
									</span>
									in the amount of:</b>
								</td>
								<td align = "right" width="169px">
								<c:choose>
								<c:when test="${buyerPrice >999999.99}">
									<b style="font-size: 15px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${providerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
								</c:when>	
								<c:otherwise>
									<b style="font-size: 20px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${providerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</table>
					<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">		
							
	<tr>
	<td style="word-wrap: break-word; width:245px;">
							<div id="comment" style="display: block;word-wrap: break-word; width:245px;">
							<br>
								<b> Comments: </b>
	
								<span id="buyerComments${theTab}">${providerComments}</span>
							</div>
							</td>
							</tr>
					</table>
					<table border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	
	
						
	<tr>
	<td>
	
	
						<div id="lastRequest" class="lastRequest" style="display: block;">
	
							<br>
						<b>	The last request sent to the provider was on:
							<span id="buyerEntryDatelastRequest${theTab}">
	${buyerEntryDate}
							</span><br>
							in the amount of: $
							<span id="buyerPricelastRequest${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/>
							</span></b>
	<br>
	
						</div>
	
	</td>
	</tr>
	<tr>
								<td>
			<div style="background:#FAAFBA; display: none;" id="errorMsge"  class="errorBoxes errorMsg">
				
				</div>
				</td>
				</tr>
	<tr>
	<td>
								
						<div id="buyerDecision" class="buyerDecision"  style="display: block;">
							<hr>
	
							<br>
							Do you agree to the fee requested by the provider to cancel this
							service order in exchange for any work performed, late notice of
							cancellation, or visits to the job site?
							<br>
							<input type="radio" name="complete" value="1"
								onclick="hideRequestDiv();" checked="true" />
							<b>I agree to the amount requested</b>
							<br><br>
							<input type="radio" name="complete" value="2"
								onclick="showRequestDiv();" />
							<b>I would like to request a new amount</b><br><br>
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
	
							<b>New Amount: <sup style="color: red">
												*
											</sup></b>
	
							$<input type="text"  onkeypress="return isNumberKey(event);"  onblur="setCancellationAmount(this);" class="cancellationAmount" id="cancellationAmount" maxlength="7" size="1"></input>
							.<input type="text" onkeypress="return isNumberKey(event);" onblur="setCancelAmt(this);"  class="cancelAmt" id="cancelAmt" name="cancelAmt" maxlength="2" size="1"></input>
	
							<br>
							<b>Comments: <sup style="color: red">
												*
											</sup></b>
							<textarea rows=""  name="cancelComment" onblur="setCancelComment(this);" id="cancelComment" class="cancelComment"  cols="25" onkeyup="textCounter(this,'cancelComntCtr',600);" onkeydown="textCounter(this,'cancelComntCtr',600);"></textarea>
							<div style="width:90%;">
										<div style="float: right"><span id="cancelComntCtr" class="cancelComntCtr">600</span>&nbsp characters remaining</div>
							</div>
	
						</div>
	</td>
	</tr>
	
	<tr>
	<td>
	
						<div id="submit" class="submit" style="display: block;">
							<img onclick="buyerAgree();" src="${staticContextPath}/images/common/spacer.gif"
								width="72" height="22"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />
	
						</div>
						</td>
						</tr>
						
						<tr>
	<td>
	
						<div id="disagreeSubmit"  class="disagreeSubmit" style="display: none;">
							<img onclick="buyerDisagree();" src="${staticContextPath}/images/common/spacer.gif"
								width="72" height="22"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />
	
						</div>
						</td>
						</tr>
						<!-- TODO: change to 'withdraw this request' button  -->
						
	
					</table>
					
					</c:if>
					
					<c:if test="${pendingCancelSubstatus == null }">
					<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	<input type="hidden" id="action" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount" class="text" name="cancelAmount" value="">
	
						
							<tr width="549px">
								<td width="180px">
									<b>On
									<span id="buyerEntryDate${theTab}">${buyerEntryDate}</span>
									you sent
									<span id="notice">
									a request in 
									</span>
									the amount of:</b>
								</td>
								<td align = "right" width="169px">
								<c:choose>
								<c:when test="${buyerPrice >999999.99}">
									<b style="font-size: 15px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>
								</c:when>	
								<c:otherwise>
									<b style="font-size: 20px;">$<span id="buyerPrice${theTab}"><fmt:formatNumber value="${buyerPrice}" groupingUsed="TRUE" minFractionDigits="2" maxFractionDigits="2"/></span> </b>								
								</c:otherwise>
								</c:choose>
								</td>
							</tr>
							</table>
		<table span = "100%" border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">					
	<tr>
	<td style="word-wrap: break-word; width:245px;">
							<div id="comment" style="display: block;word-wrap: break-word; width:245px;">
							<br>
								<b> Comments: </b>
	
								<span id="buyerComments${theTab}">${buyerComments}</span>
							</div>
					</td>
					</tr>		
					</table>
					<table border="0" cellpadding="0" cellspacing="0" class=""
						style="background-color: #F2F5A9">
	
	<tr>
	<td>
	
						<div id="firstView${theTab}" style="display: block;">
							<br>
							<hr>
							<br><br>
							<b>Waiting for provider response.</b> 
							<br>
							The provider will either
							accept or decline the amount you requested.
							<br><br>
						</div>
	
	
	</td>
	</tr>
	<tr>
	<td>
						
	
						<div id="disagreeSubmit" style="display: none;">
							<img onclick="buyerDisagree();" src="${staticContextPath}/images/common/spacer.gif"
								width="72" height="22"
								style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />
	
						</div>
						
				</td>
				</tr>		
						
	
					</table>
					</c:if>
					</form>

</div>