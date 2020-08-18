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






<script language="javascript" type="text/javascript">

		jQuery.noConflict();
		jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>
		});

		<c:out value="${classicJs}"/>

	</script>


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


<c:if test="${!IS_SIMPLE_BUYER}">
	<div id="auxNav">

		<c:if test="${IS_LOGGED_IN}">
			<ul>
				<li class="parentNavItem" style="width: 110px;">
					<a href="${contextPath}/dashboardAction.action" id="adminOfc"></a>
					<ul id="auxSublist1" class="level2">
		</c:if>

		<c:if
			test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
			<tags:security actionName="addServiceProAction">
				<li class="parentNavItem">
					<s:a cssClass="subnavArrow"
						href="%{#request['contextPath']}/manageUserActiondoLoadUsers.action">Manage
						Team</s:a>
				</li>
			</tags:security>
			<tags:security actionName="allTabView">
				<li class="parentNavItem">
					<a class="subnavArrow" href="${contextPath}/allTabView.action">Edit Company
						Profile</a>
				</li>
			</tags:security>
			<li class="parentNavItem">
				<a class="subnavArrow" href="${contextPath}/companyTabAction.action">View
					Company Profile</a>
			</li>
		</c:if>

		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">

			<tags:security actionName="BAManageUsersAction">
				<li class="parentNavItem">
					<a class="subnavArrow"
						href="${contextPath}/buyerAdminManageUsers_execute.action">
							Manage Team
					</a>
				</li>

				<li class="parentNavItem">
					<a class="subnavArrow" href="${contextPath}/buyerEditCompanyProfileAction_execute.action?headerTxt=Edit Company Profile">
						Edit Company Profile
					</a>
				</li>
				<li class="parentNavItem">
					<a class="subnavArrow" href="${contextPath}/manageCustomRefs_displayPage.action?headerTxt=Manage Custom References">
						Manage Custom References
					</a>
				</li>

			</tags:security>



			<tags:security actionName="buyerFileUploadAction">
				<li class="parentNavItem">
					<a class="subnavArrow"
						href="<s:url action='%{contextPath}/buyerFileUploadAction.action'/>">Service Order Import Tool</a>
				</li>
			</tags:security>

			<tags:security actionName="SPNBuyerLandingAction">
				<li class="parentNavItem">
					<a class="subnavArrow"
						href="${contextPath}/spnBuyerLanding_displayPage.action">My
						SPN</a>
				</li>
			</tags:security>

			<c:if test="${isMaintenance==true}">

			<li class="parentNavItem">
				<a class="subnavArrow" href="" onclick="return false;" style="cursor: text">Maintenance Panel</a>
				<ul class="level3">
					<tags:security actionName="adminMarketAdj">
						<li>
							<a href="${contextPath}/adminMarketAdj_displayPage.action"
								class="subnav" id="marketAdjustmentLink">Market Adjustment
								Rate</a>
						</li>
					</tags:security>
					<tags:security actionName="jobCode">
						<li>
							<a href="${contextPath}/jobCode_execute.action" class="subnav"
								id="jobCodeMappingLink">Job Code Mapping</a>
						</li>
					</tags:security>
					<tags:security actionName="templateMaintenance_">
						<li>
							<a href="${contextPath}/templateMaintenance_execute.action"
								class="subnav" id="templateMaintenanceLink">Template
								Maintenance</a>
						</li>
					</tags:security>
					<tags:security actionName="spnBuyerLanding_">
						<li>
							<a href="${contextPath}/spnBuyerLanding_displayPage.action"
								class="subnav" id="templateMaintenanceLink">Select Provider
								Networks</a>
						</li>
					</tags:security>
					<tags:security actionName="spnAdminAction_">
						<li>
							<a href="${contextPath}/spnAdminAction_displayPage.action"
								class="subnav" id="templateMaintenanceLink">Select Provider
								Campaign</a>
						</li>
					</tags:security>
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
	<c:when test="${IS_LOGGED_IN && !IS_SIMPLE_BUYER}">
		<div id="pageHeader">
			<h2>
				<c:out value="${pageTitle}" />
			</h2>
		</div>
	</c:when>
</c:choose>
<!-- /#header -->
