<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String sortColumnSession = (String)session.getAttribute("sortColumnName");
	String sortOrderSession = (String)session.getAttribute("sortOrder");
%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
		
<div style="margin-left: 12px; width: 670px;">
	<c:if test="${filtersDisabled == 'false'}">
	<div style="margin-bottom:5px; text-align:right;  border-top:2px solid #666;">
	<p> <strong>${paginationVO.totalRecords}</strong> Provider(s) cover Zip Code <strong>${ETM_FILTER_ZIP_KEY}</strong></p>
	</div>
	</c:if>
<table class="provSearchHdr" cellpadding="0" cellspacing="0" border=0  style="margin-top:0; width:100%">
	<tr>
	
		<td style="padding-left:12px; width:290px;">
		<div style="float:left;"><a href="javascript: sortByColumn2('ETMProvider', 'etmSearchResults','${contextPath}/etmSearch_sortResultSet.action');" 
				 style="color:white">Provider</a></div>
		<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none; float:left; margin:6px" id="sortByETMProvider" />
		</td>
		
		<td style="width:145px;">
		<div style="float:left;">
			<a href="#" style="color:white" onclick="sortByColumn2('ETMSlRatings', 'etmSearchResults','${contextPath}/etmSearch_sortResultSet.action')">
				Rating
			 </a>
			 </div>
			<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none; float:left; margin:6px;" id="sortByETMSlRatings" />
		</td>	
		<td style="width:105px;">
			<!-- <a href="" class="sortGridColumnUp2">-->
			<div style="float:left;">
			<a href="#" style="color:white" onclick="sortByColumn2('ETMTotalOrder', 'etmSearchResults','${contextPath}/etmSearch_sortResultSet.action')">
				Total Orders /
				<br> My Orders
			 </a>
			 </div>
			<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none; float:left; margin:10px 0 0 0;" id="sortByETMTotalOrder" />
		</td>
		<td>
			<!-- <a href="" class="sortGridColumnUp2">-->
			<div style="float:left;">
			<a href="#" style="color:white" onclick="sortByColumn2('ETMDistance', 'etmSearchResults','${contextPath}/etmSearch_sortResultSet.action')">
				Distance &
				<br />
				Location
			 </a>
			</div>
			<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:block; float:left; margin: 10px 0 0 5px" id="sortByETMDistance" />
		</td>
	</tr>
</table>
</div>
<div class="grayTableContainer" style="margin-left: 12px; width: 668px; height: 407px;">
	<table class="provSearch" cellpadding="0" cellspacing="0" border=0 width="100%">
	   <c:choose>
			<c:when test="${not empty soProviderSearchList}">
			
			   <c:forEach var="provider" items="${soProviderSearchList}">
					<tr>
						<td  style="width: 290px;padding-left:12px">
						<a href = "#"  onClick="javascript:openProviderProfile(${provider.resourceId},${provider.vendorId}, '')">
						<c:if test="${provider.backgroundStateId == 9}">
								<img src="${staticContextPath}/images/icons/backgroundChecked.gif" alt="icon" class="inlineIcon">
						</c:if>
						${provider.firstName}&nbsp;${fn:substring(provider.lastName,0,1)}.
							(User Id# ${provider.resourceId})</a>
							<br>
							<strong>Company ID#</strong> ${provider.vendorId}
							<c:forEach items="${provider.spnetStates}" var="spnData">
								 <p>
									<c:if test="${spnData.spnName != null}">
										<strong style="text-align:left">${spnData.spnName} Group:</strong><br>
									</c:if>
									<c:if test="${spnData.performanceLevel != null}">
										<img title="${spnData.performanceLevelDesc}" src="${staticContextPath}/images/spn/priority_${spnData.performanceLevel}.png">
										<br>								
									</c:if>
								</p>
							</c:forEach>
		
						</td>
						<td style="width:145px;">
								<strong>ServiceLive&nbsp;Rating:</strong>
								<br/>
						  	<c:choose>
				        		<c:when test="${provider.ratingCount >= 0}">
				        			&nbsp;&nbsp;<img src="${staticContextPath}/images/common/stars_<c:out value="${provider.ratingImageId}"/>.gif" border="0" />
				        		</c:when>
				        		<c:otherwise>
				        			&nbsp;&nbsp;<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
				        		</c:otherwise>
				        	</c:choose>
				        	<c:choose>
				        		<c:when test="${provider.ratingCount >= 0}">
				        			&nbsp;(${provider.ratingCount} ratings)
				        		</c:when>
				        		
				        	</c:choose>
							
							<br/>
							<strong>My&nbsp;Rating:</strong>
							<br/>
						  	<c:choose>
				        		<c:when test="${provider.buyersRatingCount >= 0}">
				        			&nbsp;&nbsp;<img src="${staticContextPath}/images/common/stars_<c:out value="${provider.buyersRatingsImageId}"/>.gif" border="0" />
				        		</c:when>
				        		<c:otherwise>
				        			&nbsp;&nbsp;<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
				        		</c:otherwise>
				        	</c:choose>
				        	<c:choose>
				        		<c:when test="${provider.buyersRatingCount >= 0}">
				        			&nbsp;(${provider.buyersRatingCount} ratings)
				        		</c:when>
				        		
				        	</c:choose>
							
						</td>	
						<td  style="width:105px;">
							<c:if test="${provider.totalOrdersCompleted == null}">
								0
							</c:if>
							${provider.totalOrdersCompleted}
							
							<c:choose>
				        		<c:when test="${provider.buyersTotalOrder == null }">
				        			/0
				        		</c:when>
				        		<c:otherwise>
				        			/${provider.buyersTotalOrder}
				        		</c:otherwise>
				        	</c:choose>
						</td>
						<td>
							${provider.distance} mi
							<br>
							${provider.city}, ${provider.state}
						</td>
					</tr>
				</c:forEach>
				
				<!-- change this for adding pagination -->
				<!-- <jsp:include page="/jsp/paging/pagingsupport.jsp"/> -->
				
			</c:when>
			<c:otherwise>
					<fmt:message bundle="${serviceliveCopyBundle}"
					key="no_records_found" />
			</c:otherwise>
		</c:choose>	
	</table>
</div>

<div dojoType="dijit.Dialog" id="etmMsg" title="Explore the Marketplace ServiceLive.com">
			We are refreshing your results.
		</div>

