<h3 align="left">${row.buyerName} ${row.spnName}</h3>
<div class="content">			 
	<div class="clearfix" style="margin-bottom: -25px; margin-top: -15px;">
		<div class="right">
			<strong>Contact Information:</strong><br />
			${row.contactName} <br>
			<a href="mailto:${row.contactEmail}">${row.contactEmail}</a><br />
			${row.contactPhone}
		</div>
	</div>
	<div class="tabs">
		<ul class="clearfix">
			<c:if test="${row.hasAlias && (row.aliasProviderFirmState == 'PF SPN MEMBER' || row.aliasProviderFirmState == 'PF FIRM OUT OF COMPLIANCE') && row.isAliasEffective}">
				<li id="${criteriaList}" class="tab1 tablink"><a href="spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId=${row.spnId}">Requirements</a><br></li>
			</c:if>	
			<c:if test="${!(row.hasAlias && (row.aliasProviderFirmState == 'PF SPN MEMBER' || row.aliasProviderFirmState == 'PF FIRM OUT OF COMPLIANCE') && row.isAliasEffective)}">
				<li id="${criteriaList}" class="tab1 tablink"><a href="spnMonitorAction_loadSPNRequirementsTabAjax.action?spnMonitorVO.spnId=${row.spnId}">Requirements</a><br></li>
			</c:if>												 
			<li id="${spnInfoList}" class="tab1 tablink"><a href="spnMonitorAction_loadSPNInformationTabAjax.action?spnMonitorVO.spnId=${row.spnId}"><abbr title="Select Provider Network">SPN</abbr> Information</a><br></li> 
		</ul>
	</div>
</div>