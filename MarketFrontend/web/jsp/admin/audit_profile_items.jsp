<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive Audit Workflow</title>
		<link rel="shortcut" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/sl_admin.css" />				
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />				

	</head>
	<body class="tundra acquity">	

				<!-- BEGIN CENTER -->				
			
				<div id="whitestripe">

				<div id="newWrap" class="auditWorkflow clearfix">
					<div id="auditOptionsBack" class="clearfix"></div>
					<div id="auditOptions" class="clearfix">
						<a class="closelink" href="javascript:window.close()">Close Audit Window</a>
						<h2>Provider: ${powerAuditorVendorInfo.businessName} (#${powerAuditorVendorInfo.vendorId}) - ${powerAuditorVendorInfo.vendorCity}, ${powerAuditorVendorInfo.vendorState}</h2>
						<h3>Administrator: ${powerAuditorVendorInfo.primaryAdminLastName}, ${powerAuditorVendorInfo.primaryAdminFirstName}</h3>
						<c:set var="myLength" value="${fn:length(powerAuditorVendorInfo.primaryAdminPhoneNumber)}" />
						
						<c:if test="${myLength > 3}">
							<c:set var="tmpStr1" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 0, 3)}" />
						</c:if>
						<c:if test="${myLength > 6}">
							<c:set var="tmpStr2" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 3, 6)}" />
						</c:if>
						<c:if test="${myLength >= 10}">
							<c:set var="tmpStr3" value="${fn:substring(powerAuditorVendorInfo.primaryAdminPhoneNumber, 6, -1)}" />
						</c:if>
						<h3>
						<c:if test="${myLength >= 10}">
							${tmpStr1}-${tmpStr2}-${tmpStr3}
						</c:if>
						</h3>
						<a class="auditlink" href="${contextPath}/powerAuditorWorkflowControllerAction_execute.action">Audit Next Credential</a>
						<br />
						<h3>Audit Task ID: ${auditTask.auditTaskId}</h3>
					</div>
				</div>
				</div>
				
				<div id="photoAudit" class="clearfix">
					
					<div class="left">
					<div class="clearfix">
					<div><h2>Latest Photo</h2>
						<img src="${staticContextPath}/images/dynamic/default.png">
					</div>
					<div class="photoItem newPhoto"> (Needs Approval)</h2>
						<img src="${staticContextPath}/images/dynamic/default.png">
					</div>
					</div>


					<div id="otherSP" class="clearfix">
						<h2>
						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>
							<br />
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>
							<br />					
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>
							<br />				
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>					
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>						
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>
						</div>


						<div class="sguy clearfix">
							<img src="${staticContextPath}/images/dynamic/default_sm.jpg" class="left">
							<strong>Provider Name</strong>
							<br />
						</div>
						
					</div>
					
					</div>
					<div class="left auditSB">
						
						
						
						<table border="0" cellpadding="0" cellspacing="0" class="auditPro" width="100%">
							<tr>
								<th></th>
								<th class="sp">Service Provider</th>
								<th class="pf">Provider Firm</th>
							</tr>
							<tr>
								<th class="wh">User ID</th>
								<td class="sp">1234567890</td>
								<td>1234567890</td>
							</tr>
							<tr class="alt">
								<th>Street Address</th>
									<td class="sp">123 Sesame Street<br />Roswell, NM 012345</td>
									<td>123 Sesame Street<br />Roswell, NM 012345</td>
								</tr>
								<tr>
								<th class="wh">Status</th>
									<td class="sp"></td>
									<td></td>
								</tr>
								<tr class="alt">
								<th>Phone Contact</th>
									<td class="sp"></td>
									<td></td>
								</tr>
								<tr>
								<th class="wh">Email Contact</th>
									<td class="sp"></td>
									<td></td>
								</tr>
						</table>
						
						<h2>Company Overview</h2>
						<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p>
						
						
					</div>
				</div>
				
				
				
			<!-- END CENTER   -->

	</body>
</html>
