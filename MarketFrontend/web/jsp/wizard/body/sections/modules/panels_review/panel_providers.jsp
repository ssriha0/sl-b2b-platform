<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!-- NEW MODULE/ WIDGET-->
<div dojoType="dijit.TitlePane" title="Providers"
	class="contentWellPane">
	<p>
		Your service order will be submitted to the following providers. The
		first provider to accept your order will be awarded your service
		contract. Need to edit the list? Click <strong> Previous </strong> button below to navigate to the <strong> Providers tab </strong>
	</p>
	<p>
		<strong>${fn:length(reviewDTO.providersList)} providers selected for posting</strong>
	</p>
	<table class="provReviewHdr" cellpadding="0" cellspacing="0"
		style="margin: 0; vertical-align: middle; width: 665px;">
		<tr>

			<td class="column2">
				% Match
			</td>
			<td class="column3">
				Provider
			</td>
			<td class="column4">
				Total<br> Orders
			</td>
			<td class="column5">
				My Orders
			</td>
			<td class="column6">
				Distance & <br /> Location
			</td>
			<td style="width: 20px"></td>
		</tr>
	</table>
	<div class="grayTableContainer" style="width: 663px; height: 168px;">
		<table class="provReview" cellpadding="0" cellspacing="0">
			<c:forEach var="provider" items="${reviewDTO.providersList}">
				<tr>
					<td class="column2">
						${provider.match}
					</td>
					<td class="column3">						
						${provider.firstName}&nbsp;${fn:substring(provider.lastName,0,1)}.&nbsp;(User Id#&nbsp;${provider.resourceId})
						<c:if test="${provider.showFav == true}">
							<img src="${staticContextPath}/images/icons/favicon.gif" alt="icon"
								class="inlineIcon">
						</c:if>
						<c:if test="${provider.showCheck == true}">
						<img src="${staticContextPath}/images/icons/grayCheck.gif" alt="icon"
							class="inlineIcon">
						</c:if>
						<br>
						<strong>Company ID#</strong> ${provider.vendorId} 
						<br>
						<strong>ServiceLive&nbsp;Rating</strong>
						<span class="providerRating">
			        		${provider.slRating}/5.0							
						</span> (${provider.slRatingsCount} ratings)<br />
						<c:if test="${provider.myRating} != 0.0">
						<strong>My Rating</strong>
						<span class="providerRating">
			        		${provider.myRating}/5.0							
						</span>&nbsp;(${provider.myRatingsCount} ratings)
						</c:if>
						
					</td>
					<td class="column4">
						${provider.totalOrders}
					</td>
					<td class="column5">
						${provider.myOrders}
					</td>
					<td class="column6">
						${provider.distance} mi
						<br>
						${provider.locationString}
					</td>
				</tr>
			</c:forEach>

		</table>
	</div>
</div>


