<%@page import="java.util.*"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<%String isPrimaryInd = (String)session.getAttribute("isPrimaryInd").toString(); %>
<%Boolean canManageSO = (Boolean)session.getAttribute("canManageSO");%>
<c:set var="role" value="${SERVICE_ORDER_CRITERIA_KEY.roleId}" />
<c:set var="isPrimaryInd" value="<%=isPrimaryInd%>"/>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
	
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="soMonitor.commonGrid"/>
	</jsp:include>
<div class="monitorTab-leftCol">
	<c:choose><c:when test="${(isPrimaryInd == true || canManageSO == true || dispatchInd == true) && role == '1'}">
	<table cellpadding="0px" cellspacing="2px" width="650 px">
			<tr>				
				<td width="150px">		
				<p class="paddingBtm">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.status.filter" />
				</p>
				</td>
				<td width="20px">
				<p class="paddingBtm">
					
		<select name="statusId<%=request.getAttribute("tab")%>"
			id="statusId<%=request.getAttribute("tab")%>" class="dropdown-80"
			onchange="getSelectedStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe','<%=request.getAttribute("tab")%>' )">
			<%  if(((ArrayList)(request.getAttribute("serviceOrderStatusVOList"))).size() >1 ) {  %>
			<option value="<%=request.getAttribute("tab")%>">
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
			</option>
			<%}  %>
			<c:forEach var="statusSubstatusList"
				items="${serviceOrderStatusVOList}">
				<option value="${statusSubstatusList.statusId}" <c:if test = "${statusSubstatusList.selected == true}">selected="selected"</c:if>>
					<fmt:message bundle='${serviceliveCopyBundle}'
						key='workflow.state.${role}.${statusSubstatusList.statusId}' />
				</option>
			</c:forEach>

		</select>
				</p>
				</td>
				<td width="150px">		
				<p class="paddingBtm">
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
				</p>
				</td>
				<td width="20px">
				<p class="paddingBtm">
				<select name="subStatusId<%=request.getAttribute("tab")%>"
			id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
			onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', null)">
			<option value="0">
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
			</option>
			<%  if(((ArrayList)(request.getAttribute("serviceOrderStatusVOList") )).size()==1  || ((request.getAttribute("showSubStatus"))!=null && ((Boolean) request.getAttribute("showSubStatus")).booleanValue() == true)) {  %>
			<c:forEach var="statusSubstatusList"
				items="${serviceOrderStatusVOList}" varStatus="inx">
				<c:choose>
					<c:when test="${(inx.first && fn:length(serviceOrderStatusVOList)==1)||statusSubstatusList.selected == true }">
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
			<% }%>
		</select>
				</p>
				</td>
			</tr>			
			<tr>		
				<!-- Filter by Service Provider -->		
				<td width="150px">		
				<p class="paddingBtm">				
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.servicepro.filter" />
				</p>
				</td>
				<td width="20px">
				<p class="paddingBtm">
				<select name="serviceProName<%=request.getAttribute("tab")%>"
					id="serviceProName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', null )">					
					<option value="null">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>
					<c:forEach var="providerList" items="${ServiceProvidersList}" varStatus="provider">
						<option value="${providerList.providerId}">
							${providerList.lastName},${providerList.firstName}
						</option>
					</c:forEach>									
				</select>
				</p>
				</td>
				<!-- Filter by Market -->
				<td width="150px">		
				<p class="paddingBtm">						 		
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.market.filter" />
				</p>
				</td>
				<td width="20px">
				<p class="paddingBtm">
				<select name="marketName<%=request.getAttribute("tab")%>"
					id="marketName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', null )">
					
					<option value="null">
						<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
					</option>				
					<c:forEach var="marketList" items="${ServiceProvidersMarketsList}" varStatus="market">
						<option value="${marketList.marketId}">
							${marketList.marketName}
						</option>
					</c:forEach>
				</select>	
				</p>
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
			<%  if(((ArrayList)(request.getAttribute("serviceOrderStatusVOList"))).size() >1 ) {  %>
			<option value="<%=request.getAttribute("tab")%>">
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
			</option>
			<%}  %>
			<c:forEach var="statusSubstatusList"
				items="${serviceOrderStatusVOList}">
				<c:if test="${!isNonFundedBuyer || statusSubstatusList.statusId != 165}">
				  <option value="${statusSubstatusList.statusId}" <c:if test = "${statusSubstatusList.selected == true}">selected="selected"</c:if>>
				    <fmt:message bundle='${serviceliveCopyBundle}' key='workflow.state.${role}.${statusSubstatusList.statusId}' />
			      </option>
			    </c:if>
				
			</c:forEach>
		</select>
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.substatus.filter" />
				<select name="subStatusId<%=request.getAttribute("tab")%>"
			id="subStatusId<%=request.getAttribute("tab")%>" class="dropdown-120"
			onchange="getSelectedSubStatus('statusId<%=request.getAttribute("tab")%>', 'subStatusId<%=request.getAttribute("tab")%>', 'serviceProName<%=request.getAttribute("tab")%>','marketName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', null)">
			<option value="0">
				<fmt:message bundle="${serviceliveCopyBundle}" key="grid.show.all" />
			</option>
			<%  if(((ArrayList)(request.getAttribute("serviceOrderStatusVOList") )).size()==1  || ((request.getAttribute("showSubStatus"))!=null && ((Boolean) request.getAttribute("showSubStatus")).booleanValue() == true)) {  %>
			<c:forEach var="statusSubstatusList"
				items="${serviceOrderStatusVOList}" varStatus="inx">
				<c:choose>
					<c:when test="${(inx.first && fn:length(serviceOrderStatusVOList)==1)||statusSubstatusList.selected == true}">
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
			<% }%>
		</select>
		</p>
		</c:otherwise></c:choose>
	<div class="clear"></div>
	<div style="color: blue;font-size:13px;" id="cancelMessage" class="cancelMessage">
	</div><br>
	<div class="grid-table-container" style="position: relative">
				
		<table cellpadding="0px" cellspacing="0px" width="710px" class="scrollerTableHdr recdOrdersHdr">
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
					<img src="${staticContextPath}/images/grid/arrow-down-white.gif" class="funky_img" border="0" style="display:block" id="sortByServiceDate<%=request.getAttribute("tab")%>" />
	    		 	<a href="#" style="color:white" onclick="sortByColumn('<%=request.getAttribute("tab")%>','ServiceDate', 'commonGridHandler')">
					  <fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.service.date" />
					</a>  
				</td>
				<td class="column6">
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.label.location" />
				</td>
			</tr>
			<tr>
				<td colspan="6">
					<iframe width="100%" height="960px" marginwidth="0" scrolling="yes"
						marginheight="0" frameborder="0"
						src="${contextPath}/monitor/gridLoader.action?msg=${msg}&dateNum=${dateNum}&tab=<%=request.getAttribute("tab") %>"
						name="<%=request.getAttribute("tab")%>" frameborder="0"
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
		style="display: none; z-index: 5000">
		<jsp:include flush="true"
			page="/jsp/so_monitor/menu/soMonitorRightMenu.jsp" />
	</div>
</div>