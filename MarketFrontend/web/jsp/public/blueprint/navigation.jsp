<%@page language="java" session="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="com.newco.marketplace.web.dto.ServiceOrdersCriteria" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="tempPassword" scope="request" value="<%=request.getParameter("tempPassword")%>" />
<c:set var="omTabViewPermission" scope="session" value="<%=session.getAttribute("omTabView")%>" />
<c:set var="lmTabViewPermission" scope="session" value="<%=session.getAttribute("showLeadsSignUp")%>" />
<style type="text/css">
.calendar-link {
	cursor: pointer;
    background-color: #dddddd;
    padding-top: 7px;
    margin-top: 5px;
    margin-bottom: 0px;
    padding-left: 15px;
    padding-right: 15px;
    border-top-left-radius: 5px;
    border-top-right-radius: 5px;
    height: 19px;
    font-weight: 600;
    color: #707071;
    font-size: 12px;
    text-shadow: 0px 1px darkgrey;
}
</style>

<script type="text/javascript" src="${staticContextPath}/scripts/plugins/jquery.jqmodal.js"></script>
<%--
	ROLE DEFINITIONS
	1 PROVIDER
	2 SERVICELIVE ADMIN
	3 COMMERCIAL BUYER
	4 ?
	5 HOME/OFFICE BUYER
 --%>
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

	function submitZipCode()
	{
		if (document.getElementById('zipCode') && document.getElementById('zipCode').value != '')
		{	
			var zipCode=document.getElementById('zipCode').value;
			var queryParams='?ZipCodeEntered='+zipCode;
			var buyerTypeId=document.getElementById('buyerIdd').value;
			queryParams=queryParams+'&buyerTypeId='+buyerTypeId;
			var form = document.getElementById("submitZip");
			form.action="homepage_submitZip.action"+queryParams;
			form.submit();
		}
	}
	function exploreTheMarketplace(buyerTypeId)
	{
		document.getElementById('buyerIdd').value=buyerTypeId;
	    jQuery.noConflict();
		jQuery(document).ready(function($) {
		    $('#exploreMarketPlace').jqm({modal:true, toTop: true});
	    	$('#exploreMarketPlace').jqmShow();
		});
	}
</script>
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

<div id="navigation" class="span-24 clearfix">
	<c:if test="${IS_LOGGED_IN}">
	<ul class="superfish">
		<c:if test="${SecurityContext == null}">
		<li class="nav-level1" id="nav-whatissl"><a title="What Is ServiceLive?">What Is SericeLive?</a>
			<ul class="level2 whatIsSlnew-subnav">	
				<li><a href="${contextPath}/jsp/public/what_is_servicelive/body/what_is_sl_landing.jsp" title="About ServiceLive">About Us</a></li>			
				<li><a href="${contextPath}/jsp/public/what_is_servicelive/body/aboutServiceProviders.jsp" title="About Our Service Providers">About Our Service Providers</a></li>
				<li><a href="${contextPath}/jsp/public/support/support_faq.jsp" title="Frequently Asked Questions">Frequently Asked Questions</a></li>
			</ul>
		</li>
		<!-- <li class="nav-level1" id="nav-home-office"><a href="${contextPath}/joinNowBuyerSimpleAction.action" title="Home/Office Consumers">Home/Office</a></li>  -->
		<li class="nav-level1" id="nav-commercial"><a href="${contextPath}/joinNowBuyerAction.action" title="Commercial Service Buyers">Commercial</a></li>
		<li class="nav-level1" id="nav-service-providers"><a href="${contextPath}/joinNowAction.action" title="Service Providers">Service Providers</a></li>
		<li class="nav-level1" id="nav-etm"><a onclick="exploreTheMarketplace('1')" href="#" title="Get Started">Get Started</a></li>
		</c:if>
		<c:if test="${tempPassword == null}">
			<c:if test="${SecurityContext != null}">
				<!-- everyone logged in gets a dashboard -->
				<li class="nav-level1" id="nav-dashboard"><a href="${contextPath}/dashboardAction.action" title="Dashboard">Dashboard</a></li>
				
				<c:if test="${!IS_SIMPLE_BUYER && SERVICE_ORDER_CRITERIA_KEY.roleId == 1 
							 	&& (SecurityContext.primaryInd || SecurityContext.dispatchInd || SecurityContext.providerAdminInd) && DISPLAY_CALENDAR}">
					<li class="nav-level1 calendar-link" id="nav-calendar"><a href="${contextPath}/calendarAction.action" title="Calendar"
					style="height: 19px;">Calendar</a></li>
				</c:if>
				<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId != 5}">
				<!-- not for home/office buyers -->
					<tags:security actionName="serviceOrderMonitor">
						<li class="nav-level1" id="nav-som"><a href="${contextPath}/serviceOrderMonitor.action" title="Service Order Monitor">Service Order Monitor</a></li>
					</tags:security>
					<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
						<tags:security actionName="pbController_execute">
							<li class="nav-level1" id="nav-wfm"><a href="${contextPath}/pbController_execute.action" title="Workflow Monitor">Workflow Monitor</a></li>
						</tags:security>
					</c:if>
					<tags:security actionName="financeManagerController">
						<li class="nav-level1" id="nav-slwallet"><a href="${contextPath}/financeManagerController_execute.action" title="ServiceLive Wallet">Service Live Wallet</a></li>
					</tags:security>
					<c:if test="${omTabViewPermission == 'true' && SERVICE_ORDER_CRITERIA_KEY.roleId != 2 && SERVICE_ORDER_CRITERIA_KEY.roleId != 3}">
							<li class="nav-level1" id="nav-orderMngmt">
								<a href="${contextPath}/orderManagementController.action"
									title="Order Management">Order Management</a>
							</li>
					</c:if>
					<!-- TODO - Check whether the provider has a partner id. If yes display this link-->
						 <c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && lmTabViewPermission == 1}">
							<li class="nav-level1" id="nav-leadMngmt">		
							 <!-- <a id="leadsManagement" style="position: relative; top: 5px; left: 0px;color:#58585A;text-shadow:0 1px #FFFFFF;
								float:left;-moz-box-sizing:border-box;font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:13px;background:none repeat scroll 0 0 #DADADA;
								font-weight:bold;border-radius:5px 5px 0 0;margin-right:4px;padding:5px 5px;line-height:20px;"
								href="/MarketFrontend/leadsManagementController.action">Leads Management</a> -->
								<a id="leadsManagement"	href="/MarketFrontend/leadsManagementController.action">Leads Management</a>
							</li>
						</c:if> 
						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3 && buyerLeadManagementPermission == 'true'}">
						 <li class="nav-level1" id="nav-leadMngmt">
						 <!-- <a id="buyerLeadsManagement" style="position: relative; top: 5px; left: 0px;color:#58585A;text-shadow:0 1px #FFFFFF;
								float:left;-moz-box-sizing:border-box;font-family:Helvetica Neue,Helvetica,Arial,sans-serif;font-size:13px;background:none repeat scroll 0 0 #DADADA;
								font-weight:bold;border-radius:2px 2px 0 0;margin-right:4px;padding:5px 5px;line-height:14px;height: 26px;"
							 href="${contextPath}/buyerLeadManagementController.action"
									id="buyerLeadManagement">Leads Management</a> -->
						 <a id="buyerLeadsManagement" href="${contextPath}/buyerLeadManagementController.action">Leads Management</a>
							</li>
						 </c:if>
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
		<tags:security actionName="manageReasonCodes_" >
			<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="SKUMaintenance_" >
			<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>
		<tags:security actionName="buyerSurvey_" >
			<c:set var="isMaintenance" scope="request" value="true" />
		</tags:security>		


		<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId != 5}">
		<li class="nav-level1" id="nav-admin-office"><a href="#">Administrator Office</a>
			<ul>
				<c:if test="${SecurityContext != null && SERVICE_ORDER_CRITERIA_KEY.roleId == 1}">
				<!-- provider admin office -->
				<tags:security actionName="addServiceProAction">
					<li><a href="${contextPath}/manageUserActiondoLoadUsers.action" title="Manage Team">Manage Team</a></li>
				</tags:security>
				<tags:security actionName="allTabView">
					<li><a href="${contextPath}/allTabView.action" title="Company Profile">Company Profile</a></li>
				</tags:security>
					<li><a href="${contextPath}/companyTabAction.action" title="View Company Profile">View Company Profile</a></li>
				<tags:security actionName="zipcoverage_">
					<li><a href="${contextPath}/zipcoverage_displayPage.action" title="View Company Zip Code Coverage">View Company Zip Code Coverage</a></li>
				</tags:security>	
					<!--  Sl-21464 Permission for D2C provider start-->
						<c:if test="${skuCountOfPrimaryIndustry}">
							<li>
							<a href="<s:url value='%{contextPath}/providerPortalAction.action'/>">ServiceLive
									Consumer Services</a>
							</li>
						</c:if>
			<!--  Sl-21464 Permission for D2C provider End-->
					<!-- SL-20548 -->
					<li><a href="${contextPath}/externalCalendarIntegration.action" title="Calendar Settings">Calendar Settings</a></li>
					<c:choose>
				        <c:when test ="${SecurityContext.primaryInd && SecurityContext.spnMonitorAvailable > 0}">
				    <li><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a></li>	
			            </c:when>
			            <c:otherwise>
			            <tags:security actionName="spnManagerAction_">
			            	<li><a href="${contextPath}/spnMonitorAction_loadSPNMonitor.action">SPN Monitor</a></li>	
			            </tags:security>
			            </c:otherwise>
			            </c:choose>
				<tags:security actionName="w9registrationAction_">
					<li><a href="<s:url value='%{contextPath}/w9registrationAction_execute.action'/>" title="W9 Legal Tax Information">W9 Legal Tax Information</a></li>
				</tags:security>
				<c:if test="${SecurityContext.unArchivedCARRulesAvailable>0}">
					<tags:security actionName="businessinfoAction">
						<li><a href="<s:url value='%{contextPath}/manageAutoOrderAcceptanceAction_execute.action'/>" title="Manage Auto Acceptance">Manage Auto Acceptance</a></li>
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
				<tags:security actionName="financeManagerController">
				<li class="parentNavItem"><a style="height: 100%;" href="${contextPath}/buyerEditPIIAction_execute.action?headerTxt=Personal Identification">Taxpayer/Personal Identification Information</a>	</li>
				</tags:security>
				<tags:security actionName="buyerFileUploadAction">
					<li><a href="<s:url action='%{contextPath}/buyerFileUploadAction.action'/>" title="Service Order Import Tool">Service Order Import Tool</a></li>
				</tags:security>
					<tags:security actionName="spnLoginAction">
				<li class="parentNavItem">
					<a class="subnavArrow"
						href="${contextPath}/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnMonitorNetwork_display">SPN Monitor</a>
				</li>
			</tags:security>
			
			<tags:security actionName="spnAuditorController_route">
				<li class="parentNavItem">
					<a class="subnavArrow"
						href="${contextPath}/spnMonitorAction_callExternalSPN.action?targetExternalAction=spnAuditorController_route">SPN Auditor</a>
				</li>
			</tags:security>
		
				<tags:security actionName="SPNBuyerLandingAction">
					<li><a href="${contextPath}/spnBuyerLanding_displayPage.action" title="My SPN">My SPN</a></li>
				</tags:security>
				<li><a href="${contextPath}/manageCustomRefs_displayPage.action?headerTxt=Manage Custom References">Manage Custom References</a></li>

				<!-- /pro buyer admin office -->
				</c:if>
				<c:if test="${isMaintenance==true}"> 
				<li><a href="#" onclick="return false;" style="cursor: pointer" title="Maintenance Panel">Maintenance Panel</a>
					<ul style="left:-145px;">
						<tags:security actionName="adminMarketAdj">
							<li><a href="${contextPath}/adminMarketAdj_displayPage.action" title="Market Adjustment Rate">Market Adjustment Rate</a></li>
						</tags:security>
	                    <c:if test="${hasCarAuth}"> 
							<li>
								<a href="${contextPath}/rrDashboard_display.action" class="subnav" id="carLink">Conditional Routing</a>
							</li>
	                    </c:if>
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
						<tags:security actionName="manageReasonCodes_">
							<li><a href="${contextPath}/reasonCode_displayPage.action" title="Reason Code Manger">Reason Code Manager</a></li>
						</tags:security>
						<tags:security actionName="buyerSurvey_">
							<li><a href="${contextPath}/buyerSurvey_displayPage.action" title="CSAT & NPS Surveys">Email & Survey Configuration</a></li>
						</tags:security>
						<c:if test="${hasAutocloseAuth}">
							<li><a style="height: 100%;" href="${contextPath}/autoClose_displayPage.action" title="Manage Auto Close & Pay Rules1">Manage Auto Close & Pay Rules</a></li>
						</c:if>	
						<c:if test="${hasAutoCloseInHome}">
							<li><a style="height: 100%;" href="${contextPath}/autoCloseHSR_displayPage.action" title="Manage Auto Close & Pay Rules1">Manage Auto Close & Pay Rules</a></li>
						</c:if>
						<tags:security actionName="SKUMaintenance_">
						<li><a href="${contextPath}/sku_maintenancePage.action" title="SKU Maintenance">SKU Maintenance</a></li>
						</tags:security>
						<!-- SLT-1614 -->
						<tags:security actionName="WFMConfigure_">
						<li><a href="${contextPath}/wfm_maintenancePage.action" title="Worflow Set Up">Workflow Queue Set Up</a></li>
						</tags:security>	
						<!-- SLT-1614 -->				
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
	</c:if>
</div>
<style type="text/css">
	.jqmWindow{background-color:#FFF;border:5px solid #a8a8a8;color:#333;display:none;left:50%;margin-left:-300px;padding:0;position:absolute;top:20%;width:600px;z-index:999999;}
	.jqmWindow textarea{width:530px;}
	.jqmWindow #hpIntro,.jqmWindow #hpIntro h2,.submitnext p{margin-top:0;}
	.jqmWindow input.findProv {margin: 10px; float: right;}
	.jqmWindow input#zipCode { margin: 10px;}
	.jqmOverlay {background-color:#000000;}
	.modalContent{padding:20px;}
	#exploreMarketPlace {top:10%;}
	.modalHomepage{background:#58585a;color:#FFF;font-weight:700;padding:10px;text-align:right;}
	a.jqmClose{color:#FFF;font-weight:700;font-family:Verdana,Arial,Helvetica,sans-serif;font-size:10px;}	
</style>
<div class="jqmWindow" id="exploreMarketPlace" style="display: none">
	<div class="modalHomepage">
		<a href="#" class="jqmClose">Close</a>
	</div>
	<s:form name="mp" id="submitZip" onsubmit="submitZipCode()" action="homepage_submitZip.action" method="POST"
		theme="simple">
		<div class="modalContent">
			<h2 style="font-size:14px;font-weight:600;font-family:Verdana,Arial,Helvetica,sans-serif;">
				Please enter the Zip Code of the location where work will be done.
			</h2>
			<s:textfield onclick="clear_enter_zip();" onblur="add_enter_zip();"
				name="zipCode" id="zipCode" value="Enter Zip Code" />			
			<a href="#" onclick="javascript:submitZipCode();" alt="Submit">
				<img style='float: right;' src="${staticContextPath}/images/simple/button-find-sp.png" />
			</a>			
		</div>		
		<input id="pop_name" type="hidden" name="popularSimpleServices[0].name"	value="<s:property value='name'/>" />
		<input id="mainCategoryId" type="hidden" name="popularSimpleServices[0].mainCategoryId" value="<s:property value='mainCategoryId'/>" />
		<input id="categoryId" type="hidden" name="popularSimpleServices[0].categoryId" value="<s:property value='categoryId'/>" />
		<input id="subCategoryId" type="hidden" name="popularSimpleServices[0].subCategoryId" value="<s:property value='sategoryId'/>" />
		<input id="serviceTypeTemplateId" type="hidden" name="popularSimpleServices[0].serviceTypeTemplateId" value="<s:property value='serviceTypeTemplateId'/>" />
		<input id="buyerIdd" type="hidden" name="popularSimpleServices[0].buyerTypeId" value="<s:property value='buyerTypeId'/>" />
	</s:form>
</div>