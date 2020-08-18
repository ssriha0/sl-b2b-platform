<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="tempPassword" scope="request" value="<%=request.getParameter("tempPassword")%>" />


<%--
	ROLE DEFINITIONS
	1 PROVIDER
	2 SERVICELIVE ADMIN
	3 COMMERCIAL BUYER
	4 ?
	5 HOME/OFFICE BUYER
 --%>

<div id="navigation" class="span-24 clearfix">
	<ul class="superfish">
		<c:if test="${SecurityContext == null}">
		<li class="nav-level1" id="nav-whatissl"><a title="What Is ServiceLive?">What Is SericeLive?</a></li>
			<ul>		
				<li><a href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp">About Us</a></li>		
				<li><a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp" title="About Our Service Providers">About Our Service Providers</a></li>
				<li><a href="${contextPath}/jsp/public/support/support_faq.jsp" title="Frequently Asked Questions">Frequently Asked Questions</a></li>
			</ul>
		</li>
		<li class="nav-level1" id="nav-home-office"><a href="${contextPath}/joinNowBuyerSimpleAction.action" title="Home/Office Consumers">Home/Office</a></li>
		<li class="nav-level1" id="nav-commercial"><a href="${contextPath}/joinNowBuyerAction.action" title="Commercial Service Buyers">Commercial</a></li>
		<li class="nav-level1" id="nav-service-providers"><a href="${contextPath}/joinNowAction.action" title="Service Providers">Service Providers</a></li>
		<li class="nav-level1" id="nav-etm"><a onclick="exploreTheMarketplace('1')" href="#" title="Get Started">Get Started</a></li>
		</c:if>
		<c:if test="${tempPassword == null}">
			<c:if test="${SecurityContext != null}">
				<!-- everyone logged in gets a dashboard -->
				<li class="nav-level1" id="nav-dashboard"><a href="${contextPath}/dashboardAction.action" title="Dashboard">Dashboard</a></li>
	
				<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId != 5}">
				<!-- not for home/office buyers -->
					<tags:security actionName="serviceOrderMonitor">
						<li class="nav-level1" id="nav-som"><a href="${contextPath}/serviceOrderMonitor.action" title="Service Order Monitor">Service Order Monitor</a></li>
					</tags:security>
					<tags:security actionName="financeManagerController">
						<li class="nav-level1" id="nav-slwallet"><a href="${contextPath}/financeManagerController_execute.action" title="ServiceLive Wallet">Service Live Wallet</a></li>
					</tags:security>
					<tags:security actionName="etmSearch_">
					<li class="nav-level1" id="nav-etm"><a href="${contextPath}/etmSearch_execute.action" title="Get Started">Get Started</a></li>
					</tags:security>
				<!-- /not for home/office buyers -->
				</c:if>
				
				<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 5}">
				<!-- for home/office buyers -->
				<li class="nav-level1" id="nav-account"><a href="#" onclick="return:false;" style="cursor:pointer;" title="Services">Services</a>
					<ul>
						<tags:security actionName="serviceOrderMonitor">
						<li><a href="${contextPath}/serviceOrderMonitor.action" title="Service Order Monitor">Service Order Monitor</a></li>
						</tags:security>
						<li><a href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoSelectLocation" title="Create A New Service Order">Create A New Service Order</a></li>
					</ul>
				</li>
				<tags:security actionName="financeManagerController">
					<li class="nav-level1" id="nav-slwallet"><a href="${contextPath}/financeManagerController_execute.action" title="ServiceLive Wallet">Service Live Wallet</a></li>
				</tags:security>
				<li class="nav-level1" id="nav-account"><a href="#" onclick="return:false;" style="cursor:pointer;" title="Account">Account</a>
					<ul>
						<li><a href="${contextPath}/csoEditAccount_displayPage.action" title="My Information">My Information</a></li>
						<li><a href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoManageLocations" title="Manage Locations">Manage Locations</a></li>
					</ul>
				</li>
				<li class="nav-level1" id="nav-newso"><a href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoSelectLocation" title="Create A New Service Order">Create A New Service Order</a></li>
				
				<!-- /for home/office buyers -->
				</c:if>
				
			</c:if>
		</c:if>


		<c:set var="isMaintenance" scope="request" value="false" />
		<tags:security actionName="adminMarketAdj" >
		<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="jobCode" >
		<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="templateMaintenance_" >
		<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="spnBuyerLanding_" >
		<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="spnAdminAction_" >
		<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="adminTripCharge_" >
			<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="orderGroupManager_" >
			<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>


		<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId != 5}">
		<li class="nav-level1" id="nav-admin-office"><a href="#">Administrator Office</a>
			<ul>
				<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
				<!-- provider admin office -->
				<tags:security actionName="addServiceProAction">
					<li><s:a href="${contextPath}/manageUserActiondoLoadUsers.action" title="Manage Team">Manage Team</s:a></li>
				</tags:security>
				<tags:security actionName="allTabView">
					<li><a href="${contextPath}/allTabView.action" title="Company Profile">Company Profile</a></li>
				</tags:security>
					<li><a href="${contextPath}/companyTabAction.action" title="View Company Profile">View Company Profile</a></li>
				<tags:security actionName="zipcoverage_">
					<li><a href="${contextPath}/zipcoverage_displayPage.action" title="View Company Zip Code Coverage">View Company Zip Code Coverage</a></li>
				</tags:security>
				<tags:security actionName="w9registrationAction_">
					<li><a href="<s:url value='${contextPath}/w9registrationAction_execute.action'/>" title="W9 Legal Tax Information">W9 Legal Tax Information</a></li>
				</tags:security>
				<c:if test="${SecurityContext.unArchivedCARRulesAvailable>0}">
					<tags:security actionName="businessinfoAction">
						<li><a href="<s:url value='${contextPath}/manageAutoOrderAcceptanceAction_execute.action'/>" title="Manage Auto Acceptance">Manage Auto Acceptance</a></li>
					</tags:security>
				</c:if>
				<!-- /provider admin office -->
				</c:if>
				<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
				<!-- pro buyer admin office -->
				<tags:security actionName="BAManageUsersAction">
					<li><a href="${contextPath}/buyerAdminManageUsers_execute.action" title="Manage Team">Manage Team</a></li>
				</tags:security>
				<li class="parentNavItem"><a href="${contextPath}/buyerEditCompanyProfileAction_execute.action?headerTxt=Edit Company Profile">Edit Company Profile</a> </li>
				<tags:security actionName="buyerFileUploadAction">
					<li><a href="<s:url action='${contextPath}/buyerFileUploadAction.action'/>" title="Service Order Import Tool">Service Order Import Tool</a></li>
				</tags:security>
				<tags:security actionName="SPNBuyerLandingAction">
					<li><a href="${contextPath}/spnBuyerLanding_displayPage.action" title="My SPN">My SPN</a></li>
				</tags:security>
				<li><a href="${contextPath}/manageCustomRefs_displayPage.action?headerTxt=Manage Custom References">Manage Custom References</a></li>

				<!-- /pro buyer admin office -->
				</c:if>
				<c:if test="${isMaintenance==true}"> 
				<li><a href="#" onclick="return false;" style="cursor: pointer" title="Maintenance Panel">Maintenance Panel</a>
					<ul>
						<tags:security actionName="adminMarketAdj">
							<li><a href="${contextPath}/adminMarketAdj_displayPage.action" title="Market Adjustment Rate">Market Adjustment Rate</a></li>
						</tags:security>
						<tags:security actionName="jobCode">
							<li><a href="${contextPath}/jobCode_execute.action" title="Job Code Mapping">Job Code Mapping</a></li>
						</tags:security>
						<tags:security actionName="templateMaintenance_">
							<li><a href="${contextPath}/templateMaintenance_execute.action" title="Template Maintenance">Template Maintenance</a></li>
						</tags:security>
						<tags:security actionName="spnBuyerLanding_">
							<li><a href="${contextPath}/spnBuyerLanding_displayPage.action" title="Select Provider Networks">Select Provider Networks</a></li>
						</tags:security>
						<tags:security actionName="spnAdminAction_">
							<li><a href="${contextPath}/spnAdminAction_displayPage.action" title="SPN Campaign">Select Provider Campaign</a></li>
						</tags:security>
						<tags:security actionName="adminTripCharge_">
						<li><a href="${contextPath}/adminTripCharge_displayPage.action" title="Trip Charge Config">Trip Charge Config</a></li>
						</tags:security>
						<tags:security actionName="orderGroupManager_">
						<li><a href="${contextPath}/orderGroupManager_displayPage.action" title="Order Group Manager">Order Group Manager</a></li>
						</tags:security>
						<!-- to do 
						<li><a href="#">Document Manager</a></li>
						-->
					</ul>
				</li>
				</c:if>
			</ul>
		</li>
		</c:if>
	</ul>
</div>