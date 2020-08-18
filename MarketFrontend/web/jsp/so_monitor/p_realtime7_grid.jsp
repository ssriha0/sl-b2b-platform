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
<c:set var="provider" value="false"  scope="request"/><%-- ss: needed for presentation logic brevity --%>
<c:set var="sladmin" value="false"  scope="request"/>
<c:if test="${roleType == 1}"><c:set var="provider" value="true" scope="request" /></c:if>
<c:if test="${SecurityContext.slAdminInd}"><c:set var="sladmin" value="true" scope="request" /></c:if>

		<div class="monitorTab-leftCol">
			<!-- Provider should either be admin or should have rights to manages SOs to access the service pro and market filters -->
			<c:choose><c:when test="${(isPrimaryInd == true || canManageSO == true) && role == '1'}">	
			<c:if test="${provider}"><h3>Received</h3></c:if>
			<table cellpadding="0" cellspacing="2" width="650" id="filterContainer">
			<tr <c:if test="${provider}"> style="display:none;"</c:if>>				
				<td>		
				<p class="paddingBtm">			
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.status.filter" />
				</p>
				</td>
				<td>
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
				<c:if test="${provider}"><td colspan="2"></td></c:if>
				<td>
				<p class="paddingBtm">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
					</p>
				</td>
						
				<td>
				<select name="subStatusId<%=request.getAttribute("tab")%>"
					id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', 'buyerRoleId<%=request.getAttribute("tab")%>')">
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
				<!-- Filter by Service Provider -->
				<td>				
				<c:choose><c:when test="${provider}">Provider&nbsp;Name:</c:when>
				<c:otherwise><fmt:message bundle="${serviceliveCopyBundle}" key="grid.servicepro.filter" /></c:otherwise></c:choose>
				</td>
				<td>
				<select  name="serviceProName<%=request.getAttribute("tab")%>"
					id="serviceProName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', 'buyerRoleId<%=request.getAttribute("tab")%>')">					
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

				<c:if test="${provider}">
					<td>
						&nbsp;&nbsp;Type:
					</td>
					<td>
					
						<select	name="buyerRoleId<%=request.getAttribute("tab")%>"
								  id="buyerRoleId<%=request.getAttribute("tab")%>"
								   class="dropdown-110"
								onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', 'buyerRoleId<%=request.getAttribute("tab")%>')">					
							<option value="null">
								<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
							</option>
					
							<option value=3>
								Commercial Orders
							</option>
							<option value=5>
								Non Commercial Orders
							</option>
						</select>
					</td>

				</c:if>


				<!-- Filter by Market -->
				<td>
				<c:choose><c:when test="${provider}">&nbsp;&nbsp;Market:</c:when>
				<c:otherwise><fmt:message bundle="${serviceliveCopyBundle}" key="grid.market.filter" /></c:otherwise></c:choose>
				</td>
				<td>
				<select name="marketName<%=request.getAttribute("tab")%>"
					id="marketName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe','buyerRoleId<%=request.getAttribute("tab")%>' )">
					
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
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
				<select name="subStatusId<%=request.getAttribute("tab")%>"
					id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', 'buyerRoleId<%=request.getAttribute("tab")%>')">
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
		<table cellpadding="0px" cellspacing="0px" width="710px" class="scrollerTableHdr recdOrdersHdr">
				<c:choose><c:when test="${provider}">
					<thead class="scrollerTableHdr" id="receivedTabGridHeader">
					<tr><%-- ss: sorting param needs to be figured out --%>
						<th class="column1 headerSortIcon" style="background-position:69px 10px" 
						onclick="sortByColumn('<%=request.getAttribute("tab")%>','ProviderLastName', 'commonGridHandler')">
							Provider
						</th>
						<th class="column2 headerSortIcon" style="background-position:43px 10px; width:183px;" 
						onclick="sortByColumn('<%=request.getAttribute("tab")%>','SoId', 'commonGridHandler')">
							Details
						</th>
						<th class="column3 headerSortIcon" style="background-position:55px 10px;" 
						onclick="sortByColumn('<%=request.getAttribute("tab")%>','City', 'commonGridHandler')">
							Location
						</th>
						<th class="column4 headerSortIcon" style="background-position:75px 10px;" 
						onclick="sortByColumn('<%=request.getAttribute("tab")%>','ServiceDate', 'commonGridHandler')">
							Service Date<br/>& Time
						</th>
						<th class="column5 headerSortIcon" style="background-position:32px 10px; width:120px;" 
						onclick="sortByColumn('<%=request.getAttribute("tab")%>','SpendLimitTotal', 'commonGridHandler')">
							Price
						</th>
						<th class="column6 headerSortIcon" style="background-position:50px 10px;" 
							onclick="sortByColumn('<%=request.getAttribute("tab")%>','ageOfOrder', 'commonGridHandler')">
								<div style="padding:0; margin:0 0 0 2px;">Age of<br/> Order</div>
							</th>
					</tr>
					</thead>					
					
					
					
				</c:when>
				<c:otherwise>
					<tr>
						<td class="column1">
							<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.view.detail" />
						</td>
						<td class="column2">
							<img src="${staticContextPath}/images/grid/arrow-up-white.gif" class="funky_img" border="0" style="display:none" id="sortByStatus<%=request.getAttribute("tab")%>" />
			    		     <a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','Status', 'commonGridHandler')">
							  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.status" />
							 </a>
						</td>
						<td class="column3">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortBySoId<%=request.getAttribute("tab")%>" />
			    		 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','SoId', 'commonGridHandler')">
							 <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.soid" />
							</a>
						</td>
						<td class="column4">
							<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.title" />
						</td>
						<td class="column5">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortBySpendLimit<%=request.getAttribute("tab")%>" />
			    		 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','SpendLimit', 'commonGridHandler')">
							  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.spend.limit" />
							</a>
						</td>
						<td class="column6">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortByTimeToAppointment<%=request.getAttribute("tab")%>" />
			    		 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','TimeToAppointment', 'commonGridHandler')">
							  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.time.to.apt" />
							</a>
						</td>
						<td class="column7">
							<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:none" id="sortByAgeOfOrder<%=request.getAttribute("tab")%>" />
			    		 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','AgeOfOrder', 'commonGridHandler')">
							 <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.age.of.order" />
							</a>
						</td>

					</tr>
				</c:otherwise></c:choose>

					<tr>
						<td colspan="7">
							<iframe width="100%" height="800" marginwidth="0" scrolling="yes"
								marginheight="0" frameborder="0"
								src="/MarketFrontend/monitor/gridLoader.action?msg=${msg}&dateNum=${dateNum}&tab=<%=request.getAttribute("tab") %>"
								id="<%=request.getAttribute("tab") %>myIframe">
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