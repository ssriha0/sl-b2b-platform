<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="omTabViewPermission" scope="session" value="<%=session.getAttribute("omTabView")%>" />

<%--
					ROLE DEFINITIONS
					1 PROVIDER
					2 SERVICELIVE ADMIN
					3 PROFESSIONAL BUYER
					5 SIMPLE BUYER
				 --%>
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
	<script>				 
		function exploreTheMarketplace(buyerTypeId)
		{	
			//alert("Fields are: name = " + name + " mainCategoryId =  " + mainCategoryId + " categoryId = " + categoryId + " subCategoryId = " + subCategoryId + " serviceTypeTemplateId =  " + serviceTypeTemplateId + " Buyer type:" + buyerTypeId);
			document.getElementById('buyerId').value=buyerTypeId;
	
			    jQuery(document).ready(function($) {
	     		jQuery('#exploreMarketPlace').jqm({modal:true, toTop: true});
	     		jQuery('#exploreMarketPlace').jqmShow();
			 });
	
		}
		

		function getSoUrl(action){
		    var path=encodeURI(window.location.href);	
		      
		    if(path.indexOf("soId") > -1 && path.indexOf("soWizardController.action") > -1){
		    	so=path.match(/soId=(.+)/)[1];
		    	var soId=so.substring(0,16);
		    	window.location.href='${contextPath}/'+action+'?soId='+soId;		    	
		    }
		    else{
		    	window.location.href='${contextPath}/'+action;
		    }
		   	  	  
		}

	</script>
				 
				 
<c:if test="${!IS_SIMPLE_BUYER}">
	<div id="mainNav"  style="z-index:202">
		<ul>

			<c:choose>
				<c:when test="${!IS_LOGGED_IN}">
	
				</c:when>
				<c:otherwise>
					<c:if
						test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 or SERVICE_ORDER_CRITERIA_KEY.roleId == 2 or SERVICE_ORDER_CRITERIA_KEY.roleId == 3 }">
						<li>
							<a id="dashboard" onclick ="getSoUrl('dashboardAction.action');" style="cursor: pointer;"></a>						
						</li>
						
						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1
										&& (SecurityContext.primaryInd || SecurityContext.dispatchInd || SecurityContext.providerAdminInd) && DISPLAY_CALENDAR}">
							<li>
								<a id="calendar" onclick="getSoUrl('calendarAction.action');" title="Calendar" class="calendar-link" style="height: 19px;">Calendar</a>
							</li>
						</c:if>
						
						<tags:security actionName="serviceOrderMonitor">
							<li>
								<a id="serviceOrderMonitor" onclick ="getSoUrl('serviceOrderMonitor.action');" style="cursor: pointer;"></a>
							</li>
						</tags:security>
						<tags:security actionName="financeManagerController">
							<li>
								<a href="${contextPath}/financeManagerController_execute.action?ss=${securityToken}"
									id="financialMgr"></a>
							</li>
						</tags:security>
						 <c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && omTabViewPermission == 'true'}">
							<li>
							<c:set var="Inbox" value="Inbox"></c:set>
								<a href="${contextPath}/orderManagementController.action?omDisplayTab=${Inbox}"
									id="orderManagement"></a>
							</li>
						</c:if> 
						
						<!-- TODO - Check whether the provider has a partner id. If yes display this link-->
						 <c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 1 && providerLeadManagementPermission == 'true' && showLeadsSignUp == 1}">
							<li>							
								<a id="leadsManagement"
								href="/MarketFrontend/leadsManagementController.action"></a>
							</li>
						</c:if> 
						<c:if
							test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
							<tags:security actionName="pbController_execute">
								<li>
									<a id="menuPowerBuyer" onclick="getSoUrl('pbController_execute.action');" style="cursor: pointer;"></a>
								</li>
							</tags:security>
						</c:if>
						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2 or SERVICE_ORDER_CRITERIA_KEY.roleId == 3}">
							<c:set var="anonFindProviderSearch" value="true"/>						
							<tags:security actionName="etmSearch_">
								<li>
									<a id="exploreMktplace" onclick="getSoUrl('etmSearch_execute.action');" style="cursor: pointer;"></a>
								</li>
								<c:set var="anonFindProviderSearch" value="false"/>
							</tags:security>
								<c:if test="${anonFindProviderSearch eq true}">
								<li>
									<a id="exploreMktplace" onclick="getSoUrl('etmSearch_execute.action');" style="cursor: pointer;"></a>
								</li>
							</c:if>
							
						</c:if>
						<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 3 && buyerLeadManagementPermission == 'true'}">
						 <li>
						 <a id="buyerLeadsManagement"  href="${contextPath}/buyerLeadManagementController.action"></a>
						 </li>
						 </c:if>
					</c:if>
				</c:otherwise>
			</c:choose>
		</ul>
	</div>
	<%-- closes off id=mainNav --%>
</c:if>