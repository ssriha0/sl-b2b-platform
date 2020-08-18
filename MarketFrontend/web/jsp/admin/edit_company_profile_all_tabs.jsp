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
		<title>ServiceLive [Provider Admin]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
			dojo.require("dojo.parser");
			dojo.require("dijit.form.Slider");
			dojo.require("dijit.layout.LinkPane");
			//dojo.require("newco.servicelive.SOMRealTimeManager");
			function myHandler(id,newValue){
				console.debug("onChange for id = " + id + ", value: " + newValue);
			}
		</script>
		<style type="text/css">
@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";

@import
	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
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
			href="${staticContextPath}/css/service_provider_profile.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/buttons.css" />
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css">
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/registration.css">
		<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js"
			type="text/javascript"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/formfields.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
		<script language="JavaScript" type="text/javascript"
			src="${staticContextPath}/javascript/nav.js"></script>
		<script language="javascript">
	selectedNav = function (){ $("mktplaceTools").addClass("selected"); } 
	window.addEvent('domready',selectedNav);
</script>
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
		 <jsp:param name="PageName" value="Admin - Edit Company"/>
	</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="headerSPReg">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>

					<div id="pageHeader">
						<img
							src="${staticContextPath}/images/sl_admin/hdr_mktplace_editCompProfile.gif"
							alt="Marketplace | Edit Company Profile"
							title="Marketplace | Edit Company Profile" />
					</div>
				</div>
				<!-- END HEADER -->
				<div class="colRight255 clearfix">
					<jsp:include page="/jsp/admin/modules/member_info.jsp" />				
					<jsp:include page="/jsp/admin/modules/admin_actions.jsp" />				
					<jsp:include page="/jsp/admin/modules/auditor_actions.jsp" />				
				</div>
			</div>
		</div>
		<!-- START TAB PANE -->
		<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
			style="height: 1300px; margin: 0;" class="tabPaneShort">
			<!-- TAB 1 -->
			<a class="" dojoType="dijit.layout.LinkPane"
				href="html_sections/modules/tab_admin_edit_provider_business.jsp"
				selected="true">Business Information</a>
			<!-- TAB 2 -->
			<a class="tab2" dojoType="dijit.layout.LinkPane"
				href="${contextPath}/jsp/admin/modules/tab_edit_provider_warranties.jsp">Warranties
				&amp; Policies</a>
			<!-- TAB 3 -->
			<a class="tab3" dojoType="dijit.layout.LinkPane"
				href="${contextPath}/jsp/admin/modules/tab_edit_provider_licenses.jsp">Licenses
				&amp; Certifications</a>
			<!-- TAB 4 -->
			<a class="tab4" dojoType="dijit.layout.LinkPane"
				href="${contextPath}/jsp/admin/modules/tab_edit_provider_insurance.jsp">Insurance</a>
		</div>
		<!-- END TAB PANE -->
		<div class="clear"></div>
		<!-- START FOOTER -->
		<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
		<!-- END FOOTER -->
	</body>
</html>
