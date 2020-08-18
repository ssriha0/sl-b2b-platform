<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<%-- Determine Conditional Autorouting authorization --%>
<c:set var="hasCarAuth" value="false" scope="page" />
<tags:security actionName="routingRulesAction_edit">
  <c:set var="hasCarAuth" value="true" scope="page" /> 
</tags:security>
<tags:security actionName="routingRulesAction_view">
  <c:set var="hasCarAuth" value="true" scope="page" />
</tags:security>
<div id="auxNav">
	<ul id="auxList">
		<%-- PROVIDER --%>
		<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId eq 1}"> 
			<li class="parentNavItem" style="width: 110px;">
				<a href="dashboardAction.action" id="adminOfc"></a>
					<ul id="auxSublist1" class="level2">
						<tags:security actionName="addServiceProAction" >
							<li class="parentNavItem">
								<s:a cssClass="subnavArrow" href="manageUserActiondoLoadUsers.action">Manage Team</s:a>
								<ul></ul>
							</li>
						</tags:security>
						<tags:security actionName="allTabView" >
							<li class="parentNavItem">
								<a class="subnavArrow" href="allTabView.action">Company Profile</a>
							</li>						
						</tags:security>
						<li class="parentNavItem">
							<a class="subnavArrow" href="companyTabAction.action">View Company Profile</a>
							<ul></ul>
						</li>						
					</ul>
			</li>
		</c:if>

		<%-- ADMIN --%>
		<c:if test ="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
			<li class="parentNavItem" style="width: 110px;">
				<a href="dashboardAction.action" id="adminOfc"></a>
					<ul id="auxSublist1" class="level2">
					</ul>
			</li>		
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

		
		<%-- BUYER --%>
		<c:if test ="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
				<li class="parentNavItem" style="width: 110px;">
					<a href="dashboardAction.action" id="adminOfc"></a>
					<ul id="auxSublist1" class="level2">
						<tags:security actionName="BAManageUsersAction" >
							<li class="parentNavItem">
								<a class="subnavArrow" href="buyerAdminManageUsers_execute.action">Manage Team</a>
								<ul></ul>
							</li>
						</tags:security>
						<tags:security actionName="SPNBuyerLandingAction" >
							<li class="parentNavItem">
								<a class="subnavArrow" href="spnBuyerLanding_displayPage.action">My SPN</a>
								<ul></ul>
							</li>
						</tags:security>
						<tags:security actionName="buyerFileUploadAction" >
							<li class="parentNavItem">
								<a class="subnavArrow" href="<s:url action='buyerFileUploadAction.action'/>">Service Order Import Tool</a>
								<ul></ul>
							</li>
						</tags:security>
						
						<c:if test="${isMaintenance==true}"> 
							<li class="parentNavItem">
								<a class="subnavArrow" href="" onclick="return false;" style="cursor: text">Maintenance Panel</a>
									<ul class="level3">
										<tags:security actionName="adminMarketAdj" >
										<li>
											<a href="adminMarketAdj_displayPage.action" class="subnav" id="marketAdjustmentLink">Market Adjustment Rate</a>
										</li>
										</tags:security>
					                    <c:if test="${hasCarAuth}"> 
											<li>
												<a href="${contextPath}/rrDashboard_display.action" class="subnav" id="carLink">Conditional Routing</a>
											</li>
					                    </c:if>
										<tags:security actionName="jobCode" >
										<li>
											<a href="jobCode_execute.action" class="subnav" id="jobCodeMappingLink">Job Code Mapping</a>
										</li>
										</tags:security>
										<tags:security actionName="templateMaintenance_" >
										<li>
											<a href="templateMaintenance_execute.action" class="subnav" id="templateMaintenanceLink">Template Maintenance</a>
										</li>
										</tags:security>
										<tags:security actionName="spnBuyerLanding_" >
										<li>
											<a href="spnBuyerLanding_displayPage.action" class="subnav" id="templateMaintenanceLink">Select Provider Networks</a>
										</li>
										</tags:security>
										<tags:security actionName="spnAdminAction_" >
										<li>
											<a href="spnAdminAction_displayPage.action" class="subnav" id="templateMaintenanceLink">Select Provider Campaign</a>
										</li>
										</tags:security>
									</ul>
							</li>
						</c:if>
						
						
					</ul>
				</li>
		</c:if>
	</ul>
</div>
