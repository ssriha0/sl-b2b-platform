<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:set var="pageTitle" scope="request" value="Select Location" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />
<% session.putValue("simpleBuyerOverride", new Boolean(true)); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

	<head>
		<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
		<title>ServiceLive : ${pageTitle}</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/toolbox.js"></script>
		<script language="javascript" type="text/javascript" src="${staticContextPath}/javascript/vars.js"></script>

	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
       	 <jsp:param name="PageName" value="SSO - Select Location"/>
	</jsp:include>
				
	</head>


	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction}">
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction}">
		</c:otherwise>
	</c:choose>
	
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
						Where will the work be done?
					</h2>
	
	
					<s:form action="ssoSelectLocation_" id="selectLocationPage" name="selectLocationPage"
						method="next" theme="simple">
	
						<jsp:include page="./simple_panel_select_location.jsp" />
	
	
						<div class="textcenter">
							<div class="clearfix buttnNav">
	
								<s:submit type="image" action="ssoSelectLocation" method="next"
									src="%{#request['staticContextPath']}/images/common/spacer.gif"
									cssStyle="background-image: url(%{#request['staticContextPath']}/images/simple/button-next.png); width:90px; height:28px;"
									cssClass="rightinput" theme="simple" value="" />								
							</div>
						</div>
					</s:form>
					
				</div>
			</div>
		</div>
	
		<!-- acquity: empty divs to ajax the modal content into -->
		
		<div id="serviceFinder" class="jqmWindow"></div>
		<div id="modal123" class="jqmWindowSteps"></div>
		<div id="zipCheck" class="jqmWindow"></div>


		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
	</body>
</html>
