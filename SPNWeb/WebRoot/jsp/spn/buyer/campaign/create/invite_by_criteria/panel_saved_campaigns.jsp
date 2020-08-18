<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%-- 
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<h3 class="collapse c-saved" title="c-saved">
	<span>Saved Campaigns</span><span class="plus c-saved"></span><span
		class="min c-saved"></span>
</h3>
<div class="toggle c-saved">
	<fieldset>
		<table id="saved-campaigns" border="0" cellpadding="0" cellspacing="0">
			<c:forEach var="campaign" items="${campaignList}">
				<tr>
					<td>
						<strong>${campaign.campaignName}</strong>
						<br />
						<span class="sm">Last Run: <fmt:formatDate value="${campaign.startDate}" pattern="MM-dd-yyyy" /> - <fmt:formatDate value="${campaign.endDate}" pattern="MM-dd-yyyy" /></span>
					</td>
					<td class="textcenter">
						###
					</td>
					<td class="textright">
					<input type="button" id="selectSpn"
						onclick="loadCampaign('spnCreateCampaign_loadSelectedCampaignDataAjax', 'campaignHeader.campaignId=${campaign.campaignId}')"
						value="Select"/>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
</div>


