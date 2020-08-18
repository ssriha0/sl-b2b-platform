
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>
<c:set var="taskLevelPriceInd" value="${taskLevelPriceInd}"
	scope="session" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/jqueryui/jquery-ui-1.10.4.custom.min.css" />
		<link href="${staticContextPath}/javascript/confirm.css"
			rel="stylesheet" type="text/css" />
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.simplemodal.1.4.4.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript">
		jQuery(document).ready(function($) {	
			$('#comment_leftChars').keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			   }			   
			});
			$('#reqCancelAmount').keypress(function(event) {
			  if ( event.which == 13 ) {
			     event.preventDefault();
			   }			   
			});
		});
		</script>
	</head>
	<body>

		<div id="cancellationInfo">
			<form id="frmCancelFromSOM" name="frmCancelFromSOM">
				<input type="hidden" id="tripChargeOverride"
					name="tripChargeOverride" value="${TRIP_CHARGE_OVERRIDE }" />
				<input type="hidden" id="within24Hours"
					name="within24Hours" value="${isWithin24Hours }" />
				<input type="hidden" id="providerPaymentCheck"
					name="providerPaymentCheck" value="false" />
				<input type="hidden" id="providerApproveIndicator"
					name="providerApproveIndicator" value="false" />
				<input type="hidden" id="taskLevelPricing" name="taskLevelPricing"
					value="${currentSO.soPricing }" />
				<input type="hidden" id="cancelAmt" name="cancelAmt" value="0.00" />
				<input type="hidden" id="wfStateId" name="wfStateId" value="${currentSO.status}" />
				<input type="hidden" id="reason" name="reason" />
				<input type="hidden" id="action" name="action" />
				<input type="hidden" id="soId" name="soId" value="${currentSO.id}" />
				<input type="hidden" id="scopeChangeCancelComments" name="scopeChangeCancelComments" />
				<input type="hidden" id="scopeChangeCancelReason" name="scopeChangeCancelReason" />
				<input type="hidden" id="scopeChangeCancelReasonCode" name="scopeChangeCancelReasonCode" />		
				<input type="hidden" id="cancellationFee" name="cancellationFee" value="${cancellationFee}" />
				
				<div id="checkspend" class="divContainerUp"
					style="visibility: hidden">
				</div>
				<div align="center">
					<div class="modalHomepage" style="height: 18px">
						<div id="cancelHeading" style="float: left;">

						</div>
					
					</div>
					<div class="modalheaderoutline">
						<div class="rejectServiceOrderFrame"
							style="width: 100%; height: 320px;border: 0px">

							<div class="rejectServiceOrderFrameBody"
								style="width: 630px; padding-left: 15px">
								<div id="cancelE1" class="errorBox" style="display: none;height:30px"></div>
								<br />
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
								<br>
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
									test="${(currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170)}">
									<c:choose><c:when test="${TRIP_CHARGE_OVERRIDE==true}">
										<c:set var="disableStatus" value="false" />
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
									<input type="checkbox" id="providerPaymentCheck" checked="${checkStatus}"
										disabled="${disableStatus}" name="providerPaymentCheck">Check this box if the provider needs to be paid an additional amount for this order.
								</c:if>
								<c:if
									test="${currentSO.status==150 && isWithin24Hours && TRIP_CHARGE_OVERRIDE!=true}">
									<div class="warningBox">
										<br />
										<br />
										Please note: You will be charged a fee of $<fmt:formatNumber value="${cancellationFee}" minFractionDigits="2" maxFractionDigits="2"/> for canceling
										this order.
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
									test="${(currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170)}">
									<br />
									<br />
									<b>Requested cancellation amount</b> <span class="req">*</span>
									<br />
									<div id="cancelAmountDiv">
										<input type="text" id="reqCancelAmount" name="reqCancelAmount">
								</c:if>
								<br />
								<br />
								<b>Comments</b>
								<span class="req">*</span>
								<br />
								<textarea style="width: 375px" name="comments" id="comments"
									onkeyup="countAreaChars(this.form.comments, this.form.comment_leftChars, 600, event);"
									onkeydown="countAreaChars(this.form.comments, this.form.comment_leftChars, 600, event);"></textarea><br>
								<input type="text" id="comment_leftChars" name="comment_leftChars" value="600"
									maxlength="3" size="3" readonly="readonly" onKeyPress="return false;"/>Characters remaining
								<br />
								<br />
								<br />
								<br />

							</div>
						</div>

					</div>
				</div>
			</form>
			<div id="cancelButtonDiv" style="background-color: #DEDDDD; height: 50px">
				<br />
				<a href="#" onclick="closeModalPopup()" id="closeButton" class="simplemodal-close"
					style="color: red; float: left"><u>Close Window</u></a>
				<c:choose><c:when 
					test="${currentSO.status==150 && isWithin24Hours==false && TRIP_CHARGE_OVERRIDE==true && currentSO.priceType=='TASK_LEVEL'}">
					<input type="button" id="cancelOrder" value="Cancel Service Order" onclick="submitCancellation()"  class="button action" style="float: right;width: 130px"/>
				</c:when>
				<c:when
					test="${((currentSO.status==150 && TRIP_CHARGE_OVERRIDE==true)||(currentSO.status==155||currentSO.status==170))&& currentSO.soPricing!=null && currentSO.soPricing=='TASK_LEVEL'}">
					<input type="button" id="nextScope" value="NEXT"  onclick="fnNextMangeScope()" class="button action" style="float: right;width: 130px"/>
				</c:when>
				<c:otherwise>
				<input type="button" id="cancelOrder" value="Cancel Service Order" onclick="submitCancellation()" class="button action" style="float: right;width: 130px"/>					
				</c:otherwise></c:choose>
			</div>
		</div>
				<div id="manageScopeDiv">
				</div>
				<div id="releasePaymentDiv" style="display:none">
				<table>
					<tr>
					Accepted By: 
					</tr>
					<br/>
					<tr>
					<div>
					The amount of $<span id="releaseAmount"></span> will be paid to the provider firm for the services performed.
					</div>
					</tr>
					<tr>
					<input type="checkbox" id="releasePay" name="releasePay" checked/>Yes, I'm sure I want to release payment to this provider.<span class="req">*</span>
					</tr>
					<tr class="thirdRow" height="10%">
						<td colspan="2">
							<a href="javascript:void(0);" style="float: left;color: red;" onclick="fnPrevMangeScope()" class="prevScope" id="prevScope"><u>Back</u></a>
							<input type="button" id="releaseAndPaySubmit" value="Release Payment" onclick="submitReleasePayment()"  class="button action" style="float: right;width: 130px"/>
						</td>
					</tr>
					</table>
				</div>
	</body>
	<script type="text/javascript">
	</script>


</html>
