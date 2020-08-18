<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="dashboardVitalStatsBody"
	<c:if test="${provider}">style="background:none;"</c:if>>
	<c:choose>
		<c:when test="${provider}">
			<div id="soStatsTitleBar">
				Service Order Statistics
			</div>
		</c:when>
		<c:otherwise>
			<div class="dashboardVitalStatsHeader" style="background:none repeat scroll 0% 0% rgb(204, 204, 204);">
		<span style="position:absolute;padding-top:5px;padding-left:10px;"><b>Vital Statistics</b></span>
		
		</div>
		</c:otherwise>
	</c:choose>

	<div
		class="dashboardVitalStatsCell <c:if test="${!provider}">dashboardVitalStatsSeparator</c:if>">
		<br />
		<c:choose>
			<c:when test="${provider}">
				<table style="width:100%" cellpadding="0" cellspacing="0" border="0"
					id="soStats">
					<tr>
					<c:if test="${viewOrderPricing==true}">
						<td style="width:25%">
							<c:choose>
								<c:when test="${vitalStatsDTO.values[0] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[0]}"
										style=""> <span id="${vitalStatsDTO.idList[0]}">${vitalStatsDTO.values[0]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[0]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[0]}">
										${vitalStatsDTO.values[0]} - ${vitalStatsDTO.labels[0]} </span>
								</c:otherwise>
							</c:choose>

						</td>
						</c:if>
						<td style="width:25%">
							<c:choose>
								<c:when test="${vitalStatsDTO.values[1] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[1]}"
										style=""> <span id="${vitalStatsDTO.idList[1]}">${vitalStatsDTO.values[1]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[1]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[1]}">
										${vitalStatsDTO.values[1]} - ${vitalStatsDTO.labels[1]} </span>
								</c:otherwise>
							</c:choose>

						</td>
						<c:if test="${viewOrderPricing==true}">
						<td style="width:33%">
							<c:choose>
								<c:when test="${vitalStatsDTO.values[6] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[0]}&tabStatus=${vitalStatsDTO.tabStatus[6]}"
										style=""> <span id="${vitalStatsDTO.idList[6]}">${vitalStatsDTO.values[6]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[6]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[6]}">
										${vitalStatsDTO.values[6]} - ${vitalStatsDTO.labels[6]} </span>
								</c:otherwise>
							</c:choose>

						</td>
						</c:if>
					</tr>
					
					<tr>
					<c:if test="${viewOrderPricing==true}">
						<td>
							<c:choose>
								<c:when test="${vitalStatsDTO.values[2] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[2]}"
										style=""> <span id="${vitalStatsDTO.idList[2]}">${vitalStatsDTO.values[2]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[2]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[2]}">
										${vitalStatsDTO.values[2]} - ${vitalStatsDTO.labels[2]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${vitalStatsDTO.values[3] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[3]}"
										style=""> <span id="${vitalStatsDTO.idList[3]}">${vitalStatsDTO.values[3]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[3]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[3]}">${vitalStatsDTO.values[3]}
										- ${vitalStatsDTO.labels[3]} </span>
								</c:otherwise>
							</c:choose>
						</td>
					</c:if>
						<td>
							<c:choose>
								<c:when test="${vitalStatsDTO.values[7] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.idList[7]}&pendingReschedule=pendingReschedule"
										style=""> <span id="${vitalStatsDTO.values[7]}">${vitalStatsDTO.values[7]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[7]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.values[7]}">${vitalStatsDTO.values[7]}
										- ${vitalStatsDTO.labels[7]}</span>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
					
					<tr>
						<td>
							<c:choose>
								<c:when test="${vitalStatsDTO.values[4] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[4]}"
										style=""> <span id="${vitalStatsDTO.idList[4]}">${vitalStatsDTO.values[4]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[4]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.idList[4]}">${vitalStatsDTO.values[4]}
										- ${vitalStatsDTO.labels[4]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						<c:if test="${viewOrderPricing==true}">
						<!-- Sl-21645 commenting code for bulleting boards and adding for Estimation Request -->
						<%-- <td>
							<c:choose>
								<c:when test="${vitalStatsDTO.values[5] !=0}">
									<a
										href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[5]}"
										style=""> <span id="${vitalStatsDTO.values[5]}">${vitalStatsDTO.values[5]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[5]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.values[5]}">${vitalStatsDTO.values[5]}
										- ${vitalStatsDTO.labels[5]} </span>
								</c:otherwise>
							</c:choose>
						</td> --%>
						<td colspan="2">
							<c:choose>
								<c:when test="${vitalStatsDTO.values[8]>0}">
									<a
										href="orderManagementController.action?omDisplayTab=Inbox"
										style=""> <span id="${vitalStatsDTO.values[8]}">${vitalStatsDTO.values[8]}</span><span
										style="color: #666;"> - ${vitalStatsDTO.labels[8]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${vitalStatsDTO.values[8]}">${vitalStatsDTO.values[8]}
										- ${vitalStatsDTO.labels[8]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						</c:if>
												
					</tr>

				</table>
			</c:when>

			<c:otherwise>
			<div style="width: 275px">
				<b style="padding-left: 4px">Current Orders:</b>
				<table width="300px" cellpadding="5" cellspacing="5" border="0">
					<tr>
						<td>
							${vitalStatsDTO.labels[0]} -
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[0] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[0]}"
											style=""> <span id="${vitalStatsDTO.idList[0]}">
												${vitalStatsDTO.values[0]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.idList[0]}">
											${vitalStatsDTO.values[0]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						<td>
							${vitalStatsDTO.labels[1]} -
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[1] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[1]}"
											style=""> <span id="${vitalStatsDTO.idList[1]}">
												${vitalStatsDTO.values[1]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.idList[1]}">
											${vitalStatsDTO.values[1]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
					</tr>
					<tr>
						<td>
							${vitalStatsDTO.labels[2]} -
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[2] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[2]}"
											style=""> <span id="${vitalStatsDTO.idList[2]}">
												${vitalStatsDTO.values[2]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.idList[2]}">
											${vitalStatsDTO.values[2]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						<td>
							${vitalStatsDTO.labels[3]} -
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[3] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.labels[3]}"
											style=""> <span id="${vitalStatsDTO.idList[3]}">
												${vitalStatsDTO.values[3]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.idList[3]}">
											${vitalStatsDTO.values[3]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						
					</tr>
					<tr>
						  <c:if test = "${vitalStatsDTO.isNonFundedBuyer=='false'}">
						 <td>
							    ${vitalStatsDTO.labels[4]} - 
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[4] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.idList[4]}&tabStatus=${vitalStatsDTO.tabStatus[4]}"
											style=""> <span id="${vitalStatsDTO.labels[4]}">
												${vitalStatsDTO.values[4]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.labels[4]}">
											${vitalStatsDTO.values[4]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>   
						</c:if>
						
						
						
						 <c:if test = "${vitalStatsDTO.isNonFundedBuyer=='false'}">
						 <td>
							${vitalStatsDTO.labels[5]} -
							<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[5] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.idList[5]}&pendingReschedule=pendingReschedule"
											style=""> <span id="${vitalStatsDTO.labels[5]}">
												${vitalStatsDTO.values[5]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.labels[5]}">
											${vitalStatsDTO.values[5]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						</c:if>		
						
						
						<td>
							 <c:if test = "${vitalStatsDTO.isNonFundedBuyer=='true'}">
							${vitalStatsDTO.labels[4]} -
									<b> <c:choose>
									<c:when test="${vitalStatsDTO.values[4] !=0}">
										<a
											href="serviceOrderMonitor.action?displayTab=${vitalStatsDTO.idList[4]}&pendingReschedule=pendingReschedule"
											style=""> <span id="${vitalStatsDTO.labels[4]}">
												${vitalStatsDTO.values[4]} </span> </a>
									</c:when>
									<c:otherwise>
										<span id="${vitalStatsDTO.labels[4]}">
											${vitalStatsDTO.values[4]} </span>
									</c:otherwise>
								</c:choose> </b>
						</c:if>		
						</td>

					</tr>
				</table>
			</div>
			</c:otherwise>
		</c:choose>
	</div>

	<c:choose>
		<c:when test="${provider}">
		<c:if test="${viewOrderPricing==true}">
			<div id="totalValue" class="">
				Total Value Received:
				<strong><fmt:formatNumber
						value="${vitalStatsDTO.totalValueReceived}" type="currency"
						currencySymbol="$" />*</strong>
				<p>
					*Excludes
					<span id="bidRequests" class="glossaryItem">Bid Requests</span>
				</p>
			</div>
			</c:if>


		</c:when>

		<c:otherwise>
			<div id="lifetimeRatings" class="tipLeft">
				<div class="tipContents">
					<p>
						${dashboardDTO.lifetimeRating}/5.00
					</p>
				</div>
			</div>
			<div id="currentRatings" class="tipLeft">
				<div class="tipContents">
					<p>
						${dashboardDTO.currentRating}/5.00
					</p>
				</div>
			</div>
			<div id="givenRatings" class="tipLeft">
				<div class="tipContents">
					<p>
						${dashboardDTO.givenRating}/5.00
					</p>
				</div>
			</div>

			<c:choose>
				<c:when test="${userRole != 'SimpleBuyer'}">
					<div class="dashboardVitalStatsCell">
						<br />
						<div style="padding-left: 10px; width: 275px">
						<b style="padding-left: 4px">Ratings:</b>
						<table width="275px" cellpadding="5" cellspacing="5" border="0">
							<tr>
								<td>
									<%-- <b>Received (${dashboardDTO.numRatingsReceived})</b> --%>
									<span> <c:choose>
										<c:when test="${dashboardDTO.numRatingsReceived!=null}">
											<b>Received (${dashboardDTO.numRatingsReceived})</b>
										</c:when>
										<c:otherwise>
											<b>Received (0)</b>
										</c:otherwise>
									</c:choose> </span>
								</td>
								<td>
									<%-- <b>Given (${dashboardDTO.numRatingsGiven})</b> --%>
									<span> <c:choose>
										<c:when test="${dashboardDTO.numRatingsGiven!=null}">
											<b>Given (${dashboardDTO.numRatingsGiven})</b>
										</c:when>
										<c:otherwise>
											<b>Given (0)</b>
										</c:otherwise>
									</c:choose> </span>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%" cellpadding="0" cellspacing="0" border="0">
										<tr>
											<td width="45%">
												Lifetime -
											</td>
											<td width="55%">
												<span> <c:choose>
														<c:when test="${dashboardDTO.lifetimeStarsNumber == 0}">
															<img
																src="${staticContextPath}/images/common/stars_notRated.gif"
																border="0" />
														</c:when>
														<c:otherwise>
															<img
																src="${staticContextPath}/images/common/stars_<c:out value="${dashboardDTO.lifetimeStarsNumber}"/>.gif"
																border="0" />
														</c:otherwise>
													</c:choose> </span>
											</td>
										</tr>
									</table>
								</td>
								<td>
									<span> <c:choose>
											<c:when test="${dashboardDTO.givenStarsNumber == 0}">
												<img
													src="${staticContextPath}/images/common/stars_notRated.gif"
													border="0" />
											</c:when>
											<c:otherwise>
												<img
													src="${staticContextPath}/images/common/stars_<c:out value="${dashboardDTO.givenStarsNumber}"/>.gif"
													border="0" />
											</c:otherwise>
										</c:choose> </span>
								</td>
							</tr>
							<tr>
								<td>
									<table width="100%">
										<tr>
											<td width="45%">
												Current -
											</td>
											<td width="55%">
												<span> <c:choose>
														<c:when test="${dashboardDTO.currentStarsNumber == 0}">
															<img
																src="${staticContextPath}/images/common/stars_notRated.gif"
																border="0" />
														</c:when>
														<c:otherwise>
															<img
																src="${staticContextPath}/images/common/stars_<c:out value="${dashboardDTO.currentStarsNumber}"/>.gif"
																border="0" />
														</c:otherwise>
													</c:choose> </span>
											</td>
										</tr>
									</table>
								</td>
								<td></td>
							</tr>
						</table>
						</div>
					</div>
					<!-- vital -->
				</c:when>
			</c:choose>

		</c:otherwise>
	</c:choose>


</div>



