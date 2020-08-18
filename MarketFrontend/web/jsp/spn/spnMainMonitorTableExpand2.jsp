<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>  
<%@page import="com.newco.marketplace.interfaces.SPNConstants"%>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="selectedSPN" value="<%=(String)request.getAttribute(SPNConstants.SELECTED_SPN)%>"></c:set>   

<script type="text/javascript">
	$(document).ready(function() {
		$('.tabs').tabs();
	});
</script>


<h3 align="left"> ${spnMonitorVO.buyerName} ${spnMonitorVO.spnName}</h3>
<div class="content">			 
	<div class="clearfix" style="margin-bottom: 5px; margin-top: -15px;">
		<div class="right">
			<strong>Contact Information:</strong><br />
			${spnMonitorVO.contactName} <br>
			<a href="mailto:${spnMonitorVO.contactEmail}">${spnMonitorVO.contactEmail}</a><br />
			${spnMonitorVO.contactPhone}
		</div>
	</div>
	<div id="nextId" class="tabs">
		<ul class="clearfix">
			<c:if test="${spnMonitorVO.hasAlias && (spnMonitorVO.aliasProviderFirmState == 'PF SPN MEMBER' || spnMonitorVO.aliasProviderFirmState == 'PF FIRM OUT OF COMPLIANCE') && spnMonitorVO.isAliasEffective}">
				<li id="${spnMonitorVO.spnId}" class="tab1 tablink ui-tabs-active">
					<a href="spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId=${spnMonitorVO.spnId}">
					    Requirements
					</a>
					<br>
				</li>
			
			</c:if>
			
			<c:if test="${!(spnMonitorVO.hasAlias && (spnMonitorVO.aliasProviderFirmState == 'PF SPN MEMBER' || spnMonitorVO.aliasProviderFirmState == 'PF FIRM OUT OF COMPLIANCE') && spnMonitorVO.isAliasEffective)}">
				<li id="${spnMonitorVO.spnId}" class="tab1 tablink ui-tabs-active">
					<a href="spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId=${spnMonitorVO.spnId}">
						Requirements
					</a>
					<br>
				</li>
			</c:if>		
													 
			<li id="${spnInfoList}" class="tab1 tablink">
				<a href="spnMonitorAction_loadSPNInformationTabAjax.action?spnMonitorVO.spnId=${spnMonitorVO.spnId}">
					<abbr title="Select Provider Network">SPN</abbr>
					Information
				</a>
				<br>
			</li> 
		</ul>
	</div>
</div>