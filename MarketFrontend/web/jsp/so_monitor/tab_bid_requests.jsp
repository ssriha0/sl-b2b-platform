<%@page import="java.util.ArrayList" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString(); %>
<%Boolean canManageSO = (Boolean)session.getAttribute("canManageSO");%>
<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<c:set var="isPrimaryInd" value="<%=isPrimaryInd%>"/>

<h2>Bid Requests</h2>
		<div class="monitorTab-leftCol">
			<!-- Provider should either be admin or should have rights to manages SOs to access the service pro and market filters -->
			<c:choose><c:when test="${(isPrimaryInd == true || canManageSO == true || dispatchInd == true) && role == '1'}">	
			<table cellpadding="0px" cellspacing="2px" width="650 px">
			 <tr style="display:none;">				
				<td width="100px">		
				<p class="paddingBtm">			
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.status.filter" />
				</p>
				</td>
				<td width="20px">
				<p class="paddingBtm">
				<select name="statusId<%=request.getAttribute("tab")%>"
					id="statusId<%=request.getAttribute("tab")%>" class="dropdown-80"
					onchange="getSelectedStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe','<%=request.getAttribute("tab")%>' )">
					<%  if (((ArrayList)(request.getAttribute("serviceOrderStatusVOList"))).size() >1 ) {  %>
					<option value="<%=request.getAttribute("tab")%>">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<%}  %>
					<c:forEach var="statusSubstatusList"
						items="${serviceOrderStatusVOList}">
						<option value="${statusSubstatusList.statusId}">
							<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${role}.${statusSubstatusList.statusId}'/>
						</option>
					</c:forEach>
				</select>
				</p>
				</td>				
				<td width="100px">
				<p class="paddingBtm">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
					</p>
				</td>
				<td width="20px">
				<select name="subStatusId<%=request.getAttribute("tab")%>"
					id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','bidRequestsIframe', null)">
					<option value="0">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<c:forEach var="statusSubstatusList"
						items="${serviceOrderStatusVOList}" varStatus="inx">
						<c:choose>
							<c:when test="${inx.first}">
								<c:set var="firstIdx" value="${statusSubstatusList.statusId}" />
								<c:forEach var="serviceOrderSubStatuses"
									items="${statusSubstatusList.serviceOrderSubStatusVO}">
									<option value="${serviceOrderSubStatuses.subStatusId}">
										<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.substate.${serviceOrderSubStatuses.subStatusId}'/>
									</option>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
				</select>
				</td>
			</tr>	
			<tr>
				<%-- Filter by Service Provider --%>
				<td width="100px">				
					Provider Name
				</td>
				<td width="20px">
				<select name="serviceProName<%=request.getAttribute("tab")%>"
					id="serviceProName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','bidRequestsIframe', null )">					
					<option value="null">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<c:forEach var="providerList" items="${ServiceProvidersList}" varStatus="provider">
						<option value="${providerList.providerId}">
						<c:if test="${providerList.lastName != null}">
						 	${providerList.lastName}
						 </c:if>						 
						 <c:if test="${providerList.firstName != null}">
						 	,${providerList.firstName}
						 </c:if>					
						</option>
					</c:forEach>									
				</select>
				</td>
				<!-- Filter by Market -->
				<td width="100px">
					<p class="paddingBtm" style="text-align:right; margin-right:20px;">Market</p>
				</td>
				<td width="20px">
				<select name="marketName<%=request.getAttribute("tab")%>"
					id="marketName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','bidRequestsIframe', null )">
					
					<option value="null">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>				
					<c:forEach var="marketList" items="${ServiceProvidersMarketsList}" varStatus="market">
						<option value="${marketList.marketId}">
							<c:if test="${marketList.marketName!= null}">
								${marketList.marketName}
							</c:if>
						</option>
					</c:forEach>
				</select>	
				</td>
			</tr>			
		</table>
		</c:when>
		<c:otherwise>
			<p class="paddingBtm">			
			<fmt:message bundle="${serviceliveCopyBundle}" key="grid.status.filter" />
			<select name="statusId<%=request.getAttribute("tab")%>"
					id="statusId<%=request.getAttribute("tab")%>" class="dropdown-80"
					onchange="getSelectedStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','bidRequestsIframe','<%=request.getAttribute("tab")%>' )">
					<%  if (((ArrayList)(request.getAttribute("serviceOrderStatusVOList"))).size() >1 ) {  %>
					<option value="<%=request.getAttribute("tab")%>">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<%}  %>
					<c:forEach var="statusSubstatusList"
						items="${serviceOrderStatusVOList}">
						<option value="${statusSubstatusList.statusId}">
							<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${role}.${statusSubstatusList.statusId}'/>
						</option>
					</c:forEach>
				</select>
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
				<select name="subStatusId<%=request.getAttribute("tab")%>"
					id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','bidRequestsIframe', null)">
					<option value="0">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<c:forEach var="statusSubstatusList"
						items="${serviceOrderStatusVOList}" varStatus="inx">
						<c:choose>
							<c:when test="${inx.first}">
								<c:set var="firstIdx" value="${statusSubstatusList.statusId}" />
								<c:forEach var="serviceOrderSubStatuses"
									items="${statusSubstatusList.serviceOrderSubStatusVO}">
									<option value="${serviceOrderSubStatuses.subStatusId}">
										<fmt:message bundle='${serviceliveCopyBundle}' key='workflow.substate.${serviceOrderSubStatuses.subStatusId}'/>
									</option>
								</c:forEach>
							</c:when>
						</c:choose>
					</c:forEach>
				</select>
				</p>
		</c:otherwise></c:choose>
		 
			<div class="clear"></div>

			<div class="grid-table-container" style="position: relative">
						
								
				<table cellpadding="0px" cellspacing="0px" width="710px" id="bidsTabGridHeader">
				<thead class="scrollerTableHdr">	
					<tr>
						<th class="column1 headerSortIcon" style="background-position: 54px 10px;" onclick="sortByThisColumn(this,0);">Provider</th>
						<th class="column2">Title</th>
						<th class="column3 headerSortIcon" style="background-position: 52px 10px;" onclick="sortByThisColumn(this,2);">Location</th>
						<th class="column4 headerSortIcon" style="background-position: 75px 10px;" onclick="sortByThisColumn(this,3);">Service Date<br/> & Time</th>
						<th class="column5 headerSortIcon" style="background-position: 61px 10px;" onclick="sortByThisColumn(this,4);">Posting<br/>Expiration</th>
						<th class="column6">Bid Price</th>
					</tr>
				</thead>
					<tr>
						<td colspan="6">
							<iframe width="100%" height="800" frameborder="0" border="0"
								src="/MarketFrontend/monitor/gridLoader.action?msg=${msg}&tab=Bid%20Requests&dateNum=${dateNum}"
								id="bidRequestsIframe" name="bidRequestsIframe">
							</iframe>
						</td>
					</tr>
				</table>
			</div>
		</div>




		<div class="monitorTab-rightCol">
		
			<c:if test="${SecurityContext.slAdminInd}">
					<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
			</c:if>
		
			<div id="rightMenu_${theTab}" name="rightMenu_${theTab}"
				style="display: none; z-index: 5000;">
				<jsp:include flush="true"
					page="/jsp/so_monitor/menu/soMonitorRightMenu.jsp" />
			</div>
		</div>

