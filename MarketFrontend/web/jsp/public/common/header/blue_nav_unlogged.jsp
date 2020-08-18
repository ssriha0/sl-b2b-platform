<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<div id="mainNav">
	<ul>

		<%--********* Handle logged in users **********--%>
		<c:if test="${SecurityContext != null}">
		
			<c:choose>
				<c:when test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
					<li>
						<a href="dashboardAction.action" id="dashboard"></a>
					</li>
					<li class="divider"></li>
				</c:when>
				<c:otherwise>
					<c:if test="${SecurityContext.regComplete}">
						<li>
							<a href="dashboardAction.action" id="dashboard"></a>
						</li>
						<li class="divider"></li>
					</c:if>
				</c:otherwise>
			</c:choose>


			<tags:security actionName="serviceOrderMonitor">
				<li>
					<a href="serviceOrderMonitor.action" id="serviceOrderMonitor"></a>
				</li>
			</tags:security>

			<tags:security actionName="financeManagerController">
				<li>
					<a href="financeManagerController_execute.action" id="financialMgr"></a>
				</li>
				<li class="divider"></li>
			</tags:security>

			<c:if
				test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3 or SERVICE_ORDER_CRITERIA_KEY.roleId == 2 }">
				<tags:security actionName="pbController_execute">
					<li>
						<a href="pbController_execute.action" id="menuPowerBuyer"></a>
					</li>
					<li class="divider"></li>
				</tags:security>
			</c:if>


			<tags:security actionName="etmSearch_">
				<li>
					<a href="etmSearch_execute.action" id="exploreMktplace"
						class="topLevel"></a>
				</li>
				<li class="divider"></li>
			</tags:security>

		</c:if>




		<%--************  Start of unlogged *****************--%>

		<c:if test="${SecurityContext == null}">
			<li class="parentNavItem">
				<a					
					id="whatIsSL" class="topLevel"></a>
				<ul class="level2 whatIsSl-subnav">
						<%-- 
					<ul class="level3">
						<li>
							<a href="${contextPath}/soWhatIsSL_companyNews.action" class="subnav" id="compNewsLink">Company News</a>
						</li>
					</ul>
					--%>	
					<li class="parentNavItem">
						<a href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp" class="subnav subnavArrow"
							id="aboutUsLink">About Us</a>
					</li>				
					<li class="parentNavItem">
						<a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp" class="subnav">About Our Service Providers</a>
					</li>
					<li class="parentNavItem">
						<a href="${contextPath}/jsp/public/support/support_faq.jsp" class="subnav">Frequently Asked Questions</a>
					</li>
				</ul>
			</li>
			<li class="divider"></li>
			<!-- <li>
				<a id="homeowners" href="${contextPath}/joinNowBuyerSimpleAction.action" class="topLevel"></a>
			</li>
			 
			<li class="divider"></li> -->
			<li>
				<a id="businesses" href="${contextPath}/joinNowBuyerAction.action" class="topLevel"></a>
			</li>
			<li class="divider"></li>
			<li>
				<a href="joinNowAction.action" id="serviceProviders" class="topLevel"></a>
			</li>

			<li class="divider"></li>
			<li>
				<%-- <a href="${contextPath}/jsp/public/need_service_now/body/need_service_now.jsp" id="needServiceNow" class="topLevel"></a> --%>
				<a id="exploreMktplace" href="${contextPath}/homepage_etm.action"></a>
			</li>
			<li class="divider"></li>
		</c:if>
		<%-- End of unlogged --%>




	</ul>
</div>
