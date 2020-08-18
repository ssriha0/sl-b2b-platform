<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"  %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


	<h4 class="header4">
		Routing Priority
	</h4>
	<c:choose>
	<c:when test="${hideEditLink == 1}">
	</c:when>
	<c:otherwise>
		<a
			href="${contextPath}/spn/spnReleaseTiers_display.action?spnBeingEdited=${spnBeingEdited}">
			<c:choose>
			<c:when test="${fn:length(releaseTiers) == 0}">
			<h5 class="header5" style="cursor:pointer;">
				Set Routing Priorities
			</h5>
			</c:when>
			<c:otherwise>
			<h5 class="header5" style="cursor:pointer;">
				Edit Routing Priorities
			</h5>
			</c:otherwise>
			</c:choose>
		</a>	
	</c:otherwise>
	</c:choose>
	<c:choose>
	<c:when test="${fn:length(releaseTiers) == 0}">
		<br>
		<div style="clear:both" class="noRoutingPrioritiesAlert">
			This network does not have routing priorities created. Click 'Set Routing Priorities' to add priorities for this network.
		</div>
	</c:when>
	<c:otherwise>
	<table id="routingTiersTable" cellpadding="0" cellspacing="0" style="border-bottom: none!important;">
		<tr>
			<td style="border-bottom: 1px solid #CCCCCC!important;border-right: 1px solid #CCCCCC!important;border-left:none!important;">
					<b>Priority</b>
			</td>
			<td style="border-bottom: 1px solid #CCCCCC!important;border-right: 1px solid #CCCCCC!important;border-left:none!important;">
					<b>Number of ${perfCriteriaLevel}s</b>
			</td>
			<td style="border-right:1px solid #CCCCCC!important;border-bottom: 1px solid #CCCCCC!important;border-left:none!important;">
					<b>Early Access Time</b>
			</td>
		</tr>
	
		<c:forEach items="${releaseTiers}" var="tier" varStatus="status">
			<tr>
				<td style="border-bottom: 1px solid #CCCCCC!important;border-right: 1px solid #CCCCCC!important;border-left:none!important;">
					${tier.tierId}
				</td>
				<td style="border-bottom: 1px solid #CCCCCC!important;border-right: 1px solid #CCCCCC!important;border-left:none!important;">
					<%-- <c:forEach items="${tier.tierPerformanceLevels}" var="pl" varStatus="status">	
						<img  src="${staticContextPath}/images/spn/priority${pl}small.gif"  alt="<s:property value='%{#perfLevel.label}'/>" >
					</c:forEach> --%>
					<c:choose>
					<c:when test="${tier.tierId==4}">
					Remaining
					</c:when>
					<c:otherwise>
					<c:choose>
					<c:when test="${status.index==0}">
					 Top ${tier.tierMinute.noOfMembers}
					</c:when>   
					<c:otherwise>
					 Next ${tier.tierMinute.noOfMembers}
					</c:otherwise>
					</c:choose>
					</c:otherwise>
					</c:choose>
				</td>
				<td style="border-bottom: 1px solid #CCCCCC!important;border-right: 1px solid #CCCCCC!important;border-left:none!important;">
				<c:choose>
				<c:when test="${tier.tierMinute.advancedDays > 0}">
					<c:choose>
					<c:when test="${tier.tierMinute.advancedDays > 1}">
					${tier.tierMinute.advancedDays} days
					</c:when>
					<c:otherwise>
					${tier.tierMinute.advancedDays} day
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${tier.tierMinute.advancedHours > 0}">
					<c:choose>
					<c:when test="${tier.tierMinute.advancedHours > 1}">
					${tier.tierMinute.advancedHours} hours
					</c:when>
					<c:otherwise>
					${tier.tierMinute.advancedHours} hour
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:when test="${tier.tierMinute.advancedMinutes > 0}">
					<c:choose>
					<c:when test="${tier.tierMinute.advancedMinutes > 1}">
					${tier.tierMinute.advancedMinutes} minutes
					</c:when>
					<c:otherwise>
					${tier.tierMinute.advancedMinutes} minute
					</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					-
				</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</c:forEach>
	
	</table>
	
	<div style="text-align: left;" class="marketplaceOverflowContainer">
		<label for="marketplaceOverflow" style="vertical-align: middle;">
			Marketplace Overflow
		</label>
			<c:choose>
			<c:when test="${marketplaceOverflow == 1}">
				Yes
			</c:when>
			<c:otherwise>
				No
			</c:otherwise>
			</c:choose>
			<%-- 
		<s:select id="marketplaceOverflow" name="marketplaceOverflow"
			list="yesNoOptions" theme="simple" listKey="value" listValue="label"
			disabled="true" cssStyle="vertical-align:middle;"
			value="${marketplaceOverflow}" />
				--%>
		<a href="" style="vertical-align: middle;" id="whatisMplcOverflow" onclick="return false;"><img
				src="${staticContextPath}/images/icons/info.gif" width="16"
				height="16" border="0" style="vertical-align: absmiddle;" /> </a> What
		is Marketplace Overflow?
	</div>
	</c:otherwise>
	</c:choose>
<%-- ss: this is the code for the Marketpalce Overflow explainer popup --%>
<script type="text/javascript">
jQuery(document).ready(function($){
$("#whatisMplcOverflow").mouseover(function(e){
      var x = e.pageX;
      var y = e.pageY;
      $("#explainer-marketplaceoverflow").css("top",y-20);
      $("#explainer-marketplaceoverflow").css("left",x+20);
      $("#explainer-marketplaceoverflow").show();
     
 	}); 
 	$("#whatisMplcOverflow").mouseout(function(e){
 		$("#explainer-marketplaceoverflow").hide();
 	});
});
</script>
<div id="explainer-marketplaceoverflow" style="display:none; position:absolute; width:205px; height:125px; padding:10px 20px; line-height: 130%; font-size:11px;
 background: url(${staticContextPath}/images/common/explainerBg.gif) no-repeat;">
		<h5>Marketplace Overflow</h5>
		<p>Allows orders to route outside of your network AFTER each member has received the order. This is helpful where your network has lower market coverage.</p>
		</div>
<%-- /end explaier code --%>		

