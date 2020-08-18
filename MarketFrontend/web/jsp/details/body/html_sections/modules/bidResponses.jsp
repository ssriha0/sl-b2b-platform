<%@page import="com.newco.marketplace.interfaces.OrderConstants"%>
<%@page import="com.newco.marketplace.web.utils.SODetailsUtils"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>		
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:if test="${showCommunication == true || filterFollowupBids == true}">
<div style="display:block; width: 300px; float: right; text-align: right;">
<c:choose>
<c:when test="${filterFollowupBids}"><a href="" style="display:block; width: 300px; float: right; text-align: right;" 
	onclick="filterFollowupBids(false); return false;">View All Items</a></c:when>
	<c:when test="${newConditionalAcceptedCnt > 0 || newConditionalAcceptedCnt == null}"><a href="" style="display:block; width: 300px; float: right; text-align: right;" 
	onclick="filterFollowupBids(true); return false;">View Items Needing Followup</a></c:when>
</c:choose>
</div>
</c:if>
<h2 style="width: 200px;" name="responses">Responses</h2><br>

<c:choose>
<c:when test="${not empty providerOfferMap}">
	<c:if test="${'provider' == criteriaLevel}">
			<c:forEach items="${providerOfferMap}" var="provMap" varStatus="status">
			<div>
			<c:choose>
			<c:when test="${null == provMap.value[0].tierId}">
				<b>Marketplace Routed Providers</b>
			</c:when>
			<c:otherwise>
				<b>Priority ${provMap.value[0].tierId} | 
				<c:choose>
				<c:when test="${1 == status.count}">Top </c:when>	
				<c:when test="${fn:length(providerOfferMap) == status.count}">Last </c:when>			
				<c:otherwise>Next </c:otherwise>
				</c:choose>
				${fn:length(provMap.value)} Rated Providers</b>
			</c:otherwise>
			</c:choose>
			<span style="float:right;color:#777777;">
				Routed <fmt:formatDate value="${provMap.key}" pattern="MM/dd/yy hh:mm a" />
			</span>
			</div>
			<table class="globalTableLook responseTable" cellpadding="0" cellspacing="0">
				<tr>
					<th class="odd"></th>
					<th class="col1 odd first">Provider Information</th>
					<th class="col3 odd" width="100">Response Status</th>
					<th class="col4 odd last">Details</th>	      		      	
				</tr>
				
				<c:forEach var="dto" items="${provMap.value}" varStatus="dtoIndex">
			    	<tr>
			      		<td class="odd" style="width: 60px; background-color:#eee; vertical-align: middle;">
			      			<strong>Firm ID#<br /><a href="providerProfileFirmInfoAction_execute.action?vendorId=${dto.vendorId}&buyerId=${buyerId}&popup=true" target="_blank">${dto.vendorId }</a></strong>
			      		</td>
			      		<c:set var="rowSpanCount" value="${rowSpanCount - 1}"/>
			     		<td class="col1 first odd" style="width: 220px; padding:10px 5px;">
				      		<a href = "providerProfileInfoAction_execute.action?resourceId=${dto.resourceId}&amp;vendorId=${dto.vendorId}&amp;popup=true" target="_providerProfile">${dto.firstName}&nbsp;
							<c:choose><c:when test="isSLAdmin==true"> ${dto.lastName} </c:when>
							<c:otherwise>${fn:substring(dto.lastName,0,1)}</c:otherwise>
							</c:choose>
				      		(User Id#&nbsp;${dto.resourceId})</a> 
				      	
							<br/>
				        	<strong>Current Posted</strong> ${dto.postedSOCount } <br/>
				        	<c:if test = "${slAdmin == true}" >
				        		<strong>Company Phone</strong> ${dto.phoneNumber } <br/>
				        	</c:if>
			       			<strong>ServiceLive&nbsp;Rating</strong> <!-- 4.9/5.0 (211 ratings)<br /> -->
			       			<span class="providerRating">
				     			<c:choose>
					        		<c:when test="${dto.slRatingsCount != 0}">
					        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.slRatingNumber}"/>.gif" border="0" />
					        		</c:when>
					        		<c:otherwise>
					        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
					        		</c:otherwise>
					        	</c:choose>
			       			</span>&nbsp;(${dto.slRatingsCount} ratings)<br />
			       		
			        		<strong>My Rating</strong> <!-- 4.9/5.0 (211 ratings) -->
			        		<span class="providerRating">
				        	    <c:choose>
					        		<c:when test="${dto.myRatingsCount != 0}">
					        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.myRatingNumber}"/>.gif" border="0" />
					        		</c:when>
					        		<c:otherwise>
					        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
					        		</c:otherwise>
					        	</c:choose>
				        	</span>&nbsp;(${dto.myRatingsCount} ratings)<br />
				        	<strong>Performance Score:</strong> ${dto.score}%(${dto.rank})<br>
					        <c:if test="${not empty dto.distanceFromBuyer}">
					        		<strong>Approx. Distance : </strong>${dto.distanceFromBuyer} miles
					        </c:if>
					        <c:if test="${showTieredRoutingInfo}">
					        	<c:if test="${not empty dto.networkAndPerformanceLevelList}">
					        		<br />
					        		<c:forEach items="${dto.networkAndPerformanceLevelList}" var="spnData">
										<c:if test="${spnData.networkName != null}">
											<br />
											<c:if test="${null != dto.tierId}">
												<div class="spnIcon" style=" padding: 3px 5px 10px 20px;">
													<strong>${spnData.networkName}</strong>
												</div>
											</c:if>
										</c:if>
									</c:forEach>
					        	</c:if>
					        </c:if> 
			      	 	</td> 
			       		<!-- End Provider Column -->      
			       
				      	<td class="col3 odd" style="width: 403px;padding: 0px;" colspan="2">
				      		<c:if test="${dto.responseId != null}">
					      		<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" style="table-layout:fixed; margin-bottom:0;">
					      			<tr>
					      				<td style="width: 110px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
					
									      <!--  Start Posted Status Column -->
									      	<c:choose>
									      		<c:when test="${dto.responseId == 3 || dto.responseId == 5}">
									      			<div class="redIcon"><strong>Rejected</strong></div>
									      		</c:when>
									      		<c:when test="${dto.responseId == 2 && priceModelBid}">
									      			<div class="yellowIcon"><strong>Bid Submitted</strong></div>
									      		</c:when>
									      		<c:when test="${dto.responseId == 2 && !priceModelBid}">
									      			<div class="yellowIcon"><strong>Counter Offered</strong></div>
									      		</c:when>
									      		<c:when test="${dto.responseId == 6 && priceModelBid}">
									      			<div class="yellowIcon"><strong>Bid Expired</strong></div>
									      		</c:when>
									      		<c:when test="${dto.responseId == 6 && !priceModelBid}">
									      			<div class="yellowIcon"><strong>Counter Offer Expired</strong></div>
									      		</c:when>
									      		<c:otherwise>
									      			<strong>${dto.responseDesc}</strong>
									      		</c:otherwise>
									      	</c:choose>    	
									      	
									      	<c:if test="${dto.responseDesc == null}">
									      		${dto.callStatusDescription}
									      	</c:if>  
									    </td>
									    <!-- End Posted Status Column -->
									    <!--  Start Details Column -->
									      
									    <td class="col4 last odd" style="padding:10px 5px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
									      <!-- Start new details column -->
									      <c:choose>
									      	<c:when test="${dto.responseId == 1 || dto.responseId == 2 || dto.responseId == 6}">
									      		<c:if test="${dto.responseId == 2 && dto.expiresInHours != null && wfStateId == 110}">
									      			(expires in <c:if test="${dto.expiresInDays > 0}">${dto.expiresInDays}d </c:if>${dto.expiresInHours }h ${dto.expiresInMinutes }m) 
									      		</c:if> 
										        <c:if test="${dto.conditionalSpendLimit != null}"> $<fmt:formatNumber value="${dto.conditionalSpendLimit}"
													type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
												</c:if><br />
												<c:if test="${priceModelBid}">
													Parts: $<fmt:formatNumber value="${dto.partsAndMaterialsBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
													/ Labor: $<fmt:formatNumber value="${dto.totalLaborBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" /> <br />
													Est. Time to Complete: <fmt:formatNumber value="${dto.totalHoursBid}" type="NUMBER" maxFractionDigits="0" /> h <br />
												</c:if>
												<c:if test="${dto.conditionalChangeDate1 != null}">
													<c:catch var="e">
											      		<fmt:parseDate var="sd1" value="${dto.conditionalChangeDate1}" type="date" pattern="yyyy-MM-dd"/>
											      		<fmt:parseDate var="sd2" value="${dto.conditionalChangeDate2}" type="date" pattern="yyyy-MM-dd"/>
											      	</c:catch>
										    	  	<c:out value="${e}"  escapeXml="false" />
										      	  	<fmt:formatDate value="${sd1}" type="date" dateStyle="medium" /> 
										      	  	<c:if test="${dto.conditionalChangeDate2 != null}">      	  
										      	  		- <fmt:formatDate value="${sd2}" type="date" dateStyle="medium" />
										      	  	</c:if><br />
										      	  	<c:if test="${!priceModelBid}">
												        ${dto.conditionalStartTime } 
												        <c:if test="${dto.conditionalChangeDate2 != null}">      	  
												        	- ${dto.conditionalEndTime}
												       		<c:if test="${dto.serviceLocationTimeZone != null}">      	  
												        		&nbsp;&nbsp;(${dto.serviceLocationTimeZone})
												        	</c:if><br />
												        </c:if>
											        </c:if>
											    </c:if>
											    <br />
											    <c:choose>
											    	<c:when test="${dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
											    		Needs Followup<br />
											    	</c:when>
											    	<c:when test="${dto.viewStatusName == 'UNREAD'}"></c:when>
											    	<c:otherwise>
											    	  <c:if test="${dto.activityId != null}">
											    		<a href="" style="font-weight: bold;font-size:0.9em;" 
											    				onclick="flagBidForFollowup(${dto.activityId}); return false;">
															Flag for Followup<br />
														</a>
													  </c:if>
											    	</c:otherwise>
											    </c:choose>
											    <c:choose>
											    	<c:when test="${dto.viewStatusName == 'UNREAD' || dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
											    		<a href="" style="font-weight: bold;font-size:0.9em;" 
													    		onclick="markBidAsRead(${dto.activityId}, ${filterFollowupBids}); return false;">
															Mark as Read
														</a>
											    	</c:when>
											    </c:choose>
											</c:when>
															     
									      	<c:otherwise>
									      		<c:choose>
									      		<c:when test="${dto.responseId == 5 }">Released on</c:when>
									      		<c:when test="${dto.responseId == 3 }">Rejected on</c:when>
									      		</c:choose>
									      		<br />
									      		<c:if test="${ dto.responseDate != null }">
									      		<fmt:parseDate var="responseDate" value="${dto.responseDate}" type="date" pattern="yyyy-MM-dd"/>
									      		<fmt:formatDate value="${responseDate}" type="date" dateStyle="medium" /> (by ${dto.firstName}&nbsp;${fn:substring(dto.lastName,0,1)}) <br />
									      		</c:if>
									      		
                                                <!-- Added for Rejection Comment  -->
									      		<c:if test="${dto.responseId == 3 and dto.responseComment != null}">
							                       <br/><strong>Rejection Comment</strong>
							                           <table width="170" border="0" align="right" style="table-layout:fixed"><tr><td align ="right" valign="top" width="170" style="white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word">${dto.responseComment}</td></tr> </table>
                                                </c:if>									      		
									      		
									      	</c:otherwise>
									      	</c:choose>
											<!-- End new details column -->
									      
										    <p>
										        <c:if test="${dto.responseId==2 and wfStateId == 110 and (not empty groupOrderId or empty THE_SERVICE_ORDER.parentGroupId )}" >
									        		<img onclick="submitForm('${dto.resourceId}', '${dto.vendorId }', '${dto.conditionalChangeDate1}', '${dto.conditionalChangeDate2}', '${dto.conditionalStartTime}', '${ dto.conditionalEndTime}', '${dto.responseReasonId}','${dto. conditionalSpendLimit}')" 
									        			src="${staticContextPath}/images/spacer.gif" width="117" height="20" class="btn20Bevel"
									        			<c:choose>
									        			<c:when test="${priceModelBid}">style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:when>
									        			<c:otherwise>style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:otherwise>
									        			</c:choose>
									        		 >
												</c:if>
									        </p>
									    </td>
									    <!-- End Details Column -->
									</tr>
									<c:if test="${(dto.responseId != 5 && dto.responseId != 3 && (dto.responseComment != null || dto.responseReasonDesc != null)) || (dto.responseId == 3 && dto.responseReasonDesc != null)}">
										 <tr>
										 	<td colspan="2" style="border:none;background-color:#dbdbdb;">
											 	  <div style="overflow:hidden;">
											 		<c:choose>
											 			<c:when test="${dto.responseId == 3 && dto.responseReasonDesc != null}">${dto.responseReasonDesc }</c:when>
											 			<c:otherwise>
											 				<c:choose>
											 					<c:when test="${dto.responseComment != null }">${dto.responseComment}</c:when>
											 					<c:otherwise>${dto.responseReasonDesc}</c:otherwise>
											 				</c:choose>
											 			</c:otherwise>
											 		</c:choose>
											 		<c:if test="${dto.responseId == 2 && !priceModelBid}">
											 			<c:if test="${taskLevelIsOn == 'true' && (dto.responseReasonId==8 || dto.responseReasonId==9)}">
											 				<b style="color: blue;text-align: left;float:left;">
											 				This service Order is priced at "task level". By Accepting the Conditions, the New 
											 				Counter Offer price will be proportionally distributed among the existing tasks. </b>
											 			</c:if>
											 		</c:if>
											 	  </div>
										 	</td>
										 </tr>
									</c:if>
								</table>
							</c:if>
						</td>
			    	</tr>
				
				    <c:if test = "${slAdmin == true}" >
						<tr>
							<td colspan="3" style="col1 first odd">
								<div style="margin: 10px; background: #eee; padding: 10px;">
									<span class="right">Last entered by: ${dto.modifyingAdmin}.
										<c:if test="${dto.modifiedDateTime != null}">
										  <c:catch var="e">
										  	on 
										    <fmt:parseDate var="mt1" value="${dto.modifiedDateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
										  </c:catch>
										  <fmt:formatDate value="${mt1}" type="both" dateStyle="medium" /> 
										 </c:if>
									</span>
									<h4>Market Maker Response</h4>
									<label style="display: block; padding: 3px 0;">Call Status: 
										<select id = "callStatusId[${dtoIndex.index}]" name = "callStatusId" style="width:300px;">
											<option value="#">Select a Status</option>
											<c:forEach items="${callStatusList}" var="cs">
												<c:if test="${dto.callStatusId != cs.label}">
													<option value="${cs.label}">${cs.value}</option>
												</c:if>
												
												<c:if test="${dto.callStatusId == cs.label}">
													<option value="${cs.label}" selected>${cs.value}</option>
												</c:if>
											</c:forEach>
										</select>
									</label>
									<label style="display: block; padding: 3px 0;">Comment: 
										<input type="text" name = "mktMakerComments" id = "mktMakerComments[${dtoIndex.index}]" style="width: 300px;" value = "${dto.mktMakerComments}" />
										<input type="button" onclick="updateMarketMakerComments('${dto.resourceId}','${dto.firstName}','${dto.lastName}','${dtoIndex.index}')" value="Update" />					
									</label>
								</div>
							</td>
						</tr>
					</c:if>
					<input type="hidden" name="vendorId" id="vendorId" value ="${dto.resourceId}">
				</c:forEach>
			</table>
		</c:forEach>
	</c:if>
	<c:if test="${'firm' == criteriaLevel}">
		<c:forEach items="${providerOfferMap}" var="provMap" varStatus="status">
			<div>
			
			<c:set var="mpInd" value="false"></c:set>
			<c:set var="priority" value="0"></c:set>
			<c:forEach items="${provMap.value}" var="firmMap">
				<c:forEach var="dto" items="${firmMap.value}">
				<c:choose>
					<c:when test="${null == dto.tierId}">
						<c:set var="mpInd" value="true"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="priority" value="${dto.tierId}"></c:set>
					</c:otherwise>
				</c:choose>
				</c:forEach>
			</c:forEach>
			
			<c:choose>
			<c:when test="${mpInd == true}">
				<b>Marketplace Routed Firms</b>
			</c:when>
			<c:otherwise>
				<b>Priority ${priority} | 
				<c:choose>
				<c:when test="${1 == status.count}">Top </c:when>	
				<c:when test="${fn:length(providerOfferMap) == status.count}">Last </c:when>			
				<c:otherwise>Next </c:otherwise> 
				</c:choose>
				${fn:length(provMap.value)} Rated Firms</b>
			</c:otherwise>
			</c:choose>
			<span style="float:right;color:#777777;">
				Routed <fmt:formatDate value="${provMap.key}" pattern="MM/dd/yy hh:mm a" />
			</span>
			</div>
			<table class="globalTableLook responseTable" cellpadding="0" cellspacing="0">
				<tr>
					<th class="odd"></th>
					<th class="col1 odd first">Provider Information</th>
					<th class="col3 odd" width="100">Response Status</th>
					<th class="col4 odd last">Details</th>	      		      	
				</tr>
				
				<c:forEach items="${provMap.value}" var="firmMap">
					<c:set var="rowSpanCount" value="0"/>
					<c:forEach var="dto" items="${firmMap.value}" varStatus="dtoIndex">
				    	<tr>
				    		<c:if test="${rowSpanCount <= 0 }">
				    			<c:set var="rowSpanCount" value="${fn:length(firmMap.value)}"/>
					      		<td class="odd" style="width: 60px; background-color:#eee; vertical-align: middle;" rowspan="${rowSpanCount }">
					      			<strong>Firm ID#<br /><a href="providerProfileFirmInfoAction_execute.action?vendorId=${dto.vendorId}&buyerId=${buyerId}&popup=true" target="_blank">${dto.vendorId }</a></strong>
					      			<br>
					      			<strong>${firmMap.key.firmscore}%</strong><br>
					      			(${firmMap.key.firmRank})
					      		</td>
				      		</c:if>
				      		<c:set var="rowSpanCount" value="${rowSpanCount - 1}"/>
				     		<td class="col1 first odd" style="width: 220px; padding:10px 5px;">
					      		<a href = "providerProfileInfoAction_execute.action?resourceId=${dto.resourceId}&amp;vendorId=${dto.vendorId}&amp;popup=true" target="_providerProfile">${dto.firstName}&nbsp;<c:choose><c:when test="isSLAdmin==true"> ${dto.lastName} </c:when><c:otherwise>${fn:substring(dto.lastName,0,1)}</c:otherwise></c:choose>
					      		(User Id#&nbsp;${dto.resourceId})</a> 
					      	
								<br/>
					        	<strong>Current Posted</strong> ${dto.postedSOCount } <br/>
					        	<c:if test = "${slAdmin == true}" >
					        		<strong>Company Phone</strong> ${dto.phoneNumber } <br/>
					        	</c:if>
				       			<strong>ServiceLive&nbsp;Rating</strong> <!-- 4.9/5.0 (211 ratings)<br /> -->
				       			<span class="providerRating">
					     			<c:choose>
						        		<c:when test="${dto.slRatingsCount != 0}">
						        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.slRatingNumber}"/>.gif" border="0" />
						        		</c:when>
						        		<c:otherwise>
						        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
						        		</c:otherwise>
						        	</c:choose>
				       			</span>&nbsp;(${dto.slRatingsCount} ratings)<br />
				       		
				        		<strong>My Rating</strong> <!-- 4.9/5.0 (211 ratings) -->
				        		<span class="providerRating">
					        	    <c:choose>
						        		<c:when test="${dto.myRatingsCount != 0}">
						        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.myRatingNumber}"/>.gif" border="0" />
						        		</c:when>
						        		<c:otherwise>
						        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
						        		</c:otherwise>
						        	</c:choose>
					        	</span>&nbsp;(${dto.myRatingsCount} ratings)<br />
						        <c:if test="${not empty dto.distanceFromBuyer}">
						        		<strong>Approx. Distance : </strong>${dto.distanceFromBuyer} miles
						        </c:if>
						        <c:if test="${showTieredRoutingInfo}">
						        	<c:if test="${not empty dto.networkAndPerformanceLevelList}">
						        		<br />
						        		<c:forEach items="${dto.networkAndPerformanceLevelList}" var="spnData">
											<c:if test="${spnData.networkName != null}">
												<br />
												<c:if test="${null != dto.tierId}">
													<div class="spnIcon" style=" padding: 3px 5px 10px 20px;">
														<strong>${spnData.networkName}</strong>
													</div>
												</c:if>
											</c:if>
										</c:forEach>
						        	</c:if>
						        </c:if> 
				      	 	</td> 
				       		<!-- End Provider Column -->      
				       
					      	<td class="col3 odd" style="width: 403px;padding: 0px;" colspan="2">
					      		<c:if test="${dto.responseId != null}">
						      		<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" style="table-layout:fixed; margin-bottom:0;">
						      			<tr>
						      				<td style="width: 110px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
						
										      <!--  Start Posted Status Column -->
										      	<c:choose>
										      		<c:when test="${dto.responseId == 3 || dto.responseId == 5}">
										      			<div class="redIcon"><strong>Rejected</strong></div>
										      		</c:when>
										      		<c:when test="${dto.responseId == 2 && priceModelBid}">
										      			<div class="yellowIcon"><strong>Bid Submitted</strong></div>
										      		</c:when>
										      		<c:when test="${dto.responseId == 2 && !priceModelBid}">
										      			<div class="yellowIcon"><strong>Counter Offered</strong></div>
										      		</c:when>
										      		<c:when test="${dto.responseId == 6 && priceModelBid}">
										      			<div class="yellowIcon"><strong>Bid Expired</strong></div>
										      		</c:when>
										      		<c:when test="${dto.responseId == 6 && !priceModelBid}">
										      			<div class="yellowIcon"><strong>Counter Offer Expired</strong></div>
										      		</c:when>
										      		<c:otherwise>
										      			<strong>${dto.responseDesc}</strong>
										      		</c:otherwise>
										      	</c:choose>      	
										      	
										      	<c:if test="${dto.responseDesc == null}">
										      		${dto.callStatusDescription}
										      	</c:if>  
										    </td>
										    <!-- End Posted Status Column -->
										    <!--  Start Details Column -->
										      
										    <td class="col4 last odd" style="padding:10px 5px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
										      <!-- Start new details column -->
										      	<c:choose>
										      	<c:when test="${dto.responseId == 1 || dto.responseId == 2 || dto.responseId == 6}">
										      		<c:if test="${dto.responseId == 2 && dto.expiresInHours != null && wfStateId == 110}">
										      			(expires in <c:if test="${dto.expiresInDays > 0}">${dto.expiresInDays}d </c:if>${dto.expiresInHours }h ${dto.expiresInMinutes }m) 
										      		</c:if> 
											        <c:if test="${dto.conditionalSpendLimit != null}"> $<fmt:formatNumber value="${dto.conditionalSpendLimit}"
														type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
													</c:if><br />
													<c:if test="${priceModelBid}">
														Parts: $<fmt:formatNumber value="${dto.partsAndMaterialsBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
														/ Labor: $<fmt:formatNumber value="${dto.totalLaborBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" /> <br />
														Est. Time to Complete: <fmt:formatNumber value="${dto.totalHoursBid}" type="NUMBER" maxFractionDigits="0" /> h <br />
													</c:if>
													<c:if test="${dto.conditionalChangeDate1 != null}">
														<c:catch var="e">
												      		<fmt:parseDate var="sd1" value="${dto.conditionalChangeDate1}" type="date" pattern="yyyy-MM-dd"/>
												      		<fmt:parseDate var="sd2" value="${dto.conditionalChangeDate2}" type="date" pattern="yyyy-MM-dd"/>
												      	</c:catch>
											    	  	<c:out value="${e}"  escapeXml="false" />
											      	  	<fmt:formatDate value="${sd1}" type="date" dateStyle="medium" /> 
											      	  	<c:if test="${dto.conditionalChangeDate2 != null}">      	  
											      	  		- <fmt:formatDate value="${sd2}" type="date" dateStyle="medium" />
											      	  	</c:if><br />
											      	  	<c:if test="${!priceModelBid}">
													        ${dto.conditionalStartTime } 
													        <c:if test="${dto.conditionalChangeDate2 != null}">      	  
													        	- ${dto.conditionalEndTime}
													       		<c:if test="${dto.serviceLocationTimeZone != null}">      	  
													        		&nbsp;&nbsp;(${dto.serviceLocationTimeZone})
													        	</c:if><br />
													        </c:if>
												        </c:if>
												    </c:if>
												    <br />
												    <c:choose>
												    	<c:when test="${dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
												    		Needs Followup<br />
												    	</c:when>
												    	<c:when test="${dto.viewStatusName == 'UNREAD'}"></c:when>
												    	<c:otherwise>
												    	  <c:if test="${dto.activityId != null}">
												    		<a href="" style="font-weight: bold;font-size:0.9em;" 
												    				onclick="flagBidForFollowup(${dto.activityId}); return false;">
																Flag for Followup<br />
															</a>
														  </c:if>
												    	</c:otherwise>
												    </c:choose>
												    <c:choose>
												    	<c:when test="${dto.viewStatusName == 'UNREAD' || dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
												    		<a href="" style="font-weight: bold;font-size:0.9em;" 
														    		onclick="markBidAsRead(${dto.activityId}, ${filterFollowupBids}); return false;">
																Mark as Read
															</a>
												    	</c:when>
												    </c:choose>
												</c:when>
											   	<c:otherwise>
										      		<c:choose>
										      		<c:when test="${dto.responseId == 5 }">Released on</c:when>
										      		<c:when test="${dto.responseId == 3 }">Rejected on</c:when>
										      		</c:choose>
										      		<br />
										      		<c:if test="${ dto.responseDate != null }">
										      		<fmt:parseDate var="responseDate" value="${dto.responseDate}" type="date" pattern="yyyy-MM-dd"/>
										      		<fmt:formatDate value="${responseDate}" type="date" dateStyle="medium" /> (by ${dto.firstName}&nbsp;${fn:substring(dto.lastName,0,1)}) <br />
										      		</c:if>
										      		
									      		    <!-- Added for Rejection Comment  -->
									      		    <c:if test="${dto.responseId == 3 and dto.responseComment != null}">
							                        <br /><strong>Rejection Comment</strong>
							                             <table width="170" border="0" align="right" style="table-layout:fixed"><tr><td align ="right" valign="top" width="170" style="white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word">${dto.responseComment}</td></tr> </table>
                                                    </c:if>	
										      	</c:otherwise>
										      	</c:choose>
										      
											    <p>
											        <c:if test="${dto.responseId==2 and wfStateId == 110 and (not empty groupOrderId or empty THE_SERVICE_ORDER.parentGroupId )}" >
										        		<img onclick="submitForm('${dto.resourceId}', '${dto.vendorId }', '${dto.conditionalChangeDate1}', '${dto.conditionalChangeDate2}', '${dto.conditionalStartTime}', '${ dto.conditionalEndTime}', '${dto.responseReasonId}','${dto. conditionalSpendLimit}')" 
										        			src="${staticContextPath}/images/spacer.gif" width="117" height="20" class="btn20Bevel"
										        			<c:choose>
										        			<c:when test="${priceModelBid}">style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:when>
										        			<c:otherwise>style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:otherwise>
										        		 	</c:choose>
										        		 >
													</c:if>
										        </p>
										    </td>
										    <!-- End Details Column -->
										</tr>
										<c:if test="${(dto.responseId != 5 && dto.responseId != 3 && (dto.responseComment != null || dto.responseReasonDesc != null)) || (dto.responseId == 3 && dto.responseReasonDesc != null)}">
											 <tr>
											 	<td colspan="2" style="border:none;background-color:#dbdbdb;">
												 	  <div style="overflow:hidden;">
												 		<c:choose>
												 			<c:when test="${dto.responseId == 3 && dto.responseReasonDesc != null}">${dto.responseReasonDesc }</c:when>
												 			<c:otherwise>
												 				<c:choose>
												 					<c:when test="${dto.responseComment != null }">${dto.responseComment}</c:when>
												 					<c:otherwise>${dto.responseReasonDesc}</c:otherwise>
												 				</c:choose>
												 			</c:otherwise>
												 		</c:choose>
												 		<c:if test="${dto.responseId == 2 && !priceModelBid}">
												 			<c:if test="${taskLevelIsOn == 'true' && (dto.responseReasonId==8 || dto.responseReasonId==9)}">
												 				<b style="color: blue;text-align: left;float:left;">
												 				This service Order is priced at "task level". By Accepting the Conditions, the New 
												 				Counter Offer price will be proportionally distributed among the existing tasks. </b>
												 			</c:if>
												 		</c:if>
												 	  </div>
											 	</td>
											 </tr>
										</c:if>
									</table>
								</c:if>
							</td>
				    	</tr>
					
					    <c:if test = "${slAdmin == true}" >
							<tr>
								<td colspan="3" style="col1 first odd">
									<div style="margin: 10px; background: #eee; padding: 10px;">
										<span class="right">Last entered by: ${dto.modifyingAdmin}.
											<c:if test="${dto.modifiedDateTime != null}">
											  <c:catch var="e">
											  	on 
											    <fmt:parseDate var="mt1" value="${dto.modifiedDateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
											  </c:catch>
											  <fmt:formatDate value="${mt1}" type="both" dateStyle="medium" /> 
											 </c:if>
										</span>
										<h4>Market Maker Response</h4>
										<label style="display: block; padding: 3px 0;">Call Status: 
											<select id = "callStatusId[${dtoIndex.index}]" name = "callStatusId" style="width:300px;">
												<option value="#">Select a Status</option>
												<c:forEach items="${callStatusList}" var="cs">
													<c:if test="${dto.callStatusId != cs.label}">
														<option value="${cs.label}">${cs.value}</option>
													</c:if>
													
													<c:if test="${dto.callStatusId == cs.label}">
														<option value="${cs.label}" selected>${cs.value}</option>
													</c:if>
												</c:forEach>
											</select>
										</label>
										<label style="display: block; padding: 3px 0;">Comment: 
											<input type="text" name = "mktMakerComments" id = "mktMakerComments[${dtoIndex.index}]" style="width: 300px;" value = "${dto.mktMakerComments}" />
											<input type="button" onclick="updateMarketMakerComments('${dto.resourceId}','${dto.firstName}','${dto.lastName}','${dtoIndex.index}')" value="Update" />					
										</label>
									</div>
								</td>
							</tr>
						</c:if>
						<input type="hidden" name="vendorId" id="vendorId" value ="${dto.resourceId}">
					</c:forEach>
				</c:forEach>
			</table>
		</c:forEach>
	</c:if>
</c:when>
<c:otherwise>
	<table class="globalTableLook responseTable" cellpadding="0" cellspacing="0">
		<tr>
			<th class="odd"></th>
			<th class="col1 odd first">Provider Information</th>
			<th class="col3 odd" width="100">Response Status</th>
			<th class="col4 odd last">Details</th>	      		      	
		</tr>
		
		<c:set var="rowSpanCount" value="0"/>
		<c:forEach var="dto" items="${providerOfferList}" varStatus="dtoIndex">
	    	<tr>
	      		<c:if test="${rowSpanCount <= 0 }">
	      			<c:set var="rowSpanCount" value="${dto.selectedProvidersInFirm}"/>
	      			<td class="odd" style="width: 60px; background-color:#eee; vertical-align: middle;" rowspan="${rowSpanCount }">
	      				<strong>Firm ID#<br /><a href="providerProfileFirmInfoAction_execute.action?vendorId=${dto.vendorId}&buyerId=${buyerId}&popup=true" target="_blank">${dto.vendorId }</a></strong>
	      			</td>
	     		</c:if>
	      		<c:set var="rowSpanCount" value="${rowSpanCount - 1}"/>
	     		<td class="col1 first odd" style="width: 220px; padding:10px 5px;">
		      		<a href = "providerProfileInfoAction_execute.action?resourceId=${dto.resourceId}&amp;vendorId=${dto.vendorId}&amp;popup=true" target="_providerProfile">${dto.firstName}&nbsp;<c:choose><c:when test="isSLAdmin==true"> ${dto.lastName} </c:when><c:otherwise>${fn:substring(dto.lastName,0,1)}</c:otherwise></c:choose>
		      		(User Id#&nbsp;${dto.resourceId})</a> 
		      	
					<br/>
		        	<strong>Current Posted</strong> ${dto.postedSOCount } <br/>
		        	<c:if test = "${slAdmin == true}" >
		        		<strong>Company Phone</strong> ${dto.phoneNumber } <br/>
		        	</c:if>
	       			<strong>ServiceLive&nbsp;Rating</strong> <!-- 4.9/5.0 (211 ratings)<br /> -->
	       			<span class="providerRating">
		     			<c:choose>
			        		<c:when test="${dto.slRatingsCount != 0}">
			        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.slRatingNumber}"/>.gif" border="0" />
			        		</c:when>
			        		<c:otherwise>
			        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
			        		</c:otherwise>
			        	</c:choose>
	       			</span>&nbsp;(${dto.slRatingsCount} ratings)<br />
	       		
	        		<strong>My Rating</strong> <!-- 4.9/5.0 (211 ratings) -->
	        		<span class="providerRating">
		        	    <c:choose>
			        		<c:when test="${dto.myRatingsCount != 0}">
			        			<img src="${staticContextPath}/images/common/stars_<c:out value="${dto.myRatingNumber}"/>.gif" border="0" />
			        		</c:when>
			        		<c:otherwise>
			        			<img src="${staticContextPath}/images/common/stars_notRated.gif" border="0" />
			        		</c:otherwise>
			        	</c:choose>
		        	</span>&nbsp;(${dto.myRatingsCount} ratings)<br />
			        <c:if test="${not empty dto.distanceFromBuyer}">
			        		<strong>Approx. Distance : </strong>${dto.distanceFromBuyer} miles
			        </c:if>
			        <c:if test="${showTieredRoutingInfo}">
			        	<c:if test="${not empty dto.networkAndPerformanceLevelList}">
			        		<br />
			        		<c:forEach items="${dto.networkAndPerformanceLevelList}" var="spnData">
								<c:if test="${spnData.networkName != null && (criteriaLevel == ''||criteriaLevel == ' ')}">
									<br /><div class="spnIcon" style=" padding: 3px 5px 10px 20px;">
										<strong>${spnData.networkName}</strong>
									</div>
								</c:if>
							</c:forEach>
			        	</c:if>
			        </c:if>     	
	      	 	</td> 
	       		<!-- End Provider Column -->      
	       
		      	<td class="col3 odd" style="width: 403px;padding: 0px;" colspan="2">
		      		<c:if test="${dto.responseId != null}">
			      		<table cellpadding="0" cellspacing="0" width="100%" height="100%" border="0" style="table-layout:fixed; margin-bottom:0;">
			      			<tr>
			      				<td style="width: 110px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
			
							      <!--  Start Posted Status Column -->
							      	<c:choose>
							      		<c:when test="${dto.responseId == 3 || dto.responseId == 5}">
							      			<div class="redIcon"><strong>Rejected</strong></div>
							      		</c:when>
							      		<c:when test="${dto.responseId == 2 && priceModelBid}">
							      			<div class="yellowIcon"><strong>Bid Submitted</strong></div>
							      		</c:when>
							      		<c:when test="${dto.responseId == 2 && !priceModelBid}">
							      			<div class="yellowIcon"><strong>Counter Offered</strong></div>
							      		</c:when>
							      		<c:when test="${dto.responseId == 6 && priceModelBid}">
							      			<div class="yellowIcon"><strong>Bid Expired</strong></div>
							      		</c:when>
							      		<c:when test="${dto.responseId == 6 && !priceModelBid}">
							      			<div class="yellowIcon"><strong>Counter Offer Expired</strong></div>
							      		</c:when>
							      		<c:otherwise>
							      			<strong>${dto.responseDesc}</strong>
							      		</c:otherwise>
							      	</c:choose>      	
							      	
							      	<c:if test="${dto.responseDesc == null}">
							      		${dto.callStatusDescription}
							      	</c:if>  
							    </td>
							    <!-- End Posted Status Column -->
							    <!--  Start Details Column -->
							      
							    <td class="col4 last odd" style="padding:10px 5px;<c:if test="${dto.responseComment == null }">border:none;</c:if>">
							      <!-- Start new details column -->
							      	
							      	<c:choose>
							      	<c:when test="${dto.responseId == 1 || dto.responseId == 2 || dto.responseId == 6}">
							      		<c:if test="${dto.responseId == 2 && dto.expiresInHours != null && wfStateId == 110}">
							      			(expires in <c:if test="${dto.expiresInDays > 0}">${dto.expiresInDays}d </c:if>${dto.expiresInHours }h ${dto.expiresInMinutes }m) 
							      		</c:if> 
								        <c:if test="${dto.conditionalSpendLimit != null}"> $<fmt:formatNumber value="${dto.conditionalSpendLimit}"
											type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
										</c:if><br />
										<c:if test="${priceModelBid}">
											Parts: $<fmt:formatNumber value="${dto.partsAndMaterialsBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" />
											/ Labor: $<fmt:formatNumber value="${dto.totalLaborBid}" type="NUMBER" minFractionDigits="2" maxFractionDigits="2" /> <br />
											Est. Time to Complete: <fmt:formatNumber value="${dto.totalHoursBid}" type="NUMBER" maxFractionDigits="0" /> h <br />
										</c:if>
										<c:if test="${dto.conditionalChangeDate1 != null}">
											<c:catch var="e">
									      		<fmt:parseDate var="sd1" value="${dto.conditionalChangeDate1}" type="date" pattern="yyyy-MM-dd"/>
									      		<fmt:parseDate var="sd2" value="${dto.conditionalChangeDate2}" type="date" pattern="yyyy-MM-dd"/>
									      	</c:catch>
								    	  	<c:out value="${e}"  escapeXml="false" />
								      	  	<fmt:formatDate value="${sd1}" type="date" dateStyle="medium" /> 
								      	  	<c:if test="${dto.conditionalChangeDate2 != null}">      	  
								      	  		- <fmt:formatDate value="${sd2}" type="date" dateStyle="medium" />
								      	  	</c:if><br />
								      	  	<c:if test="${!priceModelBid}">
										        ${dto.conditionalStartTime } 
										        <c:if test="${dto.conditionalChangeDate2 != null}">      	  
										        	- ${dto.conditionalEndTime}
										       		<c:if test="${dto.serviceLocationTimeZone != null}">      	  
										        		&nbsp;&nbsp;(${dto.serviceLocationTimeZone})
										        	</c:if><br />
										        </c:if>
									        </c:if>
									    </c:if>
									    <br />
									    <c:choose>
									    	<c:when test="${dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
									    		Needs Followup<br />
									    	</c:when>
									    	<c:when test="${dto.viewStatusName == 'UNREAD'}"></c:when>
									    	<c:otherwise>
									    	  <c:if test="${dto.activityId != null}">
									    		<a href="" style="font-weight: bold;font-size:0.9em;" 
									    				onclick="flagBidForFollowup(${dto.activityId}); return false;">
													Flag for Followup<br />
												</a>
											  </c:if>
									    	</c:otherwise>
									    </c:choose>
									    <c:choose>
									    	<c:when test="${dto.viewStatusName == 'UNREAD' || dto.viewStatusName == 'REQUIRES_FOLLOW_UP'}">
									    		<a href="" style="font-weight: bold;font-size:0.9em;" 
											    		onclick="markBidAsRead(${dto.activityId}, ${filterFollowupBids}); return false;">
													Mark as Read
												</a>
									    	</c:when>
									    </c:choose>
									</c:when>
								     
							      	<c:otherwise>
							      		<c:choose>
							      		<c:when test="${dto.responseId == 5 }">Released on</c:when>
							      		<c:when test="${dto.responseId == 3 }">Rejected on</c:when>
							      		</c:choose>
							      		<br />
							      		<c:if test="${ dto.responseDate != null }">
							      		<fmt:parseDate var="responseDate" value="${dto.responseDate}" type="date" pattern="yyyy-MM-dd"/>
							      		<fmt:formatDate value="${responseDate}" type="date" dateStyle="medium" /> (by ${dto.firstName}&nbsp;${fn:substring(dto.lastName,0,1)}) <br />
							      		</c:if>

									    <!-- Added for Rejection Comment  -->
									    <c:if test="${dto.responseId == 3 and dto.responseComment != null}">
							               <br /><strong>Rejection Comment</strong><br />
							                            <table width="170" border="0" align="right" style="table-layout:fixed"><tr><td align ="right" valign="top" width="170" style="white-space:pre-wrap;white-space:-moz-pre-wrap;white-space:-pre-wrap;white-space:-o-pre-wrap;word-wrap:break-word">${dto.responseComment}</td></tr> </table>
                                        </c:if>	
							      		
							      	</c:otherwise>
							      	</c:choose>
							      <!-- End new details column -->
								    <p>
								        <c:if test="${dto.responseId==2 and wfStateId == 110 and (not empty groupOrderId or empty THE_SERVICE_ORDER.parentGroupId )}" >
							        		<img onclick="submitForm('${dto.resourceId}', '${dto.vendorId }', '${dto.conditionalChangeDate1}', '${dto.conditionalChangeDate2}', '${dto.conditionalStartTime}', '${ dto.conditionalEndTime}', '${dto.responseReasonId}','${dto. conditionalSpendLimit}')" 
							        			src="${staticContextPath}/images/spacer.gif" width="117" height="20" class="btn20Bevel"
							        			
							        			<c:choose>
							        			<c:when test="${priceModelBid}">style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:when>
							        			<c:otherwise>style="background-image: url(${staticContextPath}/images/btn/acceptConditions.gif);"</c:otherwise>
							        		 	</c:choose>
							        		 >
										</c:if>
							        </p>
							    </td>
							    <!-- End Details Column -->
							</tr>
							<c:if test="${(dto.responseId != 5 && dto.responseId != 3 && (dto.responseComment != null || dto.responseReasonDesc != null)) || (dto.responseId == 3 && dto.responseReasonDesc != null)}">
								 <tr>
								 	<td colspan="2" style="border:none;background-color:#dbdbdb;">
									 	  <div style="overflow:hidden;">
									 		<c:choose>
									 			<c:when test="${dto.responseId == 3 && dto.responseReasonDesc != null}">${dto.responseReasonDesc }</c:when>
									 			<c:otherwise>
									 				<c:choose>
									 					<c:when test="${dto.responseComment != null }">${dto.responseComment}</c:when>
									 					<c:otherwise>${dto.responseReasonDesc}</c:otherwise>
									 				</c:choose>
									 			</c:otherwise>
									 		</c:choose>
									 		<c:if test="${dto.responseId == 2 && !priceModelBid}">
									 			<c:if test="${taskLevelIsOn == 'true' && (dto.responseReasonId==8 || dto.responseReasonId==9)}">
									 				<b style="color: blue;text-align: left;float:left;">
									 				This service Order is priced at "task level". By Accepting the Conditions, the New 
									 				Counter Offer price will be proportionally distributed among the existing tasks. </b>
									 			</c:if>
									 		</c:if>
									 	  </div>
								 	</td>
								 </tr>
							</c:if>
						</table>
					</c:if>
				</td>
	    	</tr>
		
		    <c:if test = "${slAdmin == true}" >
				<tr>
					<td colspan="3" style="col1 first odd">
						<div style="margin: 10px; background: #eee; padding: 10px;">
							<span class="right">Last entered by: ${dto.modifyingAdmin}.
								<c:if test="${dto.modifiedDateTime != null}">
								  <c:catch var="e">
								  	on 
								    <fmt:parseDate var="mt1" value="${dto.modifiedDateTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
								  </c:catch>
								  <fmt:formatDate value="${mt1}" type="both" dateStyle="medium" /> 
								 </c:if>
							</span>
							<h4>Market Maker Response</h4>
							<label style="display: block; padding: 3px 0;">Call Status: 
								<select id = "callStatusId[${dtoIndex.index}]" name = "callStatusId" style="width:300px;">
									<option value="#">Select a Status</option>
									<c:forEach items="${callStatusList}" var="cs">
										<c:if test="${dto.callStatusId != cs.label}">
											<option value="${cs.label}">${cs.value}</option>
										</c:if>
										
										<c:if test="${dto.callStatusId == cs.label}">
											<option value="${cs.label}" selected>${cs.value}</option>
										</c:if>
									</c:forEach>
								</select>
							</label>
							<label style="display: block; padding: 3px 0;">Comment: 
								<input type="text" name = "mktMakerComments" id = "mktMakerComments[${dtoIndex.index}]" style="width: 300px;" value = "${dto.mktMakerComments}" />
								<input type="button" onclick="updateMarketMakerComments('${dto.resourceId}','${dto.firstName}','${dto.lastName}','${dtoIndex.index}')" value="Update" />					
							</label>
						</div>
					</td>
				</tr>
			</c:if>
			<input type="hidden" name="vendorId" id="vendorId" value ="${dto.resourceId}">
	
		</c:forEach>
	
		<c:if test="${empty providerOfferList}">
			<tr>
				<td colspan="3" class="col1 first odd">
					No Records Found...
				</td>
			</tr>
		</c:if>
	</table>
</c:otherwise>
</c:choose>	
