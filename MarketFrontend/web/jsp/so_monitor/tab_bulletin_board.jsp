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
			<c:if test="${(isPrimaryInd == true || canManageSO == true || dispatchInd== true) && role == '1'}">	
			<c:if test="${provider}"><h3>Bulletin Board</h3></c:if>
			<table cellpadding="0" cellspacing="2" width="650" id="filterContainer">
			<!--  <tr <c:if test="${provider}"> style="display:none;"</c:if>>  -->
	
			<%-- 
			<c:set var="serviceProNameID" value="serviceProName<%=request.getAttribute("tab")%>"  scope="request"/>
			<c:set var="myIframeID" value="<%=request.getAttribute("tab")%>myIframe"  scope="request"/>
			<c:set var="buyerRoleIdID" value="buyerRoleId<%=request.getAttribute("tab")%>"  scope="request"/>
			<c:set var="searchWordsID" value="searchWords<%=request.getAttribute("tab")%>"  scope="request"/>
				 --%>
			<tr>
			
				<td width="100px">
					<label>Search: 
					<input type="text" name="searchWordsBulletin Board" onfocus="clearDefault(this)" id="searchWordsBulletin Board" style="width: 100px;" theme="simple"/>
					</label>
				</td>
				
				<td width="20px">
					<s:submit theme="simple" value="Search" cssClass="button action" onclick="getSelectedProviderBullBoard('serviceProNameBulletin Board','Bulletin BoardmyIframe','buyerRoleIdBulletin Board','searchWordsBulletin Board', 'true')"/>
				</td>
			
				<!-- Filter by Service Provider -->
				<td width="100px">				
				<c:choose><c:when test="${provider}">Provider&nbsp;Name:
					<select  name="serviceProName<%=request.getAttribute("tab")%>"
					id="serviceProName<%=request.getAttribute("tab")%>" class="dropdown-110"
					onchange="getSelectedProviderBullBoard('serviceProName<%=request.getAttribute("tab")%>','<%=request.getAttribute("tab")%>myIframe', 'buyerRoleId<%=request.getAttribute("tab")%>', 'searchWords<%=request.getAttribute("tab")%>', 'false')">					
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
				</c:when>
				<c:otherwise>
					<fmt:message bundle="${serviceliveCopyBundle}" key="grid.servicepro.filter" />
				</c:otherwise></c:choose>
				
				</td>
				</tr>			
		</table>
		</c:if>
		
	<div class="clear"></div>
	<div class="grid-table-container" style="position: relative">
		<table cellpadding="0px" cellspacing="0px" width="710px" class="scrollerTableHdr recdOrdersHdr">
				<c:if test="${provider}">
					<thead class="scrollerTableHdr" id="receivedTabGridHeader">
					<tr><%-- ss: sorting param needs to be figured out --%>
						<th class="column1" style="background-position:69px 10px">
							Provider
						</th>
						<th class="column2 headerSortIcon" style="background-position:43px 10px; width:183px;" 
						onclick="sortByColumnBullBoard('<%=request.getAttribute("tab")%>','SoId', 'commonGridHandler')">
							Details
						</th>
						<th class="column3 headerSortIcon" style="background-position:55px 10px;" 
						onclick="sortByColumnBullBoard('<%=request.getAttribute("tab")%>','City', 'commonGridHandler')">
							Location
						</th>
						<th class="column4 headerSortIcon" style="background-position:75px 10px;" 
						onclick="sortByColumnBullBoard('<%=request.getAttribute("tab")%>','ServiceDate', 'commonGridHandler')">
							Service Date<br/>& Time
						</th>
						<th class="column5" style="background-position:32px 10px; width:120px;">
						</th>
						<th class="column6 headerSortIcon" style="background-position:50px 10px;" 
							onclick="sortByColumnBullBoard('<%=request.getAttribute("tab")%>','ageOfOrder', 'commonGridHandler')">
								<div style="padding:0; margin:0 0 0 2px;">Age of<br/> Order</div>
							</th>
					</tr>
					</thead>					
					
				</c:if>
					<tr>
						<td colspan="7">
							<iframe width="100%" height="800" marginwidth="0"
								marginheight="0" frameborder="0"
								src="/MarketFrontend/monitor/gridLoader.action?msg=${msg}&dateNum=${dateNum}&tab=<%=request.getAttribute("tab") %>"
								id="<%=request.getAttribute("tab") %>myIframe" name="<%=request.getAttribute("tab") %>myIframe">
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

