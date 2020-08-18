<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<jsp:include page="/jsp/public/common/commonIncludes.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="pageTitle" scope="request"
	value="ServiceLive Frequently Asked Questions" />
<c:set var="showTags" scope="request" value="1" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<title><c:out value="${pageTitle}" />
		</title>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="isDebug: false, parseOnLoad: true"></script>
		<script type="text/javascript">
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.Dialog");
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.LinkPane");
	dojo.require("dijit.TitlePane");
	//dojo.require("newco.servicelive.SOMRealTimeManager");
  	
	function myHandler(id,newValue){
		console.debug("onChange for id = " + id + ", value: " + newValue);
	}
</script>
		<style type="text/css">
@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
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
			href="${staticContextPath}/css/support.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/acquity.css" />
		<!--[if lte IE 7]>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/acquity-ie.css" />
		<![endif]-->

		<script type="text/javascript"
			src="${staticContextPath}/javascript/prototype.js"></script>

		<script language="JavaScript"
			src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
		
		<style type="text/css">
	      .ie7 .bannerDiv{margin-left:-945px;}
		</style>	
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			<jsp:param name="PageName" value="FAQ" />
		</jsp:include>
	</head>

	<c:choose>
		<c:when test="${IS_LOGGED_IN && IS_SIMPLE_BUYER}">
			<body class="tundra acquity simple" onload="${onloadFunction};">
			<!--  <body class="tundra acquity simple" onload="${onloadFunction};displayBanner();"> -->
		</c:when>
		<c:otherwise>
			<body class="tundra acquity" onload="${onloadFunction};">
			<!-- <body class="tundra acquity" onload="${onloadFunction};displayBanner();">-->
		</c:otherwise>
	</c:choose>


	<div id="page_margins">
		<div id="page">
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav" />
				<tiles:insertDefinition name="newco.base.blue_nav" />
				<tiles:insertDefinition name="newco.base.dark_gray_nav" />
			</div>
			<div style="padding: 10px;">
				
			</div>
			<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
				style="margin: 30px 0 0 0; height: 1300px"
				class="tabPaneShort clearfix">
				<a id="generaltab" class="" dojoType="dijit.layout.LinkPane" style="overflow-x: hidden;overflow-y: auto;" 
					href="html_sections/modules/tab_faq_general.html" selected="true">General</a>
				<a id="servprvtab" class="" dojoType="dijit.layout.LinkPane"  style="overflow-x: hidden;overflow-y: auto;" 
					href="html_sections/modules/tab_faq_providers.html">Service
					Providers</a>
				<a id="persIdentificationtab" class="" dojoType="dijit.layout.LinkPane"  style="overflow-x: hidden;overflow-y: auto;" 
					href="html_sections/modules/tab_faq_personalInformation.html">Personal Information</a>
			</div>
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		</div>
	</div>
	</body>
</html>
