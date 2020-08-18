<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"     uri="/struts-tags"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@page import="com.newco.marketplace.security.ActivityMapper"%>
<%@page import="com.newco.marketplace.constants.Constants"%>
<%@page import="com.newco.marketplace.auth.SecurityContext"%>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="ProviderRegistration.providerProfileAllTabs"/>
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

		<title>ServiceLive [Provider Registration]</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
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
	<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dijitTabPane-serviceLive.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_order_wizard.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />



<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/js_registration/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/js_registration/utils.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/calendar.css?random=20051112" media="screen"></link>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/js_registration/calendar.js?random=20060118"></script>

<style>
	#content_nav {
		font-size: 100%;
	}
	#content_right {
		background: none;
		width: 705px;
		height: auto;
		padding: 12px 0px 0px 15px;
		font-size: 100%;
	}
	#content_right_header {
		padding: 0px 0px 10px 0px;
	}
	.content_right_row_label {
		width: 265px;
	}
	.content_right_row_box {
		width: 420px;
	}
	#buttons {
		float: left;
		padding: 20px 0px 40px 0px;
	}
	#skills {
		float: left;
		width: 400px;
		margin: 10px 0px 0px 0px;
	}
	.skills_header_row {
		float: left;
		width: 686px;
		font-weight: bold;
		text-align: center;
	}
	.skills_row {
     	float: left;
		width: 672px;
		border-color: #CCCCCC;
		border-style: solid;
		border-top: 1px solid #CCCCCC;
		border-left: 0px none;
		border-right: 0px none;
		border-bottom: 0px none;
		vertical-align: top;
	}
	
	.skills_label {
		float: left;
		width: 196px;
	}
	.skills_item {
		float: left;
		width: 68px;
		text-align: center;
	}
	.skills_header_item {
		float: left;
		width: 68px;
	}
	.skills_header_item_blank {
		float: left;
		width: 196px;
	}
	.lang_item {
		float: left;
		width: 120px;
		text-align: left;
	}
</style>


	</head>
	<body class="tundra">
	 
<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
         <jsp:param name="PageName" value="Provider Registration - Profile (${tabView})"/>
</jsp:include>
	
		<div id="page_margins">
			<div id="page" class="clearfix">
				<!-- BEGIN HEADER -->
	 			<div id="header">
     
					<tiles:insertDefinition name="newco.base.topnav"/>
					<tiles:insertDefinition name="newco.base.blue_nav"/>
					<tiles:insertDefinition name="newco.base.dark_gray_nav"/>			                  									
				
				</div>
				<!-- END HEADER -->

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
					
					
					
					
					<c:if test="${( !sessionScope.companyFlag &&  sessionScope.loginRoleId !=3)} ">
					<!-- TAB 1 -->
					<a dojoType="dijit.layout.LinkPane"
					 <c:if test="${tabView == 'tab1'}">selected="true"</c:if>
					 
						href="<s:url action="jsp/providerRegistration/completeProfileActiondoLoad.action"/>"
						
      		>
						<span
						class="tabIcon"
						id="tab1">Complete<br> Profile</span>
					</a>
				</c:if>
					
					<a dojoType="dijit.layout.LinkPane"
					 <c:if test="${tabView == 'tab2'}">selected="true"</c:if>
					 href=
					  
						"<s:url action="jsp/providerRegistration/publicProfileActiondoLoad.action"/>"
						
      		>
						<span
						class="tabIcon"
						id="tab1">Public<br> Profile</span>
					</a>
					
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
