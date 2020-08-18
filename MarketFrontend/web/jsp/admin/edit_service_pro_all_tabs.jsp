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
		<title>ServiceLive [Service Pro Profile]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
			href="${staticContextPath}/css/registration.css" />		
		<link rel="stylesheet" type="text/css"
			href="${staticContextPath}/css/admin.css" />
			
		
			
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
	<body class="tundra">
	    
		<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
			 <jsp:param name="PageName" value="Admin - Edit Service Pro"/>
		</jsp:include>	
	
		<div id="page_margins">
			<div id="page">
				<!-- START HEADER -->
				<div id="header">
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>


					<div id="pageHeader">
						<div>
							<img
								src="${staticContextPath}/images/sl_admin/hdr_mktplace_spProfile.gif"
								alt="Service Pro Profile" />
						</div>
					</div>
										
				</div>
				<!-- END HEADER -->
				
				<jsp:include page="/jsp/sl_login/common/errorMessage.jsp" flush="true"/>


				<!-- START TAB PANE -->
				<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
					style="height: 1700px; width: 711px; margin: 80px 0 0 0;"
					class="spProfileTabs">
					<!-- TAB 1 -->
					<a class="" dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_edit_sp_general_info.jsp"
						selected="true"><div class="tabIcon completeOn">
							General
							<br>
							Information
						</div> </a>
					<!-- TAB 2 -->
					<a class="tab2" dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_marketplace.jsp"><div
							class="tabIcon incomplete">
							Marketplace
							<br>
							Preferences
						</div> </a>
					<!-- TAB 3 -->
					<a class="tab3" dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_skills.jsp"><div
							class="tabIcon incomplete">
							Skills &amp;
							<br>
							Services
						</div> </a>
					<!-- TAB 4 -->
					<a class="tab4" dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_licenses.jsp"><div
							class="tabIcon error">
							Licenses &amp;
							<br>
							Certifications
						</div> </a>
					<!-- TAB 5 -->
					<a class="tab5" dojoType="dijit.layout.LinkPane"
						href="${contextPath}/jsp/admin/modules/tab_background.jsp"><div
							class="tabIcon incomplete">
							Background
							<br>
							Check
						</div> </a>
					<!-- TAB 6 -->
					<a class="tab6" dojoType="dijit.layout.LinkPane" href=""><div
							class="tabIcon incomplete">
							Site
							<br>
							Preferences
						</div> </a>
												
				</div>
				<!-- END TAB PANE -->



			</div>


			<jsp:include page="/jsp/public/common/defaultFooter.jsp" />

		</div>

	</body>
</html>
