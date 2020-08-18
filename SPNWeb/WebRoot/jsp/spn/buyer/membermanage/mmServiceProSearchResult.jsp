<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<div class="tableWrapper">
		<table cellspacing="0" cellpadding="0" border="0" id="spnMMSearchResultsTable">
		<thead>
			<tr>
				<th class="sortable">Provider</th>
				<th class="sortable">SL Status<br/><span>Company / Provider</span></th>
				<th class="nosort">Networks</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${searchResultsList}" var="aPro">
			<tr>
					<td>
						<a href="#" class="proName" onclick="javascript:openProviderProfile(${aPro.serviceProvider.id},${aPro.serviceProvider.providerFirm.id}, '/MarketFrontend/')">
							${aPro.serviceProvider.contact.firstName} ${aPro.serviceProvider.contact.lastName}</a>
							<br/>(ID #${aPro.serviceProvider.id})
						<br/>Company&nbsp;ID&nbsp;#${aPro.serviceProvider.providerFirm.id}
					</td>
					<td>
						${aPro.serviceProvider.providerFirm.serviceLiveWorkFlowState.wfState} / ${aPro.serviceProvider.serviceLiveWorkFlowId.wfState}
					</td>
					<td class="colNetworks">
						<c:set var="threePlus" value="false" />
						<c:set var="totalNetworks" value="${0}" />
						<table style ="border:0px;">
			<c:forEach items="${aPro.serviceProviderNetworkInfo}" var="aSPN" varStatus="status">
			<c:set var="iconLabel" value=""/>
			<c:choose>
			<c:when test="${status.index < 3}">
			<c:choose>
				<c:when test="${aSPN.wfState.statusIcon == 'green'}"><c:set var="iconLabel" value="Network Provider"/></c:when>
				<c:when test="${aSPN.wfState.statusIcon == 'red'}"><c:set var="iconLabel" value="Inactive - a required credential or criterion is out of compliance"/></c:when>
				<c:when test="${aSPN.wfState.statusIcon == 'black'}"><c:set var="iconLabel" value="Removed - the provider has been removed from the network"/></c:when>
			</c:choose>
			
			<tr>
				<td class="colNetworks" style= "border-bottom:0px;padding:0px">
				<img src="/ServiceLiveWebUtil/images/common/status-<c:out value="${aSPN.wfState.statusIcon}" default="transp"/>.png" title="${iconLabel}" alt="${iconLabel}" />
				</td>
				<td class="colNetworks"  style= "border-bottom:0px;padding:0px;vertical-align:middle;" > 
				    <c:out value="${fn:replace(fn:replace(aSPN.serviceProviderStatePk.spnHeader.spnName, 'Select Provider Network',''),'SELECT PROVIDER NETWORK','')}" /> 
					<img src="/ServiceLiveWebUtil/images/spn/priority${aSPN.performanceLevel.id}small.gif" title="${aSPN.performanceLevel.description}" /><br/>
				</td>
			</tr>
			
			</c:when>
			<c:otherwise>
				<c:set var="threePlus" value="true" />
			</c:otherwise>
			</c:choose>
			<c:set var="totalNetworks" value="${totalNetworks+1}" />
			</c:forEach>
			</table>
			<c:if test="${threePlus}"><span class="msg">Showing 3 of ${totalNetworks} networks</span></c:if>
					</td>
				</tr>
		</c:forEach>
		</tbody>
	</table>
</div>