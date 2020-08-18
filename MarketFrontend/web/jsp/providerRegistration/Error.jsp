<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<c:set var="loginActionPath" scope="request" value="<%="https://" + request.getServerName() + ":" + System.getProperty("https.port") + request.getContextPath() + "/doLogin.action"%>"/>	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>Provider Registration Error</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/modules.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/what_is_sl.css" />
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/utils.js"></script>
	</head>
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
		</c:otherwise>
	</c:choose>
	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Error - Provider Registration"/>
		</jsp:include>	
	
		<div id="page_margins">
		   
			<div id="page">
				<!-- START HEADER -->
				<div id="headerRightRail">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					<div id="rightRail">
						<jsp:include page="/jsp/public/homepage/body/modules/corpHQ_top.jsp" />
					</div>
				</div>
				<!-- BEGIN CENTER -->

				<div class="clearfix" id="">
					<table height="240px">
						<tr>
							<td>
								<div id="homepageContent" style="padding-bottom: 100px;">
									<div class="colLeft711">
										<form action="${loginActionPath}" method="post" name="doLogin">
											<h3>
												<s:actionerror />
												<s:fielderror />
											</h3>
											<p>
												An unrecoverable System error has occurred while processing
												your request.
											</p>

											<p>
												Please logout of the application and login again.
											</p>

											<p>
												If the problem persists email or call customer support at
												1-888-549-0640.
											</p>


											<div class="clearfix">
										</form>

									</div>
							</td>
						</tr>
					</table>
				</div>
			</div>

			<!-- END CENTER   -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</body>
</html>

