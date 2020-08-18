<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title>ServiceLive [Error]</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />

		<style type="text/css">
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
			@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
			</style>

		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/modules.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />


		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
		</script>
	</head>
	
	
	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
		</c:otherwise>
	</c:choose>
        
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<div id="auxNav"></div>
				</div>
				<!-- END HEADER -->
				
				<%-- 
				<table width="100%" height=200px>
					<tr>
						<td>
						</td>
					</tr>				
				</table>
				--%>
				<div class="content" style="font-size: 12px">
						<fmt:message bundle="${serviceliveCopyBundle}" key="common.error.page.message" />
						<br>
						<br>
						<table height="150px" border=0 width="100%">
							<tr>
								<td colspan=2 style="font-size: 12px">
									<c:if test="${errorText != null}">
										<p class="errorBox">
											${errorText}
										</p>
										<br>
									</c:if>
									<fmt:message bundle="${serviceliveCopyBundle}" key="common.error.page.login.message" />
								</td>
							</tr>
							<tr height="30">
								<td align="left">
									<div class="smallText lightBlue">
									</div>
								</td>
							</tr>
						</table>
				</div>
				


					<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

				</div>


			</div>
	</body>
</html>
