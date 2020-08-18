<%@ page language="java"
	import="java.util.*,com.servicelive.routingrulesengine.RoutingRulesConstants" pageEncoding="UTF-8"%>

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />

<html>
<head>
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/autoAcceptance.css" />
<script type="text/javascript">var staticContextPath="${staticContextPath}";</script>
<script type="text/javascript">
 jQuery(document).ready(function($){
	var routingRuleId = '${routingRuleId}';
 	jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"color": "#00A0D2"});
 	jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"text-decoration": "underline"});
 	
 	jQuery('#'+routingRuleId+'autoAcceptHistoryProv').click(function () {
 		jQuery('#'+routingRuleId+'ruleCommentsTextView').hide();
 		jQuery('#'+routingRuleId+'accpetHistoryDiv').show();
 		jQuery('#'+routingRuleId+'quickViewProv').css({"color": "#00A0D2"});
 		jQuery('#'+routingRuleId+'quickViewProv').css({"text-decoration": "underline"});
 		jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"color": "#666666"});
 		jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"text-decoration": "none"});
 	});
 	
 	jQuery('#'+routingRuleId+'quickViewProv').click(function () { 		
 		jQuery('#'+routingRuleId+'accpetHistoryDiv').hide();
 		jQuery('#'+routingRuleId+'ruleCommentsTextView').show();
 		jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"color": "#00A0D2"});
 		jQuery('#'+routingRuleId+'autoAcceptHistoryProv').css({"text-decoration": "underline"});
 		jQuery('#'+routingRuleId+'quickViewProv').css({"color": "#666666"});
 		jQuery('#'+routingRuleId+'quickViewProv').css({"text-decoration": "none"});
 	});
 	
 	$('#'+routingRuleId+'close').click(function () {
			$(".ruleView").hide();
			return false;
	});
 });

</script>

<style type="text/css">
	p{margin: 5px 0;}	
</style>

 </head>

	<body>	
		<span style="float:right;">
			<b id="${routingRuleId}close" style="cursor: pointer;">Close</b>
		</span>
		<b id="${routingRuleId}quickViewProv" style="font-size: 13px" >
			<span style="cursor: pointer;">View Rule&nbsp;</span> 
		</b> 
		<b>|</b> 
		<b id="${routingRuleId}autoAcceptHistoryProv" style="font-size: 13px" >
			<span style="cursor: pointer;">&nbsp;History</span>
		</b>
		<br><br>
		<div id="${routingRuleId}ruleCommentsTextView">
			<p>${ruleQuickView.ruleName}</p>
			<p><b>Rule Status </b> : ${ruleQuickView.ruleStatus}</p> 
			<c:forEach var="content" items="${ruleQuickView.customReference}">   
				<p><b> ${content.key}</b> :    ${content.value}</p>
			</c:forEach>			
			
			<c:if test="${not empty ruleQuickView.pickUplocationCodes}">
				<p><b>Pick Up Location Code </b> : ${ruleQuickView.pickUplocationCodes}</p>
			</c:if>
			<c:if test="${not empty ruleQuickView.zipCode}">
				<p><b>Zip Code  </b> : ${ruleQuickView.zipCode}</p>
			</c:if>	
			<c:if test="${not empty ruleQuickView.market}">
				<p><b>Market  </b> : ${ruleQuickView.market}</p>
			</c:if>	
			<c:if test="${not empty ruleQuickView.jobCodes}">
				<p><b>Job Codes </b> : ${ruleQuickView.jobCodes}</p>
			</c:if>
			<p><b>Contact </b> : ${ruleQuickView.firstName} ${ruleQuickView.lastName}, ${ruleQuickView.email}</p> 
		</div>
		
		<div id="${routingRuleId}accpetHistoryDiv" style="display: none;">
			<table class="histTable" cellpadding="0" cellspacing="0">
				<tr style="background-color: #D8D8D8;height: 20px;">
					<td class="histTd"><b>AutoAcceptance&nbsp;</b></td>
					<td class="histTd"><b>Receive Opportunity Email/SMS</b></td>
					<td class="histTd"><b>Updated By</b></td>
					<td class="histTd"><b>Updated On</b></td>					
					<td style="font-size: 12px;padding: 5px;"><b>Comments</b></td>		
				</tr>
				<tbody>
					<c:forEach items="${autoAcceptHistForProv}" var="autoAccpetlist" varStatus ="status">
						<tr <c:if test="${status.count%2!=0}">style="background-color: #F2F2F2"</c:if>>
							<td class="histTd">${autoAccpetlist.autoAcceptance}</td>
							<td class="histTd">
							     <c:if test="${true == autoAccpetlist.opportunityEmailInd}">
							      YES
							     </c:if>
							     <c:if test="${false == autoAccpetlist.opportunityEmailInd}">
							      NO
							     </c:if> 
							</td>
							<td class="histTd" style="width:20%">
								${autoAccpetlist.role}<br>
								<c:if test="${autoAccpetlist.updatedBy != ''}">
									(${autoAccpetlist.updatedBy})<br>
								</c:if>
							</td>
							<td class="histTd" style="width:30%">
								<fmt:formatDate value="${autoAccpetlist.updatedOn}" pattern="MM-dd-yyyy hh:mm a" />&nbsp;(CST)
							</td>							
							<td style="font-size: 12px;padding: 5px;word-wrap:break-word;">${autoAccpetlist.comments}</td>
						</tr>					
					</c:forEach>
				</tbody>
			</table>
		</div>
	</body>
</html>