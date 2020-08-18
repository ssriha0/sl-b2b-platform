<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> 
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/main.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/iehacks.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/dashboard.css" />
<link rel="stylesheet" type="text/css"
	href="${staticContextPath}/css/dashboard/tooltips.css" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
	<div class="leftTileItem" id="dbTile_overallStatusMonitor">
		<div class="titleContainer">
			<div class="titleBar">
				<h2>ServiceLive Consumer Services</h2>
			</div>
		</div>
		<div class="contentContainer">
			<div class="content">
				<p>
					<strong>GROW YOUR BUSINESS!</strong>&nbsp;Build your profile and add your service offerings to get orders from our growing member community!
				</p>
				<input type="submit" onclick="window.location='${contextPath}/providerPortalAction.action';"  value="Get Started" style="margin-left: 113px; width: initial; font-family: Verdana" class="button action submit">
			</div>
		</div>
		<div class="shadowBottom"></div>
</div>