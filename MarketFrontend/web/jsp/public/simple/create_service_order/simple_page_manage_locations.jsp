<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="pageTitle" scope="request" value="Manage Locations" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<%
	session.putValue("simpleBuyerOverride", new Boolean(true));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive : ${pageTitle}</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />
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
       	 <jsp:param name="PageName" value="SSO - Manage Locations"/>
	</jsp:include>
	
	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>


			<div id="hpWrap" class="shaded clearfix">
				<div id="hpContent">
					<div id="hpIntro" class="clearfix">
						<h2>
							Manage Locations
						</h2>

						<s:form action="ssoManageLocations" id="manageLocationsPage"
							name="manageLocationsPage" method="post" theme="simple">
							<c:set var="manageInd" value="true" scope="request" />
							<jsp:include page="./simple_panel_select_location.jsp" />
						</s:form>

						<a href="dashboardAction.action">Go to Dashboard</a>

						<!-- acquity: empty divs to ajax the modal content into -->
						
						<div id="serviceFinder" class="jqmWindow"></div>
						<div id="modal123" class="jqmWindowSteps"></div>
						<div id="zipCheck" class="jqmWindow"></div>


					</div>
				</div>
			</div>

			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</div>
	</body>
</html>

