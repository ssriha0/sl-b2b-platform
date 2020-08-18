<%@page import="java.util.ArrayList"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="soMonitor.realtimeGrid"/>
	</jsp:include>
		<div class="monitorTab-leftCol">
		
			<s:if test="buyerBidOrdersEnabled != true">
			<p class="paddingBtm">
				<fmt:message bundle="${serviceliveCopyBundle}"
					key="grid.status.filter" />
				<select name="statusId<%=request.getAttribute("tab")%>"
					id="statusId<%=request.getAttribute("tab")%>" class="dropdown-80"
					onchange="getSelectedStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>','','','<%=request.getAttribute("tab")%>myIframe','<%=request.getAttribute("tab")%>' )">
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
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>','','','<%=request.getAttribute("tab")%>myIframe', null)">
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
			
			</s:if><s:else>
			
			<p class="paddingBtm" style="float:left; margin-right:1em;">
				Order Type Filter: 
				<select name="priceModelId<%=request.getAttribute("tab")%>"
					id="priceModelId<%=request.getAttribute("tab")%>" class="dropdown-120"
					onchange="getSelectedPriceModel('priceModelId<%=request.getAttribute("tab")%>','','','<%=request.getAttribute("tab")%>myIframe', null)">
					<option value="0">
						All Types
					</option>
					<option value="NAME_PRICE">
						Priced Request Orders
					</option>
					<option value="ZERO_PRICE_BID">
						Bid Request Orders
					</option>
					<option value="ZERO_PRICE_SEALED_BID">
						Sealed Bid Request Orders
					</option>
				</select>
			</p>
			
			<p class="paddingBtm">
				Sort By:
				<a href="#" onclick="sortByColumn('Posted','SoId', 'commonGridHandler')"> Service Order # </a> |
				<a href="#" onclick="sortByColumn('Posted','SpendLimit', 'commonGridHandler')"> Maximum Price </a> |
				<a href="#" onclick="sortByColumn('Posted','TimeToAppointment', 'commonGridHandler')"> Appt. Time</a> |
				<a href="#" onclick="sortByColumn('Posted','AgeOfOrder', 'commonGridHandler')"> Age of Order </a>
			</p>
			
			</s:else>

			<div class="clear"></div>

			<div class="grid-table-container" style="position: relative">
				<table cellpadding="0" cellspacing="0" class="scrollerTableHdr recdOrdersHdr">

					<tr >
						<th class="column1">
							&nbsp;
						</th>
						<th class="column2">
							<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none" id="sortByStatus<%=request.getAttribute("tab")%>" /><a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','Status', 'commonGridHandler')"> <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.status" /> </a>
						</th>
						<th class="column3">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortBySoId<%=request.getAttribute("tab")%>" /><a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','SoId', 'commonGridHandler')"> <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.soid" /> </a>
						</th>
						<th class="column4">
							<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.title" />
						</th>
						<th class="column5">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortBySpendLimit<%=request.getAttribute("tab")%>" /><a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','SpendLimit', 'commonGridHandler')"> <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.spend.limit" /> </a>
						</th>
						<th class="column6">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:block" id="sortByTimeToAppointment<%=request.getAttribute("tab")%>" /><a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','TimeToAppointment', 'commonGridHandler')"> Appt. Time
							</a>
						</th>
						<th class="column7">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortByAgeOfOrder<%=request.getAttribute("tab")%>" /><a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','AgeOfOrder', 'commonGridHandler')"> <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.age.of.order" /> </a>
						</th>
					</tr>

					<tr>
						<td colspan="7">
							<iframe width="100%" height="800" marginwidth="0" marginheight="0" frameborder="0" src="/MarketFrontend/monitor/gridLoader.action?msg=${msg}&dateNum=${dateNum}&tab=<%=request.getAttribute("tab") %>" name="<%=request.getAttribute("tab")%>myIframe" id="<%=request.getAttribute("tab")%>myIframe"> </iframe>
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

