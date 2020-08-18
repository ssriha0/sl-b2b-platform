<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:if test="${!empty spnCommMonitorList}">


<script type="text/javascript">
	function showAgreementModal(spnId) { 
		jQuery.noConflict();
		jQuery(document).ready(function($) {			
	    	$('#try').jqm({ajax:'spnBuyerAgreeModal.action?&spnID='+spnId, toTop: true});
		   	$('#try').jqmShow();
		});					
	}
</script>



<div class="dashboardCommMonitorBody" style="height: auto; border-bottom: 0px;">
<div id="commMonitorBar">Communication Monitor</div>
	<table cellpadding="0px" cellspacing="0px" class="globalTableLook">
		<thead>
			<tr class=""> 
				<th class="col1 first odd">From</th>
				<th class="col2 even textleft">Subject</th>
				<th class="col3 odd textleft">Date / Time</th>
			</tr>
		</thead>
		<c:forEach items="${spnCommMonitorList}" var="row">
			<tr>
				<td class="col1 first odd">${row.businessName}</td>
				<td class="col2 even textleft">
					<c:set var="expireText" value="Expires: "/>
					<c:choose>
						<c:when test="${row.isInvitation== '1' or row.isNotInterestedInv== '1'}">
							<c:choose>
								<c:when test="${row.isMember}">
									<a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action?selectedSpnId=${row.spnId}">New requirements for membership in ${row.spnName}</a>
								</c:when>
								<c:when test="${row.isInvExpired == 1}"> 
			 						${row.communicationSubject}
									<c:set var="expireText" value="Expired: "/>
			 					</c:when>
			 					<c:otherwise>
			 						<a href="${contextPath}/spnProviderInvitation_loadInvitation.action?&spnID=${row.spnId}">${row.communicationSubject}</a>
			 					</c:otherwise>
		 					</c:choose>
	 					</c:when>
	 					<c:otherwise>
	 						<c:choose>
		 						<c:when test="${row.pfInvStatus == 'PF SPN DECLINED'}">
		 							${row.communicationSubject}
		 						</c:when>
		 						<c:otherwise>
		 							<a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action?selectedSpnId=${row.spnId}">${row.communicationSubject}</a>
		 						</c:otherwise>
	 						</c:choose>
	 					</c:otherwise>
 					</c:choose>
				</td>
				<td class="col3 last odd">
					<c:choose>
						<c:when test="${row.isMember}">
							<div style="text-align: left; font-weight: bold;">EFFECTIVE DATE: <fmt:formatDate value="${row.effectiveDate}" pattern ="MM/dd/yyyy"/></div>
							<div style="text-align: left;"><fmt:formatDate value="${row.providerFirmStateModifiedDate}" pattern ="MM/dd/yyyy HH:MM a z"/></div>
						</c:when>
						<c:when test="${row.pfInvStatus != 'PF SPN DECLINED'}">
							<div align ="left"><fmt:formatDate value="${row.invModifiedDate}" pattern ="MM/dd/yyyy HH:MM a z"/></div>				 	
							<div align ="left">${expireText}<fmt:formatDate  value="${row.campEndDate}" pattern ="MM/dd/yyyy"/></div>
						</c:when>	
						<c:otherwise>
							<div align ="left"><fmt:formatDate  value="${row.providerFirmStateModifiedDate}" pattern ="MM/dd/yyyy HH:MM a z"/></div>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
</c:if>
