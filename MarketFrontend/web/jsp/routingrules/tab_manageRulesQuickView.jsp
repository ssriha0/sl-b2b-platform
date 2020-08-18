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
<c:set var="alertActive"
	value="<%=RoutingRulesConstants.ROUTING_ALERT_STATUS_ACTIVE%>" />

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/routingrules.css" />
<link rel="stylesheet" href="${staticContextPath}/css/jqueryui/jquery-ui-1.7.2.custom.css" type="text/css"></link>
<script type="text/javascript">var staticContextPath="${staticContextPath}";</script>
<script type="text/javascript">
$(document).on('click', ".quickViewCancelLink", function(){
			$(".ruleCommentView").hide();
			$(".ruleCommentViewSearch").hide();
			return false;
		});

 		function showRule(){
 			jQuery("#autoAcceptHistoryBuyer").css("color", "#00A0D2");
			jQuery(".accpetHistoryDiv").hide();
			jQuery(".ruleCommentsTextView").show();
 		}

		function showHistory(){
			jQuery("#quickViewBuyer").css("color", "#00A0D2");
			jQuery(".accpetHistoryDiv").show();
			jQuery(".ruleCommentsTextView").hide();
		}
</script>
 <tags:security actionName="routingRulesAction_view">
 	<c:set var="isReadOnly" value="true" scope="page" />
 </tags:security>
 <tags:security actionName="routingRulesAction_edit">
     <c:set var="isReadOnly" value="false" scope="page" />
 </tags:security>
<html>
	<head>
	<body>
	
										<div style="text-align:right;">
												<a id="${routingRuleId}" class="quickViewCancelLink"  href="javascript:void(0);"><b>Close </b></a>
											</div>
											<b style="font-size: 12px" class="viewRuleBuyer" id="quickViewBuyer">
												<a style="color:#00A0D2;" href="javascript:showRule()">View Rule</a> </b> 
												<c:if test="${autoAcceptStatus==true }">
												<b>|</b> 
												<b style="font-size: 12px" id="autoAcceptHistoryBuyer">
												<a style="color:#00A0D2;" href="javascript:showHistory();"> History</a></b>
												</c:if>
											<div class="ruleCommentsTextView" style="padding-top:5px;">
												<p>${ruleQuickView.ruleName}
												<p><b>Rule Status </b> : ${ruleQuickView.ruleStatus} 
												<p><b>Provider Firm </b> : ${ruleQuickView.providerFirm}
												<c:forEach var="content" items="${ruleQuickView.customReference}">   
						     						<p><b> ${content.key}</b> :    ${content.value}
						       	 				</c:forEach>
						        				<c:if test="${not empty ruleQuickView.pickUplocationCodes}">
													<p><b>Pick Up Location Code </b> : ${ruleQuickView.pickUplocationCodes}
												</c:if>
						        				<c:if test="${not empty ruleQuickView.zipCode}">
													<p><b>Zip Code  </b> : ${ruleQuickView.zipCode}
												</c:if>	
												<c:if test="${not empty ruleQuickView.market}">
													<p><b>Market  </b> : ${ruleQuickView.market}
												</c:if>	
												<c:if test="${not empty ruleQuickView.jobCodes}">
													<p><b>Job Codes </b> : ${ruleQuickView.jobCodes}
												</c:if>
												<p><b>Contact </b> : ${ruleQuickView.firstName} ${ruleQuickView.lastName} , ${ruleQuickView.email} 	
											</div>
										<div class="accpetHistoryDiv" style="display: none;padding-top:10px;"></div>
		</body>
		</html>