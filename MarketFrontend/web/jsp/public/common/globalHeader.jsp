<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="selectLocnPage" scope="request" value="<%=request.getAttribute("SELECT_LOCATION_PAGE")%>" />
<c:set var="findProvidersPage" scope="request" value="<%=request.getAttribute("FIND_PROVIDERS_PAGE")%>" />
<c:set var="simpleBuyerPaymentPage" scope="request" value="<%=request.getAttribute("SB_PAYMENT_PAGE")%>" />
<c:set var="descAndSchedulePage" scope="request" value="<%=request.getAttribute("DESC_SCHE_PAGE")%>" />
<c:set var="simpleBuyerReviewPage" scope="request" value="<%=request.getAttribute("SB_REVIEW_PAGE")%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath()%>" />
<%-- Determine Conditional Autorouting authorization --%>
<c:set var="hasCarAuth" value="false" scope="page" />
<tags:security actionName="routingRulesAction_edit">
  <c:set var="hasCarAuth" value="true" scope="page" /> 
</tags:security>
<tags:security actionName="routingRulesAction_view">
  <c:set var="hasCarAuth" value="true" scope="page" />
</tags:security>

<%
 ServiceOrdersCriteria commonCriteria = (ServiceOrdersCriteria) session.getAttribute("SERVICE_ORDER_CRITERIA_KEY");
 
 boolean isLoggedIn = false; //SecurityContext != null
 if (commonCriteria != null && commonCriteria.getSecurityContext() != null) {
 	isLoggedIn = true;
 }
 
 boolean simpleOverride = false;//passed from the jsp including global header
 boolean isSimpleBuyer = false;//roleID = 5
 Boolean sesSimpleOverride = (Boolean) session.getAttribute("simpleBuyerOverride");
 if (sesSimpleOverride != null && sesSimpleOverride.booleanValue()) {
 	simpleOverride = true;
 }
 if ((isLoggedIn && commonCriteria.getSecurityContext().getRoleId().intValue() == 5)) {
 	isSimpleBuyer = true;
 }
 boolean isProvider;//roleID = 1 
 boolean isProBuyer;//roleID = 3


 %> 


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"> <%-- html close tag is in globalFooter.jsp --%>
<head>
	<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
	<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
	<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
	<script type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>	
	<script type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>


	<c:if test="${!empty currentMenu}">
	<script language="javascript" type="text/javascript">
		
		jQuery.noConflict();
		jQuery(document).ready(function($){
			$("a#<c:out value="${currentMenu}"/>").addClass("selected");
			<c:out value="${jQueryjs}"/>				
		});
		
		<c:out value="${classicJs}"/>
		
	</script>
	</c:if>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css"/>
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
	
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
	
	
		function exploreTheMarketplace(buyerTypeId)
		{
			//alert("Fields are: name = " + name + " mainCategoryId =  " + mainCategoryId + " categoryId = " + categoryId + " subCategoryId = " + subCategoryId + " serviceTypeTemplateId =  " + serviceTypeTemplateId + " Buyer type:" + buyerTypeId);
			//document.getElementById('pop_name').value=name;
			document.getElementById('buyerId').value=buyerTypeId;
	
			jQuery.noConflict();
		    jQuery(document).ready(function($) {
	     		$('#exploreMarketPlace').jqm({modal:true, toTop: true});
	     		$('#exploreMarketPlace').jqmShow();
			 });
	
		}
	</script>
	
</head>

<c:choose>
	<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
		<body class="tundra acquity simple" onload="${onloadFunction};">	
	</c:when>
	<c:otherwise>
		<body class="tundra acquity" onload="${onloadFunction};">	<%-- body close tag is in globalFooter.jsp --%>
	</c:otherwise>
</c:choose>
    
	<div id="page_margins"> <%-- page_margins close tag is in globalFooter.jsp --%>
		<div id="page">
			<div id="header">

				<jsp:include page="/jsp/public/common/defaultLogo.jsp" />					

				<div id="topNav">
					<c:choose>
						<c:when test="${IS_LOGGED_IN}">
							<div class="smallText">
								Welcome, ${SERVICE_ORDER_CRITERIA_KEY.FName}
								${SERVICE_ORDER_CRITERIA_KEY.LName}!
								<s:if test="isSLAdmin==true">
									<em>#-1</em>
								</s:if>
								<s:else>
									<em>#${SERVICE_ORDER_CRITERIA_KEY.vendBuyerResId}</em>
								</s:else>
								<c:choose>
									<c:when test="${lifetimeRatingStarsNumber == null}"></c:when>
									<c:when test="${lifetimeRatingStarsNumber == 0}">Not Rated</c:when>
									<c:otherwise>
										<img
											src="${staticContextPath}/images/common/stars_<c:out value="${lifetimeRatingStarsNumber}"/>.gif"
											alt="" border="0" />
									</c:otherwise>
								</c:choose>
								<br />
								<a href="http://community.servicelive.com">Community</a> |
									<a href="http://blog.servicelive.com" title="Blog">Blog</a> | 
									<a href="${contextPath}/jsp/public/support/support_main.jsp">Support</a> | 
									<a href="${contextPath}/contactUsAction.action">Contact Us</a>
								|
								<a href="${contextPath}/doLogout.action">Logout</a>
							</div>
						</c:when>
						<c:otherwise>
						<a href="http://blog.servicelive.com" title="Blog">Blog</a> |
							<a href="http://community.servicelive.com">Community</a> | 
							<a href="${contextPath}/jsp/public/support/support_main.jsp">Support</a> | 
							<a href="${contextPath}/contactUsAction.action">Contact Us</a> | 
							<a class="btn17" style="float: none; display: inline-block; width:47px; height: 17px; background-image: url('${staticContextPath}/images/buttons/login_header.gif');" href="${loginActionPath}/loginAction.action"><img src="${staticContextPath}/images/spacer.gif" width="47" height="17" alt="Login" style="margin-bottom: -5px;"></a>
							<!-- <a href="${contextPath}/jsp/public/simple/join_now.jsp" id="joinNowLink">Join Now</a> | -->
							<!-- <a href="" id="searchLink">Search</a> -->
						</c:otherwise>
					</c:choose>
				</div>
				<%-- topNav --%>
				<%--
					ROLE DEFINITIONS
					1 PROVIDER
					2 SERVICELIVE ADMIN
					3 PROFESSIONAL BUYER
					5 SIMPLE BUYER
				 --%>
				<c:if test="${!IS_SIMPLE_BUYER}">
					<div id="mainNav">
						<ul>

							<c:choose>
								<c:when test="${!IS_LOGGED_IN}">
									<li class="parentNavItem">
										<a											
											id="whatIsSL"></a>
										<ul class="level2 whatIsSlnew-subnav">
											<li class="parentNavItem">
												<a href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp"
													class="subnav subnavArrow" id="aboutUsLink">About Us</a>
											</li>											
											<li class="parentNavItem">
												<a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp"
													class="subnav">About Our Service Providers</a>
											</li>
											<li class="parentNavItem">
												<a
													href="${contextPath}/jsp/public/support/support_faq.jsp"
													class="subnav">Frequently Asked Questions</a>
											</li>
										</ul>
									</li>
									
									<!-- <li>
										<a
											href="${contextPath}/joinNowBuyerSimpleAction.action"
											id="homeowners"></a>
									</li>
									 -->
									<li>
										<a
											href="${contextPath}/joinNowBuyerAction.action"
											id="businesses"></a>
									</li>
									<li>
										<a
											href="${contextPath}/joinNowAction.action"
											id="serviceProviders"></a>
									</li>
									<li>
										<a
											onClick="exploreTheMarketplace('1')"
											href="#" id="exploreMktplace"></a>
									</li>
								</c:when>
								<c:otherwise>
									<c:if
										test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 or SERVICE_ORDER_CRITERIA_KEY.roleId == 2 or SERVICE_ORDER_CRITERIA_KEY.roleId == 3 }">
										<li>
											<a href="${contextPath}/dashboardAction.action"
												id="dashboard"></a>
										</li>
										<tags:security actionName="serviceOrderMonitor">
											<li>
												<a href="${contextPath}/serviceOrderMonitor.action"
													id="serviceOrderMonitor"></a>
											</li>
										</tags:security>
										<tags:security actionName="financeManagerController">
											<li>
												<a
													href="${contextPath}/financeManagerController_execute.action"
													id="financialMgr"></a>
											</li>
										</tags:security>
										<c:if
											test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3 or SERVICE_ORDER_CRITERIA_KEY.roleId == 2 }">
											<tags:security actionName="pbController_execute">
												<li>
													<a href="${contextPath}/pbController_execute.action"
														id="menuPowerBuyer"></a>
												</li>
											</tags:security>
										</c:if>
										<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
											<c:set var="anonFindProviderSearch" value="true"/>																
											<tags:security actionName="etmSearch_">
												<li>
													<a href="${contextPath}/etmSearch_execute.action" id="exploreMktplace"></a>
												</li>
												<c:set var="anonFindProviderSearch" value="false"/>												
											</tags:security>
											
											<c:if test="${anonFindProviderSearch eq true}">
												<li>
													<a onclick="exploreTheMarketplace('1')" href="#"
														id="exploreMktplace"></a>
												</li>
											</c:if>											
										</c:if>
									</c:if>
								</c:otherwise>
							</c:choose>
						</ul>
					</div>
					<%-- closes off id=mainNav --%>
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
									<a class="subnavArrow" href="${contextPath}/allTabView.action">Company
										Profile</a>
								</li>
							</tags:security>
							<li class="parentNavItem">
								<a class="subnavArrow"
									href="${contextPath}/companyTabAction.action">View Company
									Profile</a>
							</li>
						</c:if>

						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">

							<tags:security actionName="BAManageUsersAction">
								<li class="parentNavItem">
									<a class="subnavArrow"
										href="${contextPath}/buyerAdminManageUsers_execute.action">Manage
										Team</a>
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
													class="subnav" id="marketAdjustmentLink">Market
													Adjustment Rate</a>
											</li>
										</tags:security>
					                    <c:if test="${hasCarAuth}"> 
											<li>
												<a href="${contextPath}/rrDashboard_display.action" class="subnav" id="carLink">Conditional Routing</a>
											</li>
					                    </c:if>
										<tags:security actionName="jobCode">
											<li>
												<a href="${contextPath}/jobCode_execute.action"
													class="subnav" id="jobCodeMappingLink">Job Code Mapping</a>
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
													class="subnav" id="templateMaintenanceLink">Select
													Provider Networks</a>
											</li>
										</tags:security>
										<tags:security actionName="spnAdminAction_">
											<li>
												<a href="${contextPath}/spnAdminAction_displayPage.action"
													class="subnav" id="templateMaintenanceLink">Select
													Provider Campaign</a>
											</li>
										</tags:security>
									</ul>
								</li>
							</c:if>
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
			</div>
			<div class="jqmWindow" id="exploreMarketPlace">
				<div class="modalHomepage">
					<a href="#" class="jqmClose">Close</a>
				</div>
				<s:form name="mp" action="homepage_submitZip.action" method="POST"
					theme="simple">
					<div class="modalContent">
						<h2>Please enter the Zip Code of the location where work will be done.</h2>
						<s:textfield onclick="clear_enter_zip();" onblur="add_enter_zip();" name="zipCode" id="zipCode" value="Enter Zip Code" />
						<s:submit type="image" cssClass="findProv" src="%{#request['staticContextPath']}/images/simple/button-find-sp.png" />									
					</div>
					<input id="buyerId" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="" />
				</s:form>

			</div>
			<!-- /#header -->
