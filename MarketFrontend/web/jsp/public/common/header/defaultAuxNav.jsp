<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="selectLocnPage" scope="request" value="<%=request.getAttribute("SELECT_LOCATION_PAGE")%>" />
<c:set var="findProvidersPage" scope="request" value="<%=request.getAttribute("FIND_PROVIDERS_PAGE")%>" />
<c:set var="simpleBuyerPaymentPage" scope="request" value="<%=request.getAttribute("SB_PAYMENT_PAGE")%>" />
<c:set var="descAndSchedulePage" scope="request" value="<%=request.getAttribute("DESC_SCHE_PAGE")%>" />
<c:set var="simpleBuyerReviewPage" scope="request" value="<%=request.getAttribute("SB_REVIEW_PAGE")%>" />
<c:if test="${isPrimaryResource == false || isSLAdmin == true}"><c:set var="termsLegalNoticeChecked" value="false" scope="request" /></c:if>
<%-- Determine Conditional Autorouting authorization --%>
<c:set var="hasCarAuth" value="false" scope="page" />
<tags:security actionName="routingRulesAction_edit">
  <c:set var="hasCarAuth" value="true" scope="page" /> 
</tags:security>
<tags:security actionName="routingRulesAction_view">
  <c:set var="hasCarAuth" value="true" scope="page" />
</tags:security>

<%-- Determine Autoclose authorization --%>
<c:set var="hasAutocloseAuth" value="false" scope="page" />

<tags:security actionName="autoClose_">
  <c:set var="hasAutocloseAuth" value="true" scope="page" /> 
</tags:security>


<%-- Determine Autoclose authorization --%>
<c:set var="hasAutoCloseInHome" value="false" scope="page" />

<tags:security actionName="autoCloseHSR_">
  <c:set var="hasAutoCloseInHome" value="true" scope="page" /> 
</tags:security>

<%
	ServiceOrdersCriteria commonCriteria = (ServiceOrdersCriteria) session
			.getAttribute("SERVICE_ORDER_CRITERIA_KEY");

	boolean isLoggedIn = false; //SecurityContext != null
	if (commonCriteria != null
			&& commonCriteria.getSecurityContext() != null) {
		isLoggedIn = true;
	}

	boolean simpleOverride = false;//passed from the jsp including global header
	boolean isSimpleBuyer = false;//roleID = 5
	Boolean sesSimpleOverride = (Boolean) session
			.getAttribute("simpleBuyerOverride");
	if (sesSimpleOverride != null && sesSimpleOverride.booleanValue()) {
		simpleOverride = true;
	}
	if ((isLoggedIn && commonCriteria.getSecurityContext().getRoleId()
			.intValue() == 5)) {
		isSimpleBuyer = true;
	}
	boolean isProvider;//roleID = 1
	boolean isProBuyer;//roleID = 3
%>

<c:if test="${!empty currentMenu}">
	<script language="javascript" type="text/javascript">
	if(typeof jQuery != 'undefined'){
		jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>
		});
}
	</script>
</c:if>
<c:if test="${ termsLegalNoticeChecked && not isSLAdmin && isPrimaryResource}">	
	<script language="javascript" type="text/javascript">
	jQuery(document).ready(function($){
			   		
					 $('a#adminOfc').click(function(){ 
						$("a").removeAttr('href'); 
						$("a").removeAttr("onclick");
											
					});
					 $('a#adminOfc').hover(function(){ 
						$("a").removeAttr('href');    
						$("a").removeAttr("hover");
					});
			   
	});
	</script>
</c:if>
<script language="JavaScript" type="text/javascript">
			function clear_enter_zip()
			{
				if (document.getElementById('zipCode').value == 'Enter Zip Code')
				{
					document.getElementById('zipCode').value = '';
				}
			}

			function add_enter_zip()
			{
				if (document.getElementById('zipCode').value == '')
				{
					document.getElementById('zipCode').value = 'Enter Zip Code';
				}
			}


	</script>

<c:if test="${!noCss}">
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
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
<tags:security actionName="manageReasonCodes_" >
	<c:set var="isMaintenance" scope="request" value="true" />
</tags:security>
<tags:security actionName="SKUMaintenance_" >
	<c:set var="isMaintenance" scope="request" value="true" />
</tags:security>
<tags:security actionName="buyerSurvey_" >
	<c:set var="isMaintenance" scope="request" value="true" />
</tags:security>	

<c:if test="${!IS_SIMPLE_BUYER}">
	<div id="auxNav"  style="z-index: 200">

		<c:if test="${IS_LOGGED_IN}">
			<ul>
				<li class="parentNavItem" style="width: 110px;">
					<a href="${contextPath}/dashboardAction.action" id="adminOfc"></a>
					<ul id="auxSublist1" class="level2">
		</c:if>

		<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && not termsLegalNoticeChecked}">
			<tags:security actionName="addServiceProAction">
				<li class="parentNavItem">
					<a class="subnavArrow"  href="${contextPath}/manageUserActiondoLoadUsers.action">Manage
						Team</a>
				</li>
			</tags:security>
			<tags:security actionName="allTabView">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow" href="${contextPath}/allTabView.action">Edit Company
						Profile</a>
				</li>
			</tags:security>
			<li style="height:36px" class="parentNavItem">
				<a style="height:25px" class="subnavArrow" href="${contextPath}/companyTabAction.action">View
					Company Profile</a>
			</li>
			<c:if test="${SecurityContext.primaryInd || isSLAdmin}">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow" href="${contextPath}/zipcoverage_displayPage.action">View
						Company Zip Code Coverage</a>
				</li>
			</c:if>
			<!--  Sl-21464 Permission for D2C provider start-->
			<c:if test="${skuCountOfPrimaryIndustry}">
				<li style="height: 40px" class="parentNavItem">
				<a class="subnavArrow" style="height: 29px"
					href="<s:url value='%{contextPath}/providerPortalAction.action'/>">ServiceLive
						Consumer Services</a>
				</li>
			</c:if>
			<!--  Sl-21464 Permission for D2C provider End-->
			<!-- SL-20548 -->
			<li style="height: 40px">
				<a class="subnavArrow" style="height: 29px"
				href="${contextPath}/externalCalendarIntegration.action" title="Calendar Settings">Calendar Settings</a>
			</li>
			<c:choose>
			<c:when test ="${SecurityContext.primaryInd && SecurityContext.spnMonitorAvailable > 0}">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow" href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a>					
				</li>	
			</c:when>
			 <c:otherwise>
			<tags:security actionName="spnManagerAction_">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow" href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a>					
				</li>	
			</tags:security>
			</c:otherwise>	
			</c:choose>	
			<tags:security actionName="w9registrationAction_">
				<li style="height:40px" class="parentNavItem">
					<a class="subnavArrow" style= "height:29px" href="<s:url value='%{contextPath}/w9registrationAction_execute.action'/>">W9 Legal Tax Information</a>
				</li>
			</tags:security>	
			
			<c:if test="${SecurityContext.unArchivedCARRulesAvailable>0}">
				<tags:security actionName="businessinfoAction">				
					<li style="height:36px" class="parentNavItem">
						<a class="subnavArrow" style="height:31px" href="<s:url value='%{contextPath}/manageAutoOrderAcceptanceAction_execute.action'/>">Manage Auto Acceptance</a>
					</li>
				</tags:security>	
			</c:if>
			
		</c:if>

		<!-- SL-21142 
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
		<li style="height:36px" class="parentNavItem">
					<a style="height:20px" class="subnavArrow" href="<s:url value='%{contextPath}/lmsFileGetAction_getLmsDetailHistory.action'/>">
						&nbsp;LMS Integration Tool &nbsp;&nbsp;&nbsp;
					</a>
				</li>
		</c:if>
 -->
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">

			<tags:security actionName="BAManageUsersAction">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow"
						href="${contextPath}/buyerAdminManageUsers_execute.action">
							Manage Team &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
				</li>

				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow" href="${contextPath}/buyerEditCompanyProfileAction_execute.action?headerTxt=Edit Company Profile">
						Edit Company Profile &nbsp;&nbsp;&nbsp;&nbsp;
						
					</a>
				</li>
			</tags:security>	
			<tags:security actionName="financeManagerController">				
				<li style="height:47px" class="parentNavItem">
					<a style="height:36px" href="${contextPath}/buyerEditPIIAction_execute.action?headerTxt=Personal Identification">
					Taxpayer/Personal Identification Information
					</a>
				</li>
			</tags:security>
			<tags:security actionName="BAManageUsersAction">	
				<li style="height:47px" class="parentNavItem">
					<a style="height:36px" class="subnavArrow" href="${contextPath}/manageCustomRefs_displayPage.action?headerTxt=Manage Custom References">
						Manage Custom References &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</a>
				</li>
			</tags:security>
			<tags:security actionName="spnLoginAction">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow"
						href="${contextPath}/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnMonitorNetwork_display">
						SPN Monitor &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
				</li>
			</tags:security>
			
			<tags:security actionName="spnAuditorController_route">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow"
						href="${contextPath}/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnAuditorController_route">
						SPN Auditor &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
				</li>
			</tags:security>
		
		 <tags:security actionName="approveSPNCampaignAction" isAdmin="true">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow"
						href="${contextPath}/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnMonitorNetwork_display&buyerId=${SecurityContext.companyId}">
						SPN Approve Campaign &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
				</li>
		</tags:security>
		
			<tags:security actionName="buyerFileUploadAction">
				<li style="height:47px" class="parentNavItem">
					<a style="height:36px" class="subnavArrow"
						href="<s:url action='%{contextPath}/buyerFileUploadAction.action' />">
						Service Order Import Tool &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
				</li>
			</tags:security>

			<tags:security actionName="SPNBuyerLandingAction">
				<li style="height:36px" class="parentNavItem">
					<a style="height:25px" class="subnavArrow"
						href="${contextPath}/spnBuyerLanding_displayPage.action">My
						SPN &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</a>
				</li>
			</tags:security>

			<c:if test="${isMaintenance==true}">

			<li style="height:36px" class="parentNavItem">
				<a style="height:25px" class="subnavArrow" href="" onclick="return false;" style="cursor: text">Maintenance Panel</a>
				<ul class="level3">
					<tags:security actionName="adminMarketAdj">
						<li style="height:47px">
							<a style="height:36px" href="${contextPath}/adminMarketAdj_displayPage.action"
								class="subnav" id="marketAdjustmentLink">Market Adjustment
								Rate&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</a>
						</li>
					</tags:security>

                    <c:if test="${hasCarAuth}"> 
						<li style="height:36px">
							<a style="height:25px" href="${contextPath}/rrDashboard_display.action" class="subnav" id="carLink">
							Conditional Routing &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						</li>
                    </c:if>

					<tags:security actionName="jobCode">
						<li style="height:36px">
							<a style="height:25px" href="${contextPath}/jobCode_execute.action" class="subnav"
								id="jobCodeMappingLink">Job Code Mapping &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</a>
						</li>
					</tags:security>

					<tags:security actionName="templateMaintenance_">
						<li style="height:36px">
							<a style="height:25px" style="height:25px" href="${contextPath}/templateMaintenance_execute.action"
								class="subnav" id="templateMaintenanceLink" >Template
								Maintenance</a>
						</li>
					</tags:security>

					<tags:security actionName="adminTripCharge_">
						<li style="height:36px">
							<a style="height:25px" href="${contextPath}/adminTripCharge_displayPage.action"
								class="subnav" id="templateMaintenanceLink">Trip Charge Config &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</a>
						</li>
					</tags:security>

					<tags:security actionName="orderGroupManager_">
						<li style="height:36px">
							<a style="height:25px" href="${contextPath}/orderGroupManager_displayPage.action"
								class="subnav" id="templateMaintenanceLink">Order Group Manager&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								</a>
						</li>
					</tags:security>
					
						
					<tags:security actionName="manageReasonCodes_">
						<li style="height:47px">
							<a style="height:36px" href="${contextPath}/reasonCode_displayPage.action"
								class="subnav" id="templateMaintenanceLink">
								Reason Code Manager  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						</li>
					</tags:security>
					
					<tags:security actionName="buyerSurvey_">
						<li style="height:47px">
							<a style="height:36px" href="${contextPath}/buyerSurvey_displayPage.action"
								class="subnav" id="templateMaintenanceLink">
								Email & Survey Configuration</a>
						</li>
					</tags:security>
					
					<c:if test="${hasAutocloseAuth}">
						<li style="height:47px">
								<a style="height:36px" style="height: 100%;" href="${contextPath}/autoClose_displayPage.action"
									class="subnav" id="templateMaintenanceLink">Manage Auto Close & Pay Rules</a>
						</li>
					</c:if>
					
					<c:if test="${hasAutoCloseInHome}">
						<li style="height:47px">
								<a style="height:36px" style="height: 100%;" href="${contextPath}/autoCloseHSR_displayPage.action"
									class="subnav" id="templateMaintenanceLink">Manage Auto Close & Pay Rules</a>
						</li>
					</c:if>
					<tags:security actionName="SKUMaintenance_">
					<li style="height:36px"> 
					<a style="height:25px"  href="${contextPath}/sku_maintenancePage.action" class="subnav" id="templateMaintenanceLink">SKU Maintenance</a>
					</li>
					</tags:security>
					<!-- SLT-1614 -->
					<tags:security actionName="WFMConfigure_">
					<li style="height:36px"> 
					<a style="height:25px"  href="${contextPath}/wfm_maintenancePage.action" class="subnav" id="wfmMaintenanceLink">Workflow Queue Set Up</a>
					</li>
					</tags:security>
					<!-- SLT-1614 -->
					</ul>
			</li>
			</c:if>
		</c:if>

		<c:if test="${IS_LOGGED_IN && !IS_SIMPLE_BUYER}">
			</ul>
			</ul>
		</c:if>

	</div>

</c:if>

<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<div id="mainNav">
			<ul>
				<li>
					<a id="dashboard" href="${contextPath}" title="home"></a>
				</li>
				<li class="parentNavItem">
					<a id="services"  title="services"></a>
					<ul class="level2 whatIsSl-subnav">
						<tags:security actionName="serviceOrderMonitor">
							<li>
								<a class="subnav subnavArrow" href="${contextPath}/serviceOrderMonitor.action" id="som">Service Order Monitor</a>
							</li>
						</tags:security>
						<li>
							<a class="subnav subnavArrow" href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoSelectLocation" title="Create New Service Order">Create New Service Order</a>
						</li>
					</ul>
				</li>
				<li class="parentNavItem">
					<a href="${contextPath}/financeManagerController.action" id="financialMgr" title="ServiceLive Wallet"></a>
					<ul class="level2">
						<li>
							<a class="subnav subnavArrow" href="${contextPath}/financeManagerController.action" title="Balances">Balances</a>
						</li>
						<!-- 	<li><a href="${contextPath}/" title="Manage Wallet">Manage Wallet</a></li>
											<li><a href="${contextPath}/" title="View History">View History</a></li> -->
					</ul>
				</li>
				<li class="parentNavItem">
					<a href="#" id="accountmnu" title="Account"></a>
					<ul class="level2">
						<li>
							<a class="subnav subnavArrow" href="${contextPath}/csoEditAccount_displayPage.action"
								title="My Information">My Information</a>
						</li>
						<li>
							<a class="subnav subnavArrow"
								href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoManageLocations"
								title="Manage Locations">Manage Locations</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
		<div id="auxNav">
			<ul>
				<li><a id="newso"
					href="${contextPath}/ssoController_execute.action?appMode=CREATE&view=ssoSelectLocation"></a></li>
		</div>

	</c:when>
	<c:when test="${IS_LOGGED_IN && !IS_SIMPLE_BUYER && not empty pageTitle}">
		<div id="pageHeader">
			<h2>
				<c:out value="${pageTitle}" />
			</h2>
		</div>
	</c:when>
</c:choose>
<!-- /#header -->
