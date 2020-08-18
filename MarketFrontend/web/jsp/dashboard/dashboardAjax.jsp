<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="/struts-tags" prefix="s" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<c:set var="contextPath" scope="request"
	value="<%=request.getContextPath()%>" />

<c:set var="staticContextPath" scope="request" value="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/ServiceLiveWebUtil"%>" />

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type"
			content="text/html; charset=iso-8859-1">
		<title>Dashboard Ajax Test</title><link rel="shortcut icon" href="${staticContextPath}/images/favicon.ico" />

		<script type="text/javascript" src="js/prototype.js"></script>
		<script type="text/javascript"
			src="${staticContextPath}/javascript/dojo/dojo/dojo.js"
			djConfig="debugAtAllCosts: true"></script>

		<script language="JavaScript" type="text/javascript">

	dojo.require("newco.jsutils");
	dojo.require("newco.rthelper");
	var cb =  function cbFucn (data ) {
		newco.jsutils.updateWithXmlData("current_balance", "current_balance", data);		
		newco.jsutils.updateWithXmlData("available_balance", "available_balance", data);
		newco.jsutils.updateWithXmlData("drafted", "drafted", data);
		newco.jsutils.updateWithXmlData("num_issues", "num_issues", data);
		newco.jsutils.updateWithXmlData("num_technicians_unapproved", "num_technicians_unapproved", data);
		newco.jsutils.updateWithXmlData("posted", "posted", data);
		newco.jsutils.updateWithXmlData("problem", "problem", data);
		newco.jsutils.updateWithXmlData("profile_changes", "profile_changes", data);
		newco.jsutils.updateWithXmlData("received", "received", data);
		newco.jsutils.updateWithXmlData("todays", "todays", data);
		newco.jsutils.updateWithXmlData("total_dollars", "total_dollars", data);
		newco.jsutils.updateWithXmlData("total_orders", "total_orders", data);
		
	}
	
	_commonMgr = newco.rthelper.initNewDashboardRTManager('dashboardPeriodicRefresh.action', 3000,
	 cb
	 )
	
	 _commonMgr.addToken(
	 			newco.rthelper.createCommonToken(23,34,55)
	 	);
	
	dojo.addOnLoad(_commonMgr.startService())
	
</script>

	</head>

	<body>
		<h1>
			Ajax Dashboard Test of periodic updates
		</h1>
		<table>
			<tr>
				<td></td>
				<td></td>
			</tr>
		</table>

		<iframe scrolling="auto" src="${contextPath}/jsp/dashboard/dashboardAjaxIframe.jsp" height="100" width="200">
		</iframe>
		<br>

		<button onclick="_commonMgr._stopService();">
			stop service
		</button>
		<button onclick="_commonMgr.startService();">
			start service
		</button>
		<br>
		CurrentBalance: <span id ="current_balance">???</span><br>
		AvailableBalance:<span id ="available_balance">???</span><br>
		Drafted:<span id ="drafted">???</span><br>
		NumIssues:<span id ="num_issues">???</span><br>
		NumTechniciansUnapproved:<span id ="num_technicians_unapproved">???</span><br>
		Posted:<span id ="posted">???</span><br>
		Problem:<span id ="problem">???</span><br>
		ProfileChanges:<span id ="profile_changes">???</span><br>
		Received:<span id ="received">???</span><br>
		Todays:<span id ="todays">???</span><br>
		TotalDollars:<span id ="total_dollars">???</span><br>
		TotalOrders:<span id ="total_orders">???</span><br>
		
	</body>
	
</html>
