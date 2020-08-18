<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jqmodal/jqModal.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modalVideo.css" />

<!-- icons by Font Awesome - http://fortawesome.github.com/Font-Awesome -->
<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
    
<c:set var="so" scope="request"
	value="<%=request.getAttribute("THE_SERVICE_ORDER")%>" />
<c:set var="groupedId" scope="request"
	value="<%=request.getAttribute("groupOrderId")%>" />
<c:set var="finalPrice" scope="request"
	value="<%=request.getAttribute("finalPrice")%>" />
<c:set var="ACCEPTED_STATUS" value="<%= new Integer(OrderConstants.ACCEPTED_STATUS)%>" />
<c:set var="ACTIVE_STATUS" value="<%= new Integer(OrderConstants.ACTIVE_STATUS)%>" />
	
	<script>
	var releasseSOErrLabel = document.getElementById("releaseSOErrLabel");
	releasseSOErrLabel.innerHTML = "";
	releasseSOErrLabel.style.visibility = 'hidden';
	$('#releaseReasonCode').change(function(){
		//$('#reasonText').val($('#reasonTexts').text());
		var reasonText = jQuery('#releaseReasonCode option:selected').html();
		$('#reasonText').val($.trim(reasonText));
	});
	$('#releaseSOComments').focus(function(){
		$('#releaseSOComments').html("");
	});
	$('#releaseSOComments').blur(function(){
		if($('#releaseSOComments').html()==""){
			$('#releaseSOComments').html("Comments");
		}
	});
	$('#releaseFromFirmInd').click(function(){
		if($('#releaseFromFirmInd').is(":checked")){
		$('#providerRelease').html("This service order will be released at the firm level and returned to the market place.");
		}else{
			$('#providerRelease').html("This service order will be released by this provider. Other pros in your firm which were initially routed the order will still be able to accept it.");	
		}
		});
	

	</script>
			<form action="/MarketFrontend/releaseSO.action" method="post"
				id="releaseSoForm" name="releaseSoForm" style="padding: 0px;">
				<input type="hidden" name="statusId" value="${so.status}" id="statusId">
				<input type="hidden" name="soId" value="${soId}" id="soId">								
				<input type="hidden" name="reasonText" id="reasonText">
				<input type="hidden" name="releaseByName" id="releaseByName" value="${fn:substringAfter(fn:substringBefore(so.providerWidget, '('),',')}${fn:substringBefore(fn:substringBefore(so.providerWidget, '('),',')}">
				<input type="hidden" name="releaseById" id="releaseById" value="${so.acceptedResourceId}">
				<div class="modalheader" style="background:#000000;color:#FFFFFF ;text-align:left;height:25px;padding-top:10px;padding-right:4px;padding-left: 15px;">
				<a href="javascript:void(0)"><img src="${staticContextPath }/images/widgets/tabClose.png"
					style="float:right;" onclick="closeReleaseModal();" /></a>
					<b>Release Service Order</b>
				</div>
				<div class="modalheaderoutline" >
					<div class="serviceOrderAcceptFrame" style="border:none;margin-left:10px;">
						<div class="serviceOrderAcceptFrameBody">
							If you can no longer complete this service order, you may release
							it to be re-routed to other providers. <b>There's no cost to release
							a service order, but doing so violates the agreement</b> you've made
							with your buyer and may have a negative impact on your overall
							provider reputation.
							<br />
							<div id="releaseSOErrLabel" style="visibility: hidden"></div>
							<br/>
							<select name="releaseReasonCode" id="releaseReasonCode" style="width:100%">
							    <option value="-1">-Select a reason for release-</option>
							     <c:if test="${!empty releaseReasonCodes}">
								    <c:forEach var="reason" items="${releaseReasonCodes}" varStatus="rowCounter" >
									    <option value="${reason.respReasonId}" id="reasonTexts">
									    	 ${reason.descr} 
									    </option>
									</c:forEach>
								<%--  <option value="-2">Other</option> --%>
								</c:if>
							</select>
							<br />
							<br />
							
							<textarea id="releaseSOComments" name="comment"
								style="width: 98%;">Comments</textarea>
							<br />
							<br />	
							<div>
							<div style="width: 370px;">
							<b>${so.title}</b><br />
							<b>Status</b>:
							<c:if test="${ACCEPTED_STATUS==so.status}">
							 Accepted
							 </c:if>
							 <c:if test="${ACTIVE_STATUS==so.status}">
							 Active
							 </c:if>
							 <b>S.O #</b>:${so.id}
							 <c:if test="${viewOrderPricing==true}">
							 <span style="color:#0B8C49">&nbsp;&nbsp;&nbsp;&nbsp;<b>Maximum Price</b>: ${spendLimit}</span>
							 </c:if>
							<br/>
							<b>When:</b> ${so.appointmentDates} at ${so.serviceWindow}
							<br/>
							<b>Where:</b> ${so.locationWidget}
							<br/>
							<c:choose>
							<c:when test="${assignmentType == 'FIRM'}">
							<b>Assigned Provider</b>: un-assigned
							</c:when>
							<c:when test="${so.acceptedResourceId==null || so.acceptedVendorId == nulll}">
							<b>Assigned Provider</b>: un-assigned
							</c:when>
							<c:otherwise>
							<div style="width: 365px;">
								<b>Assigned Provider:</b><a href="javascript:void(0);" style="word-wrap: break-word;" onclick="openProviderProfile('${so.acceptedResourceId}','${so.acceptedVendorId}','/MarketFrontend/');">${fn:substringAfter(fn:substringBefore(so.providerWidget, "("),",")}${fn:substringBefore(fn:substringBefore(so.providerWidget, "("),",")}</a>&nbsp;(ID# ${fn:substringAfter(so.providerWidget, "(")}
								<br/>
								<div style="margin-top: 15px;">
									<input type="checkbox" name="releaseFromFirmInd" value="1" id="releaseFromFirmInd"/> Release this order from this firm
								</div>
							</div>
							</c:otherwise>
							</c:choose>
							</div>
							<div id="infoHdr" style="background-color: #CCCCCC;	width:150px;height:5px;width: 178px;float: right; margin-top: -70px;"></div>
							<div id="notification" class="infoWindow" style="width: 25%; padding-bottom: 2px; padding-left: 5px; padding-right: 20px; display: block; float: right; margin-top: -65px;background-color: #f7fc92;width:150px;float: right;margin-right: 2px;text-align: left;">
									<div style=" margin-top:15px;"><i class="icon-lightbulb" style="font-size: large;"></i></div>
									<c:choose>
									<c:when test="${assignmentType == 'FIRM'}">
									<div style="margin-top:-30px; margin-left:15px;font-size: 9px; line-height: 15px;">This service order will be released at the firm level and returned to the market place</div>
									</c:when>
									<c:otherwise>
									<div style="margin-top:-30px; margin-left:15px;font-size: 9px; line-height: 15px;" id="providerRelease">This service order will be released by this provider. Other pros in your firm which were initially routed the order will still be able to accept it.</div>
									</c:otherwise>
									</c:choose>
							</div>
							</div>
						</div>
					</div>
					<hr>
					<p style="background-color: #eeeeee;height:45px;padding-top: 10px;">
					<img id="submitReleaseSOButton"
						src="${staticContextPath}/images/common/spacer.gif" width="65"
						height="20"
						style="background-image: url(${staticContextPath}/images/btn/release.gif);"
						class="btn20Bevel" align="right"
						onClick="validateAndSubmitReleaseSOModal()" />

		</p>
				</div>
			</form>