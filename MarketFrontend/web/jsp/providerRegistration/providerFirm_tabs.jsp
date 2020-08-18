<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.companyAlTabs"/>
	</jsp:include>	
	
<%
	response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
	response.setHeader("Pragma", "no-cache"); //HTTP 1.0
	response.setDateHeader("Expires", -1);
%>

<html>
	<head>

		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1" />
		<meta HTTP-EQUIV="Pragma" CONTENT="no-cache">
		<c:choose>
	<c:when  test="${providerInfoFromManageRule==true }">
	<title>ServiceLive [Provider Information]</title>
	</c:when>
	<c:otherwise>
		<title>ServiceLive [Provider Registration]</title>
		</c:otherwise></c:choose>
		<link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
			dojo.require("dijit.layout.ContentPane");
			dojo.require("dijit.layout.TabContainer");
			dojo.require("dijit.TitlePane");
			dojo.require("dijit.Dialog");
			dojo.require("dijit._Calendar");
			dojo.require("dojo.date.locale");
			dojo.require("dijit.form.DateTextBox");
			dojo.require("dojo.parser");
		dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
			
			var contextPath = '${pageContext.request.contextPath}';
		</script>

		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css"
	;

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css"
	;
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/slider.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/main.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/iehacks.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/top-section.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/tooltips.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/service_order_wizard.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />



		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script language="JavaScript"
			src="${staticContextPath}/javascript/js_registration/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/utils.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/calendar.css?random=20051112" media="screen"></link>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>



	</head>
	<body class="tundra">
      
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName"
				value="Provider Registration - Profile (${tabView})" />
		</jsp:include>

		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
				<div id="header">

					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>			                        
	
					<div id="pageHeader">
						<div>
						<c:choose>
						<c:when test="${providerInfoFromManageRule==true }">
						<img
								src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_registerProviderFirmProfile_manageRule.gif"
								alt=" Provider Firm Profile"
								title=" Provider Firm Profile" />
					</c:when>
						<c:otherwise>
							<img
								src="${staticContextPath}/images/images_registration/sp_firm_registration/hdr_registerProviderFirmProfile.gif"
								alt="Register | Provider Firm Profile"
								title="Register | Provider Firm Profile" />
							</c:otherwise>
							</c:choose>
						</div>
					</div>


					<%-- <jsp:include flush="true" page="header/reg_hdr_welcome.jsp"></jsp:include> --%>
				</div>
				<!-- END HEADER -->
				<div class="colRight255 clearfix">
					<c:if test="${SecurityContext.slAdminInd}">
						<jsp:include page="/jsp/so_monitor/menu/widgetAdminMemberInfo.jsp" />
					</c:if>
				</div>

				<!-- START TAB PANE -->
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 800px; width: 711px; margin: 0;"
					class="provProfileTabs">
					<!-- TAB 1 -->
					<a dojoType="dijit.layout.LinkPane"
						href="<s:url action="jsp/providerRegistration/companyProfileAction_doLoad.action"/>">
						<span class="tabIcon" id="tab1">Complete Profile</span> </a>
					<!-- TAB 2 -->
					<a dojoType="dijit.layout.LinkPane"
						href="<s:url action="jsp/providerRegistration/companyPublicAction_doLoad.action"/>">
						<span class="tabIcon" id="tab2">Public Profile</span></a>
				</div>
				<!-- END TAB PANE -->

				<div class="clear"></div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
		</div>
	</body>
</html>
