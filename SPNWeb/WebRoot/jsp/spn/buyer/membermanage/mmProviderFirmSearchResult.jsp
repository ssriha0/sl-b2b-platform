<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>		

<div class="tableWrapper">
	<table id="spnMMSearchResultsTable" cellspacing="0" cellpadding="0" border="0">
		<thead>
			<tr>
				<th class="sortable" style="background-position: 90px 7px">Provider Firm</th>
				<th class="sortable">SL Status</th>
				<th class="nosort">Networks</th>
				<th class="nosort"  style="text-align:center" ># of <br/> Providers</th>
				
			</tr>
		</thead>	
		<tbody>
			<c:forEach items="${searchResultsList}" var="aPro">
				<tr>
					<td>
						<a
							href="#"
							class="proName"
							onclick="javascript:openProviderFirmProfile(${aPro.providerFirm.id})">
							${aPro.providerFirm.businessName}
						</a>
						<br/>ID #${aPro.providerFirm.id}
						
					</td>
					<td>
						${aPro.providerFirm.serviceLiveWorkFlowState.wfState}
					</td>
					<td class="colNetworks">
					<c:set var="threePlus" value="false" />
					<c:set var="totalNetworks" value="${0}" />
			<c:forEach items="${aPro.providerFirmNetworkInfo}" var="aSPN" varStatus="status">
						<c:set var="iconLabel" value=""/>
			
			<c:choose>			
				<c:when test="${status.index < 3}">
				<c:choose>
					<c:when test="${aSPN.wfState.statusIcon == 'green'}"><c:set var="iconLabel" value="Network Firm"/></c:when>
					<c:when test="${aSPN.wfState.statusIcon == 'red'}"><c:set var="iconLabel" value="Inactive - a required credential or criterion is out of compliance"/></c:when>
					<c:when test="${aSPN.wfState.statusIcon == 'black'}"><c:set var="iconLabel" value="Removed - the firm has been removed from the network"/></c:when>
				</c:choose>
						 <img src="/ServiceLiveWebUtil/images/common/status-<c:out value="${aSPN.wfState.statusIcon}" default="transp"/>.png" title="${iconLabel}" alt="${iconLabel}" /> 
								    <c:out value="${fn:replace(fn:replace(aSPN.providerFirmStatePk.spnHeader.spnName, 'Select Provider Network',''),'SELECT PROVIDER NETWORK','')}" /> 
								   <br/>
				</c:when>   
				<c:otherwise>
					<c:set var="threePlus" value="true" />
				</c:otherwise>
			</c:choose>
			
			<c:set var="totalNetworks" value="${totalNetworks+1}" />
			</c:forEach>
			<c:if test="${threePlus}"><span class="msg">Showing 3 of ${totalNetworks} networks</span></c:if>
					</td>
					<td class="proCount">
					  ${aPro.providerFirm.serviceProCount}
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>