<%@ page import="org.apache.commons.lang.StringEscapeUtils,org.owasp.esapi.ESAPI;"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/ServiceLiveWebUtil"%>" />
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />

<%-- title of the current page
<c:set var="pageTitle" scope="request" value="D2C: Provider Profile" />

tell header not to insert any JS
<c:set var="noJs" value="true" scope="request" />

tell header not to insert any css
<c:set var="noCss" value="true" scope="request" />

to be consumed by the header nav to highlight the SOM tab
<c:set var="currentMenu" scope="request" value="serviceOrderMonitor" /> --%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="/" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8;no-cache" />
<meta charset=UTF-8> <meta name=viewport content="width=device-width,initial-scale=1"/>
<title>ServiceLive: Provider Profile</title>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />

<style type="text/css">
.ie7 .bannerDiv {
	margin-left: -1020px;
}
</style>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />

</head>
<body class="tundra acquity">
	<div id="page_margins">
		<div id="page">
			<!-- START HEADER -->
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
				<div id="pageHeader"></div>
			</div>
			<!-- END HEADER -->

			<!-- start main content -->
			<div>
				<link rel="stylesheet" type="text/css" href="${staticContextPath}/d2c-provider/dist/vendor.css"/>
				<link rel="stylesheet" type="text/css" href="${staticContextPath}/d2c-provider/dist/app.css" />
					<my-app>Loading...</my-app>
				<script type="text/javascript" src="${staticContextPath}/d2c-provider/dist/polyfills.js"></script>
				<script type="text/javascript" src="${staticContextPath}/d2c-provider/dist/vendor.js"></script>
				<script type="text/javascript" src="${staticContextPath}/d2c-provider/dist/app.js"></script>
			</div>
			<!-- end main content -->

			<!-- start common footer -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- end common footer -->
		</div>
	</div>
</body>
</html>