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
		<title>ServiceLive Ratings</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="this is my page">
		<meta http-equiv="content-type"
			content="text/html; charset=ISO-8859-1">
		<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
		<script type="text/javascript" src="${staticContextPath}/javascript/tooltip.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>

		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
	</head>
	<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/main.css" />
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/iehacks.css" />
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/top-section.css" />
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/buttons.css" />
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/tooltips.css" />
	<link rel="stylesheet" type="text/css"
		href="${staticContextPath}/css/so_details.css">
	<script type="text/javascript"
		src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
		djConfig="isDebug: false, parseOnLoad: true"></script>
	<script type="text/javascript">
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dijit.TitlePane");
	dojo.require("dojo.parser");
	dojo.require("dijit.layout.LinkPane");
	//dojo.require("newco.servicelive.SOMRealTimeManager");
</script>
	<script language="javascript">
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
	<head>
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
	</head>
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Admin - View Ratings"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/sl_admin/hdr_mktplace_soDetails.gif"
							alt="Service Order Details" />
					</div>
				</div>
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 1600px; width: 975px;" class="detailsTabs">

					<a dojoType="dijit.layout.LinkPane" href="">Summary</a>
					<a dojoType="dijit.layout.LinkPane" selected="true"
						href="html_sections/modules/tab_edit_ratings.jsp">View Ratings</a>
				</div>
				<!-- END TAB PANE -->
				<div class="clear">
				</div>
			</div>
			<!-- START FOOTER -->
			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
			<!-- END FOOTER -->
		</div>
	</body>
</html>
