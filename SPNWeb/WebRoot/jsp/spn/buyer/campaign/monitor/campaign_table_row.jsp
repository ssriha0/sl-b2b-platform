<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="CAMPAIGN_PENDING" scope="page" value="<%=com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_PENDING%>"/>
<c:set var="CAMPAIGN_ACTIVE" scope="page" value="<%=com.servicelive.spn.common.SPNBackendConstants.WF_STATUS_CAMPAIGN_ACTIVE%>"/>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="seconds" value="<%=String.valueOf(new java.util.Date().getTime())%>"  />

<tr>
	<td class="br">
		<img src="${staticContextPath}/images/common/status-${campaign.wfStatusColor}.png" />
		<br /><span class="sm"><abbr title="${campaign.wfStatus}">${campaign.wfStatus}</abbr></span>
	</td>
	<td class="textleft br">
					<strong>		
						<a href="#campaign-<s:property value='campaignId' />" class="nwk-title">
							<span>${campaign.campaignName}
							</span>				
							<span class="open">
							</span>
							<span class="closed">
							</span>
						</a>					
					</strong>					
					<br clear="both">				
					<span class="sm">${campaign.dates}</span>
	</td>
	<td class="br">
		${campaign.networkName}
	</td>
	<td class="highlight ">
		<abbr title="# firms with # total available providers have been invited but haven't yet responded.">
			${campaign.invitedPros.providerFirmCounts}/${campaign.invitedPros.providerCounts}
		</abbr>
	</td>
</tr>
<tr class="info">
	<td colspan="4">
		<div class="network-title textleft">${campaign.campaignName}</div>
		<div class="content clearfix">
			<div class="edit" style="position:inherit; height:40px;">
				<table width="100%">
					<tr>
						<td width="60%">
							&nbsp; 
						</td>
						<c:if test="${CAMPAIGN_ACTIVE == campaign.wfStatusId}">
							<td>
								<s:form id="monitorCampaignStopForm" name="monitorCampaignStopForm" action="spnMonitorCampaign_stopCampaign">
									<s:hidden name="campaignId" id="campaginId" value="%{#request['campaign'].campaignId}" />
									<s:submit  type="input"
										method="stopCampaign"
										cssClass="default right"
										theme="simple"
										value="Stop Campaign" />
								</s:form>
							</td>
						</c:if>			
					<c:if test="${CAMPAIGN_PENDING == campaign.wfStatusId}">
						<td>
							<tags:authorities authorityName="Approve Campaign" > 
								<div>
									<input type="hidden" name="campaignId" id="campaignId" value="${campaign.campaignId}" />
									<input type="submit" class="action right jqMApprove" style="margin-right: 5px; margin-top: 4px;" value="Approve Campaign"/>
								</div>
								
								<div id="alert-${campaign.campaignId}" class="jqmWindow">
									<div class="modal-header clearfix"><a href="#" class="right jqmClose">Close</a></div>
									<div class="modal-content">
										<label>Review Comments/Changes Made:</label>
										<s:form id="monitorCampaignApproveForm" name="monitorCampaignApproveForm" action="spnMonitorCampaign_approveCampaign">
											<input type="hidden" name="campaignId" id="campaignId" value="${campaign.campaignId}" />
											<textarea name="approveComments"></textarea>
											<div class="clearfix">
												<a class="cancel jqmClose left" href="#">Cancel</a>
												<input type="submit" class="action right" value="Done">
											</div>
										</s:form>
									</div>
								</div>
							</tags:authorities >
						</td> 
					</c:if>
				
					<td>								
						<s:form id="monitorCampaignForm" name="monitorCampaignForm" action="spnCreateCampaign_display">
							<input type="hidden" name="campaignHeader.campaignId" id="campaignHeader.campaignId" value="${campaign.campaignId}" />
							<%-- <s:hidden name="campaignHeader.campaignId" id="campaignHeader.campaignId" value="%{#request['campaign'].campaignId}" /> --%>
							<s:submit type="input" cssClass="right default"
								theme="simple" value="Edit Campaign" cssStyle="margin-right: 5px;"/>
						</s:form>
					</td>
				</tr>
			</table>
			</div>
			
			<div class="subTabs" id="subTabsCampaign${campaign.campaignId}">
	       	     	<ul>
	          	      	<li><a href="${contextPath}/spnMonitorCampaign_displayTabCampaignDetailsAjax.action?campaignId=${campaign.campaignId}&randomNum=${seconds}"><span>Campaign Details</span></a></li>
	        	       	<li>	        	       		
	        	       		<a href="${contextPath}/spnMonitorCampaign_displayTabCampaignHistoryAjax.action?campaignId=${campaign.campaignId}&randomNum=${seconds}">
	        	       			<span>Campaign History</span>
	        	       		</a>	        	       		
	        	       	</li>
	        	       	<li><a href="${contextPath}/spnMonitorCampaign_displayTabNetworkDetailsAjax.action?networkId=${campaign.spnId}&randomNum=${seconds}"><span><abbr title="Select Provider Network">SPN</abbr> Details</span></a></li>
	            	</ul>
			</div>
			<script type="text/javascript">
			$("#subTabsCampaign${campaign.campaignId}").tabs();
			</script>
		</div>		
	</td>
</tr>
