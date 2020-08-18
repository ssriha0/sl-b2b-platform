<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="dashboardVitalStatsBody"
	<c:if test="${provider}">style="background:none;"</c:if>>
	<c:choose>
		<c:when test="${provider}">
			<div id="soStatsTitleBar">
			<span style="margin-left:-450px;">Lead Order Statistics </span> 
			</div>
		</c:when>
		<c:otherwise>
			<div id="soStatsTitleBar">
			<span style="padding-right:280px;">Lead Order Statistics </span> 
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
				
						<td style="width:33%">
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[0] !=0}">
									<a
										href="leadsManagementController.action#new"
										style=""> <span id="${leadDashboardVitalStatsDTO.idList[0]}">${leadDashboardVitalStatsDTO.values[0]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[0]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.idList[0]}">
										${leadDashboardVitalStatsDTO.values[0]} - ${leadDashboardVitalStatsDTO.labels[0]} </span>
								</c:otherwise>
							</c:choose>

						</td>
					
						<td style="width:33%">
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[1] !=0}">
									<a
										href="leadsManagementController.action?status=inactive&inactiveLoad=true?#completed"
										style=""> <span id="${leadDashboardVitalStatsDTO.idList[1]}">${leadDashboardVitalStatsDTO.values[1]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[1]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.idList[1]}">
										${leadDashboardVitalStatsDTO.values[1]} - ${leadDashboardVitalStatsDTO.labels[1]} </span>
								</c:otherwise>
							</c:choose>

						</td>
						
						
						<td style="width:33%">
							

						</td>
						
					</tr>
				
					<tr>
						<td>
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[2] !=0}">
									<a
										href="leadsManagementController.action#working"
										style=""> <span id="${leadDashboardVitalStatsDTO.idList[2]}">${leadDashboardVitalStatsDTO.values[2]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[2]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.idList[2]}">
										${leadDashboardVitalStatsDTO.values[2]} - ${leadDashboardVitalStatsDTO.labels[2]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[3] !=0}">
									<a
										href="leadsManagementController.action?status=inactive&inactiveLoad=true?#cancelled"
										style=""> <span id="${leadDashboardVitalStatsDTO.idList[3]}">${leadDashboardVitalStatsDTO.values[3]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[3]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.idList[3]}">${leadDashboardVitalStatsDTO.values[3]}
										- ${leadDashboardVitalStatsDTO.labels[3]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						<td>
						</td>
					</tr>
				
					<tr>
						<td>
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[4] !=0}">
									<a
										href="leadsManagementController.action#scheduled"
										style=""> <span id="${leadDashboardVitalStatsDTO.idList[4]}">${leadDashboardVitalStatsDTO.values[4]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[4]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.idList[4]}">${leadDashboardVitalStatsDTO.values[4]}
										- ${leadDashboardVitalStatsDTO.labels[4]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						
						<td>
							<c:choose>
								<c:when test="${leadDashboardVitalStatsDTO.values[5] !=0}">
									<a
										href="leadsManagementController.action?status=inactive&inactiveLoad=true?#stale"
										style=""> <span id="${leadDashboardVitalStatsDTO.values[5]}">${leadDashboardVitalStatsDTO.values[5]}</span><span
										style="color: #666;"> - ${leadDashboardVitalStatsDTO.labels[5]}</span> </a>
								</c:when>
								<c:otherwise>
									<span id="${leadDashboardVitalStatsDTO.values[5]}">${leadDashboardVitalStatsDTO.values[5]}
										- ${leadDashboardVitalStatsDTO.labels[5]} </span>
								</c:otherwise>
							</c:choose>
						</td>
						
												
					</tr>

				</table>
				
			</c:when>

			<c:otherwise>
			<div style="width: 275px">
			
				<table width="275px" cellpadding="5" cellspacing="5" border="0">
					<tr>
						<td>
							${leadDashboardVitalStatsDTO.labels[0]} -
							<b> <c:choose>
									<c:when test="${leadDashboardVitalStatsDTO.values[0] !=0}">
									 <span id="${leadDashboardVitalStatsDTO.idList[0]}">
												${leadDashboardVitalStatsDTO.values[0]} </span> 
									</c:when>
									<c:otherwise>
										<span id="${leadDashboardVitalStatsDTO.idList[0]}">
											${leadDashboardVitalStatsDTO.values[0]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						<td>
							${leadDashboardVitalStatsDTO.labels[1]} -
							<b> <c:choose>
									<c:when test="${leadDashboardVitalStatsDTO.values[1] !=0}">
										 <span id="${leadDashboardVitalStatsDTO.idList[1]}">
												${leadDashboardVitalStatsDTO.values[1]} </span> 
									</c:when>
									<c:otherwise>
										<span id="${leadDashboardVitalStatsDTO.idList[1]}">
											${leadDashboardVitalStatsDTO.values[1]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
					</tr>
					<tr>
						<td>
							${leadDashboardVitalStatsDTO.labels[2]} -
							<b> <c:choose>
									<c:when test="${leadDashboardVitalStatsDTO.values[2] !=0}">
							 <span id="${leadDashboardVitalStatsDTO.idList[2]}">
												${leadDashboardVitalStatsDTO.values[2]} </span> 
									</c:when>
									<c:otherwise>
										<span id="${leadDashboardVitalStatsDTO.idList[2]}">
											${leadDashboardVitalStatsDTO.values[2]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
						<td>
							${leadDashboardVitalStatsDTO.labels[3]} -
							<b> <c:choose>
									<c:when test="${leadDashboardVitalStatsDTO.values[3] !=0}">
								 <span id="${leadDashboardVitalStatsDTO.idList[3]}">
												${leadDashboardVitalStatsDTO.values[3]} </span> 
									</c:when>
									<c:otherwise>
										<span id="${leadDashboardVitalStatsDTO.idList[3]}">
											${leadDashboardVitalStatsDTO.values[3]} </span>
									</c:otherwise>
								</c:choose> </b>
						</td>
					</tr>
					
				</table>
			</div>
			</c:otherwise>
		</c:choose>
	</div>

	<c:choose>
		<c:when test="${provider}">
		
		
		<div style="margin-left: 300px;margin-top:30px; position: absolute;">
		<span style="font-family:Verdana;font-size:30px;font-weight:bold;font-style:normal;
		text-decoration:none;color:#CCCCCC;">&nbsp;${leadDashboardVitalStatsDTO.totalLeads}</span>
		 		 		 <div style="height: 10px;"></div>
		<b>Total Leads</b> 
		</div>
		
		<div style="margin-left: 425px;margin-top:30px; position: absolute;">
		<span style="font-family:Verdana;font-size:30px;font-weight:bold;font-style:normal;text-decoration:none;color:#CCCCCC;">&nbsp;${leadDashboardVitalStatsDTO.conversionRate}</span>
	<span style="font-family:Verdana;font-size:20px;font-weight:bold;font-style:normal;text-decoration:none;color:#CCCCCC;">
		%</span>
		 <div style="height: 0px;"></div>
		<span id="convRate" class="glossaryItem">Conversion Rate</span> 
		</div>
		 
		<div style="margin-left: 550px;margin-top:30px; position: absolute;">
		<span style="font-family:Verdana;font-size:30px;font-weight:bold;font-style:normal;text-decoration:none;color:#CCCCCC;">
		&nbsp;${leadDashboardVitalStatsDTO.averageResponseTime}</span>
		<span style="font-family:Verdana;font-size:20px;font-weight:bold;font-style:normal;text-decoration:none;color:#CCCCCC;">
		min</span>

		 		 <div style="height: 0px;"></div>
		<span id="avgResponse" class="glossaryItem">Avg. Response</span>
		</div> 
		
 <input type="hidden" id="completedLead"  name="completedLead" value="${leadDashboardVitalStatsDTO.values[1]}" />
 <input type="hidden" id="totalLead"  name="totalLead" value="${leadDashboardVitalStatsDTO.totalLeads}" />
 <input type="hidden" id="goal"  name="goal" value="${leadDashboardVitalStatsDTO.goal}" />

 
		
			<!--  <div id="totalValue" class="">
				Total Value Received:
				<strong><fmt:formatNumber
						value="${leadDashboardVitalStatsDTO.totalValueReceived}" type="currency"
						currencySymbol="$" />*</strong>
				<p>
					*Excludes
					<span id="bidRequests" class="glossaryItem">Bid Requests</span>
				</p>
			</div>-->
		


		</c:when>
		<c:otherwise>
		<div style="margin-left: 300px;margin-top:20px; position: absolute;">
		<span style="font-family:Verdana;font-size:36px;font-weight:bold;font-style:normal;
		text-decoration:none;color:#CCCCCC;">${leadDashboardVitalStatsDTO.totalLeads}</span>
		 		 		 <div style="height: 10px;"></div>
		<b>Total Leads</b> 
		</div>
		</c:otherwise>
	</c:choose>


</div>



