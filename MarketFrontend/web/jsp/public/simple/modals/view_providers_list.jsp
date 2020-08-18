<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

	
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
	

<!-- NEW MODULE/ WIDGET-->
<div title="Providers" >

	<p style="font-size: 11px; padding-top: 5px">
		Your service order will be submitted to the following <strong>${fn:length(providers)}</strong> providers. The
		first provider to accept your order will be awarded your service
		contract.
	</p>
	<table class="provSelectHdr" style="width: 665px;">
		<tr>
			<td class="column2">
				% Match
			</td>
			<td class="column3">
				Provider
			</td>
			<td class="column4">
				ServiceLive<br/> Rating
			</td>
			<td class="column5">
				Order<br/> Completed
			</td>
			<td class="column6">
				Distance & <br/> Location
			</td>
		</tr>
	</table>
	<div class="grayTableContainer" style="margin: 0; vertical-align: middle; width: 665px; height: 290px">
		<table class="provReview" cellpadding="0" cellspacing="0" style="margin: 0; vertical-align: middle; width: 665px;">
			<c:forEach var="provider" items="${providers}" varStatus="providerIndex">
				<tr>
					<td class="column2">
						${provider.percentageMatch}
					</td>
					<td class="column3">						
						${provider.providerFirstName}&nbsp;${fn:substring(provider.providerLastName,0,1)}.&nbsp;(User Id#&nbsp;${provider.resourceId})
						<br/>
						<strong>Company ID#</strong> ${provider.vendorID} 
					</td>
					<td class="column4">
						<img src="${staticContextPath}/images/common/stars_<c:out value="${provider.providerStarRatingImage}"/>.gif" border="0" />
						<br/>
						&nbsp;(${provider.providerStarRating} ratings)
					</td>
					<td class="column5">&nbsp;
						${provider.totalSOCompleted}
					</td>
					<td class="column6">
						${provider.distance} mi
						<br/>
						${provider.city}, ${provider.state}
					</td>
				</tr>
			</c:forEach>

		</table>
	</div>
	<a href="#" class="jqmClose" style="font-size:12px;color: #333; margin-left: 300px">Close</a>
 
	
	
	

</div>


