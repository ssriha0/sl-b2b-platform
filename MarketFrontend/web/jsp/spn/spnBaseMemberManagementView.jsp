<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<c:set var="role" value="${roleType}"/>	
<c:set var="workflowTab" scope="page" value="<%=request.getParameter("workflowTab") %>" />	
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
	<title></title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		
<style type="text/css">
	@import "${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra.css";
	@import	"${staticContextPath}/javascript/dojo/dijit/themes/tundra/tundra_rtl.css";
</style>
		
		
		<link rel="stylesheet" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" type="text/css"></link>
		<link rel="stylesheet" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" type="text/css"></link>
		
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/so_monitor.css"/>
		<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/global.css" />
	 
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/slider.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/finance_mgr/main.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/tooltips.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/service_provider_profile.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/registration.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css" />
        <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/admin.css" />
		
		
		
	
<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/hideShow.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
    	djConfig="isDebug: false, parseOnLoad: true"></script>
<script type="text/javascript">
	dojo.require("dijit.layout.ContentPane");
	dojo.require("dijit.layout.TabContainer");
	dojo.require("dojo.parser");
    dojo.require("newco.jsutils");
</script>
		
		


<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/tooltip.js" ></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

		<script language="JavaScript" type="text/javascript">
			newco.jsutils.setGlobalContext('${contextPath}'); 
		</script>	
		
		</head>
<!--[if IE]>
     <link rel="stylesheet" type="text/css" href="${staticContextPath}/css/scrolling_table.ie.css" />
<![endif]-->



<body class="tundra">		
    
	<jsp:include page="/jsp/public/common/omitInclude.jsp" flush="true">
		 <jsp:param name="PageName" value="SPN - Member Management"/>
	</jsp:include>	
    
	<div id="page_margins">
		<div id="page">

			<!-- START HEADER -->
			<div id="header">
				<tiles:insertDefinition name="newco.base.topnav"/>
				<tiles:insertDefinition name="newco.base.blue_nav"/>
				<tiles:insertDefinition name="newco.base.dark_gray_nav"/>
			</div>
	
	<c:set var="tabtitle" value="${title}" />	
	<div id="mainTabContainer" dojoType="dijit.layout.TabContainer"
							   style="height: 600px;" cacheContent="false">
				<div  id="members"
				       title="Members"
					dojoType="dijit.layout.ContentPane"
					href="${contextPath}/spnMemberManager_showView.action?view=members"
					selected="true">
				</div>
				<div  id="applicants"
				       title="Applicants"
					dojoType="dijit.layout.ContentPane"
					href="${contextPath}/spnMemberManager_showView.action?view=applicants"
					selected="false">
							
				</div>
				<div  id="inactive"
				       title="Inactive"
					dojoType="dijit.layout.ContentPane"
					href="${contextPath}/spnMemberManager_showView.action?view=inactive"
					selected="false">	
				</div>
	</div>
    <tiles:insertDefinition name="newco.base.footer"/>
    </body>	
    				 		 


</html>




