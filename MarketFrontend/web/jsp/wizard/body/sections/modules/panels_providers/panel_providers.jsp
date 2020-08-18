<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/select-provider-network.css" media="screen, projection" />
<link href="${staticContextPath}/css/font-awesome.min.css" rel="stylesheet" />
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<script type="text/javascript">
	jQuery(document).ready(function($)
	{
		var routingPriorityAppliedInd = jQuery("#routingPriorityApplied").val();
		jQuery("#routingPriorityAppliedInd").val(routingPriorityAppliedInd);
		//alert(jQuery("#routingPriorityAppliedInd").val());
		<c:if test="${providersTabDTO.routingPriorityAppliedInd}">
		$("#rating").trigger('click');
		$("#rating").trigger('click');
		</c:if>
	});	
	</script>

<div class="darkGrayModuleHdr">
	Provider Search Results
</div>
<div class="grayModuleContent mainWellContent clearfix" style="height: 650px; position: static;">
	<p>
		Below are the providers in your area that match your search. To narrow your results, use the filters on the right or sort list by name.  Click on provider name to view profile.
	</p>
	<div class="floatLeft" style="margin-right:2px;">
			<strong>Quick Select Options</strong>
			<select size="1" name="selectTopProviders" id="selectTopProviders" style="padding:2px;"
			onchange="javascript:fnSelectTopProviders('selectTopProviders','soWizardProvidersCreate','${contextPath}')">
			    <option value="0">Choose Option</option>
			    <option value="${fn:length(providersTabDTO.providers)}">Select All</option>
	             <c:forEach items="${providersTabDTO.providers}" step="5" varStatus="varStatus">
	             	<c:if test="${varStatus.index > 0}">
		             	<option value="${varStatus.index}">
		             		First ${varStatus.index}
		             	</option>
	             	</c:if>
	             </c:forEach>
			</select>
	</div>
	
	<p class="floatLeft" style="font-size:90%; font-weight:bold; ">
		<c:choose>
			<c:when test="${providersTabDTO.showSelectedProviders == true}">
				<a  id="showSelectedProviders"
					onclick="javascript:previousButton('soWizardProvidersCreate_showSelectedProviders.action','soWizardProvidersCreate','tab4');"
					style="text-decoration: underline; cursor:pointer; cursor:hand;">ONLY SHOW SELECTED PROVIDERS</a>
			</c:when>
			<c:otherwise>
				<a id="showAllProviders"
					onclick="javascript:previousButton('soWizardProvidersCreate_showAllProviders.action','soWizardProvidersCreate','tab4');"
					style="text-decoration: underline; cursor:pointer;">SHOW ALL</a>
			</c:otherwise>
		</c:choose>
	</p>
	
	
	<div class="floatRight" style="margin-bottom:5px;">
	<p>
		${fn:length(providersTabDTO.providers)} providers cover Zip Code <strong>${providersTabDTO.zip}</strong>
	</p>
	</div>
	<div class="clear"></div>
	<table class="provSelectHdr" id="providerListHeader" cellpadding="0" cellspacing="0"
		style="margin: 0; vertical-align: middle;">
		<tr>
			<td class="column1">
				Select
			</td>
			<td class="column2" style="cursor:pointer;" id="matchPercentage">
				<!-- <a href="" class="sortGridColumnUp">-->% Match <i id="matchSort" class="icon-sort"></i>
			</td>
			<td class="column3_1" style="cursor:pointer;" id="providerHdr" >
				<!-- <a href="" class="sortGridColumnUp">-->Provider<i id="provSort" class="icon-sort" style="padding-left:100px;"></i>
			</td>
			<td id="rating" class="column3_2" style="<c:if test='${providersTabDTO.routingPriorityAppliedInd}'>cursor:pointer;</c:if>">
				<!-- <a href="" class="sortGridColumnUp">-->
				Rating <c:if test="${providersTabDTO.routingPriorityAppliedInd}">&nbsp;&nbsp;<img src="${staticContextPath}/images/icons/info.gif" title="Performance Score is defined by the criteria selected in the Routing Priorities of this SPN"/></c:if>
			<i id="ratingSort" style="padding-left:60px;"></i>
			</td>
			<td class="column4_merged">
				<!-- <a href="" class="sortGridColumnUp2">-->Total Orders /<br> My Orders
			</td>
			<td class="column6"  style="cursor:pointer;" id="distance">
				<!-- <a href="" class="sortGridColumnUp2">-->Distance & <br /> Location<i id="distSort" class="icon-sort" style="padding-left:30px;"></i>
			</td>
			<td style="width: 20px"></td>
		</tr>
	</table>
	<div class="grayTableContainer" id="providerList" style="height: 500px; width: 673px;">
		<table id="providerListTable" class="provSelect" cellpadding="0" cellspacing="0">
		
		<thead><tr><th></th><th></th><th></th><th></th><th></th><th></th></tr></thead>
		
		<tbody>
			<c:forEach var="provider" items="${providersTabDTO.providers}" varStatus="status">
				<tr>
					<td class="column1">
						<input type="checkbox" id="check_${provider.resourceId}" name="check_${provider.resourceId}"
						<c:if test="${provider.isChecked == true}"> CHECKED</c:if> onclick=""/>
					</td>
					<td class="column2">
						${provider.match}
					</td>
					<td class="column3_1">
					<a href="javascript:void(0);" href id="${provider.firstName}${provider.lastName}" onclick="javascript:openProviderProfile(${provider.resourceId},${provider.vendorId}, '')">
						<c:if test="${provider.showCheck == true}">
							<img src="${staticContextPath}/images/icons/backgroundChecked.gif" alt="icon" class="inlineIcon">
						</c:if>					
						${provider.firstName}&nbsp;${fn:substring(provider.lastName,0,1)}.&nbsp;(User Id#&nbsp;${provider.resourceId})</a>
						<c:if test="${provider.showFav == true}">
							<img src="${staticContextPath}/images/icons/favicon.gif" alt="icon"
								class="inlineIcon">
						</c:if>
						<br>
						<strong>Company ID#</strong> ${provider.vendorId}
						<br>
						<br>
						<div style="font-size: 8px; font-weight:bold;">								
							<c:if test="${provider.genLiabilityInsVerified == true}">
								<img src="${staticContextPath}/images/icons/verified_icon.GIF" alt="verified" 
								title="Verified by ServiceLive (<fmt:formatDate value="${provider.genLiabilityInsVerifiedDate}" type="both" pattern="MMM dd, yyyy" />)" 
								class="genVerified"/>								
							</c:if>
							</a>							
							General Liability:
							<c:choose>
								<c:when test="${provider.genLiabilityInsAmt == null }"> None Reported </c:when>
								<c:otherwise><fmt:formatNumber type="number" pattern="#,##0.00" value="${provider.genLiabilityInsAmt}"></fmt:formatNumber></c:otherwise>
							</c:choose>
							
							<br>
							<c:if test="${provider.vehLiabilityInsVerified == true}">
								<img src="${staticContextPath}/images/icons/verified_icon.GIF" alt="verified" 
								title="Verified by ServiceLive (<fmt:formatDate value="${provider.vehLiabilityInsVerifiedDate}" type="both" pattern="MMM dd, yyyy" />)"
								class="vehVerified"/>
								
							</c:if>
							</a>
							Vehicle Liability:
							<c:choose>
								<c:when test="${provider.vehLiabilityInsAmt == null }"> None Reported </c:when>
								<c:otherwise><fmt:formatNumber type="number" pattern="#,##0.00" value="${provider.vehLiabilityInsAmt}"></fmt:formatNumber></c:otherwise>
							</c:choose>
							
							<br>
							<c:if test="${provider.workCompInsVerified == true}">
								<img src="${staticContextPath}/images/icons/verified_icon.GIF" alt="verified" 
								title="Verified by ServiceLive (<fmt:formatDate value="${provider.workCompInsVerifiedDate}" type="both" pattern="MMM dd, yyyy" />)"
								class="workVerified"/>

							</c:if>
							</a>
							Worker's Compensation:
							<c:choose>
								<c:when test="${provider.workCompInsAmt == null }"> None Reported </c:when>
								<c:otherwise><fmt:formatNumber type="number" pattern="#,##0.00" value="${provider.workCompInsAmt}"></fmt:formatNumber></c:otherwise>
							</c:choose>
						</div>
												
						<c:forEach items="${provider.networkAndPerformanceLevelList}" var="spnData">
							<c:if test="${spnData.networkName != null}">
								<strong style="text-align:left">${spnData.networkName} Group:</strong><br>
							</c:if>
							<c:if test="${spnData.performanceLevelId != null}">
								<img title="${spnData.performanceLevelName}" src="${staticContextPath}/images/spn/priority_${spnData.performanceLevelId}.png">
								<br>								
							</c:if>
						</c:forEach>
						
					</td>
					
					<td class="column3_2">
					<c:choose>
					<c:when test="${providersTabDTO.routingPriorityAppliedInd}">
						<c:forEach items="${provider.networkAndPerformanceLevelList}" var="spnData">
							SPN Performance Score: 
							${spnData.performanceScore}%
						</c:forEach>
					</c:when>
					<c:otherwise>
						<strong>ServiceLive&nbsp;Rating</strong>
						<span class="providerRating">
							<c:choose>
				        		<c:when test="${provider.slRatingsCount != 0}">
				        			<img src="${staticContextPath}/images/common/stars_<c:out value="${provider.slRatingNumber}"/>.gif" border="0" />
				        		</c:when>
				        		<c:otherwise>
				        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
				        		</c:otherwise>
				        	</c:choose>
						</span>&nbsp;(${provider.slRatingsCount} ratings)<br />
<%-- For consistency removed the check for not showing myRating value when it's 0.0
						<c:if test="${provider.myRating} != 0.0">
--%>
						<strong>My Rating</strong>
						<span class="providerRating">
						<br/>
			        	<c:choose>
			        		<c:when test="${provider.myRatingsCount != 0}">
			        			<img src="${staticContextPath}/images/common/stars_<c:out value="${provider.myRatingNumber}"/>.gif" border="0" />
			        		</c:when>
			        		<c:otherwise>
			        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
			        		</c:otherwise>
			        	</c:choose>
						</span>&nbsp;(${provider.myRatingsCount} ratings)
<%--					</c:if>  --%>
						</c:otherwise>
						</c:choose>
					</td>
					
					<td class="column4_merged">
						${provider.totalOrders} / ${provider.myOrders}
					</td>
					<td class="column6">
						${provider.distance} mi
						<br>
						${provider.locationString}
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty providersTabDTO.providers}">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="no_records_found" />
			</c:if>
			</tbody>
		</table>
	</div>
	
	<div class="floatLeft">

	</div>
	<div class="floatRight">

	</div>
	<div class="clear"></div>
</div>
<div style="clear: both;">
</div>
