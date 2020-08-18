<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", 0); //prevents caching at the proxy server
%>

<%-- <link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/spn-auditor.css" media="screen, projection"> --%>
<script type="text/javascript">	
	jQuery(document).ready(function($) {
		$('.expand_pf').unbind('click').click(function() {
			$(this).children('.pf_plus_minus').toggle();
			var expandRow = $(this).parent('strong').parent('td').parent('tr').next();
			expandRow.toggle();
			var expandArea = expandRow.find('.expand_pf_area');
			if (expandRow.is(':visible'))
			{
				var pfId = $(this).parent('strong').siblings('[name=pfId]').val();  
				var spnId = $(this).parent('strong').siblings('[name=spnId]').val();
				var origModDate = $(this).parent('strong').siblings('[name=origModDate]').val();
				var lockedRecord = $(this).parent('strong').siblings('[name=lockedRecord]').val();
				var lockedByMe = false;
				if($(this).parent('strong').siblings('[name=lockedByMe]').val() == 'true'){
				var lockedByMe = true;
				}
				expandArea.load('spnAuditorApplicantsTab_displayApplicantPanelAjax.action', { 'expandCriteriaVO.spnId': spnId, 'expandCriteriaVO.providerFirmId': pfId, 'expandCriteriaVO.originalModifiedDate': origModDate, 'expandCriteriaVO.fromSearch': '1', 'expandCriteriaVO.lockedRecord': lockedRecord, 'expandCriteriaVO.lockedByMe': lockedByMe });
			}
		});
		
		//loads the modal showing expiration details
		$('.expand_expiration').click(function() {
			$(this).children('.ex_plus_minus').toggle();
			var expandRow = $(this).parent('td').parent('tr').next();
			expandRow = expandRow.next();
			expandRow.toggle();
			var pfId = $(this).siblings('[name=pfId]').val(); 
			var spnId = $(this).siblings('[name=spnId]').val();
			var id = pfId+'_'+spnId;			
			$('#displayDiv_'+id).hide();
			$('#loadingDiv_'+id).show();
			var expandArea = expandRow.find('.expand_ex_area');
			if (expandRow.is(':visible'))
			{
				expandArea.load('spnAuditorApplicantsTab_displayExpirationPanelAjax.action', { 'expandCriteriaVO.networkId': spnId, 'expandCriteriaVO.providerFirmId': pfId, 'expandCriteriaVO.spnId': spnId });
			}
		});
		
		$('#viewAllLink').unbind('click').click(function() {
			var viewAll = $('#searchByViewAll').val();
			if (viewAll == 'false')
			{
				$('#viewAllLink').html('View 30');
				$('#searchByViewAll').val('true');
			}
			else
			{
				$('#viewAllLink').html('View All');
				$('#searchByViewAll').val('false');
			}
			
			submitSearch();
		});		
	});
</script> 

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
	
<c:choose>
<c:when test="${fn:length(searchResultsList) == 0}">
	<div class="clearfix success" style="margin-top: 10px;">
		No Provider Firms match the information you entered. Please check the information and try again.
	</div>
</c:when>
<c:otherwise>

<c:if test="${searchCriteriaVO.viewAll}">
	<div style="margin-top: 10px; font-weight: bold; padding: 3px; text-align: center; border-top: 1px solid #dddddd; border-left: 1px solid #dddddd; border-right: 1px solid #dddddd; width: 100px; background-color: #EEEEEE; right: 0px;"><a href="#" id="viewAllLink">View 30</a></div>
</c:if>
<c:if test="${!searchCriteriaVO.viewAll}">
	<div style="margin-top: 10px; font-weight: bold; padding: 3px; text-align: center; border-top: 1px solid #dddddd; border-left: 1px solid #dddddd; border-right: 1px solid #dddddd; width: 100px; background-color: #EEEEEE; right: 0px;"><a href="#" id="viewAllLink">View All</a></div>
</c:if>
<div class="tableWrapper">
	<table cellspacing="0" cellpadding="0">
		<thead>
			<tr>
				<th class="tc">
					Membership Status
				</th>
				<th class="tl">
					Provider Firm
				</th>
				<th class="tc">
					<abbr title="Select Provider Network">SPN</abbr>
				</th>
				<th class="tc">
					<span title="Count of Providers that have met all requirements for this specific select provider network">
						# SPN Approved Providers</span>
				</th>
				<th class="tc">					
					<span title="Count of Providers that work for this firm and approved by ServiceLive">
						# ServiceLive Approved Providers</span>
				</th>
				<th class="tl">
					&nbsp;
				</th>
			</tr>
		</thead>	
		<tbody>
			<c:forEach items="${searchResultsList}" var="aSPN">
			<c:set var="lockedByMe" value="false" />
			<c:if test="${not empty aSPN.lockedById && ((aSPN.lockedById == USER_OBJECT_IN_SESSION.userDetails.userChildId) 
						|| (2 == USER_OBJECT_IN_SESSION.userDetails.role.id && aSPN.lockedById == adminUserId))}">
				<c:set var="lockedByMe" value="true" />
			</c:if>
				<tr>
					<td class="tc" style="width:90px;">
						<c:if test="${aSPN.membershipStatusId == 'PF SPN APPLICANT' || aSPN.membershipStatusId == 'PF SPN MEMBERSHIP UNDER REVIEW' || aSPN.membershipStatusId == 'PF SPN REAPPLICANT'}">
							<img src="${staticContextPath}/images/common/status-white.png"
								alt="Pending Approval" />
						</c:if>
						<c:if test="${aSPN.membershipStatusId == 'PF INVITED TO SPN' || aSPN.membershipStatusId == 'PF SPN INTERESTED' || aSPN.membershipStatusId == 'PF APPLICANT INCOMPLETE'}">
							<img src="${staticContextPath}/images/common/status-yellow.png"
								alt="Invited" />
						</c:if>
						<c:if test="${aSPN.membershipStatusId == 'PF SPN NOT INTERESTED'}">
							<img src="${staticContextPath}/images/common/status-black.png"
								alt="Not Interested" />
						</c:if>
						<c:if test="${aSPN.membershipStatusId == 'PF SPN MEMBER'}">
							<img src="${staticContextPath}/images/common/status-green.png"
								alt="Member" />
						</c:if>
						<c:if test="${aSPN.membershipStatusId == 'PF FIRM OUT OF COMPLIANCE' || aSPN.membershipStatusId == 'PF SPN DECLINED'}">
							<img src="${staticContextPath}/images/common/status-red.png"
								alt="Membership Inactive" />
						</c:if>
						<br />
						<small>${aSPN.membershipStatusName}</small>
					</td>
					<td class="tl" style="width:200px;">
						<a id="#anchor_${aSPN.providerFirmId}_${aSPN.spnId}" name="#anchor_${aSPN.providerFirmId}_${aSPN.spnId}" />
						<strong><a class="expand_pf" href="#anchor_${aSPN.providerFirmId}_${aSPN.spnId}">${aSPN.providerFirmName}
						<img class="pf_plus_minus" style="vertical-align: bottom;" src="${staticContextPath}/images/common/plus.gif" />
						<img class="pf_plus_minus" style="vertical-align: bottom; display: none;" src="${staticContextPath}/images/common/minus.gif" />
						</a></strong>						
						<br />
						<!-- SL-17473: displaying member compliance status -->
						<c:if test="${aSPN.memberStatus=='Attention Needed'}">
							<div class="expand_expiration">	
								<b style="color: orange;">${aSPN.memberStatus}</b>							
								<span class="ex_plus_minus" style="background-color:orange;" >+</span>
								<span class="ex_plus_minus" style="display: none;background-color:orange;">-</span>
							</div>
						</c:if>
						<c:if test="${aSPN.memberStatus=='Action Required' }">	
							<div class="expand_expiration">	
								<b style="color: red;">${aSPN.memberStatus}</b>							
								<span class="ex_plus_minus" style="background-color:red;" >+</span>
								<span class="ex_plus_minus" style="display: none;background-color:red;">-</span>
							</div>
						</c:if>				
						ID #${aSPN.providerFirmId}
						<br />
						<strong>ServiceLive Status:</strong> ${aSPN.providerFirmSLStatus}
						<input type="hidden" name="spnId" value="${aSPN.spnId}" />
						<input type="hidden" name="pfId" value="${aSPN.providerFirmId}" />
						<input type="hidden" id="searchOrigModDate_${aSPN.providerFirmId}_${aSPN.spnId}" name="origModDate" value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${aSPN.modifiedDt}" />" />
						<input type="hidden" name="lockedRecord" value="${aSPN.lockedRecord}" />
						<input type="hidden" name="lockedByMe" value="${lockedByMe}" />
						</a>
					</td>
					<td class="tc" style="width:250px;">
						${aSPN.spnName}
					</td>
					<td class="tc" style="width:110px;">
						${aSPN.numSPNEmployees}
					</td>
					<td class="tc" style="width:150px;">
						${aSPN.numEmployees}
					</td>
					<td class="tl" style="width:3x;">
						<c:if test="${aSPN.lockedRecord}">
							<span class="locked">Locked by: <br /> ${aSPN.lockedByFirstName} ${aSPN.lockedByLastName} #${aSPN.lockedById}
								</span>
						</c:if>
						<c:if test="${!aSPN.lockedRecord}">
							&nbsp;
						</c:if>
					</td>
				</tr>
				<tr style="display: none;"><td colspan="5">
					<div class="expand_pf_area"></div>
				</td></tr>
				<tr style="display: none;"><td colspan="5">
					<div id="${aSPN.providerFirmId}_${aSPN.spnId}" class="expand_ex_area"></div>
				</td></tr>
			</c:forEach>
		</tbody>
	</table>
</div>
</c:otherwise>
</c:choose>

<div id="loadSPNSpinner" class="jqmWindow">
	<div class="modal-content">
		<label><span>Gathering search results, please wait...</span></label>
		<div>
			<img src="${staticContextPath}/images/simple/searchloading.gif" />
		</div>
		<div class="clearfix">
			<a class="cancel jqmClose left" href="#">Cancel</a>
		</div>
	</div>
</div>