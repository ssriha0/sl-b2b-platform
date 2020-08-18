<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<div class="ui-tabs ui-widget ui-widget-content ui-corner-all" style="border-bottom:0px;">
<ul class="ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all">
	<li id="spnMonitorNetwork" class="ui-state-default ui-corner-top"><a href="${contextPath}/spnMonitorNetwork_display.action"><span><abbr title="Select Provider Network">SPN</abbr> Monitor</span></a></li>
	<li id="spnCreateNetwork" class="ui-state-default ui-corner-top"><a href="${contextPath}/spnCreateNetwork_display.action"><span>Create Network</span></a></li>
	<tags:authorities authorityName="TIER_ROUTE" > 
	 	<tags:authorities authorityName="SPN Routing Priorities" >
			<li id="spnRoutingTiers" class="ui-state-default ui-corner-top"><a href="<s:url value='spnReleaseTiers_display.action?spnBeingEdited=-1'/>"><span>Routing Priorities</span></a></li>
		</tags:authorities >
	</tags:authorities> 
	<tags:authorities authorityName="View Member Manager" >
			<li id="spnMemberManager" class="ui-state-default ui-corner-top"><a href="${contextPath}/spnMMSearchTabAction_display.action"><span>Member Manager</span></a></li>
	</tags:authorities >
	<li id="spnMonitorCampaign" class="ui-state-default ui-corner-top"><a href="<s:url value='spnMonitorCampaign_display.action'/>"><span>Campaign Monitor</span></a></li>
	<li id="spnCreateCampaign" class="ui-state-default ui-corner-top"><a href="<s:url value='spnCreateCampaign_display.action'/>"><span>Create Campaigns</span></a></li>
</ul>
</div>
<c:if test="${!empty param.tabName}">
	<script type="text/javascript">$("#<c:out value="${param.tabName}" />").addClass("ui-tabs-selected ui-state-active");</script> 
</c:if>

