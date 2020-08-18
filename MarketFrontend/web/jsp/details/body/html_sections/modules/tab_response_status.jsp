<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>		
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>


<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection">

<%-- 
<c:set var="showTieredRoutingInfo" scope="request" value="true" />
--%>

<script type="text/javascript">
	jQuery("#responsehistorylink").load("soDetailsQuickLinks.action?soId=${SERVICE_ORDER_ID}&groupId=${groupOrderId}&resId=${routedResourceId}");
	
	function fnReturnToTop(){
		window.location.hash="#logo";
	}
	
	function returnToResponse(){
		window.location.hash="#bidResponsesDiv";
	}
	
	function returnToComments(){
		window.location.hash="#bidNotesDiv";
	}
</script>

<div class="soNote">
<div id="rightsidemodules" class="colRight255 clearfix">
       <p id="responsehistorylink" style="color:#000;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;"><span></span></p>
 </div>
    
<c:set var="isAdminauth" scope="request" value="false" />


<tags:security actionName="etmSearch" >
	<c:set var="isAdminauth" scope="request" value="true" />
</tags:security>

<div class="contentPane">
	<c:set var="msgDisplayed" value="false"/>
	<c:forEach var="dto" items="${providerOfferList}" varStatus="dtoIndex">
		<c:if test="${dto.responseId==2 and wfStateId == 110 and not empty THE_SERVICE_ORDER.parentGroupId and msgDisplayed == 'false'}">
			<p>This order is part of <strong>Grouped Service Order ${THE_SERVICE_ORDER.parentGroupId}.</strong><br/>
				<a href='monitor/soDetailsController.action?groupId=${THE_SERVICE_ORDER.parentGroupId}&defaultTab=Response Status&displayTab=Search'>Click here</a>
				to take action on a counter offer for this Grouped Order.
			</p>
			<c:set var="msgDisplayed" value="true"/>
		</c:if>
	</c:forEach>
<p style="color:blue">	
	<%=request.getAttribute("msg")!=null?request.getAttribute("msg"):"" %>
	<%request.setAttribute("msg",""); %>
</p>

<div class="responseFilterLinks"><strong>View: </strong> 
<a href="javascript:void(0);" onclick="returnToResponse();">Responses</a> <strong>|</strong> 
<a href="javascript:void(0);" onclick="returnToComments();">Comments</a></div>
<div class="routedStatusSummary">
	<div class="blueIcon routedStatusSummaryDiv"><strong>Routed To:</strong> ${postedCnt} Provider<c:if test="${postedCnt != 1}">s</c:if></div>
	<div class="yellowIcon routedStatusSummaryDiv"><strong><c:choose><c:when test="${priceModelBid}">Bids</c:when><c:otherwise>Counter Offers</c:otherwise></c:choose>:</strong> ${conditionalAcceptedCnt }
	<c:if test="showCommunication == true"> <span class="new">(${newConditionalAcceptedCnt } New)</span></c:if></div>
	<div class="commentsIcon routedStatusSummaryDiv"><strong>Comments:</strong> ${commentCnt } <c:if test="showCommunication == true"><span class="new">(${newCommentCnt } New)</span></c:if></div>
	<div class="redIcon routedStatusSummaryDiv"><strong>Rejected By :</strong> ${rejectedCnt} Provider<c:if test="${rejectedCnt != 1}">s</c:if></div>
</div>

<c:choose>
<c:when test="${priceModelBid}">
	<h2 style="padding: 0; margin: 0;">What kind of response is your service order getting?</h2>
	<p class="paddingBtm">You can accept bids on your service order here.<br />
	If you have a large number of rejections you may want to consider trying the following:
	</p>
	<ul class="leftOffset">
		<li>Responding to questions below from the service providers you selected.</li>
		<li>Changing your scope of work to provide more detail.</li>
		<li>Expand the date range of your service order.</li>
	</ul>
</c:when>
<c:otherwise>
	<h2 style="padding: 0; margin: 0;">What kind of response is your service order getting?</h2>
	<p class="paddingBtm">You can accept counter offers on your service order here.<br />
	If you have a large number of rejections you may want to consider trying the following:
	</p>
	<ul class="leftOffset">
		<li>Responding to questions below from the service providers you selected.</li>
		<li>Changing your scope of work to provide more detail.</li>
		<li>Increase the spend limit of your service order.</li>
		<li>Expand the date range of your service order.</li>
	</ul>
</c:otherwise>
</c:choose>

<!-- NEW MODULE/ WIDGET-->
<div style="width: 683px; padding-left:5px;">
	<jsp:include page="panel_response_status_responses.jsp" />
	
	<div class="responseFilterLinks"><a href="javascript:void(0);" onclick="fnReturnToTop();">Back to Top</a></div>

	<c:if test="${showCommunication}">
		<jsp:include page="panel_response_status_communication.jsp" />
	</c:if>
	
<s:form action="/MarketFrontend/soDetailsUpdateComments.action" method="post" id="responseStatusUpdateForm" name="responseStatusUpdateForm">
	
	<input type="hidden" name="firstName" id="firstName"/>
    <input type="hidden" name="lastName" id="lastName"/>
    <input type="hidden" name="resourceId" id="resourceId"/>
    <input type="hidden" name="callStatusDescription" id="callStatusDescription"/>
    <input type="hidden" name="mktMakerComment" id="mktMakerComment"/>
    <input type="hidden" name="callStatusIdSelected" id="callStatusIdSelected"/>
    <input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}"/>
    <input type="hidden" name="groupId" id="groupId" value="${groupOrderId}"/>
    <input type="hidden" name="cameFromWorkflowMonitor" id="cameFromWorkflowMonitor" value=""/>
    <input type="hidden" name="status" id="status" value="${THE_SERVICE_ORDER_STATUS_CODE}"/>
</s:form>
</div>

<s:form action="/MarketFrontend/soDetailsAcceptCondOff.action" method="post" id="responseStatusForm" name="responseStatusForm">
	<input type="hidden" name="resourceId" id="resourceId">
	<input type="hidden" name="vendorId" id="vendorId">
	<input type="hidden" name="conditionalChangeDate1" id="conditionalChangeDate1">
	<input type="hidden" name="conditionalChangeDate2" id="conditionalChangeDate2">
	<input type="hidden" name="conditionalStartTime" id="conditionalStartTime">
	<input type="hidden" name="conditionalEndTime" id="conditionalEndTime">
	<input type="hidden" name="responseReasonId" id="responseReasonId">
	<input type="hidden" name="conditionalSpendLimit" id="conditionalSpendLimit">
	<input type="hidden" name="soId" id="soId" value="${SERVICE_ORDER_ID}">
	<input type="hidden" name="groupId" id="groupId" value="${groupOrderId}">
</s:form>
</div>
</div>