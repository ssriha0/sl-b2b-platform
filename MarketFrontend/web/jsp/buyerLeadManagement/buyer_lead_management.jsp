<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%>
<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request"
	value="<%=request.getScheme() + "://"
					+ request.getServerName() + ":" + request.getServerPort()
					+ "/ServiceLiveWebUtil"%>" />
<c:set var="maxComSize" scope="session" value="85" />
<c:set var="currentMenu" scope="request" value="buyerLeadsManagement"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<title>ServiceLive - Buyer Lead Management</title>
			
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/superfish.css" media="screen, projection">
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buttons.css"/>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/public.css"/>
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/javascript/confirm.css"/>	
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/toggle-btn.css"/>	
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/buyerLeadManagementStyle.css" />
			<link rel="stylesheet" type="text/css" href="${staticContextPath}/styles/plugins/ui.datepicker.css"/>
			<link href="//netdna.bootstrapcdn.com/font-awesome/3.1.1/css/font-awesome.min.css" rel="stylesheet" />
			<script type="text/javascript" src="${staticContextPath}/javascript/plugins/css_browser_selector.js"></script>
			<style type="text/css">
				 .ie7 .bannerDiv{margin-left:-1050px;}  
			</style>				
</head>
<script type="text/javascript">
function auxNavLeadTab()
{
$("#auxNav").css("z-index","-200");
}
</script>
<body class="tundra acquity" onload="auxNavLeadTab();">
<div id="page_margins">
			<div id="page">
			 <div id="header">
					<tiles:insertDefinition name="newco.base.topnav" />
					<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
					<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.dataTables.js"></script>
					<script type="text/javascript" src="${staticContextPath}/scripts/plugins/ui.datepicker.js"></script>
					<script  type="text/javascript" src="${staticContextPath}/javascript/buyer_lead_management.js"></script>
		    		<tiles:insertDefinition name="newco.base.blue_nav" />
					<tiles:insertDefinition name="newco.base.dark_gray_nav" />
					
				</div>
			<div id="content">
			 <input type="hidden" value='${numberOfRecords}' id="newInitialLeadCount"></input>
			 <jsp:include page="buyer_lead_management_dashboard.jsp"/>
			 
</div>				</div>
<jsp:include page="/jsp/public/common/defaultFooter.jsp" />
</div>
</body>	

</html>
