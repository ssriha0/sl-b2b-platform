<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="highlight" value="highlight" scope="request"/>
<c:if test="${rowstatus.index % 2 == 0}">
<c:set var="highlight" value="highlightLite"  scope="request"/>
</c:if>

<tr>


	<td class="textleft">
		<!-- please replace network-id with a number for the id.  Example: network-001 -->
		<a name="network-id" id="network-<s:property value='spnId' />"></a>
		<a href="#network-<s:property value='spnId' />" id="spn-<s:property value='spnId' />"
			onclick="clickViewDetails(<s:property value='spnId' />);"
			class="nwk-title nwk-title-<s:property value='spnId' />"><s:property value="spnName" />&nbsp;<span class="open"></span>&nbsp;<span class="closed"></span></a>
		<br /><span class="highlight disabled"> </span>
		<s:if test="aliasSPN != null">
		  <div style="border-top:1px dotted #666; font-size: 75%;"> <s:property value="aliasSPN.spnName"/></div>
		</s:if>
	</td>
	<td class="blr "><abbr title="# campaigns with # currently active."><s:property value="totalActiveCampaign" /></abbr></td>
	<td class="br ${highlight} smaller">
		<abbr title="# firms with # total available providers have been invited but haven't yet responded.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="invitedPros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="invitedPros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller">		
		<abbr title="# firms with # total available providers replied as interested and have not yet completed their application.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="interestedPros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="interestedPros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller">		
		<abbr title="# firms with # available providers have declined the invitation.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="notInterestedPros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="notInterestedPros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller">
		<abbr title="# firms have submitted an application with # total available providers.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="appliedPros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="appliedPros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller">
		<abbr title="# firms have submitted an application with # total available providers.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="declinePros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="declinePros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller">
		<abbr title="# firms have Incomplete application with # total available providers.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="incompletePros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="incompletePros.providerCounts" /></div>
		</abbr>
	</td>
	<td class="br ${highlight} smaller"><abbr title="# firms with # available providers are members that are out of compliance with the SPN requirements.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="firmOutOfCompliancePros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;">&nbsp;<s:property value="oocServiceProCounts" /></div>
		</abbr>
		<s:if test="aliasSPN != null">
		  <div class="left smaller subcellLeft" style="width:49%; border-right:1px dotted #666;"><s:property value="aliasSPN.firmOutOfCompliancePros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller subcellRight " style="width:49%;"><s:property value="aliasSPN.oocServiceProCounts" />  </div>
		
		</s:if>
		</td>
	<td class="br ${highlight} smaller"><abbr title="# of active Firms meeting new SPN requirements and available for order routing.">
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="activePros.providerFirmCounts" />&nbsp;</div>
		</abbr>
		<abbr title="# of active Providers meeting new SPN requirements and available for order routing.">
		<div class="left smaller" style="width:49%;"><s:property value="activeServiceProCounts" /></div>
	</abbr>
		<s:if test="aliasSPN != null">
		  
		  <abbr title="# of active Firms not yet meeting new SPN requirements and available for order routing.">
		  <div class="left smaller subcellLeft" style="width:49%; border-right:1px dotted #666;"> <s:property value="aliasSPN.activePros.providerFirmCounts" />&nbsp;</div>
		</abbr>
		<abbr title="# of active Providers not yet meeting new SPN requirements and available for order routing.">
		<div class="left smaller subcellRight" style="width:49%;"><s:property value="aliasSPN.activeServiceProCounts" />   </div>
		</abbr>
		</s:if>
	
	</td>
	<td class="br ${highlight} smaller"><abbr title="# firms with # available providers are in Removed Status ">
	  
		<div class="left smaller" style="width:49%; border-right:1px dotted #666;"><s:property value="removedPros.providerFirmCounts" />&nbsp;</div>
		<div class="left smaller" style="width:49%;"><s:property value="removedServiceoProCounts" /></div>
	
	</abbr>
		<s:if test="aliasSPN != null">
		 
		 	 <div class="left smaller subcellLeft" style="width:49%; border-right:1px dotted #666;"> <s:property value="aliasSPN.removedPros.providerFirmCounts" />&nbsp;</div>
			 <div class="left smaller subcellRight" style="width:49%;"><s:property value="aliasSPN.removedServiceoProCounts" />  </div>
		  
		</s:if>
	</td>
	<!-- if a user has rights to be an admin -->
	<!-- <td class="admin disabled"><a href="#"><img src="/ServiceLiveWebUtil/images/s_icons/cancel-disabled.png" alt="Delete" /></a></td> -->
	<!-- endif -->


</tr>
<tr class="spn-info info i-<s:property value='spnId' />">
	<td colspan="11">
		<div class="network-title" style="width:99%; text-align:left; text-indent:10px;">
			<s:property value="spnName" />
		</div>
		
		<div class="error hide" id="errorEditNetwork_${spnitr.spnId}"></div>
		
		<div class="content clearfix">
			<div class="edit">		
					<s:submit onclick="return goViewExceptions('%{#request['spnitr'].spnId}');" type="input"
						cssClass="action right" theme="simple" value="Manage Exceptions" cssStyle="margin-right: 10px; margin-top:0px;"/>					
				<s:form action="spnCreateNetwork_display" id="editNetworkForm_%{#request['spnitr'].spnId}" 
						method="post" enctype="multipart/form-data" theme="simple">
					<s:hidden name="spnid" id="spnid" value="%{#request['spnitr'].spnId}" />
					<s:submit onclick="return clickEditNetwork('%{#request['spnitr'].spnId}');" type="input"
						cssClass="action right" theme="simple" value="Edit Network" cssStyle="margin-right: 10px; margin-top:0px;"/>
				</s:form>
				
			</div>
		<div id="spnDetailsTabs<s:property value='spnId' />" name="spnDetailsTabs<s:property value='spnId' />">
			<ul id="spnDetailsTabsList">
				<li>
					<a href="#tabsSpnDetails<s:property value='spnId' />" onclick="clickViewDetails(${spnitr.spnId})">
						<span>
							<abbr title="Select Provider Network">SPN</abbr> Details
						</span>
					</a>
				</li>
				<li>
				
					<a href="#tabsSpnExceptions<s:property value='spnId' />" onclick="clickViewExceptions(<s:property value='spnId' />);" id="clickExceptions<s:property value='spnId' />">
						Exceptions
					</a>
				</li>
				<li>
				
					<a href="#tabsSpnCompliance<s:property value='spnId' />" onclick="clickViewCompliance(<s:property value='spnId' />);" id="clickComplaince<s:property value='spnId' />">
						Member Compliance
					</a>
				</li>
				
				<tags:authorities authorityName="TIER_ROUTE" >
					<tags:authorities authorityName="SPN Routing Priorities" >
					<li>
						<a href="#tabsRoutingTiers<s:property value='spnId' />" onclick="clickViewRoutingTiers(<s:property value='spnId' />);">
							Routing Priority
						</a>
					</li>
					</tags:authorities> 
				</tags:authorities> 
				<li>
				
					<a href="#tabsHistory<s:property value='spnId' />" onclick="clickNetworkHistory(<s:property value='spnId' />);">
						Network History
					</a>
				</li>
			</ul>
			<div id="tabsSpnDetails<s:property value='spnId' />">
				<div class="ui-tabs-panel" id="networkDetails-<s:property value='spnId' />" >
					<%-- This is where the View Network Details Ajax call will put its output tab_network_details.jsp --%>
				</div>
			</div>
			<div id="tabsSpnExceptions<s:property value='spnId' />">		
				<a href="#exceptionsLoad-<s:property value='spnId'/>" id="#exceptionsLoad-<s:property value='spnId'/>"></a>
				<div id="spnExceptions-<s:property value='spnId' />">
					<%--  throw spn exceptions in here --%>
				</div>
			</div>
			<div id="tabsSpnCompliance<s:property value='spnId' />">		
				<a href="#complianceLoad-<s:property value='spnId'/>" id="#complianceLoad-<s:property value='spnId'/>"></a>
				<div id="spnCompliance-<s:property value='spnId' />">
					<%--  throw spn exceptions in here --%>
				</div>
			</div>
			<div id="tabsRoutingTiers<s:property value='spnId' />">
				<div class="ui-tabs-panel textleft" id="networkRoutingTiers-<s:property value='spnId'/>" style="padding:0px">
					<%-- This is where the Routing Tiers Ajax call will put its output tab_network_details.jsp --%>
				</div>
			</div>
			<div id="tabsHistory<s:property value='spnId' />">
				<div id="networkHistory-<s:property value='spnId' />" style="padding:0px">
					<%--  throw campaign history in here --%>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(function(){
				$("#spnDetailsTabs<s:property value='spnId' />").tabs();
			});
		</script>	
		</div>
	</td>
</tr>
