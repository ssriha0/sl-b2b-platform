<jsp:include page="/jsp/public/common/commonIncludes.jsp" />

<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" scope="request" value="Registration Error" />

 <jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="JoinNow.registrationError"/>
	</jsp:include>	


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title><c:out value="${pageTitle}" />
		</title>
		<link rel="shortcut icon"
			href="${staticContextPath}/images/favicon.ico" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if lt IE 8]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/toolbox.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/vars.js"></script>
		<c:if test="${messageType == 'Signup Confirmation' }">
			<script language="javascript" type="text/javascript">
			jQuery(document).ready(function($){
				$('#mmh').jqm({'${contextPath}/jsp/public/simple/modals/mmh.jsp', modal:true, toTop: true}).jqmShow();
			});
			</script>
		</c:if>

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
			 <jsp:param name="PageName" value="Error - Registration"/>
		</jsp:include>	

	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>

			<!-- START CENTER -->
			<div id="hpWrap" class="clearfix">
				<div id="hpContent">
					<div id="hpIntro">
						<p>
							We are unable to fulfill buyer requests in the following states/U.S. Territories: 
							<c:choose><c:when test="${not empty blackoutStates}">
								<s:iterator value="blackoutStates" status="statePos">
										<s:property/><s:if test="!#statePos.last">,</s:if>
									</s:iterator>
							</c:when>
							<c:otherwise>
												AS, FM, GU, MH, MP, PW, VI
							</c:otherwise></c:choose>
						</p>
						<p align="center">
							<a href="${contextPath}">Go home</a>
					</div>
				</div>
			</div>


			<!-- END CENTER   -->
			<!-- START FOOTER NAV -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER NAV -->
		</div>
		<div id="mmh" class="jqmWindow"></div>
		</body>
</html>
