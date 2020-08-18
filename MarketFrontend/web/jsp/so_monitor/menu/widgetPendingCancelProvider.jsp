<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

<c:set var="BUYER_ROLEID"
	value="<%=new Integer(OrderConstants.BUYER_ROLEID)%>" />
<c:set var="PROVIDER_ROLEID"
	value="<%=new Integer(OrderConstants.PROVIDER_ROLEID)%>" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="theTab" scope="request"
	value="<%=request.getAttribute("tab")%>" />
<c:set var="role" value="${roleType}" />

<html>
	<head>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard.css" />		
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	
<style>
	#explainer${theTab} {
		display: none; width:220px;height:auto;border: 3px solid #adaaaa; background:#fcfae6; border-radius:10px;
		-moz-border-radius:10px; -webkit-border-radius:10px; padding:10px;
	}
</style>
	</head>
	<body>
	
	
	
<div id="heading " style="height:25px;background:#FFBF00;background-image:url(${staticContextPath}/images/reason_code_manager/pending_cancel_hdr.png);">
	<table width="100%">
		<tr>
			<td align="center"><SPAN style="padding-top: 4px">
				
							&nbsp;<img alt="Collapse" src="${staticContextPath}/images/grid/down-arrow.gif" onclick="hideWidget('${theTab}');" class="pendingCancelCollapse" style="padding-bottom: 2px;" id="pendingCancelCollapse${theTab}">
							&nbsp;<img alt="Expand"
								src="${staticContextPath}/images/grid/right-arrow.gif"
								style="display: none;padding-top: 4px;" onclick="showWidget('${theTab}');"
								class="pendingCancelExpand" id="pendingCancelExpand${theTab}">
				</SPAN>
			</td>
			<td>
				<b style="float:center;display: inline-table;">Pending Cancellation</b>
			</td>
			<td>
				<a id = "pendingCancelHistoryIcon${theTab}" style="float:right;padding-right: 1px;padding-bottom: 2px;padding-top:2px; display: inline-table;"  onmouseover="showHistory(event, '${theTab}');" onmouseout="hideHistory('${theTab}');" class="priceHistoryIcon" href="javascript:void(0);"><img src="${staticContextPath}/images/reason_code_manager/dollar_icon.png"/></a>
			</td>
		</tr>
	</table>
</div>
<div id="history${theTab}" class="pendingCancelHistory"  onmouseover="showHistoryDiv('${theTab}')"  onmouseout="hideHistory('${theTab}')" style="
			position:absolute;
			width:240px;
			background-color:white;
			border: 4px outset grey;
			z-index: 999999;display:none;height:170px;overflow: auto;">
			

</div>
		<form id="frmPendingCancel${theTab}" name="frmPendingCancel${theTab}" action="serviceOrderPendingCancel_pendingCancelSO" method="POST" theme="simple" style="background-color: #F2F5A9;">
			<input type="hidden" id="toLoc" name="toLoc">
			<input type="hidden" id="fromLoc" name="fromLoc">
			<input type="hidden" id="zip" name="zip">
			<input type="hidden" id="orderStatus" name="orderStatus">


<input type="hidden" id="action${theTab}" class="text" name="action" value="">
		<input type="hidden" id="cancelAmount${theTab}" class="text" name="cancelAmount" value="">
		
				<input type="hidden" id="serviceOrderId${theTab}" class="text" name="soId">
				
			<div style="background-color: #F2F5A9";>
			<div style="padding-left: 10px;">
				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">


					<tr width="549px">
						<td width="100px">
						<br>
						<b>	On
							<span id="buyerEntryDate${theTab}" style="background-color: #F2F5A9;"></span>&nbsp;
							you
							<span id="notice${theTab}" style="background-color: #F2F5A9;">
							</span>&nbsp;
							the amount of: </b>
						</td>
	<td align = "right" width="169px" style="padding-top: 10px;">
						<b style="font-size: 17px;">$
							<span id="buyerPrice${theTab}" style="background-color: #F2F5A9;"></span> </b>
	</td>
					</tr>
					</table>
<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9;table-layout: fixed;">
<tr >
<td width="245px" style="word-wrap: break-word; width:245px;">
					<div id="comment${theTab}" style="display: block; background-color: #F2F5A9;word-wrap: break-word; width:245px;">
						<br>
						<b> Comments: </b>

						<span id="buyerComments${theTab}"></span>
						<br>
					</div>
					</td>
					</tr>
				</table>

				<table border="0" cellpadding="0" cellspacing="0" class=""
					style="background-color: #F2F5A9">
<tr>
<td>
					<div id="firstView${theTab}" style="display: none;background-color: #F2F5A9;">

						<hr>
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
					<div id="providerDecision${theTab}" style="display: block;background-color: #F2F5A9;">
						<hr>
						<br>
						Do you agree to the amount requested by the buyer to cancel this
						service order in exchange for any work performed, late notice of
						cancellation, or visits to the job site?
						<br>
						<input type="radio" name="complete" value="1" id="agreedFirstProvider${theTab}"
							onclick="hideDisagreeDiv('${theTab}');" checked="true" />
						<b>I agree with the cancellation. </b>
						<br>
						<br>
						<input type="radio" name="complete" value="2" id="disagreedFirstProvider${theTab}"
							onclick="showDisagreeDiv('${theTab}');" />
						<b>I disagree with the cancellation. </b>
						<br>
					</div>
</td>
</tr>
<tr>
<td>
					<div id="providerDecision_pend_resp${theTab}"
						style="display: block;background-color: #F2F5A9;">
						<hr>
						<br>
						Do you agree to the amount requested by the buyer to cancel this
						service order in exchange for any work performed, late notice of
						cancellation, or visits to the job site?
						<br>
						<input type="radio" name="complete" value="1" id="agreedProvider${theTab}"
							onclick="hideDisagreeDiv('${theTab}');" checked="true" />
						<b>I agree with the cancellation. </b>
						<br>
						<br>
						<input type="radio" name="complete" value="2" id="disagreedProvider${theTab}"
							onclick="showProviderRequestDiv('${theTab}');" />
						<b>I would like to request a new amount.</b>
						<br>
						<b style="padding-left: 17px;"></b>
						<br>
					</div>
</td>
</tr>
<tr>
<td>
					<div id="providerDisagree${theTab}" style="display: none;background-color: #F2F5A9;">
						<hr>

						<br>
						<b>Select a reason</b>&nbsp;<SPAN style="color: red">*</SPAN>
						<br>
						<div style="border: none;">
							<input type="radio" id="disagreeComplete${theTab}" name="disagree" value="1" 
								onclick="hideProviderRequestDiv('${theTab}');"/>
							<b>I have completed the work</b>
							<img id="completeWork${theTab}" class="glossaryItem" style="border: none" onmouseover="showGlossaryItem('${theTab}');" 
								onmouseout="hideGlossaryItem('${theTab}');" src="${staticContextPath}/images/s_icons/help.png"  />
			
						</div>
						<div id="explainer${theTab}" style="z-index: 9999999999999;font-weight: normal;">Use this reason when you have already performed the work and the Pending Cancellation request is not valid.
							 <br>Click 'REPORT A PROBLEM' button to continue. 
							 This order will be placed in PROBLEM status and buyer will review your request.</div>						
						<br>
						<input type="radio" id="disagreeRequest${theTab}"  name="disagree" value="2"
							onclick="showProviderRequestDiv('${theTab}');" />
						<b>I would like to request a new amount</b>
						<br>
						<b style="padding-left: 17px;"></b>						<br>
					</div>
					</td>
					</tr>
		<tr>
<td>			
					
<div style="background:#FAAFBA; display: none;" id="errorMessage${theTab}"  class="errorBox errorMsg">
	
	</div>
</td>
</tr>
<tr>
<td>
					<div id="newRequest${theTab}" style="display: none;background-color: #F2F5A9;">

						<hr>
						<br>
						Complete the form below to request a different amount of money for
						this cancelled order, and add some comments about why you
						disagreed with the amount previously requested.

						<br>

						<b>Requested Amount: <sup style="color: red">
												*
												
											</sup></b>

						$<input type="text" onkeypress="return isNumberKey(event);" onblur="return validateAmount('${theTab}');" class="cancellationAmount" id="cancellationAmount${theTab}" maxlength="7" size="1"></input>
						.<input type="text" onkeypress="return isNumberKey(event);" onblur="return validateAmount('${theTab}');" id="cancelAmt${theTab}" name="cancelAmt" maxlength="2" size="1"></input>

						<br>
						<b>Comments: <sup style="color: red">
												*
											</sup></b>
						<textarea rows="" id="cancelComment${theTab}" name="cancelComment" class="cancelComment"  cols="25" onkeyup="textCounter(this,'cancelComntCtr',600);" onkeydown="textCounter(this,'cancelComntCtr',600);"></textarea>
						<div style="width:90%;">
										<div style="float: right"><span id="cancelComntCtr">600</span>&nbsp characters remaining</div>
						</div>
					</div>
</td>
</tr>

<tr>
<td>
					<div id="agreeSubmit${theTab}" style="display: none;background-color: #F2F5A9;">
					<br>
						<img src="${staticContextPath}/images/common/spacer.gif" onclick="providerAgree('${theTab}');"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
</td>
</tr>

<tr>
<td>
					<div id="reportAproblem${theTab}" style="display: none;background-color: #F2F5A9;">
					<br>
					
						<img onclick="providerReportProblem('${theTab}');"
							src="${staticContextPath}/images/common/spacer.gif" onclick=""
							width="122" height="20"
							style="background-image: url(${staticContextPath}/images/btn/reportAProblem_gold.gif); float: right;cursor: pointer;  background-repeat: no-repeat;" />

					</div>
					</td>
					</tr>

<tr>
<td>
					<div id="disagreeSubmit${theTab}" style="display: none;background-color: #F2F5A9;">
					<br>
						<img src="${staticContextPath}/images/common/spacer.gif" onclick="providerDisagree('${theTab}');"
							width="72" height="22"
							style="background-image: url(${staticContextPath}/images/btn/submit.gif); float: right;cursor: pointer;" />

					</div>
</td>
</tr>
					<!-- TODO: change to 'withdraw this request' button  -->
					<tr>
<td>
					<div id="withdrawRequest${theTab}" align="center" style="display: none;background-color: #F2F5A9;">
					<br>
						<img src="${staticContextPath}/images/common/spacer.gif" 
							width="175" height="38" onclick="providerWithdraw('${theTab}');"
							style="background-image: url(${staticContextPath}/images/btn/withdrawThisRequest.png);cursor: pointer;" />

					</div>
</td>
</tr>
				</table>

			</div>
			</div>
			

		</form>
	</body>


</html>
