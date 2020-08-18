<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName()
					+ ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Your ServiceLive Dashboard</title>
<link rel="shortcut icon"
	href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/iehacks.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/sl_admin.css" />
<script type="text/javascript"
	src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
<style type="text/css">
.ie7 .bannerDiv {
	margin-left: -1010px;
}
</style>
</head>
<body class="tundra acquity">

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		<jsp:param name="PageName" value="Admin - Dashboard" />
	</jsp:include>

	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>
			<!-- BEGIN CENTER -->



			<div id="newWrap" class="clearfix">

				<div id="newSidebar" class="secondary">

					<jsp:include page="/jsp/admin/modules/sb_tools.jsp" />

					<jsp:include page="/jsp/admin/modules/sb_office.jsp" />

				</div>


				<div id="newContent">
					<div class="pageHeading">
						<h1>
							ServiceLive Administrator <span>Dashboard</span>
						</h1>
					</div>


					<div class="post ncMarketplace">
						<div class="heading">
							<h2>Marketplace Tools</h2>
						</div>
						<div class="submenu">
							<ul>

								<li class="search"><a href="adminSearch_execute.action">Search
										Portal</a></li>
								<!-- sl-21142 -->
								<tags:security actionName="lmsFileGetAction" isAdmin="true">
								<li class="LMS Integration Tool"><a
									href="<s:url value='%{contextPath}/lmsFileGetAction_getLmsDetailHistory.action'/>">
										LMS Integration Tool</a></li>
								</tags:security>
								<!-- 21142 -->		
								<tags:security actionName="spnAdminAction_displayPage.action">
									<li class="campaign"><a
										href="spnAdminAction_displayPage.action">SPN Campaign
											Manager</a></li>
								</tags:security>
								<c:if test="${SERVICE_ORDER_CRITERIA_KEY.roleId == 2}">
									<tags:security actionName="pbController_execute">
										<li class="auditor"><a href="pbController_execute.action">Workflow
												Monitor</a></li>
									</tags:security>
								</c:if>
								<li class="auditor"><a
									href="<s:url action="powerAuditorWorkflowAction" />">Auditor
										Workflow</a></li>
							</ul>
						</div>
						<p>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="sl_admim.dashboard.marketplace_tools.desc" />
						</p>
					</div>

					<div class="post ncAdminOffice">
						<div class="heading">
							<h2>Administrative Office</h2>
						</div>

						<div class="submenu">
							<ul>
								<tags:security actionName="adminManageAPIAction">
									<li class="users"><a href="adminManageAPI_execute.action">Manage
											Applications</a></li>
								</tags:security>
								<tags:security actionName="adminManageUsersAction">
									<li class="users"><a
										href="adminManageUsers_execute.action">Manage Users</a></li>
								</tags:security>
								<tags:security actionName="financeManagerController">
									<li class="wallet"><a
										href="financeManagerController_execute.action">ServiceLive
											Wallet</a></li>
								</tags:security>
								<tags:security actionName="fullfillmentAdminAction">
									<li class="wallet"><a
										href="fullfillmentAdminAction_execute.action">Fulfillment
											Adjustment</a></li>
								</tags:security>
							</ul>
						</div>
						<p>
							<fmt:message bundle="${serviceliveCopyBundle}"
								key="sl_admim.dashboard.admin_office.desc" />
						</p>
					</div>
				</div>
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</div>
</body>
</html>
