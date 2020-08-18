<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s"   uri="/struts-tags" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="contextPath" scope="request" value="<%=request.getContextPath()%>" />
<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html >
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
<title>
	<tiles:getAsString name="title"/>
</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />
<link rel="stylesheet" href="${staticContextPath}/css/dijitTitlePane-serviceLive.css" type="text/css"></link>
<link rel="stylesheet" href="${staticContextPath}/css/dijitTabPane-serviceLive.css" type="text/css"></link>

<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/main.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/iehacks.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/grid.css">
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/dashboard/tooltips.css" />
<link rel="stylesheet" type="text/css" href="${staticContextPath}/css/top-section.css">

<script type="text/javascript"
	src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
	djConfig="isDebug: false, parseOnLoad: true"></script>
	
<script type="text/javascript">
	dojo.require("dijit.TitlePane");
	dojo.require("newco.jsutils");
	dojo.require("newco.servicelive.DashboardRealTimeManager");
		
</script>

<script type="text/javascript" src="${staticContextPath}/javascript/prototype.js"></script>
<script language="JavaScript" src="${staticContextPath}/javascript/tooltip.js" type="text/javascript"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/formfields.js"></script>
<script type="text/javascript" src="${staticContextPath}/javascript/jquery/jquery.min.js"></script>
<script language="JavaScript" type="text/javascript" src="${staticContextPath}/javascript/nav.js"></script>

<script type="text/javascript">
		
		var cb =  function cbFucn (data ) {
				newco.jsutils.updateWithXmlData("current_balance", "current_balance", data);		
				newco.jsutils.updateWithXmlData("accepted", "accepted", data);
				newco.jsutils.updateWithXmlData("available_balance", "available_balance", data);
				newco.jsutils.updateWithXmlData("drafted", "drafted", data);
				newco.jsutils.updateWithXmlData("num_issues", "num_issues", data);
				newco.jsutils.updateWithXmlData("num_technicians_unapproved", "num_technicians_unapproved", data);
				newco.jsutils.updateWithXmlData("posted", "posted", data);
				newco.jsutils.updateWithXmlData("problem", "problem", data);
				newco.jsutils.updateWithXmlData("date_dashboard", "date_dashboard", data);
				newco.jsutils.updateWithXmlData("received", "received", data);
				newco.jsutils.updateWithXmlData("todays", "todays", data);
				newco.jsutils.updateWithXmlData("total_dollars", "total_dollars", data);
				newco.jsutils.updateWithXmlData("total_orders", "total_orders", data);			
		}
		
		newco.jsutils.setGlobalContext('${contextPath}'); 
		
		_commonMgr = new DashboardRealTimeManager('${contextPath}/dashboard/dashboardPeriodicRefresh.action', 10000,
		 cb
		)
		_commonMgr.startService();

</script>



<script type="text/javascript">
var lastSortedBy;
function doSort(sortBy){
	document.getElementById('iframeID').src="/MarketFrontend/sortServiceOrders.action?sortBy="+sortBy+"&lastSortedBy="+lastSortedBy;
	(lastSortedBy == sortBy ? lastSortedBy = null : lastSortedBy = sortBy);
}
</script>	

 <tiles:insertAttribute name="scripts"/>

</head>

	<body class="tundra">
	    
		<div id="page_margins">
			<div id="page">
				<div id="header">
					<tiles:insertAttribute name="logo"/>
					<tiles:insertAttribute name="mainNav"/>											
					<tiles:insertAttribute name="pageHeader"/>					
				</div>
				
				<table border=0 width="100%" height="500">		
					<tr>
						<td valign="top">
					 		 <tiles:insertAttribute name="body"/>	 
						</td>
					</tr>
					<tr>
					
					<!-- 
						<td height="80">
				 		 	<tiles:insertAttribute name="footer"/>		 		 
						</td>
					 -->
					</tr>
				</table>
				<tiles:insertAttribute name="footer"/>	
			</div>
		</div>
	</body>
</html>
